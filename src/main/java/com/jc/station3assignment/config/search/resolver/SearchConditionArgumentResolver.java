package com.jc.station3assignment.config.search.resolver;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.jc.station3assignment.config.search.QueryConvertor;
import com.jc.station3assignment.config.search.QueryExtractor;
import com.jc.station3assignment.config.search.annotation.SearchCondition;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SearchConditionArgumentResolver implements HandlerMethodArgumentResolver {
	private final QueryExtractor queryExtractor;
	private final QueryConvertor queryConvertor;

	public SearchConditionArgumentResolver(QueryExtractor queryExtractor, QueryConvertor queryConvertor) {
		this.queryExtractor = queryExtractor;
		this.queryConvertor = queryConvertor;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(SearchCondition.class) != null;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		String queryString = request.getQueryString();
		log.info("HTTP Request QueryString: {}", queryString);

		if (queryString == null) {
			return parameter.getParameterType().getConstructor().newInstance();
		}
		String decodeQueryString = URLDecoder.decode(queryString, StandardCharsets.UTF_8);
		log.info("Decoded QueryString: {}", decodeQueryString);

		String query = queryExtractor.extract(decodeQueryString);
		log.info("Extracted Query: {}", query);

		return queryConvertor.convert(query, parameter.getParameterType());
	}
}
