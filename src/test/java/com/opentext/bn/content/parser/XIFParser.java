package com.opentext.bn.content.parser;

import static java.util.stream.Collectors.toList;

import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.management.InvalidApplicationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.opentext.bn.content.TestHelper;
import com.opentext.bn.content.parser.XIFConstants.EnvelopeLevel;
import com.opentext.bn.content.parser.XIFConstants.XifAttributeFile;
import com.opentext.bn.content.parser.XIFConstants.XifAttributeGroup;
import com.opentext.bn.content.parser.XIFConstants.XifAttributeLabel;
import com.opentext.bn.content.parser.XIFConstants.XifAttributeLabelFunctionCodeValue;
import com.opentext.bn.content.parser.XIFConstants.XifAttributeMessage;
import com.opentext.bn.content.parser.XIFConstants.XifAttributeSession;
import com.opentext.bn.content.parser.XIFConstants.XifAttributeSet;
import com.opentext.bn.content.parser.XIFConstants.XifAttributeStandard;
import com.opentext.bn.content.parser.XIFConstants.XifModelServer;
import com.opentext.bn.content.parser.entity.AssetKeys;
import com.opentext.bn.content.parser.entity.AssetVO;
import com.opentext.bn.content.parser.entity.ContentErrorEventVO;
import com.opentext.bn.content.parser.entity.ContentFileData;
import com.opentext.bn.content.parser.entity.ContentKeyVO;
import com.opentext.bn.content.parser.entity.ContentKeys;
import com.opentext.bn.content.parser.entity.DocumentVO;
import com.opentext.bn.content.parser.entity.EnvelopeVO;
import com.opentext.bn.content.parser.entity.ErrorInfoVO;
import com.opentext.bn.content.parser.entity.FileIntrospectedEventVO;
import com.opentext.bn.content.parser.entity.LegacyEventVO;
import com.opentext.bn.content.parser.entity.PayloadRefVO;
import com.opentext.bn.converters.avro.entity.Asset;
import com.opentext.bn.converters.avro.entity.ContentErrorEvent;
import com.opentext.bn.converters.avro.entity.ContentKey;
import com.opentext.bn.converters.avro.entity.DocumentEvent;
import com.opentext.bn.converters.avro.entity.EnvelopeEvent;
import com.opentext.bn.converters.avro.entity.ErrorInfo;
import com.opentext.bn.converters.avro.entity.FileIntrospectedEvent;
import com.opentext.bn.converters.avro.entity.IntrospectionType;
import com.opentext.bn.converters.avro.entity.PayloadRef;
import com.opentext.bn.converters.avro.entity.XIFIntrospectedEvent;

@Service
public class XIFParser extends XIFConstants {

	public void buildEvents(LegacyEventVO legacyVO, String xifXml, FileIntrospectedEventVO fileEvent, List<EnvelopeVO> saveEnvelopeList,
			List<DocumentVO> saveDocumentList, List<ContentErrorEventVO> saveContentErrorList, List<ContentFileData> saveContentFileList) throws Throwable {			

		try {
			parseXIFContent(legacyVO, xifXml, fileEvent, saveEnvelopeList, saveDocumentList,
					saveContentErrorList, saveContentFileList);

			buildFileEvent(fileEvent, saveEnvelopeList, saveDocumentList, saveContentErrorList, saveContentFileList);

		} catch (Throwable t) {

			throw t;
		}

	}

