package com.opentext.bn.content.parser.entity;

import lombok.Data;

import java.util.Date;


@Data
public class LegacyEventVO {

	private String transactionId = null;

	private String processId = null;

	private String taskId = null;

	private String introspectionSource = null;

	private PayloadRefVO xifInfo = null;

	private int errorCount = 0;

	private int inputEdiFileCount = 0;

	private int inputInterchangeCount = 0;

	private int inputEdiDocumentCount = 0;

	private int inputUdfFileCount = 0;

	private int inputUdfDocumentCount = 0;

	private String introspectionSessionNumber = null;

	private int outputEdiFileCount = 0;

	private int outputInterchangeCount = 0;

	private int outputEdiDocumentCount = 0;

	private int outputUdfFileCount = 0;

	private int outputUdfDocumentCount = 0;

	private int appFileCount = 0;

	private int comFileCount = 0;

	private int bypassFileCount = 0;

	private int rejectedFileCount = 0;
}
