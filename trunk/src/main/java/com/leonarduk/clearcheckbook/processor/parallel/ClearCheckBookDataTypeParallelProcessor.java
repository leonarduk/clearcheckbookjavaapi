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
 * This co-ordinates the splitting of a set of requests to the Clearcheckbook
 * website into individual messages and farming to consumers
 * 
 * @author Stephen Leonard
 * @since 12 Feb 2014
 * 
 * @version $Author:: $: Author of last commit
 * @version $Rev:: $: Revision of last commit
 * @version $Date:: $: Date of last commit
 * 
 */
public class ClearCheckBookDataTypeParallelProcessor<T extends AbstractDataType<?>>
		implements ClearCheckBookTaskProcessor<T> {

	private static final Logger _logger = Logger
			.getLogger(ClearCheckBookDataTypeParallelProcessor.class);
	private BulkProcessable<T> call;
	private int queueSize;
	private int numberOfConsumers;

	public ClearCheckBookDataTypeParallelProcessor(BulkProcessable<T> call,
			int queueSize, int numberOfConsumers) {
		this.call = call;
		this.queueSize = queueSize;
		this.numberOfConsumers = numberOfConsumers;
	}

	@Override
	public List<String> processQueue(List<T> dataTypeList)
			throws ClearcheckbookException {
		_logger.info("Starting processor for " + dataTypeList.size()
				+ " data items with queue size of " + this.queueSize + " and "
				+ this.numberOfConsumers + " Consumers");
		BlockingQueue<T> queue = new ArrayBlockingQueue<>(this.queueSize);

		_logger.debug("starting producer to produce messages in queue");
		ClearCheckBookDataTypeProducer<T> producer = new ClearCheckBookDataTypeProducer<>(
				queue, dataTypeList);
		Thread producerThread = new Thread(producer, producer.getClass()
				.getName());
		producerThread.start();

		ThreadPoolExecutor executor = new ThreadPoolExecutor(numberOfConsumers,
				numberOfConsumers, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());

		MonitorThread monitor = new MonitorThread(executor, 3);
		Thread monitorThread = new Thread(monitor);
		monitorThread.start();
		List<String> returnStatusList = Collections
				.synchronizedList(new ArrayList<String>(numberOfConsumers));
		List<ClearCheckBookDataTypeConsumer<T>> workers = new ArrayList<>(
				numberOfConsumers);
		for (int i = 0; i < numberOfConsumers; i++) {
			ClearCheckBookDataTypeConsumer<T> worker = new ClearCheckBookDataTypeConsumer<T>(
					queue, call, returnStatusList);
			executor.execute(worker);
			workers.add(worker);
		}

		_logger.debug("Waiting for producer to complete");
		try {
			producerThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		_logger.info("There are " + queue.size() + " still to process");
		while (returnStatusList.size() < dataTypeList.size()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		assert queue.size() == 0;

		_logger.info("There are " + queue.size() + " still to process");
		_logger.debug("Killing consumers");
		executor.shutdown();
		monitor.shutdown();
		_logger.info("Processor finished");
		return returnStatusList;
	}
}
