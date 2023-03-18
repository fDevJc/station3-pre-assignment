package com.jc.station3assignment.config.search;

import java.lang.reflect.InvocationTargetException;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface QueryConvertor {
	<T> T convert(String query, Class<T> classType) throws JsonProcessingException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
}
