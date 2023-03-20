package com.jc.station3assignment.unit.config.search.extractor;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.jc.station3assignment.config.search.QueryExtractor;
import com.jc.station3assignment.config.search.extractor.DefaultQueryExtractor;

class DefaultQueryExtractorTest {
	private QueryExtractor queryExtractor = new DefaultQueryExtractor();

	@Test
	void 쿼리스트링에서_쿼리를_추출한다() throws Exception {
		//given
		String queryString = "q=\"roomType\":[\"ONE_ROOM\"],\"dealType\":[\"LONG_TERM_RENT\"],\"depositRange\": [0,10000],\"rentRange\": [0,100]&other=123";

		//when
		String ret = queryExtractor.extract(queryString);

		//then
		System.out.println("ret = " + ret);
		Assertions.assertThat(ret).isEqualTo("\"roomType\":[\"ONE_ROOM\"],\"dealType\":[\"LONG_TERM_RENT\"],\"depositRange\": [0,10000],\"rentRange\": [0,100]");

	}
}