	private void parseXIFContent(LegacyEventVO xifEvent, String xmlFile, 
			FileIntrospectedEventVO fileEvent, List<EnvelopeVO> saveEnvelopeList, List<DocumentVO> saveDocumentList,
			List<ContentErrorEventVO> saveContentErrorList, List<ContentFileData> saveContentFileList)
					throws XMLStreamException, ParseException, InvalidApplicationException {

		StringBuilder xmlPathBuilder = null;
		xmlPathBuilder = new StringBuilder();

		LinkedHashMap<String, String> sessionAttributes = new LinkedHashMap<String, String>();
		// Create XML Reader
		XMLEventReader xer = null;

		LinkedHashMap<String, EnvelopeVO> envelopes = new LinkedHashMap<String, EnvelopeVO>();

		List<DocumentVO> documents = new ArrayList<DocumentVO>();
		ContentKeys contentKeys = null;
		AssetKeys keys = new AssetKeys();
		List<ContentErrorEventVO> contentErrors = null;

		ContentFileData contentFileData = null;
		DocumentVO documentData = null;
		ContentKeyVO contentKeyVO = null;
		ContentErrorEventVO contentErrorData = null;

		int appFileCount = 0;
		int bypassFileCount = 0;
		int comFileCount = 0;
		String xmlPath = null;

		xer = XMLInputFactory.newInstance().createXMLEventReader(new StringReader(xmlFile));

		// Loop through all the nodes
		while (xer.hasNext()) {
			XMLEvent staxEvent = xer.nextEvent();

			// Ignore White spaces
			if (staxEvent.isCharacters() && ((Characters) staxEvent).isWhiteSpace()) {
				continue;
			}

			// Construct BUID Maps to be looked up for later
			if (staxEvent.getEventType() == XMLEvent.START_ELEMENT) {

				String characters = null;
				String elementName = staxEvent.asStartElement().getName().getLocalPart().trim();

				//log.debug("elementName : " + elementName);
				appendElementToXmlPathBuilder(elementName, xmlPathBuilder);

				xmlPath = xmlPathBuilder.toString();

				Iterator<?> it = staxEvent.asStartElement().getAttributes();
				LinkedHashMap<String, String> attributes = new LinkedHashMap<String, String>();

				while (it.hasNext()) {
					Attribute attribute = (Attribute) it.next();
					String name = attribute.getName().getLocalPart().trim();
					String value = attribute.getValue().trim();
					attributes.put(name, value);
					//					if (log.isDebugEnabled()) {
					//						log.debug("\t" + name + "[" + value + "]");
					//					}

				}

				if (XIF_ELEMENT_SESSION.equals(xmlPath)) { // /AIRecognitionTranslationStatus/Session;
					sessionAttributes = attributes;
					// senderAddress =
					// sessionAttributes.get(XifAttributeSession.senderID.toString());
					// receiverAddress =
					// sessionAttributes.get(XifAttributeSession.receiverID.toString());
					// solutionId =
					// sessionAttributes.get(XifAttributeSession.serviceName.toString());

				} else if (XIF_ELEMENT_SERVER.equals(xmlPath)) {

				}

				else if (XIF_ELEMENT_Model.equals(xmlPath)) {
					// asset = new Asset();

					String fileName = attributes.get(XifModelServer.name.toString());
					if (fileName != null) {

						//log.info("Asset File Name  {} ", fileName);
						createAssetEntry(fileName, null, fileEvent, keys);

					}
				}

				// /AIRecognitionTranslationStatus/Input/File;
				// /AIRecognitionTranslationStatus/Output/Communication/Input/File;
				else if (XIF_ELEMENT_INPUT_FILE.equals(xmlPath) || XIF_ELEMENT_OUTPUT_COMM_INPUT_FILE.equals(xmlPath)) {

					fileEvent.setEventId(UUID.randomUUID().toString());
					fileEvent.setTransactionId(xifEvent.getTransactionId());
					fileEvent.setTaskId(xifEvent.getTaskId());
					fileEvent.setXifInfo(xifEvent.getXifInfo());
					fileEvent.setProcessId(xifEvent.getProcessId());
					fileEvent.setIntrospectionSessionNumber(
							sessionAttributes.get(XifAttributeSession.number.toString()));
					fileEvent.setIntrospectionSource("XIF");

					if (XIF_ELEMENT_OUTPUT_COMM_INPUT_FILE.equals(xmlPath)) {

						comFileCount++;
						fileEvent.setComFileCount(comFileCount);

					}

					if (XIF_ELEMENT_INPUT_FILE.equals(xmlPath)) {

						contentFileData = new ContentFileData();

						contentFileData.setDocType(sessionAttributes.get(XifAttributeSession.aprf.toString()));
						contentFileData.setSender(sessionAttributes.get(XifAttributeSession.senderID.toString()));
						contentFileData.setReceiver(sessionAttributes.get(XifAttributeSession.receiverID.toString()));
						contentFileData.setFileType("INPUT");
						contentFileData.setIntrospectionType(IntrospectionType.GENERATED);

						try {

							String size = attributes.get(XifAttributeFile.size.toString());

							if (size != null && size.trim().length() > 0) {
								contentFileData.setSize(Integer.valueOf(size));
							}

						} catch (NumberFormatException e) {
							if ((e.getMessage() != null) && (e.getMessage().indexOf("noAttribute") >= 0)) {
								//log.debug("parseStaxInput : No size attribute specified  { } ", e.getMessage());
							}

						}

						String uri = attributes.get(XifAttributeFile.uri.toString());
						// 2015.06.26 retrieve new File uri attribute from xif
						// to
						// populate contentFileId and storageId
						String uuid = stripXifUriPrefixSuffix(uri);

						if (uuid == null) {
							uuid = UUID.randomUUID().toString();
						}

						contentFileData.setFileId(uuid);

					}

					contentFileData
					.setTranslationSessionNumber(sessionAttributes.get(XifAttributeSession.number.toString()));

					envelopes = new LinkedHashMap<String, EnvelopeVO>();
					contentErrors = new ArrayList<ContentErrorEventVO>();
				}
				// /AIRecognitionTranslationStatus/Input/Standard;
				// /AIRecognitionTranslationStatus/Output/Communication/Input/Standard;
				else if (XIF_ELEMENT_INPUT_STANDARD.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_STANDARD.equals(xmlPath)) {
					// 2014.10.13 determine File.dataType by value from Standard
					// element type attribute

					String dataType = attributes.get(XifAttributeStandard.type.toString());
					String direction = attributes.get(XifAttributeStandard.direction.toString());

					contentFileData.setDataType(dataType);

					if (direction == null)
						direction = attributes.get(XifAttributeStandard.direction.toString());

					contentFileData.setDirection(direction);

					if (isEdiType(contentFileData.getDataType())) {
						EnvelopeVO envelopeData = new EnvelopeVO();

						// 2015.06.26 retrieve new Standard uuid attribute from
						// xif
						// to populate envelopeId
						String uuid = attributes.get(XifAttributeStandard.uuid.toString());
						if (uuid == null) {
							uuid = UUID.randomUUID().toString();
						}

						String eventId = UUID.randomUUID().toString();

						envelopeData.setEventId(eventId);
						envelopeData.setEventTimestamp(ZonedDateTime.now().toInstant().toEpochMilli());

						// envelopeData.setDirection(direction);

						envelopeData.setEnvelopeId(uuid);

						String type = attributes.get(XifAttributeStandard.type.toString());

						envelopeData.setDocumentType(contentFileData.getDocType());

						envelopeData.setProcessId(xifEvent.getProcessId());
						envelopeData.setTaskId(xifEvent.getTaskId());
						envelopeData.setTransactionId(xifEvent.getTransactionId());

						PayloadRefVO xifInfo = new PayloadRefVO();
						xifInfo.setPayloadId(contentFileData.getFileId());
						xifInfo.setPayloadType("DSM");

						envelopeData.setXifInfo(xifInfo);

						envelopeData.setFileType(contentFileData.getFileType());

						envelopeData.setIntrospectionType(contentFileData.getIntrospectionType());

						// envelopeData.setSnrf(contentFileData.getSnrf());

						if (isEdiType(type)) {
							envelopeData.setEnvelopeLevel(EnvelopeLevel.INTERCHANGE.toString());
						}

						if (type.equals("X12")) {
							type = "ANSI";
						} else {
							if (type.equals("EFT")) {
								type = "EDIFACT";
							}
						}

						envelopeData.setEnvelopeType(type);

						envelopeData.setSenderAddress(contentFileData.getSender());
						envelopeData.setReceiverAddress(contentFileData.getReceiver());

						envelopeData.setEnvelopeVersion(attributes.get(XifAttributeStandard.version.toString()));
						envelopeData.setIntrospectionSource("XIF");

						if (envelopeData.getEnvelopeVersion() == null)
							envelopeData.setEnvelopeVersion("default");

						// 2015.06.30 @todo enum values for productionTestFlag
						// are
						// PRODUCTION, TEST, P, T, default=PRODUCTION
						String testIndicator = attributes.get(XifAttributeStandard.testIndicator.toString());

						if (testIndicator != null) {

							envelopeData.setProductionTestFlag(testIndicator);
						} else {
							envelopeData.setProductionTestFlag("Test");
						}

						// envelopeData.setf.setContentFileType(contentFileData.getFileType());

						envelopeData.setContainingParentId(contentFileData.getFileId());
						envelopeData.setContainingParentType("FILE");

						envelopes.put(XIF_ELEMENT_STANDARD, envelopeData);
					}
				}
				// /AIRecognitionTranslationStatus/Input/Standard/Group;
				// /AIRecognitionTranslationStatus/Output/Communication/Input/Standard/Group;
				else if (XIF_ELEMENT_INPUT_GROUP.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_GROUP.equals(xmlPath)) {
					documents = new ArrayList<DocumentVO>();
					if (isX12Type(contentFileData.getDataType()) || isEdifactType(contentFileData.getDataType())) {
						EnvelopeVO envelope = new EnvelopeVO();

						// 2015.06.26 retrieve new Group uuid attribute from xif
						// to
						// populate envelopeId
						String uuid = attributes.get(XifAttributeGroup.uuid.toString());
						if (uuid == null) {
							uuid = UUID.randomUUID().toString();
						}

						String eventId = UUID.randomUUID().toString();

						envelope.setEventId(eventId);
						envelope.setEventTimestamp(ZonedDateTime.now().toInstant().toEpochMilli());

						envelope.setEnvelopeId(uuid);
						// envelope.setCreatingProcess(creatingProcess);
						envelope.setEnvelopeLevel(EnvelopeLevel.FUNCTIONAL_GROUP.toString());
						envelope.setDocumentType(contentFileData.getDocType());

						envelope.setProcessId(xifEvent.getProcessId());
						envelope.setTaskId(xifEvent.getTaskId());
						envelope.setTransactionId(xifEvent.getTransactionId());
						envelope.setIntrospectionSource("XIF");

						envelope.setContainingParentId(envelopes.get(XIF_ELEMENT_STANDARD).getEnvelopeId());
						envelope.setContainingParentType("ENVELOPE");

						envelope.setSegmentTerminator(contentFileData.getFileType().toString());

						envelope.setFileType(contentFileData.getFileType());

						envelope.setIntrospectionType(contentFileData.getIntrospectionType());

						PayloadRefVO xifInfo = new PayloadRefVO();
						xifInfo.setPayloadId(contentFileData.getFileId());
						xifInfo.setPayloadType("DSM");

						envelope.setXifInfo(xifInfo);

						// 2014.11.26 set Group envelope sentDate/sentTime from
						// Standard envelope level

						if (envelopes.get(XIF_ELEMENT_STANDARD) != null) {

							if (envelopes.get(XIF_ELEMENT_STANDARD).getSentDate() != null)
								if (envelopes.get(XIF_ELEMENT_STANDARD).getSentDate().length() > 8) {
									envelope.setSentDate(
											envelopes.get(XIF_ELEMENT_STANDARD).getSentDate().substring(0, 8));
								} else {
									envelope.setSentDate(envelopes.get(XIF_ELEMENT_STANDARD).getSentDate());
								}

							if (envelopes.get(XIF_ELEMENT_STANDARD).getSentTime() != null)
								envelope.setSentTime(envelopes.get(XIF_ELEMENT_STANDARD).getSentTime().substring(0, 4));
						}

						envelopes.put(XIF_ELEMENT_GROUP, envelope);

					}
				}
				// /AIRecognitionTranslationStatus/Input/Standard/Group/Message;
				// /AIRecognitionTranslationStatus/Output/Communication/Input/Standard/Group/Message;
				else if (XIF_ELEMENT_INPUT_MESSAGE.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_MESSAGE.equals(xmlPath)) {
					documentData = new DocumentVO();
					documents.add(documentData);

					String eventId = UUID.randomUUID().toString();

					//log.debug("documentData : " + documentData.toString());

					documentData.setFileType(contentFileData.getFileType());
					documentData.setIntrospectionSource("XIF");

					documentData.setIntrospectionType(contentFileData.getIntrospectionType());

					documentData.setEventId(eventId);
					documentData.setEventTimestamp(ZonedDateTime.now().toInstant().toEpochMilli());

					// documentData.setDateCreated(componentRunDate);
					// documentData.setDateLastModified(componentRunDate);

					// 2015.06.26 retrieve new Message uuid attribute from xif
					// to
					// populate documentId
					String uuid = attributes.get(XifAttributeMessage.uuid.toString());

					if (uuid == null) {
						uuid = UUID.randomUUID().toString();
					}

					documentData.setDocumentId(uuid);

					documentData.setProcessId(xifEvent.getProcessId());
					documentData.setTaskId(xifEvent.getTaskId());
					documentData.setTransactionId(xifEvent.getTransactionId());

					PayloadRefVO xifInfo = new PayloadRefVO();
					xifInfo.setPayloadId(contentFileData.getFileId());
					xifInfo.setPayloadType("DSM");

					documentData.setXifInfo(xifInfo);

					//log.debug("documentData UUID : " + documentData.getDocumentId());

					String type = attributes.get(XifAttributeMessage.standard.toString());

					if (type != null) {

						if (type.equals("X12")) {
							type = "ANSI";
						} else {
							if (type.equals("EFT")) {
								type = "EDIFACT";
							}
						}
					}

					documentData.setDocumentStandard(type);

					documentData.setDocumentType(attributes.get(XifAttributeMessage.type.toString()));
					documentData.setDocumentStandardVersion(attributes.get(XifAttributeMessage.version.toString()));

					if (isEdiType(contentFileData.getDataType())) {

						if (envelopes.get(XIF_ELEMENT_STANDARD) != null) {
							documentData.setContainingParentId(envelopes.get(XIF_ELEMENT_STANDARD).getEnvelopeId());
						}

						if (envelopes.get(XIF_ELEMENT_GROUP) != null) {
							documentData.setContainingParentId(envelopes.get(XIF_ELEMENT_GROUP).getEnvelopeId());
						}

						documentData.setContainingParentType("ENVELOPE");

					} else {
						documentData.setContainingParentId(contentFileData.getFileId());
						documentData.setContainingParentType("FILE");
					}

					// set content file id in document
					// documentData.setFileId(contentFileData.getFileId());

					// 2014.08.26 set FunctionalGroup level Envelope version
					// from
					// Message version
					if (envelopes.get(XIF_ELEMENT_GROUP) != null) {
						envelopes.get(XIF_ELEMENT_GROUP)
						.setEnvelopeVersion(attributes.get(XifAttributeStandard.version.toString()));
						// envelopes.get(XIF_ELEMENT_GROUP).setEnvelopeVersion("default");
					}

					// 2015.10.07 set denormalized value for standard level
					// control
					// number
					if (envelopes.get(XIF_ELEMENT_STANDARD) != null) {
						documentData
						.setControlNumberEnvelopeLevel1(envelopes.get(XIF_ELEMENT_STANDARD).getControlNumber());

						documentData.setEnvelopeVersion(envelopes.get(XIF_ELEMENT_STANDARD).getEnvelopeVersion());

						if (envelopes.get(XIF_ELEMENT_STANDARD).getSenderAddress() != null) {
							documentData.setSenderAddressEnvelopeLevel1(
									envelopes.get(XIF_ELEMENT_STANDARD).getSenderAddress());

						}

						if (envelopes.get(XIF_ELEMENT_STANDARD).getReceiverAddress() != null) {
							documentData.setReceiverAddressEnvelopeLevel1(
									envelopes.get(XIF_ELEMENT_STANDARD).getReceiverAddress());

						}

					}

					/*
					 * 
					 * if (envelopes.get(XIF_ELEMENT_STANDARD) != null) {
					 * 
					 * if (envelopes.get(XIF_ELEMENT_STANDARD).getSentDate() != null)
					 * 
					 * if (envelopes.get(XIF_ELEMENT_STANDARD).getSentDate().length () == 8) {
					 * documentData.setSentDate(envelopes.get( XIF_ELEMENT_STANDARD).
					 * getSentDate().substring(2)); } else {
					 * //documentData.setSentDate(envelopes.get( XIF_ELEMENT_STANDARD
					 * ).getSentDate()); }
					 * 
					 * if (envelopes.get(XIF_ELEMENT_STANDARD).getSentTime() != null)
					 * documentData.setSentTime(envelopes.get( XIF_ELEMENT_STANDARD).
					 * getSentTime().substring(0, 4)); }
					 * 
					 */

					// documentData.setDirectDocumentsCount(interchangeDocCount);
					// documentData.setDirectEnvelopesCount(fgInterchangeCount);

					// 2015.10.07 set denormalized value for group level control
					// number
					if (envelopes.get(XIF_ELEMENT_GROUP) != null) {
						documentData
						.setControlNumberEnvelopeLevel2(envelopes.get(XIF_ELEMENT_GROUP).getControlNumber());

						if (envelopes.get(XIF_ELEMENT_GROUP).getSenderAddress() != null) {
							documentData.setSenderAddressEnvelopeLevel2(
									envelopes.get(XIF_ELEMENT_GROUP).getSenderAddress());

						}

						if (envelopes.get(XIF_ELEMENT_GROUP).getReceiverAddress() != null) {
							documentData.setReceiverAddressEnvelopeLevel2(
									envelopes.get(XIF_ELEMENT_GROUP).getReceiverAddress());

						}

						if (envelopes.get(XIF_ELEMENT_GROUP).getDocumentFunctionalClassification() != null) {
							documentData.setFunctionalCodeEnvelopeLevel2(
									envelopes.get(XIF_ELEMENT_GROUP).getDocumentFunctionalClassification());
						}
					}
					// 2016.06.27 if not already populated, set Document
					// sender/receiver from FunctionalGroup/Standard Envelope
					// levels
					if (documentData.getSenderAddress() == null && documentData.getReceiverAddress() == null) {

						documentData.setSenderAddress(contentFileData.getSender());
						documentData.setReceiverAddress(contentFileData.getReceiver());

					}

				} else if (XIF_ELEMENT_INPUT_MESSAGE_POSSTART.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_MESSAGE_POSSTART.equals(xmlPath)) {
					characters = getStaxEventCharacters(xer, xmlPathBuilder);
					// documentData.setDocPosition(Integer.valueOf(characters));
					// 2014.08.26 default positionUom to characters
					// documentData.setp.getStructuralAttributes().setPositionUOM(PositionUOM.CHARACTERS);
				} else if (XIF_ELEMENT_INPUT_MESSAGE_POSEND.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_MESSAGE_POSEND.equals(xmlPath)) {
					characters = getStaxEventCharacters(xer, xmlPathBuilder);
					// documentData.setp.getStructuralAttributes().setPositionEnd(Integer.valueOf(characters));
				} else if (XIF_ELEMENT_INPUT_MESSAGE_SENDER_ID.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_MESSAGE_SENDER_ID.equals(xmlPath)) {
					characters = getStaxEventCharacters(xer, xmlPathBuilder);
					documentData.setSenderAddr(characters);

				} else if (XIF_ELEMENT_INPUT_MESSAGE_SENDER_QUAL.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_MESSAGE_SENDER_QUAL.equals(xmlPath)) {
					characters = getStaxEventCharacters(xer, xmlPathBuilder);

					if (characters != null) {
						documentData.setSenderQual(characters);
					}

					if (documentData.getSenderQual() != null && documentData.getSenderAddr() != null) {
						documentData
						.setSenderAddress(documentData.getSenderQual() + ":" + documentData.getSenderAddr());
					} else {
						documentData.setSenderAddress(documentData.getSenderAddr());
					}

				} else if (XIF_ELEMENT_INPUT_MESSAGE_RECEIVER_ID.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_MESSAGE_RECEIVER_ID.equals(xmlPath)) {
					characters = getStaxEventCharacters(xer, xmlPathBuilder);
					documentData.setReceiverAddr(characters);

				} else if (XIF_ELEMENT_INPUT_MESSAGE_RECEIVER_QUAL.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_MESSAGE_RECEIVER_QUAL.equals(xmlPath)) {
					characters = getStaxEventCharacters(xer, xmlPathBuilder);
					if (characters != null) {
						documentData.setReceiverQual(characters);
					}

					if (documentData.getReceiverQual() != null && documentData.getReceiverAddr() != null) {
						documentData.setReceiverAddress(
								documentData.getReceiverQual() + ":" + documentData.getReceiverAddr());
					} else {
						documentData.setReceiverAddress(documentData.getReceiverAddr());
					}

				} else if (XIF_ELEMENT_INPUT_MESSAGE_CONTROLNUMBER.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_MESSAGE_CONTROLNUMBER.equals(xmlPath)) {
					characters = getStaxEventCharacters(xer, xmlPathBuilder);
					if (characters != null) {
						// envelopes.get(XIF_ELEMENT_STANDARD).setSentDate(XIF_STANDARD_DATE.parse(characters));
						// if (envelopes.get(XIF_ELEMENT_STANDARD) != null)
						// envelopes.get(XIF_ELEMENT_STANDARD).setSentDate(characters);
						documentData.setControlNumber(characters);

					}
				} else if (XIF_ELEMENT_INPUT_MESSAGE_SET.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_MESSAGE_SET.equals(xmlPath)) {

					// contentKeyVO = new ContentKeyVO();

					String hierarchy = attributes.get(XifAttributeSet.hierarchy.toString());

					hierarchy = attributes.get(XifAttributeSet.heirarchy.toString());

				} else if (XIF_ELEMENT_INPUT_MESSAGE_LABEL.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_MESSAGE_LABEL.equals(xmlPath)) {
					characters = getStaxEventCharacters(xer, xmlPathBuilder);

					contentKeyVO = new ContentKeyVO();

					if (isPrimaryKeyAttribute(attributes.get(XifAttributeLabel.key.toString()))) {

						contentKeyVO.setKeyName(characters);
						contentKeyVO.setKeyType("primary");

						// contentKeyValues.add(ContentKey.newBuilder().setKeyType("primary").setKeyName(characters)
						// .setKeyValue("null").build());

						// System.out.println("documentKeyValueAttribute : PK "
						// +
						// documentKeyValueAttribute.getKey());

					} else if (isSecondaryKeyAttribute(attributes.get(XifAttributeLabel.key.toString()))) {

						// contentKeyValues.add(ContentKey.newBuilder().setKeyType("secondary").setKeyName(characters)
						// .setKeyValue("null").build());

						// System.out.println("documentKeyValueAttribute : SK "
						// +
						// documentKeyValueAttribute.getKey());

						contentKeyVO.setKeyName(characters);
						contentKeyVO.setKeyType("secondary");

					} else {
						contentKeyVO.setKeyName(characters);
					}

				} else if (XIF_ELEMENT_INPUT_MESSAGE_VALUE.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_MESSAGE_VALUE.equals(xmlPath)) {

					// if (ContentKey.newBuilder().getKeyName().getKey() !=
					// null) {
					// documentKeyValueAttribute.setValue(characters);

					// System.out.println("documentKeyValueAttribute : value " +
					// documentKeyValueAttribute.getValue());

					// Collection<DocumentKeyValueAttribute> dkv =
					// documentData.getDocumentKeyValues();
					// dkv.add(documentKeyValueAttribute);

					characters = getStaxEventCharacters(xer, xmlPathBuilder);

					if (contentKeyVO.getKeyName() != null) {
						contentKeyVO.setKeyValue(characters);
					}

					if (documentData.getContentKeys() == null)
						contentKeys = new ContentKeys();

					contentKeys.add(contentKeyVO);
					documentData.setContentKeys(contentKeys);

				} else if (XIF_ELEMENT_INPUT_GROUP_SENDER_ID.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_GROUP_SENDER_ID.equals(xmlPath)) {

					characters = getStaxEventCharacters(xer, xmlPathBuilder);
					if (envelopes.get(XIF_ELEMENT_GROUP) != null) {

						envelopes.get(XIF_ELEMENT_GROUP).setSenderAddress(characters);

					}
				} else if (XIF_ELEMENT_INPUT_GROUP_SENDER_QUAL.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_GROUP_SENDER_QUAL.equals(xmlPath)) {
					characters = getStaxEventCharacters(xer, xmlPathBuilder);

					if (envelopes.get(XIF_ELEMENT_GROUP) != null) {
						envelopes.get(XIF_ELEMENT_GROUP).setSenderQual(characters);

						if (envelopes.get(XIF_ELEMENT_GROUP).getSenderQual() != null
								&& envelopes.get(XIF_ELEMENT_GROUP).getSenderAddress() != null) {
							envelopes.get(XIF_ELEMENT_GROUP)
							.setSenderAddress(envelopes.get(XIF_ELEMENT_GROUP).getSenderQual() + ":"
									+ envelopes.get(XIF_ELEMENT_GROUP).getSenderAddress());
						}

					}

				} else if (XIF_ELEMENT_INPUT_GROUP_RECEIVER_ID.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_GROUP_RECEIVER_ID.equals(xmlPath)) {
					characters = getStaxEventCharacters(xer, xmlPathBuilder);
					if (envelopes.get(XIF_ELEMENT_GROUP) != null) {
						envelopes.get(XIF_ELEMENT_GROUP).setReceiverAddress(characters);
					}
				} else if (XIF_ELEMENT_INPUT_GROUP_RECEIVER_QUAL.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_GROUP_RECEIVER_QUAL.equals(xmlPath)) {
					characters = getStaxEventCharacters(xer, xmlPathBuilder);
					if (envelopes.get(XIF_ELEMENT_GROUP) != null) {
						envelopes.get(XIF_ELEMENT_GROUP).setReceiverQual(characters);

						if (envelopes.get(XIF_ELEMENT_GROUP).getReceiverQual() != null
								&& envelopes.get(XIF_ELEMENT_GROUP).getReceiverAddress() != null) {
							envelopes.get(XIF_ELEMENT_GROUP)
							.setReceiverAddress(envelopes.get(XIF_ELEMENT_GROUP).getReceiverQual() + ":"
									+ envelopes.get(XIF_ELEMENT_GROUP).getReceiverAddress());
						}

					}

				} else if (XIF_ELEMENT_INPUT_GROUP_CONTROLNUMBER.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_GROUP_CONTROLNUMBER.equals(xmlPath)) {
					characters = getStaxEventCharacters(xer, xmlPathBuilder);
					if (envelopes.get(XIF_ELEMENT_GROUP) != null && characters != null) {
						envelopes.get(XIF_ELEMENT_GROUP).setControlNumber(characters);
					}
				} else if (XIF_ELEMENT_INPUT_GROUP_LABEL.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_GROUP_LABEL.equals(xmlPath)) {
					characters = getStaxEventCharacters(xer, xmlPathBuilder);

					contentKeyVO = new ContentKeyVO();

					if (isPrimaryKeyAttribute(attributes.get(XifAttributeLabel.key.toString()))) {

						contentKeyVO.setKeyName(characters);
						contentKeyVO.setKeyType("primary");

						// contentKeyValues.add(ContentKey.newBuilder().setKeyType("primary").setKeyName(characters)
						// .setKeyValue("null").build());

						// System.out.println("documentKeyValueAttribute : PK "
						// +
						// documentKeyValueAttribute.getKey());

					} else if (isSecondaryKeyAttribute(attributes.get(XifAttributeLabel.key.toString()))) {

						// contentKeyValues.add(ContentKey.newBuilder().setKeyType("secondary").setKeyName(characters)
						// .setKeyValue("null").build());

						// System.out.println("documentKeyValueAttribute : SK "
						// +
						// documentKeyValueAttribute.getKey());

						contentKeyVO.setKeyName(characters);
						contentKeyVO.setKeyType("secondary");

					} else {
						contentKeyVO.setKeyName(characters);
					}

				} else if (XIF_ELEMENT_INPUT_GROUP_VALUE.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_GROUP_VALUE.equals(xmlPath)) {
					characters = getStaxEventCharacters(xer, xmlPathBuilder);

					if (contentKeyVO.getKeyName() != null) {
						contentKeyVO.setKeyValue(characters);
					}

					if (envelopes.get(XIF_ELEMENT_GROUP) != null) {
						if (XIF_ELEMENT_LABEL_FUNCTION_CODE.equals(contentKeyVO.getKeyName())) {
							envelopes.get(XIF_ELEMENT_GROUP).setDocumentFunctionalClassification(characters);
						}
					}

					if (envelopes.get(XIF_ELEMENT_GROUP) != null && characters != null) {

						if (envelopes.get(XIF_ELEMENT_GROUP).getContentKeys() == null)
							contentKeys = new ContentKeys();

					}

					if (contentKeys != null)
						contentKeys.add(contentKeyVO);

					if (envelopes.get(XIF_ELEMENT_GROUP) != null && characters != null) {
						envelopes.get(XIF_ELEMENT_GROUP).setContentKeys(contentKeys);
					}

				} else if (XIF_ELEMENT_INPUT_STANDARD_SENDER_ID.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_STANDARD_SENDER_ID.equals(xmlPath)) {
					characters = getStaxEventCharacters(xer, xmlPathBuilder);

					if (envelopes.get(XIF_ELEMENT_STANDARD) != null) {

						envelopes.get(XIF_ELEMENT_STANDARD).setSenderAddr(characters);
					}

				} else if (XIF_ELEMENT_INPUT_STANDARD_SENDER_QUAL.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_STANDARD_SENDER_QUAL.equals(xmlPath)) {
					characters = getStaxEventCharacters(xer, xmlPathBuilder);

					if (envelopes.get(XIF_ELEMENT_STANDARD) != null) {
						envelopes.get(XIF_ELEMENT_STANDARD).setSenderQual(characters);
					}

					if (envelopes.get(XIF_ELEMENT_STANDARD).getSenderQual() != null
							&& envelopes.get(XIF_ELEMENT_STANDARD).getSenderAddr() != null) {
						envelopes.get(XIF_ELEMENT_STANDARD)
						.setSenderAddress(envelopes.get(XIF_ELEMENT_STANDARD).getSenderQual() + ":"
								+ envelopes.get(XIF_ELEMENT_STANDARD).getSenderAddr());
					} else {
						envelopes.get(XIF_ELEMENT_STANDARD)
						.setSenderAddress(envelopes.get(XIF_ELEMENT_STANDARD).getSenderAddr());
					}

				} else if (XIF_ELEMENT_INPUT_STANDARD_RECEIVER_ID.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_STANDARD_RECEIVER_ID.equals(xmlPath)) {
					characters = getStaxEventCharacters(xer, xmlPathBuilder);
					if (envelopes.get(XIF_ELEMENT_STANDARD) != null) {

						envelopes.get(XIF_ELEMENT_STANDARD).setReceiverAddr(characters);

					}

				} else if (XIF_ELEMENT_INPUT_STANDARD_RECEIVER_QUAL.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_STANDARD_RECEIVER_QUAL.equals(xmlPath)) {
					characters = getStaxEventCharacters(xer, xmlPathBuilder);

					if (envelopes.get(XIF_ELEMENT_STANDARD) != null) {
						envelopes.get(XIF_ELEMENT_STANDARD).setReceiverQual(characters);
					}

					if (envelopes.get(XIF_ELEMENT_STANDARD).getReceiverQual() != null
							&& envelopes.get(XIF_ELEMENT_STANDARD).getReceiverAddr() != null) {
						envelopes.get(XIF_ELEMENT_STANDARD)
						.setReceiverAddress(envelopes.get(XIF_ELEMENT_STANDARD).getReceiverQual() + ":"
								+ envelopes.get(XIF_ELEMENT_STANDARD).getReceiverAddr());
					} else {
						envelopes.get(XIF_ELEMENT_STANDARD)
						.setReceiverAddress(envelopes.get(XIF_ELEMENT_STANDARD).getReceiverAddr());
					}

				} else if (XIF_ELEMENT_INPUT_STANDARD_DATE.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_STANDARD_DATE.equals(xmlPath)) {
					characters = getStaxEventCharacters(xer, xmlPathBuilder);
					if (envelopes.get(XIF_ELEMENT_STANDARD) != null && characters != null) {

						if (characters.length() == 8) {
							envelopes.get(XIF_ELEMENT_STANDARD).setSentDate(characters.substring(2));
						} else {
							envelopes.get(XIF_ELEMENT_STANDARD).setSentDate(characters);
						}
						// documentData.setSentDate(characters);
					}
				} else if (XIF_ELEMENT_INPUT_STANDARD_TIME.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_STANDARD_TIME.equals(xmlPath)) {
					characters = getStaxEventCharacters(xer, xmlPathBuilder);
					if (envelopes.get(XIF_ELEMENT_STANDARD) != null && characters != null) {
						envelopes.get(XIF_ELEMENT_STANDARD).setSentTime(characters.substring(0, 4));
						// documentData.setSentTime(characters);
					}
				} else if (XIF_ELEMENT_INPUT_STANDARD_CONTROLNUMBER.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_STANDARD_CONTROLNUMBER.equals(xmlPath)) {
					characters = getStaxEventCharacters(xer, xmlPathBuilder);
					if (envelopes.get(XIF_ELEMENT_STANDARD) != null && characters != null) {
						envelopes.get(XIF_ELEMENT_STANDARD).setControlNumber(characters);
					}

				} else if (XIF_ELEMENT_INPUT_STANDARD_LABEL.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_STANDARD_LABEL.equals(xmlPath)) {
					characters = getStaxEventCharacters(xer, xmlPathBuilder);
					contentKeyVO = new ContentKeyVO();

					if (isPrimaryKeyAttribute(attributes.get(XifAttributeLabel.key.toString()))) {

						contentKeyVO.setKeyName(characters);
						contentKeyVO.setKeyType("primary");

						// contentKeyValues.add(ContentKey.newBuilder().setKeyType("primary").setKeyName(characters)
						// .setKeyValue("null").build());

						// System.out.println("documentKeyValueAttribute : PK "
						// +
						// documentKeyValueAttribute.getKey());

					} else if (isSecondaryKeyAttribute(attributes.get(XifAttributeLabel.key.toString()))) {

						// contentKeyValues.add(ContentKey.newBuilder().setKeyType("secondary").setKeyName(characters)
						// .setKeyValue("null").build());

						// System.out.println("documentKeyValueAttribute : SK "
						// +
						// documentKeyValueAttribute.getKey());

						contentKeyVO.setKeyName(characters);
						contentKeyVO.setKeyType("secondary");

					} else {
						contentKeyVO.setKeyName(characters);
					}

				} else if (XIF_ELEMENT_INPUT_STANDARD_VALUE.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_STANDARD_VALUE.equals(xmlPath)) {
					characters = getStaxEventCharacters(xer, xmlPathBuilder);

					if (contentKeyVO.getKeyName() != null) {
						contentKeyVO.setKeyValue(characters);
					}

					if (envelopes.get(XIF_ELEMENT_STANDARD) != null && characters != null) {

						if (envelopes.get(XIF_ELEMENT_STANDARD).getContentKeys() == null)
							contentKeys = new ContentKeys();

					}

					contentKeys.add(contentKeyVO);

					if (envelopes.get(XIF_ELEMENT_STANDARD) != null && characters != null) {
						envelopes.get(XIF_ELEMENT_STANDARD).setContentKeys(contentKeys);
					}
				}

				else if (XIF_ELEMENT_OUTPUT_ERROR_FILE.equals(xmlPath)) {

					String errorFileName = attributes.get(XifAttributeFile.name.toString());
					String uri = attributes.get(XifAttributeFile.uri.toString());

					createAssetEntry(errorFileName, uri, fileEvent, keys);

					// fileEvent.setAssets(keys);

				} else if (XIF_ELEMENT_OUTPUT_APP_FILE.equals(xmlPath)) {
					// 2014.09.29 must retrieve File.docType for introspected
					// output
					// file from aprf in Output.Communication.File node

					appFileCount++;
					fileEvent.setAppFileCount(appFileCount);

					contentFileData = new ContentFileData();
					contentFileData.setDocType(attributes.get(XifAttributeFile.aprf.toString()));
					contentFileData.setSender(attributes.get(XifAttributeFile.sender.toString()));
					contentFileData.setReceiver(attributes.get(XifAttributeFile.receiver.toString()));

					contentFileData.setFileType("OUTPUT");

					contentFileData.setIntrospectionType(IntrospectionType.INTROSPECTED);

					String uri = attributes.get(XifAttributeFile.uri.toString());
					// 2015.06.26 retrieve new File uri attribute from xif to
					// populate contentFileId and storageId
					String uuid = stripXifUriPrefixSuffix(uri);

					if (uuid == null) {
						uuid = UUID.randomUUID().toString();
					}

					contentFileData.setFileId(uuid);

					try {

						String size = attributes.get(XifAttributeFile.size.toString());

						if (size != null && size.trim().length() > 0) {

							if (contentFileData.getSize() == 0)
								contentFileData.setSize(Integer.valueOf(size));

						}

					} catch (NumberFormatException e) {
						if ((e.getMessage() != null) && (e.getMessage().indexOf("noAttribute") >= 0)) {
							//log.debug("parseStaxInput : No size attribute specified  { } " + e.getMessage());
						}

					}

					// direction =
					// sessionAttributes.get(XifAttributeStandard.direction.toString());
					// contentFileData.setDirection(direction);
					// /AIRecognitionTranslationStatus/Output/Communication/File;
				} else if (XIF_ELEMENT_OUTPUT_COMM_FILE.equals(xmlPath)) {
					// 2014.09.29 must retrieve File.docType for introspected
					// output
					// file from aprf in Output.Communication.File node
					contentFileData = new ContentFileData();
					contentFileData.setDocType(attributes.get(XifAttributeFile.aprf.toString()));
					contentFileData.setSender(attributes.get(XifAttributeFile.sender.toString()));
					contentFileData.setReceiver(attributes.get(XifAttributeFile.receiver.toString()));

					String uri = attributes.get(XifAttributeFile.uri.toString());
					// 2015.06.26 retrieve new File uri attribute from xif to
					// populate contentFileId and storageId
					String uuid = stripXifUriPrefixSuffix(uri);

					if (uuid == null) {
						uuid = UUID.randomUUID().toString();
					}

					contentFileData.setFileId(uuid);

					contentFileData.setFileType("OUTPUT");

					contentFileData.setIntrospectionType(IntrospectionType.INTROSPECTED);

					try {

						String size = attributes.get(XifAttributeFile.size.toString());

						if (size != null && size.trim().length() > 0) {

							if (contentFileData.getSize() == 0)
								contentFileData.setSize(Integer.valueOf(size));

						}

					} catch (NumberFormatException e) {
						if ((e.getMessage() != null) && (e.getMessage().indexOf("noAttribute") >= 0)) {
							//log.debug("parseStaxInput : No size attribute specified  { } ", e.getMessage());
						}

					}

				} else if (XIF_ELEMENT_OUTPUT_COMM_FILE_SENDER_ID.equals(xmlPath)) {
					characters = getStaxEventCharacters(xer, xmlPathBuilder);
					if (contentFileData != null) {

						contentFileData.setSenderAddr(characters);
					}

					// System.out.println(("contentData : " +
					// contentFileData.getSender().getAddress()));

				} else if (XIF_ELEMENT_OUTPUT_COMM_FILE_SENDER_QUAL.equals(xmlPath)) {
					characters = getStaxEventCharacters(xer, xmlPathBuilder);

					if (characters != null) {
						contentFileData.setSenderQual(characters);
					}

					if (contentFileData.getSenderQual() != null && contentFileData.getSenderAddr() != null) {
						contentFileData
						.setSender(contentFileData.getSenderQual() + ":" + contentFileData.getSenderAddr());
					} else {
						contentFileData.setSender(contentFileData.getSenderAddr());
					}

				} else if (XIF_ELEMENT_OUTPUT_COMM_FILE_RECEIVER_ID.equals(xmlPath)) {
					characters = getStaxEventCharacters(xer, xmlPathBuilder);
					if (contentFileData != null) {

						contentFileData.setReceiverAddr(characters);

					}

					// System.out.println(("contentData : " +
					// contentFileData.getReceiver().getAddress()));

				} else if (XIF_ELEMENT_OUTPUT_COMM_FILE_RECEIVER_QUAL.equals(xmlPath)) {
					characters = getStaxEventCharacters(xer, xmlPathBuilder);

					if (characters != null) {
						contentFileData.setReceiverQual(characters);
					}

					if (contentFileData.getReceiverQual() != null && contentFileData.getReceiverAddr() != null) {
						contentFileData.setReceiver(
								contentFileData.getReceiverQual() + ":" + contentFileData.getReceiverAddr());
					} else {
						contentFileData.setReceiver(contentFileData.getReceiverAddr());
					}

				} else if (XIF_ELEMENT_OUTPUT_COMM_FILE_IDS_MessageType.equals(xmlPath)) {
					characters = getStaxEventCharacters(xer, xmlPathBuilder);
					if (characters != null) {
						if (contentFileData != null)
							contentFileData.setDocType(characters);
					}

					// Content Error(s)

				} else if (XIF_ELEMENT_OUTPUT_EXCEPTION_FILE.equals(xmlPath)) {

					bypassFileCount++;
					fileEvent.setBypassFileCount(bypassFileCount);

				} else if (XIF_ELEMENT_INPUT_FILE_ERROR_NUMBER.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_FILE_ERROR_NUMBER.equals(xmlPath)) {
					characters = getStaxEventCharacters(xer, xmlPathBuilder);
					contentErrorData = new ContentErrorEventVO();
					contentErrorData.setErrorId(UUID.randomUUID().toString());

					ErrorInfoVO errorInfoVO = new ErrorInfoVO();
					errorInfoVO.setErrorCode(characters);

					contentErrorData.setErrorInfo(errorInfoVO);
					contentErrorData.setContentFileId(contentFileData.getFileId());
					contentErrorData.setProcessId(xifEvent.getProcessId());
					contentErrorData.setTaskId(xifEvent.getTaskId());
					contentErrorData.setTransactionId(xifEvent.getTransactionId());
					contentErrorData.setErrorLevel("FILE");
					// contentErrors.add(contentErrorData);

					//log.info("content error list size  {} ", contentErrorData.toString());

				} else if (XIF_ELEMENT_INPUT_STANDARD_ERROR_NUMBER.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_STANDARD_ERROR_NUMBER.equals(xmlPath)) {
					characters = getStaxEventCharacters(xer, xmlPathBuilder);
					contentErrorData = new ContentErrorEventVO();
					contentErrorData.setErrorId(UUID.randomUUID().toString());

					ErrorInfoVO errorInfoVO = new ErrorInfoVO();
					errorInfoVO.setErrorCode(characters);

					contentErrorData.setErrorInfo(errorInfoVO);
					contentErrorData.setContentFileId(contentFileData.getFileId());
					if (envelopes.get(XIF_ELEMENT_STANDARD) != null) {
						contentErrorData.setEnvelopeId(envelopes.get(XIF_ELEMENT_STANDARD).getEnvelopeId());
					}
					contentErrorData.setProcessId(xifEvent.getProcessId());
					contentErrorData.setTaskId(xifEvent.getTaskId());
					contentErrorData.setTransactionId(xifEvent.getTransactionId());

					contentErrorData.setErrorLevel("INTERCHANGE");
					// contentErrors.add(contentErrorData);

				} else if (XIF_ELEMENT_INPUT_GROUP_ERROR_NUMBER.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_GROUP_ERROR_NUMBER.equals(xmlPath)) {
					characters = getStaxEventCharacters(xer, xmlPathBuilder);

					contentErrorData = new ContentErrorEventVO();
					contentErrorData.setErrorId(UUID.randomUUID().toString());

					ErrorInfoVO errorInfoVO = new ErrorInfoVO();
					errorInfoVO.setErrorCode(characters);

					contentErrorData.setErrorInfo(errorInfoVO);

					contentErrorData.setContentFileId(contentFileData.getFileId());
					if (envelopes.get(XIF_ELEMENT_STANDARD) != null) {
						contentErrorData.setEnvelopeId(envelopes.get(XIF_ELEMENT_STANDARD).getEnvelopeId());
					}
					if (envelopes.get(XIF_ELEMENT_GROUP) != null) {
						contentErrorData.setEnvelopeId(envelopes.get(XIF_ELEMENT_GROUP).getEnvelopeId());
					}
					contentErrorData.setProcessId(xifEvent.getProcessId());
					contentErrorData.setTaskId(xifEvent.getTaskId());
					contentErrorData.setTransactionId(xifEvent.getTransactionId());

					contentErrorData.setErrorLevel("INTERCHANGE");
					contentErrors.add(contentErrorData);

				} else if (XIF_ELEMENT_INPUT_MESSAGE_ERROR_NUMBER.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_MESSAGE_ERROR_NUMBER.equals(xmlPath)) {
					characters = getStaxEventCharacters(xer, xmlPathBuilder);
					contentErrorData = new ContentErrorEventVO();
					contentErrorData.setErrorId(UUID.randomUUID().toString());

					contentErrorData.setContentFileId(contentFileData.getFileId());

					ErrorInfoVO errorInfoVO = new ErrorInfoVO();
					errorInfoVO.setErrorCode(characters);

					contentErrorData.setErrorInfo(errorInfoVO);

					contentErrorData.setProcessId(xifEvent.getProcessId());
					contentErrorData.setTaskId(xifEvent.getTaskId());
					contentErrorData.setTransactionId(xifEvent.getTransactionId());

					if (envelopes.get(XIF_ELEMENT_STANDARD) != null) {
						contentErrorData.setEnvelopeId(envelopes.get(XIF_ELEMENT_STANDARD).getEnvelopeId());
					}
					if (envelopes.get(XIF_ELEMENT_GROUP) != null) {
						contentErrorData.setEnvelopeId(envelopes.get(XIF_ELEMENT_GROUP).getEnvelopeId());
					}
					if (documentData != null) {
						contentErrorData.setDocumentId(documentData.getDocumentId());
					}

					// contentErrors.add(contentErrorData);

				} else if (XIF_ELEMENT_INPUT_FILE_ERROR_SUMMARY.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_FILE_ERROR_SUMMARY.equals(xmlPath)
						|| XIF_ELEMENT_INPUT_STANDARD_ERROR_SUMMARY.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_STANDARD_ERROR_SUMMARY.equals(xmlPath)
						|| XIF_ELEMENT_INPUT_GROUP_ERROR_SUMMARY.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_GROUP_ERROR_SUMMARY.equals(xmlPath)
						|| XIF_ELEMENT_INPUT_MESSAGE_ERROR_SUMMARY.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_MESSAGE_ERROR_SUMMARY.equals(xmlPath)) {
					characters = getStaxEventCharacters(xer, xmlPathBuilder);
					contentErrorData.getErrorInfo().setInternalErrorMessage(characters);

				} else if (XIF_ELEMENT_INPUT_FILE_ERROR_DETAIL.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_FILE_ERROR_DETAIL.equals(xmlPath)
						|| XIF_ELEMENT_INPUT_STANDARD_ERROR_DETAIL.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_STANDARD_ERROR_DETAIL.equals(xmlPath)
						|| XIF_ELEMENT_INPUT_STANDARD_ERROR_DETAIL.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_STANDARD_ERROR_DETAIL.equals(xmlPath)
						|| XIF_ELEMENT_INPUT_MESSAGE_ERROR_DETAIL.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_MESSAGE_ERROR_DETAIL.equals(xmlPath)) {
					characters = getStaxEventCharacters(xer, xmlPathBuilder);
					contentErrorData.getErrorInfo().setExternalErrorMessage(characters);

					contentErrors.add(contentErrorData);

				}

				//				if (log.isDebugEnabled())
				//					log.debug(elementName + "(" + xmlPath + ")[" + characters + "]");

			} else if (staxEvent.getEventType() == XMLEvent.END_ELEMENT)

			{
				String elementName = staxEvent.asEndElement().getName().toString();
				xmlPath = xmlPathBuilder.toString();

				if (XIF_ELEMENT_INPUT_GROUP.equals(xmlPath) || XIF_ELEMENT_OUTPUT_COMM_INPUT_GROUP.equals(xmlPath)) {
					if (envelopes.size() > 0 && envelopes.get(XIF_ELEMENT_GROUP) != null) {
						if (envelopes.get(XIF_ELEMENT_GROUP).getDocumentFunctionalClassification() == null) {
							if (PURCHASE_ORDER.equals(contentFileData.getDocType())) {
								envelopes.get(XIF_ELEMENT_GROUP).setDocumentFunctionalClassification(
										XifAttributeLabelFunctionCodeValue.PO.toString());
							}
						}
						saveEnvelopeList.add(envelopes.get(XIF_ELEMENT_GROUP));
					}

					for (DocumentVO document : documents) {

						saveDocumentList.add(document);
						// String senderLogicalDocumentId =
						// documentData.getLogicalDocumentId();
						// String receiverLogicalDocumentId =
						// documentData.getLogicalDocumentId();

					}
				} else if (XIF_ELEMENT_INPUT_STANDARD.equals(xmlPath)
						|| XIF_ELEMENT_OUTPUT_COMM_INPUT_STANDARD.equals(xmlPath)) {
					if (envelopes.size() > 0 && envelopes.get(XIF_ELEMENT_STANDARD) != null) {
						saveEnvelopeList.add(envelopes.get(XIF_ELEMENT_STANDARD));
					}
				} else if (XIF_ELEMENT_INPUT.equals(xmlPath) || XIF_ELEMENT_OUTPUT_COMM_INPUT.equals(xmlPath)) {
					saveContentFileList.add(contentFileData);

					if (contentErrors != null) {

						//log.info("contentErrors test {} ", contentErrors.size());
						saveContentErrorList.addAll(contentErrors);
					}

				}

				removeElementFromXmlPathBuilder(elementName, xmlPathBuilder);
			}
		}
	}

