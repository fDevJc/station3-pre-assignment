package com.jc.station3assignment.config.search.converter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.jc.station3assignment.config.search.QueryConvertor;
import com.jc.station3assignment.room.application.dto.request.SearchRoomDto;

class DefaultConverterTest {
	public static class Sample {
		private String name;
		private String foo;

		public String getName() {
			return name;
		}

		public String getFoo() {
			return foo;
		}
	}

	@Test
	void 쿼리를_객체로_변환해준다1() throws Exception {
		//given
		String json = "\"name\":\"yang\", \"foo\":\"fooo\"";
		QueryConvertor queryConvertor = new DefaultConverter();

		//when
		Sample ret = queryConvertor.convert(json, Sample.class);

		//then
		Assertions.assertThat(ret.getName()).isEqualTo("yang");
	}

	@Test
	void 쿼리를_객체로_변환해준다2() throws Exception {
		//given
		String json = "\"roomType\":[\"ONE_ROOM\"],\"dealType\":[\"LONG_TERM_RENT\"],\"depositRange\": [0,10000],\"rentRange\": [0,100]";
		QueryConvertor queryConvertor = new DefaultConverter();

		//when
		SearchRoomDto ret = queryConvertor.convert(json, SearchRoomDto.class);

		//then
		Assertions.assertThat(ret.getRoomType().get(0)).isEqualTo("ONE_ROOM");
	}
}