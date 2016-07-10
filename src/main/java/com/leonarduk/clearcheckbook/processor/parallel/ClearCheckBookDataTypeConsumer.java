/**
 * ClearCheckBookDataTypeConsumer
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.clearcheckbook.processor.parallel;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.calls.BulkProcessable;
import com.leonarduk.clearcheckbook.dto.AbstractDataType;

/**
 * The Class ClearCheckBookDataTypeConsumer.
 *
 * @param <T>
 *            the generic type
 */
public class ClearCheckBookDataTypeConsumer<T extends AbstractDataType<?>> implements Runnable {

	/** The Constant _logger. */
	private static final Logger _logger = Logger.getLogger(ClearCheckBookDataTypeConsumer.class);

	/** The Constant nextId. */
	// Atomic integer containing the next thread ID to be assigned
	private static final AtomicInteger nextId = new AtomicInteger(0);

	/** The thread id. */
	// Thread local variable containing each thread's ID
	private final Integer threadId = ClearCheckBookDataTypeConsumer.nextId.getAndIncrement();

	/** The queue. */
	private final BlockingQueue<T> queue;

	/** The call. */
	private final BulkProcessable<T> call;

	/** The return status list. */
	private final List<String> returnStatusList;

	/**
	 * Instantiates a new clear check book data type consumer.
	 *
	 * @param queue
	 *            the queue
	 * @param call
	 *            the call
	 * @param returnStatusList
	 *            the return status list
	 */
	public ClearCheckBookDataTypeConsumer(final BlockingQueue<T> queue,
	        final BulkProcessable<T> call, final List<String> returnStatusList) {
		ClearCheckBookDataTypeConsumer._logger.debug("Starting consumer " + this.getId());
		this.queue = queue;
		this.call = call;
		this.returnStatusList = returnStatusList;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	// Returns the current thread's unique ID, assigning it if necessary
	public int getId() {
		return this.threadId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (true) {
			try {
				final T msg = this.queue.take();
				final String status = this.call.process(msg);
				ClearCheckBookDataTypeConsumer._logger.debug(status + " from " + msg);
				this.returnStatusList.add(status);
			}
			catch (final InterruptedException e) {
				ClearCheckBookDataTypeConsumer._logger.debug("Interupt called");
			}
			catch (final ClearcheckbookException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