	// Create Assets
	private void createAssetEntry(String AssetName, String uri, FileIntrospectedEventVO fileEvent, AssetKeys keys)
			throws ParseException {

		AssetVO asset = new AssetVO();

		asset.setAssetId(UUID.randomUUID().toString());
		asset.setAssetName(AssetName);

		PayloadRefVO refID = new PayloadRefVO();
		refID.setPayloadId(Optional.ofNullable(uri).orElse(""));
		refID.setPayloadType("ASSET");

		asset.setXifInfo(refID);

		if (AssetName != null) {
			if (AssetName.endsWith(".att")) {
				asset.setAssetType("Attachment");
				asset.setAssetDescription("TRANSLATION_ATTACH_AND_MAP_FILES");
			} else if (AssetName.endsWith("s.mdl")) {

				asset.setAssetType("Source Model");
				asset.setAssetDescription("TRANSLATION_ATTACH_AND_MAP_FILES");

			} else if (AssetName.endsWith(".log")) {

				asset.setAssetType("Trace File");
				asset.setAssetDescription("TRANSLATION_TRACE_AND_XIF_FILES");

			} else if (AssetName.startsWith("XIF_")) {

				asset.setAssetType("XIF File");
				asset.setAssetDescription("TRANSLATION_TRACE_AND_XIF_FILES");

			} else if ((AssetName.endsWith(".mdl"))) {
				asset.setAssetType("Model");
				asset.setAssetDescription("TRANSLATION_ATTACH_AND_MAP_FILES");
			}
		}

		if (fileEvent.getAssets() == null) {
			keys = new AssetKeys();
		}

		keys.add(asset);
		fileEvent.setAssets(keys);

	}

