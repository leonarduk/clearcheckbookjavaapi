package com.leonarduk.clearcheckbook.calls;

import java.util.List;

import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.dto.AbstractDataType;

public interface BulkProcessable<T extends AbstractDataType<?>> {
	/**
	 * See {@link AbstractCall#bulkProcess(List)}
	 * 
	 * @param dataTypeList
	 * @return
	 * @throws ClearcheckbookException
	 */
	public List<String> bulkProcess(List<T> dataTypeList)
			throws ClearcheckbookException;

	/**
	 * See {@link AbstractCall#process(AbstractDataType)}
	 * 
	 * @param dataType
	 * @return
	 * @throws ClearcheckbookException
	 */
	public String process(T dataType) throws ClearcheckbookException;

}
