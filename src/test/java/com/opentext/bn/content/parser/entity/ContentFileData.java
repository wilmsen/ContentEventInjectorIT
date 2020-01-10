package com.opentext.bn.content.parser.entity;

import com.opentext.bn.converters.avro.entity.IntrospectionType;

import lombok.Data;

@Data
public class ContentFileData {
	private String parentFileIds;
	private String creatingProcess;
	private String storage;
	private String sender;
	private String receiver;
	private String docType;
	private String snrf;
	private String dataType;
	private String primaryKeyValue;
	private long size;
	private String attachmentFileSetId;
	private String direction;
	private String fileType;
	private String translationSessionNumber;
	private String translationInstance;
	private String fileId;
	private String senderQual = null;
	private String receiverQual = null;
	private String senderAddr = null;
	private String receiverAddr = null;
	private IntrospectionType introspectionType;
}