	private boolean isEdiType(String type) {
		// 2014.12.04 includes X12, EDIFACT (EFT), TRADACOMS (ANA), and German
		// Automotive Standard (VDA)
		return XifAttributeStandardType.X12.toString().equals(type)
				|| XifAttributeStandardType.EFT.toString().equals(type)
				|| XifAttributeStandardType.ANA.toString().equals(type)
				|| XifAttributeStandardType.VDA.toString().equals(type)
				|| ("ANSI".equalsIgnoreCase(type) || "XEDIFACT".equalsIgnoreCase(type));
	}

	private boolean isUDFType(String type) {
		// 2014.12.04 includes X12, EDIFACT (EFT), TRADACOMS (ANA), and German
		// Automotive Standard (VDA)

		if ("ANSI".equalsIgnoreCase(type) || "XEDIFACT".equalsIgnoreCase(type) || "ANA".equalsIgnoreCase(type)
				|| "VDA".equalsIgnoreCase(type) || "X12".equalsIgnoreCase(type) || "EFT".equalsIgnoreCase(type)
				|| "ANA".equalsIgnoreCase(type) || "VDA".equalsIgnoreCase(type)) {
			return false;

		} else {
			return true;
		}
	}

	private boolean isX12Type(String type) {
		return XifAttributeStandardType.X12.toString().equals(type);
	}

