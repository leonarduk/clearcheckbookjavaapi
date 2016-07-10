/**
 * ClearCheckBookDataTypeParallelProcessor
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.clearcheckbook.processor.parallel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.calls.BulkProcessable;
import com.leonarduk.clearcheckbook.dto.AbstractDataType;
import com.leonarduk.clearcheckbook.processor.ClearCheckBookTaskProcessor;

/**
 * This co-ordinates the splitting of a set of requests to the Clearcheckbook website into
 * individual messages and farming to consumers.
 *
 * @author Stephen Leonard
 * @param <T>
 *            the generic type
 * @since 12 Feb 2014
 */
public class ClearCheckBookDataTypeParallelProcessor<T extends AbstractDataType<?>>
        implements ClearCheckBookTaskProcessor<T> {

	/** The Constant _logger. */
	private static final Logger _logger = Logger
	        .getLogger(ClearCheckBookDataTypeParallelProcessor.class);

	/** The call. */
	private final BulkProcessable<T> call;

	/** The queue size. */
	private final int queueSize;

	/** The number of consumers. */
	private final int numberOfConsumers;

	/**
	 * Instantiates a new clear check book data type parallel processor.
	 *
	 * @param call
	 *            the call
	 * @param queueSize
	 *            the queue size
	 * @param numberOfConsumers
	 *            the number of consumers
	 */
	public ClearCheckBookDataTypeParallelProcessor(final BulkProcessable<T> call,
	        final int queueSize, final int numberOfConsumers) {
		this.call = call;
		this.queueSize = queueSize;
		this.numberOfConsumers = numberOfConsumers;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.leonarduk.clearcheckbook.processor.ClearCheckBookTaskProcessor#processQueue(java.util.
	 * List)
	 */
	@Override
	public List<String> processQueue(final List<T> dataTypeList) throws ClearcheckbookException {
		ClearCheckBookDataTypeParallelProcessor._logger.info(
		        "Starting processor for " + dataTypeList.size() + " data items with queue size of "
		                + this.queueSize + " and " + this.numberOfConsumers + " Consumers");
		final BlockingQueue<T> queue = new ArrayBlockingQueue<>(this.queueSize);

		ClearCheckBookDataTypeParallelProcessor._logger
		        .debug("starting producer to produce messages in queue");
		final ClearCheckBookDataTypeProducer<T> producer = new ClearCheckBookDataTypeProducer<>(
		        queue, dataTypeList);
		final Thread producerThread = new Thread(producer, producer.getClass().getName());
		producerThread.start();

		final ThreadPoolExecutor executor = new ThreadPoolExecutor(this.numberOfConsumers,
		        this.numberOfConsumers, 0L, TimeUnit.MILLISECONDS,
		        new LinkedBlockingQueue<Runnable>());

		final MonitorThread monitor = new MonitorThread(executor, 3);
		final Thread monitorThread = new Thread(monitor);
		monitorThread.start();
		final List<String> returnStatusList = Collections
		        .synchronizedList(new ArrayList<String>(this.numberOfConsumers));
		final List<ClearCheckBookDataTypeConsumer<T>> workers = new ArrayList<>(
		        this.numberOfConsumers);
		for (int i = 0; i < this.numberOfConsumers; i++) {
			final ClearCheckBookDataTypeConsumer<T> worker = new ClearCheckBookDataTypeConsumer<>(
			        queue, this.call, returnStatusList);
			executor.execute(worker);
			workers.add(worker);
		}

		ClearCheckBookDataTypeParallelProcessor._logger.debug("Waiting for producer to complete");
		try {
			producerThread.join();
		}
		catch (final InterruptedException e) {
			e.printStackTrace();
		}

		ClearCheckBookDataTypeParallelProcessor._logger
		        .info("There are " + queue.size() + " still to process");
		while (returnStatusList.size() < dataTypeList.size()) {
			try {
				Thread.sleep(500);
			}
			catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
		assert queue.size() == 0;

		ClearCheckBookDataTypeParallelProcessor._logger
		        .info("There are " + queue.size() + " still to process");
		ClearCheckBookDataTypeParallelProcessor._logger.debug("Killing consumers");
		executor.shutdown();
		monitor.shutdown();
		ClearCheckBookDataTypeParallelProcessor._logger.info("Processor finished");
		return returnStatusList;
	}
}
