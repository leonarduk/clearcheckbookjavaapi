/**
 * ClearCheckBookTaskProcessor
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.clearcheckbook.processor;

import java.util.List;

import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.dto.AbstractDataType;

/**
 * The Interface ClearCheckBookTaskProcessor.
 *
 * @param <T>
 *            the generic type
 */
public interface ClearCheckBookTaskProcessor<T extends AbstractDataType<?>> {

	/**
	 * Process queue.
	 *
	 * @param dataTypeList
	 *            the data type list
	 * @return the list
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	List<String> processQueue(List<T> dataTypeList) throws ClearcheckbookException;

}
