package com.jc.station3assignment.config.search.converter;

import java.lang.reflect.InvocationTargetException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jc.station3assignment.config.search.QueryConvertor;

public class DefaultConverter implements QueryConvertor {
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public <T> T convert(String query, Class<T> classType) throws JsonProcessingException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		return objectMapper.readValue("{" + query + "}", classType);
	}
}
