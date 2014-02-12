package com.leonarduk.clearcheckbook.processor.parallel;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import com.leonarduk.clearcheckbook.dto.AbstractDataType;

public class ClearCheckBookDataTypeProducer<T extends AbstractDataType<?>>
		implements Runnable {
	private BlockingQueue<T> queue;
	private List<T> dataTypeList;

	public ClearCheckBookDataTypeProducer(BlockingQueue<T> queue,
			List<T> dataTypeList) {
		this.queue = queue;
		this.dataTypeList = dataTypeList;
	}

	@Override
	public void run() {
		try {
			for (Iterator<T> iterator = this.dataTypeList.iterator(); iterator
					.hasNext();) {
				T dataType = iterator.next();
				queue.put(dataType);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
