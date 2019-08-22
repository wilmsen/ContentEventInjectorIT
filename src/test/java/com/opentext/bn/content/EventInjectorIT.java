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

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.annotations.CitrusXmlTest;
import com.consol.citrus.dsl.testng.TestNGCitrusTestRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.kafka.endpoint.KafkaEndpoint;
import com.consol.citrus.kafka.message.KafkaMessage;
import com.consol.citrus.kafka.message.KafkaMessageHeaders;
import com.consol.citrus.message.MessageType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opentext.bn.converters.avro.entity.ContentErrorEvent;
import com.opentext.bn.converters.avro.entity.DocumentEvent;
import com.opentext.bn.converters.avro.entity.EnvelopeEvent;
import com.opentext.bn.converters.avro.entity.XIFIntrospectedEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.testng.annotations.Test;

public class EventInjectorIT extends TestNGCitrusTestRunner {

	@Autowired
	@Qualifier("citrusKafkaEndpoint")
	private KafkaEndpoint citrusKafkaEndpoint;

	@Autowired
	@Qualifier("receiveCompletedKafkaEndpoint")
	private KafkaEndpoint receiveCompletedKafkaEndpoint;
	
	@Autowired
	@Qualifier("documentKafkaEndpoint")
	private KafkaEndpoint documentKafkaEndpoint;
	
	
	@Autowired
	@Qualifier("envelopeKafkaEndpoint")
	private KafkaEndpoint envelopeKafkaEndpoint;
	

	@Test
	@CitrusTest
	public void process_ReceiveCompletedIT() throws IOException {

		/*
		 * String receiveCompletedFilepath =
		 * "src/test/resources/testfiles/receiveCompleted001.txt"; String
		 * controlFile = "src/test/resources/testfiles/controlFile.txt"; String
		 * receiveCompletedJsonString;
		 * 
		 * receiveCompletedJsonString = new
		 * String(Files.readAllBytes(Paths.get(receiveCompletedFilepath)));
		 * http(httpActionBuilder ->
		 * httpActionBuilder.client(eventInjector).send() .post(
		 * "/visibility-event-injector/transactions/12343/tasks/receive/completed")
		 * .payload(receiveCompletedJsonString).accept(MediaType.
		 * APPLICATION_JSON_VALUE)
		 * .payload(receiveCompletedJsonString).contentType("application/json"))
		 * ;
		 * 
		 * http(httpActionBuilder ->
		 * httpActionBuilder.client(eventInjector).receive().response(HttpStatus
		 * .CREATED));
		 * 
		 * String controlFileString = new
		 * String(Files.readAllBytes(Paths.get(controlFile)));
		 * 
		 * ObjectMapper mapper = new ObjectMapper(); ReceiveCompletedEvent
		 * envelope = mapper.readValue(controlFileString,
		 * ReceiveCompletedEvent.class);
		 * 
		 * receive(receiveMessageBuilder ->
		 * receiveMessageBuilder.endpoint(receiveCompletedKafkaEndpoint)
		 * .messageType(MessageType.BINARY).message(new
		 * KafkaMessage(envelope).messageKey("12343")));
		 */

		String receiveCompletedFilepath = "src/test/resources/testfiles/legacyEvent.txt";

		String receiveCompletedJsonString = new String(Files.readAllBytes(Paths.get(receiveCompletedFilepath)));

		ObjectMapper mapper = new ObjectMapper();
		XIFIntrospectedEvent envelope = mapper.readValue(receiveCompletedJsonString, XIFIntrospectedEvent.class);

		send(sendMessageBuilder -> {

			sendMessageBuilder.endpoint(citrusKafkaEndpoint).message(
					new KafkaMessage(envelope).topic("visibility.platform.introspection").messageKey("1234"));

		});

		receive(receiveMessageBuilder -> receiveMessageBuilder.endpoint(receiveCompletedKafkaEndpoint)
				//.header("KafkaMessageHeaders.TOPIC", "visibility.platform.receivecompleted")
				//.header("KafkaMessageHeaders.OFFSET", Matchers.greaterThanOrEqualTo(0))
				.messageType(MessageType.BINARY)
				.message(new KafkaMessage(envelope).messageKey("1234")));

	}
	
	
	@Test
	@CitrusTest
	public void process_documentIT() throws IOException {

		/*
		 * String receiveCompletedFilepath =
		 * "src/test/resources/testfiles/receiveCompleted001.txt"; String
		 * controlFile = "src/test/resources/testfiles/controlFile.txt"; String
		 * receiveCompletedJsonString;
		 * 
		 * receiveCompletedJsonString = new
		 * String(Files.readAllBytes(Paths.get(receiveCompletedFilepath)));
		 * http(httpActionBuilder ->
		 * httpActionBuilder.client(eventInjector).send() .post(
		 * "/visibility-event-injector/transactions/12343/tasks/receive/completed")
		 * .payload(receiveCompletedJsonString).accept(MediaType.
		 * APPLICATION_JSON_VALUE)
		 * .payload(receiveCompletedJsonString).contentType("application/json"))
		 * ;
		 * 
		 * http(httpActionBuilder ->
		 * httpActionBuilder.client(eventInjector).receive().response(HttpStatus
		 * .CREATED));
		 * 
		 * String controlFileString = new
		 * String(Files.readAllBytes(Paths.get(controlFile)));
		 * 
		 * ObjectMapper mapper = new ObjectMapper(); ReceiveCompletedEvent
		 * envelope = mapper.readValue(controlFileString,
		 * ReceiveCompletedEvent.class);
		 * 
		 * receive(receiveMessageBuilder ->
		 * receiveMessageBuilder.endpoint(receiveCompletedKafkaEndpoint)
		 * .messageType(MessageType.BINARY).message(new
		 * KafkaMessage(envelope).messageKey("12343")));
		 */

		String documentFilePath = "src/test/resources/testfiles/documentFile.txt";

		String documentJsonString = new String(Files.readAllBytes(Paths.get(documentFilePath)));

		ObjectMapper mapper = new ObjectMapper();
		DocumentEvent document = mapper.readValue(documentJsonString, DocumentEvent.class);

		/*
		send(sendMessageBuilder -> {

			sendMessageBuilder.endpoint(citrusKafkaEndpoint).message(
					new KafkaMessage(document).topic("visibility.platform.introspection").messageKey("1234"));

		});
*/
		
				
		receive(receiveMessageBuilder -> receiveMessageBuilder.endpoint(documentKafkaEndpoint).timeout(60000)
				//.header("KafkaMessageHeaders.TOPIC", "visibility.platform.receivecompleted")
				//.header("KafkaMessageHeaders.OFFSET", Matchers.greaterThanOrEqualTo(0))
				.messageType(MessageType.BINARY)
				.message(new KafkaMessage(document).messageKey("1234")));

	}
	
	
}
