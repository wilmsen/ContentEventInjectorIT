package com.opentext.bn.content.parser.entity;

import lombok.Data;

/**
 * ContentErrorEvent
 */

@Data
public class ContentErrorEventVO {

	private String errorId = null;

	private String transactionId = null;

	private String processId = null;

	private String taskId = null;

	private String errorLevel = null;

	private String contentFileId = null;

	private String envelopeId = null;

	private String documentId = null;

	private int offset = 0;

	private int position = 0;

	private ErrorInfoVO errorInfo = null;

}