	private boolean isEdifactType(String type) {
		return XifAttributeStandardType.EFT.toString().equals(type);
	}

	private boolean isTradacomsType(String type) {
		return XifAttributeStandardType.ANA.toString().equals(type);
	}

	private String stripXifUriPrefixSuffix(String xifUri) {
		if (xifUri != null) {
			return xifUri.replaceFirst("dsmkey=", "").replaceFirst("&.*", "");
		}
		return null;
	}

	private String getStaxEventCharacters(XMLEventReader xer, StringBuilder xmlPathBuilder)
			throws XMLStreamException, InvalidApplicationException {
		String characters = null;
		XMLEvent event = xer.nextEvent();

		if (event.isCharacters()) {
			characters = event.asCharacters().getData().trim();
		} else if (event.isEndElement()) {
			String elementName = event.asEndElement().getName().toString();
			removeElementFromXmlPathBuilder(elementName, xmlPathBuilder);
		} else {
			String message = "next Stax event was not Characters or EndElement";
			//log.error(message);
			throw new IllegalStateException(message);
		}

		return characters;
	}

	private boolean isPrimaryKeyAttribute(String value) {
		return XifAttributeLabelKey.primary.toString().equals(value);
	}

	private boolean isSecondaryKeyAttribute(String value) {
		return XifAttributeLabelKey.secondary.toString().equals(value);
	}

