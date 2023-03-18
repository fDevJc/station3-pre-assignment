package com.jc.station3assignment.common.docs;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;

public interface RestDocsUtils {
	static OperationRequestPreprocessor getRestDocRequest() {
		return preprocessRequest(prettyPrint());
	}

	static OperationResponsePreprocessor getRestDocResponse() {
		return preprocessResponse(prettyPrint());
	}

}
