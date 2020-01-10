package com.opentext.bn.content.parser.entity;

import java.util.ArrayList;

import lombok.Data;

@Data
public class EnvelopeVO {

	private String introspectionSource = null;

	private String transactionId = null;

	private String processId = null;

	private String taskId = null;
	
	private String envelopeLevel = null;
	
	private long eventTimestamp = 0;
	private java.lang.String eventId;
	 private com.opentext.bn.converters.avro.entity.IntrospectionType introspectionType;

	private String envelopeId = null;

	private String containingParentType = null;

	private String containingParentLevel = null;

	private String containingParentId = null;

	private String senderAddress = null;

	private String receiverAddress = null;

	private String documentType = null;

	private String controlNumber = null;

	private String envelopeType = null;

	private String envelopeVersion = null;

	private int directDocumentCount = 0;

	private int directEnvelopeCount = 0;

	private String sentDate = null;

	private String sentTime = null;

	private String segmentTerminator = null;

	private String productionTestFlag = null;

	private String documentFunctionalClassification = null;

	private ContentKeys contentKeys = null;
	
	private String senderQual = null;

	private String receiverQual = null;
	
	private String senderAddr = null;

	private String receiverAddr = null;
	
	private PayloadRefVO xifInfo = null;
	
	private String fileType = null;

}
