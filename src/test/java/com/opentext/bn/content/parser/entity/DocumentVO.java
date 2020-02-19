package com.opentext.bn.content.parser.entity;


import java.util.List;

import lombok.Data;

@Data
public class DocumentVO {

	private String introspectionSource = null;

	private String transactionId = null;
	
	private long eventTimestamp = 0;
	private java.lang.String eventId;

	private String processId = null;

	private String taskId = null;

	private String documentId = null;

	private String containingParentType = null;

	private String containingParentLevel = null;

	private String containingParentId = null;

	private String senderAddress = null;

	private String receiverAddress = null;

	private String controlNumber = null;

	private String documentType = null;

	private String documentStandard = null;

	private String documentStandardVersion = null;

	private String controlNumberEnvelopeLevel1 = null;

	private String controlNumberEnvelopeLevel2 = null;

	private String envelopeVersion = null;

	private String sentDate = null;

	private String sentTime = null;
	
	private ContentKeys contentKeys = null;
	
	 private com.opentext.bn.converters.avro.entity.IntrospectionType introspectionType;
	 
	 private java.lang.String businessDocumentId= null;
	
	private String senderQual = null;
	private String receiverQual = null;
	private String senderAddr = null;
	private String receiverAddr = null;
	private PayloadRefVO xifInfo = null;
	
	private String fileType = null;
	private String senderAddressEnvelopeLevel1;
	private String receiverAddressEnvelopeLevel1;
	private String functionalCodeEnvelopeLevel1;
	private String senderAddressEnvelopeLevel2;
	private String receiverAddressEnvelopeLevel2;
	private String functionalCodeEnvelopeLevel2;
	
}