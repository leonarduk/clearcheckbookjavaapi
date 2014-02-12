package com.leonarduk.clearcheckbook.processor;

import java.util.List;

import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.dto.AbstractDataType;

public interface ClearCheckBookTaskProcessor<T extends AbstractDataType<?>> {

	List<String> processQueue(List<T> dataTypeList)
			throws ClearcheckbookException;

}