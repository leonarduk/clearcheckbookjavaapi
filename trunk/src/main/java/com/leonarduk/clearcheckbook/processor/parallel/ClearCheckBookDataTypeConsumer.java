package com.leonarduk.clearcheckbook.processor.parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.calls.BulkProcessable;
import com.leonarduk.clearcheckbook.dto.AbstractDataType;

public class ClearCheckBookDataTypeConsumer<T extends AbstractDataType<?>>
		implements Runnable {

	private static final Logger _logger = Logger
			.getLogger(ClearCheckBookDataTypeConsumer.class);
	// Atomic integer containing the next thread ID to be assigned
	private static final AtomicInteger nextId = new AtomicInteger(0);
	// Thread local variable containing each thread's ID
	private final Integer threadId = nextId.getAndIncrement();

	private BlockingQueue<T> queue;
	private BulkProcessable<T> call;
	private List<String> returnStatusList;

	public ClearCheckBookDataTypeConsumer(BlockingQueue<T> queue,
			BulkProcessable<T> call, List<String> returnStatusList) {
		_logger.debug("Starting consumer " + getId());
		this.queue = queue;
		this.call = call;
		this.returnStatusList = returnStatusList;
	}

	// Returns the current thread's unique ID, assigning it if necessary
	public int getId() {
		return threadId;
	}

	@Override
	public void run() {
		while (true) {
			try {
				T msg = queue.take();
				String status = this.call.process(msg);
				_logger.debug(status + " from " + msg);
				this.returnStatusList.add(status);
			} catch (InterruptedException e) {
				_logger.debug("Interupt called");
			} catch (ClearcheckbookException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
