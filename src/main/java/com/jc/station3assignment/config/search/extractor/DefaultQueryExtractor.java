package com.jc.station3assignment.config.search.extractor;

import java.util.Arrays;

import com.jc.station3assignment.config.search.QueryExtractor;

public class DefaultQueryExtractor implements QueryExtractor {
	private static final String QUERY = "q";
	private static final String SPLIT_REGEX = "&";
	private static final int QUERY_START_INDEX = 2;

	@Override
	public String extract(String queryString) {
		return Arrays.stream(queryString.split(SPLIT_REGEX))
			.filter(param -> param.startsWith(QUERY))
			.findAny()
			.get()
			.substring(QUERY_START_INDEX);
	}
}