	private String getEvent(String fileName) throws Exception {
		URL url = Resources.getResource(fileName);
		return Resources.toString(url, Charsets.UTF_8);
	}

	private void buildFileEvent(FileIntrospectedEventVO fileEvent, List<EnvelopeVO> saveEnvelopeList,
			List<DocumentVO> saveDocumentList, List<ContentErrorEventVO> saveContentErrorList,
			List<ContentFileData> saveContentFileList) {
		int inputUdfDocumentCount = 0;
		int inputUdfFileCount = 0;
		int inputEdiFileCount = 0;
		int inputEdiDocumentCount = 0;
		int inputInterchangeCount = 0;

		int outputUdfDocumentCount = 0;
		int outputUdfFileCount = 0;
		int outputEdiFileCount = 0;
		int outputEdiDocumentCount = 0;
		int outputInterchangeCount = 0;
		int errorCount = 0;

		inputUdfDocumentCount = (int) saveDocumentList.stream()
				.filter(d -> isUDFType(d.getDocumentStandard()) && d.getFileType().equals("INPUT")).count();

		//log.info("inputUdfDocumentCount  {} ", inputUdfDocumentCount);

		outputUdfDocumentCount = (int) saveDocumentList.stream()
				.filter(d -> isUDFType(d.getDocumentStandard()) && d.getFileType().equals("OUTPUT")).count();

		inputEdiDocumentCount = (int) saveDocumentList.stream()
				.filter(d -> isEdiType(d.getDocumentStandard()) && d.getFileType().equals("INPUT")).count();

		//log.info("inputEdiDocumentCount  {} ", inputEdiDocumentCount);

		outputEdiDocumentCount = (int) saveDocumentList.stream()
				.filter(d -> isEdiType(d.getDocumentStandard()) && d.getFileType().equals("OUTPUT")).count();

		inputInterchangeCount = (int) saveEnvelopeList.stream()
				.filter(i -> i.getEnvelopeLevel().equalsIgnoreCase("INTERCHANGE") && i.getFileType().equals("INPUT"))
				.count();

		outputInterchangeCount = (int) saveEnvelopeList.stream()
				.filter(i -> i.getEnvelopeLevel().equalsIgnoreCase("INTERCHANGE") && i.getFileType().equals("OUTPUT"))
				.count();

		inputEdiFileCount = (int) saveContentFileList.stream()
				.filter(f -> isEdiType(f.getDataType()) && f.getFileType().equals("INPUT")).count();

		outputEdiFileCount = (int) saveContentFileList.stream()
				.filter(f -> isEdiType(f.getDataType()) && f.getFileType().equals("OUTPUT")).count();

		inputUdfFileCount = (int) saveContentFileList.stream()
				.filter(f -> isUDFType(f.getDataType()) && f.getFileType().equals("INPUT")).count();

		outputUdfFileCount = (int) saveContentFileList.stream()
				.filter(f -> isUDFType(f.getDataType()) && f.getFileType().equals("OUTPUT")).count();

		if (saveContentErrorList != null)
			errorCount = saveContentErrorList.size();

		fileEvent.setErrorCount(errorCount);
		fileEvent.setInputEdiDocumentCount(inputEdiDocumentCount);
		fileEvent.setOutputEdiDocumentCount(outputEdiDocumentCount);
		fileEvent.setInputUdfDocumentCount(inputUdfDocumentCount);
		fileEvent.setInputInterchangeCount(inputInterchangeCount);
		fileEvent.setInputEdiFileCount(inputEdiFileCount);
		fileEvent.setInputUdfFileCount(inputUdfFileCount);
		fileEvent.setOutputEdiFileCount(outputEdiFileCount);
		fileEvent.setOutputInterchangeCount(outputInterchangeCount);
		fileEvent.setOutputUdfDocumentCount(outputUdfDocumentCount);
		fileEvent.setOutputUdfFileCount(outputUdfFileCount);

	}

