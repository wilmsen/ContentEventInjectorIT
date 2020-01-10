/*
 * Copyright 2006-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.opentext.bn.content;

import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.annotations.CitrusXmlTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.testng.TestNGCitrusTestRunner;
import com.consol.citrus.exceptions.ActionTimeoutException;
import com.consol.citrus.exceptions.CitrusRuntimeException;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.MessageType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opentext.bn.content.parser.DSMRestClient;
import com.opentext.bn.content.parser.XIFParser;
import com.opentext.bn.content.parser.entity.ContentErrorEventVO;
import com.opentext.bn.content.parser.entity.ContentFileData;
import com.opentext.bn.content.parser.entity.DocumentVO;
import com.opentext.bn.content.parser.entity.EnvelopeVO;
import com.opentext.bn.content.parser.entity.FileIntrospectedEventVO;
import com.opentext.bn.content.parser.entity.LegacyEventVO;
import com.opentext.bn.converters.avro.entity.ContentErrorEvent;
import com.opentext.bn.converters.avro.entity.DocumentEvent;
import com.opentext.bn.converters.avro.entity.EnvelopeEvent;
import com.opentext.bn.converters.avro.entity.FileIntrospectedEvent;
import com.opentext.bn.converters.avro.entity.XIFIntrospectedEvent;

import com.consol.citrus.kafka.endpoint.KafkaEndpoint;
import com.consol.citrus.kafka.message.KafkaMessage;
import com.consol.citrus.validation.json.JsonMappingValidationCallback;
import com.consol.citrus.variable.VariableUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ContentMetaDataInjectorIT extends TestNGCitrusTestRunner {

	@Autowired
	@Qualifier("citrusKafkaEndpoint")
	private KafkaEndpoint citrusKafkaEndpoint;

	@Autowired
	@Qualifier("legacyKafkaEndpoint")
	private KafkaEndpoint legacyKafkaEndpoint;

	@Autowired
	@Qualifier("documentKafkaEndpoint")
	private KafkaEndpoint documentKafkaEndpoint;

	@Autowired
	@Qualifier("envelopeKafkaEndpoint")
	private KafkaEndpoint envelopeKafkaEndpoint;

	@Autowired
	@Qualifier("fileKafkaEndpoint")
	private KafkaEndpoint fileKafkaEndpoint;

	@Autowired
	@Qualifier("contentErrorKafkacEndpoint")
	private KafkaEndpoint contentErrorKafkacEndpoint;

	@Test
	@CitrusTest
	public void process_LegacyEventIT() {
		try {
			String testFilePath = "src/test/resources/testfiles/legacyEvent.txt";

			String eventJsonString = new String(Files.readAllBytes(Paths.get(testFilePath)));
			System.out.println("legacyEventJsonString 1: " + eventJsonString);

			String transactionId = UUID.randomUUID().toString();

			//String id = UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 12);
			//variable("payloadId", "Q14D-201601000000" + id);
			// "payloadId": "Q14E-201908000000000155856659"

			DSMRestClient restClient = new DSMRestClient();
			String xifFilePath = "src/test/resources/testfiles/xif2.xml";
			String xifXml = new String(Files.readAllBytes(Paths.get(xifFilePath)));
			xifXml = TestHelper.setVariableValuesForXIF(xifXml);
			System.out.println("xifXml: " + xifXml);
			String path = "src/test/resources/testfiles/xifUploaded.xml";
			//Files.writeString(Paths.get(path), xifXml);
			Files.write(Paths.get(path), xifXml.getBytes());
			String payloadIdUploaded = restClient.uploadXIFFileFromDSM(path);
			System.out.println("payloadIdUploaded: " + payloadIdUploaded);

			eventJsonString = TestHelper.setVariableValues(eventJsonString, transactionId, payloadIdUploaded);

			System.out.println("legacyEventJsonString 2: " + eventJsonString);

			ObjectMapper mapper = new ObjectMapper();
			XIFIntrospectedEvent xifIntrospectedEvent = mapper.readValue(eventJsonString, XIFIntrospectedEvent.class);

			LegacyEventVO legacyVO = XIFParser.fromAvro(xifIntrospectedEvent);

			FileIntrospectedEventVO fileEventVO = new FileIntrospectedEventVO();
			List<EnvelopeVO> saveEnvelopeList = new ArrayList<EnvelopeVO>();
			List<DocumentVO> saveDocumentList = new ArrayList<DocumentVO>();
			List<ContentErrorEventVO> saveContentErrorList = new ArrayList<ContentErrorEventVO>();
			List<ContentFileData> saveContentFileList = new ArrayList<ContentFileData>();		

			//FileIntrospectedEvent fileIntrospectedEvent = new FileIntrospectedEvent();
			List<EnvelopeEvent> envelopeEventList = new ArrayList<EnvelopeEvent>();
			List<DocumentEvent> documentEventList = new ArrayList<DocumentEvent>();
			List<ContentErrorEvent> contentErrorEventList = new ArrayList<ContentErrorEvent>();

			XIFParser xifParser = new XIFParser();

			xifParser.buildEvents(legacyVO, xifXml, fileEventVO, saveEnvelopeList, saveDocumentList,
					saveContentErrorList, saveContentFileList);
			//echo("fileEventVO eventId = " + fileEventVO.getEventId());

			for(EnvelopeVO envelopeVO: saveEnvelopeList) {
				envelopeEventList.add(xifParser.toAvroEnvelope(envelopeVO));
				echo("envelope ContainingParentType = " + envelopeVO.getContainingParentType());
				echo("envelope ContainingParentId = " + envelopeVO.getContainingParentId());				
			}

			for(DocumentVO documentVO: saveDocumentList) {
				documentEventList.add(xifParser.toAvroDocument(documentVO));
				echo("document ContainingParentType = " + documentVO.getContainingParentType());
				echo("document ContainingParentId = " + documentVO.getContainingParentId());

			}

			for(ContentErrorEventVO contentErrorVO: saveContentErrorList) {
				contentErrorEventList.add(xifParser.toAvroContentError(contentErrorVO));
			}
			FileIntrospectedEvent fileIntrospectedEvent = xifParser.toAvroFile(fileEventVO);
			//echo("fileIntrospectedEvent eventId = " + fileIntrospectedEvent.getEventId());

			send(sendMessageBuilder -> {

				sendMessageBuilder.endpoint(citrusKafkaEndpoint).message(new KafkaMessage(xifIntrospectedEvent)
						.topic("visibility.platform.introspection").messageKey(transactionId));

			});

			sleep(60000);

			int i = 0;
			
			/*
			 * echo("Receive XIF Introspected Event Start - visibility.platform.introspection"
			 * ); receive(receiveMessageBuilder -> {
			 * receiveMessageBuilder.endpoint(legacyKafkaEndpoint).messageType(MessageType.
			 * BINARY) .message(new
			 * KafkaMessage(xifIntrospectedEvent).messageKey(transactionId)).
			 * validationCallback( new
			 * JsonMappingValidationCallback<XIFIntrospectedEvent>(XIFIntrospectedEvent.
			 * class, mapper) {
			 * 
			 * @Override public void validate(XIFIntrospectedEvent payload, Map<String,
			 * Object> headers, TestContext context) { // TODO Auto-generated method stub
			 * Assert.assertNotNull(payload);
			 * Assert.assertNotNull("missing transaction Id in the payload: ",
			 * payload.getTransactionId());
			 * Assert.assertNotNull("missing citrus_kafka_messageKey: ",
			 * String.valueOf(headers.get("citrus_kafka_messageKey")));
			 * Assert.assertEquals(String.valueOf(headers.get("citrus_kafka_messageKey")),
			 * payload.getTransactionId(), "transactionid is not matching with kafka key");
			 * 
			 * // Compare all properties except processId List<String> ef = new
			 * ArrayList<String>(); ef.add("processId"); String resultStr =
			 * TestHelper.haveSamePropertyValuesExcludeFields(xifIntrospectedEvent, payload,
			 * ef);
			 * 
			 * echo("Validation Result: " + resultStr.toString()); Boolean result =
			 * resultStr.isEmpty()? true: false;
			 * Assert.assertEquals(Boolean.TRUE.toString(), result.toString(),
			 * "XIFIntrospectedEvent properies not matched"); } }); });
			 * echo("Receive Legacy Event Finish");
			 * 
			 * sleep(60000);
			 */
			
			// Document Event
						echo("The total number of Document Events is: " + documentEventList.size());
						i = 0;
						for(DocumentEvent documentEvent: documentEventList) {
							echo("Receive Document Event Start, index = " + i + " - visibility.introspection.document");
							receive(receiveMessageBuilder -> {
								receiveMessageBuilder.endpoint(documentKafkaEndpoint).messageType(MessageType.BINARY)
								.message(new KafkaMessage(documentEvent).messageKey(transactionId)).validationCallback(
										new JsonMappingValidationCallback<DocumentEvent>(DocumentEvent.class, mapper) {

											@Override
											public void validate(DocumentEvent payload, Map<String, Object> headers, TestContext context) {
												Assert.assertNotNull(payload);
												Assert.assertNotNull("missing transaction Id in the payload: ", payload.getTransactionId());
												Assert.assertNotNull("missing citrus_kafka_messageKey: ", String.valueOf(headers.get("citrus_kafka_messageKey")));
												Assert.assertEquals(String.valueOf(headers.get("citrus_kafka_messageKey")), payload.getTransactionId(), "transactionid is not matching with kafka key");
												// Compare all properties
												// Compare all properties except introspectionType
												List<String> ef = new ArrayList<String>();
												ef.add("contentKeys");
												ef.add("introspectionType");
												ef.add("sentDate");
												ef.add("sentTime");
												ef.add("eventId");
												ef.add("eventTimestamp");
												String resultStr = TestHelper.haveSamePropertyValuesExcludeFields(documentEvent, payload, ef);
												echo("Validation Result: " + resultStr.toString());

												Boolean result = resultStr.isEmpty()? true: false;								
												Assert.assertEquals(Boolean.TRUE.toString(), result.toString(), "DocumentEvent properies not matched: " + resultStr);
											}
										});
							});
							i++;
							echo("Receive Document Event Finish, index = " + i);
						}
						
			
			// Envelope Event
						echo("The total number of Envelope Events is: " + envelopeEventList.size());
						i = 0;
						for(EnvelopeEvent envelopeEvent: envelopeEventList) {
							echo("Receive Envelope Event Start, index = " + i + " - visibility.introspection.envelope");
							receive(receiveMessageBuilder -> {
								receiveMessageBuilder.endpoint(envelopeKafkaEndpoint).messageType(MessageType.BINARY)
								.message(new KafkaMessage(envelopeEvent).messageKey(transactionId)).validationCallback(
										new JsonMappingValidationCallback<EnvelopeEvent>(EnvelopeEvent.class, mapper) {

											@Override
											public void validate(EnvelopeEvent payload, Map<String, Object> headers, TestContext context) {
												Assert.assertNotNull(payload);
												Assert.assertNotNull("missing transaction Id in the payload: ", payload.getTransactionId());
												Assert.assertNotNull("missing citrus_kafka_messageKey: ", String.valueOf(headers.get("citrus_kafka_messageKey")));
												Assert.assertEquals(String.valueOf(headers.get("citrus_kafka_messageKey")), payload.getTransactionId(), "transactionid is not matching with kafka key");
											/*
											 * // Compare all properties except introspectionType List<String> ef = new
											 * ArrayList<String>(); ef.add("introspectionType"); ef.add("sentDate");
											 * ef.add("sentTime"); ef.add("containingParentId");
											 * ef.add("containingParentType"); String resultStr =
											 * TestHelper.haveSamePropertyValuesExcludeFields(envelopeEvent, payload,
											 * ef);
											 * 
											 * echo("Validation Result: " + resultStr.toString()); Boolean result =
											 * resultStr.isEmpty()? true: false;
											 * Assert.assertEquals(Boolean.TRUE.toString(), result.toString(),
											 * "EnvelopeEvent properies not matched: " + resultStr);
											 */
											}
										});
							});
							i++;
							echo("Receive Envelope Event Finish, index = " + i);
						}
			
			
			


			
			
			
			// File Event
			echo("Receive File Event - visibility.introspection.file");
			receive(receiveMessageBuilder -> {
				receiveMessageBuilder.endpoint(fileKafkaEndpoint).messageType(MessageType.BINARY)
				.message(new KafkaMessage(fileIntrospectedEvent).messageKey(transactionId)).validationCallback(
						new JsonMappingValidationCallback<FileIntrospectedEvent>(FileIntrospectedEvent.class,
								mapper) {

							@Override
							public void validate(FileIntrospectedEvent payload, Map<String, Object> headers, TestContext context) {
								Assert.assertNotNull(payload);
								Assert.assertNotNull("missing transaction Id in the payload: ", payload.getTransactionId());
								Assert.assertNotNull("missing citrus_kafka_messageKey: ", String.valueOf(headers.get("citrus_kafka_messageKey")));
								Assert.assertEquals(String.valueOf(headers.get("citrus_kafka_messageKey")), payload.getTransactionId(), "transactionid is not matching with kafka key");
								// Compare all properties
								//String resultStr = TestHelper.haveSamePropertyValues(fileIntrospectedEvent, payload);

								// Compare all properties except introspectionType
								List<String> ef = new ArrayList<String>();
								ef.add("eventId");
								ef.add("assets");
								String resultStr = TestHelper.haveSamePropertyValuesExcludeFields(fileIntrospectedEvent, payload, ef);

								echo("Validation Result: " + resultStr.toString());
								Boolean result = resultStr.isEmpty()? true: false;								
								Assert.assertEquals(Boolean.TRUE.toString(), result.toString(), "FileIntrospectedEvent properies not matched: " + resultStr);
							}
						});
			});
			echo("Receive File Event Finish");
			

			
			// ContentError Event
			echo("The total number of ContentError Events is: " + contentErrorEventList.size());
			i = 0;
			for(ContentErrorEvent contentErrorEvent: contentErrorEventList) {
				echo("Receive ContentError Event Start, index = " + i + " - visibility.introspection.contenterror");
				receive(receiveMessageBuilder -> {
					receiveMessageBuilder.endpoint(contentErrorKafkacEndpoint).messageType(MessageType.BINARY)
					.message(new KafkaMessage(contentErrorEvent).messageKey(transactionId)).validationCallback(
							new JsonMappingValidationCallback<ContentErrorEvent>(ContentErrorEvent.class, mapper) {

								@Override
								public void validate(ContentErrorEvent payload, Map<String, Object> headers, TestContext context) {
									Assert.assertNotNull(payload);
									Assert.assertNotNull("missing transaction Id in the payload: ", payload.getTransactionId());
									Assert.assertNotNull("missing citrus_kafka_messageKey: ", String.valueOf(headers.get("citrus_kafka_messageKey")));
									Assert.assertEquals(String.valueOf(headers.get("citrus_kafka_messageKey")), payload.getTransactionId(), "transactionid is not matching with kafka key");

									// Compare all properties 
									String resultStr = TestHelper.haveSamePropertyValues(contentErrorEvent, payload);
									echo("Validation Result: " + resultStr.toString()); 
									Boolean result = resultStr.isEmpty()? true: false;
									Assert.assertEquals(Boolean.TRUE.toString(), result.toString(), "ContentErrorEvent properies not matched: " + resultStr); 
								}
							});
				});
				i++;
				echo("Receive ContentError Event Finish, index = " + i);
			}			

		} catch (Throwable e) {
			catchException().exception(CitrusRuntimeException.class).when(echo("Only receive one message when duplicate messages sent."));
			e.printStackTrace();
			return;
		}

	}


	@Test
	@CitrusTest 	
	public void process_FileIntrospectedEventIT() {

		try { 
			String testFilePath = "src/test/resources/testfiles/fileIntrospected.txt";

			String eventJsonString = new String(Files.readAllBytes(Paths.get(testFilePath)));
			System.out.println("fileIntrospectedEventJsonString 1: " + eventJsonString);

			String transactionId = UUID.randomUUID().toString();

			echo("transactionId: " + transactionId);

			String payloadId = "Q14E-201912000000000" + UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 12);
			echo("payloadId: " + payloadId);

			eventJsonString = TestHelper.setVariableValues(eventJsonString, transactionId, payloadId);

			System.out.println("fileIntrospectedEventJsonString 2: " + eventJsonString);
			ObjectMapper mapper = new ObjectMapper(); 
			FileIntrospectedEvent envelope = mapper.readValue(eventJsonString, FileIntrospectedEvent.class);
			System.out.println("Envelope Send:");
			System.out.println(TestHelper.toString(envelope));
			send(sendMessageBuilder -> {

				sendMessageBuilder.endpoint(citrusKafkaEndpoint).message( new
						KafkaMessage(envelope).topic("visibility.introspection.file").messageKey(transactionId));

			});

			sleep(60000);

			receive(receiveMessageBuilder -> {
				receiveMessageBuilder.endpoint(fileKafkaEndpoint).messageType(MessageType.BINARY)
				.message(new KafkaMessage(envelope).messageKey(transactionId)).validationCallback(
						new JsonMappingValidationCallback<FileIntrospectedEvent>(FileIntrospectedEvent.class,
								mapper) {

							@Override
							public void validate(FileIntrospectedEvent payload, Map<String, Object> headers, TestContext context) {
								System.out.println("Payload Receive:");
								System.out.println(TestHelper.toString(payload));
								Assert.assertNotNull(payload);
								Assert.assertNotNull("missing transaction Id in the payload: ", payload.getTransactionId());
								Assert.assertNotNull("missing citrus_kafka_messageKey: ", String.valueOf(headers.get("citrus_kafka_messageKey")));
								Assert.assertEquals(String.valueOf(headers.get("citrus_kafka_messageKey")), payload.getTransactionId(), "transactionid is not matching with kafka key");
								// Compare all properties
								String resultStr = TestHelper.haveSamePropertyValues(envelope, payload);
								echo("Validation Result: " + resultStr.toString());
								Boolean result = resultStr.isEmpty()? true: false;								
								Assert.assertEquals(Boolean.TRUE.toString(), result.toString(), "FileIntrospectedEvent properies not matched: " + resultStr);
							}
						});
			});

		}catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace(); }

	}


	@Test
	@CitrusTest 	
	public void process_EnvelopeIntrospectedEventIT() {

		try { 

			String testFilePath = "src/test/resources/testfiles/envelopeIntrospected.txt";

			String eventJsonString = new String(Files.readAllBytes(Paths.get(testFilePath)));
			System.out.println("envelopeIntrospectedEventJsonString 1: " + eventJsonString);

			String transactionId = UUID.randomUUID().toString();
			echo("transactionId: " + transactionId);

			String payloadId = "Q14E-201912000000000" + UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 12);
			echo("payloadId: " + payloadId);

			eventJsonString = TestHelper.setVariableValues(eventJsonString, transactionId, payloadId);

			System.out.println("envelopeIntrospectedEventJsonString 2: " + eventJsonString);

			ObjectMapper mapper = new ObjectMapper(); 
			EnvelopeEvent envelope = mapper.readValue(eventJsonString, EnvelopeEvent.class);

			send(sendMessageBuilder -> {

				sendMessageBuilder.endpoint(citrusKafkaEndpoint).message( new
						KafkaMessage(envelope).topic("visibility.platform.envelope").messageKey(transactionId));

			});

			sleep(60000);

			receive(receiveMessageBuilder -> {
				receiveMessageBuilder.endpoint(envelopeKafkaEndpoint).messageType(MessageType.BINARY)
				.message(new KafkaMessage(envelope).messageKey(transactionId)).validationCallback(
						new JsonMappingValidationCallback<EnvelopeEvent>(EnvelopeEvent.class, mapper) {

							@Override
							public void validate(EnvelopeEvent payload, Map<String, Object> headers, TestContext context) {
								Assert.assertNotNull(payload);
								Assert.assertNotNull("missing transaction Id in the payload: ", payload.getTransactionId());
								Assert.assertNotNull("missing citrus_kafka_messageKey: ", String.valueOf(headers.get("citrus_kafka_messageKey")));
								Assert.assertEquals(String.valueOf(headers.get("citrus_kafka_messageKey")), payload.getTransactionId(), "transactionid is not matching with kafka key");
								// Compare all properties except introspectionType
								List<String> ef = new ArrayList<String>();
								ef.add("introspectionType");
								ef.add("sentDate");
								ef.add("sentTime");
								String resultStr = TestHelper.haveSamePropertyValuesExcludeFields(envelope, payload, ef);

								echo("Validation Result: " + resultStr.toString());
								Boolean result = resultStr.isEmpty()? true: false;								
								Assert.assertEquals(Boolean.TRUE.toString(), result.toString(), "EnvelopeEvent properies not matched: " + resultStr);
							}
						});
			});

		}catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace(); }

	}



	@Test
	@CitrusTest
	public void process_DocumentIntrospectedEventIT() {

		try { 

			String testFilePath = "src/test/resources/testfiles/documentIntrospected.txt";
			// sender address/receiver address/doctype/UUID/control numbers - should be different
			String eventJsonString = new String(Files.readAllBytes(Paths.get(testFilePath)));
			System.out.println("documentIntrospectedEventJsonString 1: " + eventJsonString);

			String transactionId = UUID.randomUUID().toString();

			echo("transactionId: " + transactionId);

			String payloadId = "Q14E-201912000000000" + UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 12);
			echo("payloadId: " + payloadId);

			eventJsonString = TestHelper.setVariableValues(eventJsonString, transactionId, payloadId);

			System.out.println("documentIntrospectedEventJsonString 2: " + eventJsonString);

			ObjectMapper mapper = new ObjectMapper(); 
			DocumentEvent envelope = mapper.readValue(eventJsonString, DocumentEvent.class);

			send(sendMessageBuilder -> {

				sendMessageBuilder.endpoint(citrusKafkaEndpoint).message( new
						KafkaMessage(envelope).topic("visibility.platform.document").messageKey(transactionId));

			});

			sleep(60000);

			receive(receiveMessageBuilder -> {
				receiveMessageBuilder.endpoint(documentKafkaEndpoint).messageType(MessageType.BINARY)
				.message(new KafkaMessage(envelope).messageKey(transactionId)).validationCallback(
						new JsonMappingValidationCallback<DocumentEvent>(DocumentEvent.class, mapper) {

							@Override
							public void validate(DocumentEvent payload, Map<String, Object> headers, TestContext context){
								Assert.assertNotNull(payload);
								Assert.assertNotNull("missing transaction Id in the payload: ", payload.getTransactionId());
								Assert.assertNotNull("missing citrus_kafka_messageKey: ", String.valueOf(headers.get("citrus_kafka_messageKey")));
								Assert.assertEquals(String.valueOf(headers.get("citrus_kafka_messageKey")), payload.getTransactionId(), "transactionid is not matching with kafka key");
								// Compare all properties
								// Compare all properties except introspectionType
								List<String> ef = new ArrayList<String>();
								ef.add("contentKeys");
								ef.add("introspectionType");
								ef.add("sentDate");
								ef.add("sentTime");
								String resultStr = TestHelper.haveSamePropertyValuesExcludeFields(envelope, payload, ef);
								echo("Validation Result: " + resultStr.toString());

								Boolean result = resultStr.isEmpty()? true: false;								
								Assert.assertEquals(Boolean.TRUE.toString(), result.toString(), "DocumentEvent properies not matched: " + resultStr);
							}
						});
			});

		}catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace(); }

	}

	@Test
	@CitrusTest
	public void process_ContentErrorEventIT() {

		try { 

			String testFilePath = "src/test/resources/testfiles/contentError.txt";

			String eventJsonString = new String(Files.readAllBytes(Paths.get(testFilePath)));
			System.out.println("contentErrorEventJsonString 1: " + eventJsonString);

			String transactionId = UUID.randomUUID().toString();

			echo("transactionId: " + transactionId);

			eventJsonString = TestHelper.setVariableValues(eventJsonString, transactionId, "");

			System.out.println("contentErrorEventJsonString 2: " + eventJsonString);

			ObjectMapper mapper = new ObjectMapper(); 
			ContentErrorEvent envelope = mapper.readValue(eventJsonString, ContentErrorEvent.class);

			send(sendMessageBuilder -> {
				sendMessageBuilder.endpoint(citrusKafkaEndpoint)
				.message( new KafkaMessage(envelope).topic("visibility.platform.contenterror").messageKey(transactionId));

			});

			sleep(60000);

			receive(receiveMessageBuilder -> {
				receiveMessageBuilder.endpoint(contentErrorKafkacEndpoint)
				.messageType( MessageType.BINARY)
				.message(new  KafkaMessage(envelope).messageKey(transactionId))
				.validationCallback( new JsonMappingValidationCallback<ContentErrorEvent>(ContentErrorEvent.class, mapper) {

					@Override 
					public void validate(ContentErrorEvent payload, Map<String, Object> headers, TestContext context) {
						Assert.assertNotNull(payload);
						Assert.assertNotNull("missing transaction Id in the payload: ", payload.getTransactionId());
						Assert.assertNotNull("missing citrus_kafka_messageKey: ", String.valueOf(headers.get("citrus_kafka_messageKey")));
						Assert.assertEquals(String.valueOf(headers.get("citrus_kafka_messageKey")), payload.getTransactionId(), "transactionid is not matching with kafka key");

						// Compare all properties 
						String resultStr = TestHelper.haveSamePropertyValues(envelope, payload);
						echo("Validation Result: " + resultStr.toString()); 
						Boolean result = resultStr.isEmpty()? true: false;
						Assert.assertEquals(Boolean.TRUE.toString(), result.toString(), "ContentErrorEvent properies not matched: " + resultStr); 
					} 
				}); 
			});

		}catch (Exception e) {			
			e.printStackTrace(); 
		}
	}


	@Test
	@CitrusTest 	
	public void process_EnvelopeIntrospectedDuplicateEventsIT() {

		try { 

			String testFilePath = "src/test/resources/testfiles/envelopeIntrospected.txt";

			String eventJsonString = new String(Files.readAllBytes(Paths.get(testFilePath)));
			System.out.println("envelopeIntrospectedEventJsonString 1: " + eventJsonString);

			String transactionId = UUID.randomUUID().toString();
			echo("transactionId: " + transactionId);

			String payloadId = "Q14E-201912000000000" + UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 12);
			echo("payloadId: " + payloadId);

			eventJsonString = TestHelper.setVariableValues(eventJsonString, transactionId, payloadId);

			System.out.println("envelopeIntrospectedEventJsonString 2: " + eventJsonString);

			ObjectMapper mapper = new ObjectMapper(); 
			EnvelopeEvent envelope1 = mapper.readValue(eventJsonString, EnvelopeEvent.class);
			EnvelopeEvent envelope2 = mapper.readValue(eventJsonString, EnvelopeEvent.class);

			send(sendMessageBuilder -> {

				sendMessageBuilder.endpoint(citrusKafkaEndpoint).message( new
						KafkaMessage(envelope1).topic("visibility.platform.envelope").messageKey(transactionId));

			});

			send(sendMessageBuilder -> {

				sendMessageBuilder.endpoint(citrusKafkaEndpoint).message( new
						KafkaMessage(envelope2).topic("visibility.platform.envelope").messageKey(transactionId));

			});

			sleep(60000);

			receive(receiveMessageBuilder -> {
				receiveMessageBuilder.endpoint(envelopeKafkaEndpoint).messageType(MessageType.BINARY)
				.message(new KafkaMessage(envelope1).messageKey(transactionId)).validationCallback(
						new JsonMappingValidationCallback<EnvelopeEvent>(EnvelopeEvent.class, mapper) {

							@Override
							public void validate(EnvelopeEvent payload, Map<String, Object> headers, TestContext context) {
								Assert.assertNotNull(payload);
								Assert.assertNotNull("missing transaction Id in the payload: ", payload.getTransactionId());
								Assert.assertNotNull("missing citrus_kafka_messageKey: ", String.valueOf(headers.get("citrus_kafka_messageKey")));
								Assert.assertEquals(String.valueOf(headers.get("citrus_kafka_messageKey")), payload.getTransactionId(), "transactionid is not matching with kafka key");
								// Compare all properties except introspectionType
								List<String> ef = new ArrayList<String>();
								ef.add("introspectionType");
								ef.add("sentDate");
								ef.add("sentTime");
								String resultStr = TestHelper.haveSamePropertyValuesExcludeFields(envelope1, payload, ef);

								echo("Validation Result: " + resultStr.toString());
								Boolean result = resultStr.isEmpty()? true: false;								
								Assert.assertEquals(Boolean.TRUE.toString(), result.toString(), "EnvelopeEvent properies not matched: " + resultStr);
							}
						});
			});

			receive(receiveMessageBuilder -> {
				receiveMessageBuilder.endpoint(envelopeKafkaEndpoint).messageType(MessageType.BINARY)
				.message(new KafkaMessage(envelope2).messageKey(transactionId));
			});

		}catch (Exception e) { 
			//echo("Exception is: " + e.getMessage());
			catchException().exception(CitrusRuntimeException.class).when(echo("Only receive one message when duplicate messages sent."));
			Assert.assertTrue(e.getMessage().startsWith("Failed to receive message from Kafka topic 'visibility.introspection.envelope'"), e.getMessage());
			e.printStackTrace(); 
		}	

		catchException().exception(CitrusRuntimeException.class).when(echo("Catch Exception for envelope event duplicate check"));

	}

	@Test
	@CitrusTest
	public void process_DocumentIntrospectedDuplicateEventsIT() {

		try { 

			String testFilePath = "src/test/resources/testfiles/documentIntrospected.txt";
			// sender address/receiver address/doctype/UUID/control numbers - should be different
			String eventJsonString = new String(Files.readAllBytes(Paths.get(testFilePath)));
			System.out.println("documentIntrospectedEventJsonString 1: " + eventJsonString);

			String transactionId = UUID.randomUUID().toString();

			echo("transactionId: " + transactionId);

			String payloadId = "Q14E-201912000000000" + UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 12);
			echo("payloadId: " + payloadId);

			eventJsonString = TestHelper.setVariableValues(eventJsonString, transactionId, payloadId);

			System.out.println("documentIntrospectedEventJsonString 2: " + eventJsonString);

			ObjectMapper mapper = new ObjectMapper(); 
			DocumentEvent envelope1 = mapper.readValue(eventJsonString, DocumentEvent.class);
			DocumentEvent envelope2 = mapper.readValue(eventJsonString, DocumentEvent.class);

			send(sendMessageBuilder -> {
				sendMessageBuilder.endpoint(citrusKafkaEndpoint).message( new
						KafkaMessage(envelope1).topic("visibility.platform.document").messageKey(transactionId));

			});

			send(sendMessageBuilder -> {
				sendMessageBuilder.endpoint(citrusKafkaEndpoint).message( new
						KafkaMessage(envelope2).topic("visibility.platform.document").messageKey(transactionId));

			});

			sleep(60000);

			receive(receiveMessageBuilder -> {
				receiveMessageBuilder.endpoint(documentKafkaEndpoint).messageType(MessageType.BINARY)
				.message(new KafkaMessage(envelope1).messageKey(transactionId)).validationCallback(
						new JsonMappingValidationCallback<DocumentEvent>(DocumentEvent.class, mapper) {

							@Override
							public void validate(DocumentEvent payload, Map<String, Object> headers, TestContext context){
								Assert.assertNotNull(payload);
								Assert.assertNotNull("missing transaction Id in the payload: ", payload.getTransactionId());
								Assert.assertNotNull("missing citrus_kafka_messageKey: ", String.valueOf(headers.get("citrus_kafka_messageKey")));
								Assert.assertEquals(String.valueOf(headers.get("citrus_kafka_messageKey")), payload.getTransactionId(), "transactionid is not matching with kafka key");
								// Compare all properties
								// Compare all properties except introspectionType
								List<String> ef = new ArrayList<String>();
								ef.add("contentKeys");
								ef.add("introspectionType");
								ef.add("sentDate");
								ef.add("sentTime");
								String resultStr = TestHelper.haveSamePropertyValuesExcludeFields(envelope1, payload, ef);
								echo("Validation Result: " + resultStr.toString());

								Boolean result = resultStr.isEmpty()? true: false;								
								Assert.assertEquals(Boolean.TRUE.toString(), result.toString(), "DocumentEvent properies not matched: " + resultStr);
							}
						});
			});

			receive(receiveMessageBuilder -> {
				receiveMessageBuilder.endpoint(documentKafkaEndpoint).messageType(MessageType.BINARY)
				.message(new KafkaMessage(envelope2).messageKey(transactionId));
			});


		}catch (Exception e) { // TODO Auto-generated catch block
			catchException().exception(CitrusRuntimeException.class).when(echo("Only receive one message when duplicate messages sent."));
			Assert.assertTrue(e.getMessage().startsWith("Failed to receive message from Kafka topic 'visibility.introspection.document'"), e.getMessage());
			e.printStackTrace(); 
		}	

		catchException().exception(CitrusRuntimeException.class).when(echo("Catch Exception for document event duplicate check"));

	}

	@Test
	@CitrusTest
	public void process_ContentErrorDuplicateEventsIT() {
		Boolean firstTest = false;
		try { 

			String testFilePath = "src/test/resources/testfiles/contentError.txt";

			String eventJsonString = new String(Files.readAllBytes(Paths.get(testFilePath)));
			System.out.println("contentErrorEventJsonString 1: " + eventJsonString);

			String transactionId = UUID.randomUUID().toString();

			echo("transactionId: " + transactionId);

			eventJsonString = TestHelper.setVariableValues(eventJsonString, transactionId, "");

			System.out.println("contentErrorEventJsonString 2: " + eventJsonString);

			ObjectMapper mapper = new ObjectMapper(); 
			ContentErrorEvent envelope1 = mapper.readValue(eventJsonString, ContentErrorEvent.class);
			ContentErrorEvent envelope2 = mapper.readValue(eventJsonString, ContentErrorEvent.class);

			send(sendMessageBuilder -> {
				sendMessageBuilder.endpoint(citrusKafkaEndpoint)
				.message( new KafkaMessage(envelope1).topic("visibility.platform.contenterror").messageKey(transactionId));

			});

			send(sendMessageBuilder -> {
				sendMessageBuilder.endpoint(citrusKafkaEndpoint)
				.message( new KafkaMessage(envelope2).topic("visibility.platform.contenterror").messageKey(transactionId));

			});

			sleep(60000);

			receive(receiveMessageBuilder -> {
				receiveMessageBuilder.endpoint(contentErrorKafkacEndpoint)
				.messageType( MessageType.BINARY)
				.message(new  KafkaMessage(envelope1).messageKey(transactionId))
				.validationCallback( new JsonMappingValidationCallback<ContentErrorEvent>(ContentErrorEvent.class, mapper) {

					@Override 
					public void validate(ContentErrorEvent payload, Map<String, Object> headers, TestContext context) {
						Assert.assertNotNull(payload);
						Assert.assertNotNull("missing transaction Id in the payload: ", payload.getTransactionId());
						Assert.assertNotNull("missing citrus_kafka_messageKey: ", String.valueOf(headers.get("citrus_kafka_messageKey")));
						Assert.assertEquals(String.valueOf(headers.get("citrus_kafka_messageKey")), payload.getTransactionId(), "transactionid is not matching with kafka key");

						// Compare all properties 
						String resultStr = TestHelper.haveSamePropertyValues(envelope1, payload);
						echo("Validation Result: " + resultStr.toString()); 
						Boolean result = resultStr.isEmpty()? true: false;
						Assert.assertEquals(Boolean.TRUE.toString(), result.toString(), "ContentErrorEvent properies not matched: " + resultStr); 
					} 
				}); 
			});

			firstTest = true;
			
			receive(receiveMessageBuilder -> {
				receiveMessageBuilder.endpoint(contentErrorKafkacEndpoint)
				.messageType( MessageType.BINARY)
				.message(new  KafkaMessage(envelope1).messageKey(transactionId)); 
			});
			
			Assert.fail("Unexpected second receive successfully. ");

		}catch (Exception e) {
			if(!firstTest) {
				Assert.fail("First receive timeout. " + e.getMessage());
			}
			catchException().exception(CitrusRuntimeException.class).when(echo("Only receive one message when duplicate messages sent."));
			Assert.assertTrue(e.getMessage().startsWith("Failed to receive message from Kafka topic 'visibility.introspection.contenterror'"), e.getMessage());
			e.printStackTrace(); 
		}	

		catchException().exception(CitrusRuntimeException.class).when(echo("Catch Exception for contenterror event duplicate check"));
	}


}
