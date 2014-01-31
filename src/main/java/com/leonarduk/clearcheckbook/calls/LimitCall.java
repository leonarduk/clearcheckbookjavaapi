package com.leonarduk.clearcheckbook.calls;

import java.util.List;

import org.apache.log4j.Logger;

import com.leonarduk.clearcheckbook.ClearCheckBookConnection;
import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.dto.LimitDataType;
import com.leonarduk.clearcheckbook.dto.ParsedNameValuePair;

/**
 * 
 * 
 * 
 * @author Stephen Leonard
 * @since 30 Jan 2014
 * 
 * @version $Author:: $: Author of last commit
 * @version $Rev:: $: Revision of last commit
 * @version $Date:: $: Date of last commit
 * 
 */
public class LimitCall extends AbstractCall<LimitDataType> {

	private static final Logger _logger = Logger.getLogger(LimitCall.class);

	public static final String TYPE = "limit";

	public LimitCall(ClearCheckBookConnection connection) {
		super(TYPE, connection);
	}

	@Override
	public List<LimitDataType> getAll() throws ClearcheckbookException {
		return super.getAll();
	}

	@Override
	public LimitDataType get(ParsedNameValuePair id)
			throws ClearcheckbookException {
		LimitDataType limitDataType = super.get(id);
		_logger.debug(limitDataType);
		return limitDataType;
	}

	@Override
	public String insert(LimitDataType input) throws ClearcheckbookException {
		String insert = super.insert(input);
		_logger.debug(insert);
		return insert;
	}

	@Override
	public boolean edit(LimitDataType input) throws ClearcheckbookException {
		boolean edit = super.edit(input);
		_logger.debug(edit);
		return edit;
	}

	@Override
	public boolean delete(ParsedNameValuePair input)
			throws ClearcheckbookException {
		boolean delete = super.delete(input);
		_logger.debug(delete);
		return delete;
	}

}