	private void appendElementToXmlPathBuilder(String elementName, StringBuilder xmlPathBuilder) {
		xmlPathBuilder.append(PATH_SEPARATOR + elementName);
	}

	private void removeElementFromXmlPathBuilder(String elementName, StringBuilder xmlPathBuilder) {
		try {
			xmlPathBuilder.delete(xmlPathBuilder.length() - (elementName.length() + 1), xmlPathBuilder.length());
		} catch (RuntimeException re) {
			String message = "could not remove element from xmlPathBuilder: xifUri + elementName[" + elementName
					+ "] xmlPathBuilder[" + xmlPathBuilder.toString() + "]";
			//log.error(message);
			throw new RuntimeException(message, re);
		}
	}

	public static LegacyEventVO fromAvro(XIFIntrospectedEvent legacyEvent) {

		LegacyEventVO legacyEventVO = new LegacyEventVO();

		legacyEventVO.setIntrospectionSource(legacyEvent.getIntrospectionSource());
		legacyEventVO.setRejectedFileCount(legacyEvent.getRejectedFileCount());
		legacyEventVO.setTaskId(legacyEvent.getTaskId());

		legacyEventVO.setProcessId(legacyEvent.getProcessId());
		if(null != legacyEvent.getXifInfo()) {
			legacyEventVO.setXifInfo(fromPayLoadAvro(legacyEvent.getXifInfo()));
		}
		legacyEventVO.setTransactionId(legacyEvent.getTransactionId());

		return legacyEventVO;
	}
	
	public FileIntrospectedEvent toAvroFile(FileIntrospectedEventVO FileIntrospectedEventVO) {

		return FileIntrospectedEvent.newBuilder().setAppFileCount(FileIntrospectedEventVO.getAppFileCount())
				.setBypassFileCount(FileIntrospectedEventVO.getBypassFileCount())
				.setComFileCount(FileIntrospectedEventVO.getComFileCount())
				.setErrorCount(FileIntrospectedEventVO.getErrorCount()).setEventId(FileIntrospectedEventVO.getEventId())
				.setEventTimestamp(FileIntrospectedEventVO.getEventTimestamp())
				.setAppFileCount(FileIntrospectedEventVO.getAppFileCount())
				.setErrorCount(FileIntrospectedEventVO.getErrorCount())
				.setInputEdiDocumentCount(FileIntrospectedEventVO.getInputEdiDocumentCount())
				.setInputEdiFileCount(FileIntrospectedEventVO.getInputEdiFileCount())
				.setInputInterchangeCount(FileIntrospectedEventVO.getInputInterchangeCount())
				.setOutputEdiDocumentCount(FileIntrospectedEventVO.getOutputEdiDocumentCount())
				.setOutputEdiFileCount(FileIntrospectedEventVO.getOutputEdiFileCount())
				.setOutputInterchangeCount(FileIntrospectedEventVO.getOutputInterchangeCount())
				.setOutputUdfDocumentCount(FileIntrospectedEventVO.getOutputUdfDocumentCount())
				.setOutputUdfFileCount(FileIntrospectedEventVO.getOutputUdfFileCount())
				.setInputUdfDocumentCount(FileIntrospectedEventVO.getInputUdfDocumentCount())
				.setInputUdfFileCount(FileIntrospectedEventVO.getInputUdfFileCount())
				.setAssets(toAvroAsset(FileIntrospectedEventVO.getAssets()))
				.setIntrospectionSessionNumber(FileIntrospectedEventVO.getIntrospectionSessionNumber())
				.setIntrospectionSource(FileIntrospectedEventVO.getIntrospectionSource())
				.setProcessId(FileIntrospectedEventVO.getProcessId())
				.setRejectedFileCount(FileIntrospectedEventVO.getRejectedFileCount())
				.setTaskId(FileIntrospectedEventVO.getTaskId())
				.setTransactionId(FileIntrospectedEventVO.getTransactionId())
				.setXifInfo(toPayLoadAvro(FileIntrospectedEventVO.getXifInfo())).build();
	}



	public DocumentEvent toAvroDocument(DocumentVO document) {

		return DocumentEvent.newBuilder().setControlNumber(document.getControlNumber())
				.setIntrospectionSource(
						document.getIntrospectionSource() == null ? "" : document.getIntrospectionSource().toString())
				.setEnvelopeVersion(document.getEnvelopeVersion() == null ? "" : document.getEnvelopeVersion())
				.setContainingParentId(document.getContainingParentId())
				.setContainingParentLevel(document.getContainingParentLevel() == null ? ""
						: document.getContainingParentLevel().toString())
				.setContainingParentType(
						document.getContainingParentType() == null ? "" : document.getContainingParentType().toString())
				.setContentKeys(toAvro(document.getContentKeys()))
				.setControlNumberLevel1(document.getControlNumberEnvelopeLevel1() == null ? ""
						: document.getControlNumberEnvelopeLevel1())
				.setControlNumberLevel2(document.getControlNumberEnvelopeLevel2() == null ? ""
						: document.getControlNumberEnvelopeLevel2())
				.setDocumentId(document.getDocumentId())
				.setDocumentStandardVersion(
						document.getDocumentStandardVersion() == null ? "" : document.getDocumentStandardVersion())
				.setDocumentType(document.getDocumentType())
				.setProcessId(document.getProcessId() == null ? "" : document.getProcessId())
				.setReceiverAddress(document.getReceiverAddress()).setSenderAddress(document.getSenderAddress())
				.setSentDate(document.getSentDate() == null ? "" : document.getSentDate())
				.setSentTime(document.getSentTime() == null ? "" : document.getSentTime())
				.setTaskId(document.getTaskId())
				.setDocumentStandard(document.getDocumentStandard() == null ? "" : document.getDocumentStandard())
				.setFileInfo(toPayLoadAvro(document.getXifInfo()))
				.setEventId(document.getEventId() == null ? "" : document.getEventId())
				.setEventTimestamp(document.getEventTimestamp()).setIntrospectionType(document.getIntrospectionType())
				.setBusinessDocumentId(document.getBusinessDocumentId() == null ? "" : document.getBusinessDocumentId())
				.setTransactionId(document.getTransactionId() == null ? "" : document.getTransactionId())
				.setSenderAddressEnvelopeLevel1(document.getSenderAddressEnvelopeLevel1() == null ? ""
						: document.getSenderAddressEnvelopeLevel1())
				.setSenderAddressEnvelopeLevel2(document.getSenderAddressEnvelopeLevel2() == null ? ""
						: document.getSenderAddressEnvelopeLevel2())
				.setReceiverAddressEnvelopeLevel1(document.getReceiverAddressEnvelopeLevel1() == null ? ""
						: document.getReceiverAddressEnvelopeLevel1())
				.setReceiverAddressEnvelopeLevel2(document.getReceiverAddressEnvelopeLevel2() == null ? ""
						: document.getReceiverAddressEnvelopeLevel2())
				.setFunctionalCodeEnvelopeLevel2(document.getFunctionalCodeEnvelopeLevel2() == null ? ""
						: document.getFunctionalCodeEnvelopeLevel2())
				.build();

	}
	
