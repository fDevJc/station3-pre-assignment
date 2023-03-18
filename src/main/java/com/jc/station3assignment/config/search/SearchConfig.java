package com.jc.station3assignment.config.search;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jc.station3assignment.config.search.converter.DefaultConverter;
import com.jc.station3assignment.config.search.extractor.DefaultQueryExtractor;
import com.jc.station3assignment.config.search.resolver.SearchConditionArgumentResolver;

@Configuration
public class SearchConfig implements WebMvcConfigurer {
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new SearchConditionArgumentResolver(queryExtractor(), queryConvertor()));
	}

	private QueryExtractor queryExtractor() {
		return new DefaultQueryExtractor();
	}

	private QueryConvertor queryConvertor() {
		return new DefaultConverter();
	}
}
