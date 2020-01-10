package com.opentext.bn.content.parser.entity;

import lombok.Data;

/**
 * ErrorInfo
 */
@Data
public class ErrorInfoVO {

	private String errorCode = null;
	private String externalErrorMessage = null;
	private String internalErrorMessage = null;
	private boolean isPayloadRelated = false;

}