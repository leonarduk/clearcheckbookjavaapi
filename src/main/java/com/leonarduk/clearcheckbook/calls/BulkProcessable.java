/**
 * BulkProcessable
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.clearcheckbook.calls;

import java.util.List;

import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.dto.AbstractDataType;

/**
 * The Interface BulkProcessable.
 *
 * @param <T>
 *            the generic type
 */
public interface BulkProcessable<T extends AbstractDataType<?>> {

	/**
	 * See {@link AbstractCall#bulkProcess(List)}.
	 *
	 * @param dataTypeList
	 *            the data type list
	 * @return the list
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public List<String> bulkProcess(List<T> dataTypeList) throws ClearcheckbookException;

	/**
	 * See {@link AbstractCall#process(AbstractDataType)}.
	 *
	 * @param dataType
	 *            the data type
	 * @return the string
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public String process(T dataType) throws ClearcheckbookException;

}
