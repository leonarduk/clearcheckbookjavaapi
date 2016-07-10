/**
 * ClearCheckBookDataTypeProducer
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.clearcheckbook.processor.parallel;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import com.leonarduk.clearcheckbook.dto.AbstractDataType;

/**
 * The Class ClearCheckBookDataTypeProducer.
 *
 * @param <T>
 *            the generic type
 */
public class ClearCheckBookDataTypeProducer<T extends AbstractDataType<?>> implements Runnable {

	/** The queue. */
	private final BlockingQueue<T> queue;

	/** The data type list. */
	private final List<T> dataTypeList;

	/**
	 * Instantiates a new clear check book data type producer.
	 *
	 * @param queue
	 *            the queue
	 * @param dataTypeList
	 *            the data type list
	 */
	public ClearCheckBookDataTypeProducer(final BlockingQueue<T> queue,
	        final List<T> dataTypeList) {
		this.queue = queue;
		this.dataTypeList = dataTypeList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			for (T dataType : this.dataTypeList) {
				this.queue.put(dataType);
			}
		}
		catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}
}
