package com.leonarduk.clearcheckbook.processor;

import java.util.List;

import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.calls.BulkProcessable;
import com.leonarduk.clearcheckbook.dto.AbstractDataType;

public class ClearCheckBookTaskSerialProcessor<T extends AbstractDataType<?>>
		implements ClearCheckBookTaskProcessor<T> {

	private BulkProcessable<T> call;

	public ClearCheckBookTaskSerialProcessor(BulkProcessable<T> call) {
		this.call = call;
	}

	@Override
	public List<String> processQueue(List<T> dataTypeList)
			throws ClearcheckbookException {
		return this.call.bulkProcess(dataTypeList);
	}

}
