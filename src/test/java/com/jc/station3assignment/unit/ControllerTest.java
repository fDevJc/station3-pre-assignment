package com.jc.station3assignment.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jc.station3assignment.authentication.application.AuthService;
import com.jc.station3assignment.authentication.presentation.AuthController;
import com.jc.station3assignment.room.application.RoomService;
import com.jc.station3assignment.room.presentation.RoomController;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@AutoConfigureRestDocs
@WebMvcTest({RoomController.class, AuthController.class})
public abstract class ControllerTest {

	@Autowired
	private WebApplicationContext ctx;
	@Autowired
	private RestDocumentationContextProvider contextProvider;

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	@MockBean
	protected RoomService roomService;

	@MockBean
	protected AuthService authService;

	@BeforeEach
	void init() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
			.apply(MockMvcRestDocumentation.documentationConfiguration(contextProvider))
			.addFilters(new CharacterEncodingFilter("UTF-8", true))
			.build();
	}
}
