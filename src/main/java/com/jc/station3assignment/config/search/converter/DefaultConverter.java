package com.jc.station3assignment.config.search.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jc.station3assignment.config.search.QueryConvertor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultConverter implements QueryConvertor {
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public <T> T convert(String query, Class<T> classType) throws JsonProcessingException {
		String json = "{" + query + "}";
		log.info("Json String :{}", json);
		return objectMapper.readValue(json, classType);
	}
}
