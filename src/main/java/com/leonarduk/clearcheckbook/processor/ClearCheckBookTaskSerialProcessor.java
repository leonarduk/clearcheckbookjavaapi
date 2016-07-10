/**
 * ClearCheckBookTaskSerialProcessor
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.clearcheckbook.processor;

import java.util.List;

import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.calls.BulkProcessable;
import com.leonarduk.clearcheckbook.dto.AbstractDataType;

/**
 * The Class ClearCheckBookTaskSerialProcessor.
 *
 * @param <T>
 *            the generic type
 */
public class ClearCheckBookTaskSerialProcessor<T extends AbstractDataType<?>>
        implements ClearCheckBookTaskProcessor<T> {

	/** The call. */
	private final BulkProcessable<T> call;

	/**
	 * Instantiates a new clear check book task serial processor.
	 *
	 * @param call
	 *            the call
	 */
	public ClearCheckBookTaskSerialProcessor(final BulkProcessable<T> call) {
		this.call = call;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.leonarduk.clearcheckbook.processor.ClearCheckBookTaskProcessor#processQueue(java.util.
	 * List)
	 */
	@Override
	public List<String> processQueue(final List<T> dataTypeList) throws ClearcheckbookException {
		return this.call.bulkProcess(dataTypeList);
	}

}