	public EnvelopeEvent toAvroEnvelope(EnvelopeVO envelope) {

		return EnvelopeEvent.newBuilder().setContentKeys(toAvro(envelope.getContentKeys()))
				.setIntrospectionSource(
						envelope.getIntrospectionSource() == null ? "" : envelope.getIntrospectionSource())
				.setDirectEnvelopeCount(envelope.getDirectEnvelopeCount())
				.setDirectDocumentCount(envelope.getDirectDocumentCount())
				.setContainingParentId(envelope.getContainingParentId() == null ? "" : envelope.getContainingParentId())
				.setContainingParentLevel(
						envelope.getContainingParentLevel() == null ? "" : envelope.getContainingParentLevel())
				.setContainingParentType(
						envelope.getContainingParentType() == null ? "" : envelope.getContainingParentType())
				.setControlNumber(envelope.getControlNumber() == null ? "" : envelope.getControlNumber())
				.setTransactionId(envelope.getTransactionId() == null ? "" : envelope.getTransactionId())
				.setTaskId(envelope.getTaskId() == null ? "" : envelope.getTaskId()).setSentTime(envelope.getSentTime())
				.setSentDate(envelope.getSentDate())
				.setSenderAddress(envelope.getSenderAddress() == null ? "" : envelope.getSenderAddress())
				.setSegmentTerminator(envelope.getSegmentTerminator() == null ? "" : envelope.getSegmentTerminator())
				.setReceiverAddress(envelope.getReceiverAddress() == null ? "" : envelope.getReceiverAddress())
				.setProductionTestFlag(envelope.getProductionTestFlag() == null ? "" : envelope.getProductionTestFlag())
				.setProcessId(envelope.getProcessId() == null ? "" : envelope.getProcessId())
				.setDocumentFunctionalClassification(envelope.getDocumentFunctionalClassification() == null ? ""
						: envelope.getDocumentFunctionalClassification())
				.setEnvelopeVersion(envelope.getEnvelopeVersion())
				.setEnvelopeType(envelope.getEnvelopeType() == null ? "" : envelope.getEnvelopeType())
				.setEnvelopeLevel(envelope.getEnvelopeLevel() == null ? "" : envelope.getEnvelopeLevel())
				.setEnvelopeId(envelope.getEnvelopeId() == null ? "" : envelope.getEnvelopeId())
				.setFileInfo(toPayLoadAvro(envelope.getXifInfo()))
				.setEventId(envelope.getEventId() == null ? "" : envelope.getEventId())
				.setEventTimestamp(envelope.getEventTimestamp()).setIntrospectionType(envelope.getIntrospectionType())
				.setDocumentType(envelope.getDocumentType() == null ? "" : envelope.getDocumentType()).build();
	}
	
	public ContentErrorEvent toAvroContentError(ContentErrorEventVO error) {

		ErrorInfo errorInfo = null;

		if (error == null) {
			return null;
		}

		ErrorInfoVO modelErrorInfo = error.getErrorInfo();

		if (modelErrorInfo != null) {

			ErrorInfo.Builder builder = ErrorInfo.newBuilder();

			// errorInfo = ErrorInfo.newBuilder()
			builder.setErrorCode(modelErrorInfo.getErrorCode() == null ? "" : modelErrorInfo.getErrorCode());
			builder.setIsPayloadRelated(modelErrorInfo.isPayloadRelated());
			builder.setExternalErrorMessage(
					modelErrorInfo.getExternalErrorMessage() == null ? "" : modelErrorInfo.getExternalErrorMessage());
			builder.setInternalErrorMessage(
					modelErrorInfo.getInternalErrorMessage() == null ? "" : modelErrorInfo.getInternalErrorMessage());
			errorInfo = builder.build();
		}

		ContentErrorEvent.Builder builder2 = ContentErrorEvent.newBuilder();
		builder2.setErrorId(error.getErrorId() == null ? "" : error.getErrorId());
		builder2.setTransactionId(error.getTransactionId() == null ? "" : error.getTransactionId());
		builder2.setTaskId(error.getTaskId() == null ? "" : error.getTaskId());
		builder2.setProcessId(error.getProcessId() == null ? "" : error.getProcessId());
		builder2.setPosition(error.getPosition());
		builder2.setOffset(error.getOffset());
		builder2.setErrorLevel(error.getErrorLevel() == null ? "" : error.getErrorLevel());
		builder2.setErrorInfo(errorInfo);
		builder2.setEnvelopeId(error.getEnvelopeId() == null ? "" : error.getEnvelopeId());
		builder2.setDocumentId(error.getDocumentId() == null ? "" : error.getDocumentId());
		builder2.setContentFileId(error.getContentFileId() == null ? "" : error.getContentFileId());

		return builder2.build();

	}

	private static PayloadRefVO fromPayLoadAvro(PayloadRef payloadRef) {

		PayloadRefVO payloadRefVO = new PayloadRefVO();

		payloadRefVO.setPayloadId(payloadRef.getPayloadId());
		payloadRefVO.setPayloadType(payloadRef.getPayloadType());

		return payloadRefVO;
	}

	private com.opentext.bn.converters.avro.entity.PayloadRef toPayLoadAvro(PayloadRefVO payloadRef) {

		return com.opentext.bn.converters.avro.entity.PayloadRef.newBuilder()
				.setPayloadType(payloadRef.getPayloadType().toString()).setPayloadId(payloadRef.getPayloadId()).build();
	}

	private List<ContentKey> toAvro(List<ContentKeyVO> keys) {
		List<ContentKey> lContentKeys = new ArrayList<ContentKey>();

		if (keys != null) {
			for (ContentKeyVO key : keys.toArray(new ContentKeyVO[keys.size()])) {

				ContentKey avroKey = ContentKey.newBuilder()
						.setKeyValue(key.getKeyValue() == null ? "" : key.getKeyValue())
						.setKeyType(key.getKeyType() == null ? "" : key.getKeyType())
						.setKeyName(key.getKeyName() == null ? "" : key.getKeyName()).build();
				lContentKeys.add(avroKey);
			}

		}
		return lContentKeys;
	}
	
	public List<Asset> toAvroAsset(List<AssetVO> keys) {
		List<Asset> Assetkeys = new ArrayList<Asset>();

		if (keys != null) {
			for (AssetVO key : keys.toArray(new AssetVO[keys.size()])) {

				Asset avroKey = Asset.newBuilder().setAssetId(key.getAssetId() == null ? "" : key.getAssetId())
						.setAssetName(key.getAssetName() == null ? "" : key.getAssetName())
						.setAssetType(key.getAssetType() == null ? "" : key.getAssetType())
						.setAssetDescription(key.getAssetDescription() == null ? "" : key.getAssetDescription())
						.setXifInfo(toPayLoadAvro(key.getXifInfo())).build();
				Assetkeys.add(avroKey);
			}

		}
		return Assetkeys;
	}

	public static void main(String[] args) {

		try {
			String testFilePath = "src/test/resources/testfiles/legacyEvent.txt";

			String eventJsonString = new String(Files.readAllBytes(Paths.get(testFilePath)));
			System.out.println("legacyEventJsonString 1: " + eventJsonString);

			String transactionId = UUID.randomUUID().toString();

			//String id = UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 12);
			//variable("payloadId", "Q14D-201601000000" + id);
			// "payloadId": "Q14E-201908000000000155856659"

			DSMRestClient restClient = new DSMRestClient();
			String xifFilePath = "src/test/resources/testfiles/xif.xml";
			String xifXml = new String(Files.readAllBytes(Paths.get(xifFilePath)));
			String payloadIdUploaded = restClient.uploadXIFFileFromDSM(xifFilePath);
			System.out.println("payloadIdUploaded: " + payloadIdUploaded);

			eventJsonString = TestHelper.setVariableValues(eventJsonString, transactionId, payloadIdUploaded);

			System.out.println("legacyEventJsonString 2: " + eventJsonString);

			ObjectMapper mapper = new ObjectMapper();
			XIFIntrospectedEvent envelope = mapper.readValue(eventJsonString, XIFIntrospectedEvent.class);

			LegacyEventVO legacyVO = XIFParser.fromAvro(envelope);

			FileIntrospectedEventVO fileEvent = new FileIntrospectedEventVO();
			List<EnvelopeVO> saveEnvelopeList = new ArrayList<EnvelopeVO>();
			List<DocumentVO> saveDocumentList = new ArrayList<DocumentVO>();
			List<ContentErrorEventVO> saveContentErrorList = new ArrayList<ContentErrorEventVO>();
			List<ContentFileData> saveContentFileList = new ArrayList<ContentFileData>();		

			new XIFParser().buildEvents(legacyVO, xifXml, fileEvent, saveEnvelopeList, saveDocumentList,
					saveContentErrorList, saveContentFileList);

		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
