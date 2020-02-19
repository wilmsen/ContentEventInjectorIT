package com.opentext.bn.content;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opentext.bn.converters.avro.entity.DocumentEvent;
import com.opentext.bn.converters.avro.entity.EnvelopeEvent;

public class TestHelper {

	public static String setVariableValues(String str, String transactionId, String payloadId) {
		String taskId = UUID.randomUUID().toString();
		String processId = UUID.randomUUID().toString();
		String eventId = UUID.randomUUID().toString();
		String assetId = UUID.randomUUID().toString();
		String documentId = UUID.randomUUID().toString();
		String envelopeId = UUID.randomUUID().toString();
		String fileId = UUID.randomUUID().toString();
		String errorId = UUID.randomUUID().toString();
		String controlNumber = UUID.randomUUID().toString().substring(0, 9);


		// ContentError only
		if (str.contains("errorId")) {
			str = str.replaceAll("var_errorId", errorId);

			int start = str.indexOf("errorLevel");
			int end = str.indexOf("offset");
			String s = str.substring(start, end);
			if(s.contains("ENVELOPE")) {
				str = str.replaceAll("var_fileId", "")
						.replaceAll("var_documentId", "")
						.replaceAll("var_envelopeId", envelopeId);
			}else if(s.contains("DOCUMENT")) {
				str = str.replaceAll("var_fileId", "")
						.replaceAll("var_envelopeId", "")
						.replaceAll("var_documentId", documentId);
			}else if(s.contains("FILE")) {
				str = str.replaceAll("var_documentId", "")
						.replaceAll("var_envelopeId", "")
						.replaceAll("var_fileId", fileId);
			}
		}else {
			str = str.replaceAll("var_documentId", documentId)
					.replaceAll("var_envelopeId", envelopeId)
					.replaceAll("var_fileId", fileId);
		}

		str = str.replaceAll("var_transactionId", transactionId == null? "": transactionId)
				.replaceAll("var_taskId", taskId)
				.replaceAll("var_processId", processId)
				.replaceAll("var_payloadId", payloadId == null? "": payloadId)
				.replaceAll("var_assetId", assetId)
				.replaceAll("var_controlNumber", controlNumber)
				.replaceAll("var_eventId", eventId);

		return str;
	}

	public static String setVariableValuesForXIF(String str) {
		String uuid1 = UUID.randomUUID().toString();
		String uuid2 = UUID.randomUUID().toString();
		String uuid3 = UUID.randomUUID().toString();
		String uuid4 = UUID.randomUUID().toString();
		String uuid5 = UUID.randomUUID().toString();
		String uuid6 = UUID.randomUUID().toString();

		String dsmKey1 = "Q14E-201912000000000" + UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 12);
		String dsmKey2 = "Q14E-201912000000000" + UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 12);
		String dsmKey3 = "Q14E-201912000000000" + UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 12);
		String dsmKey4 = "Q14E-201912000000000" + UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 12);
		String dsmKey5 = "Q14E-201912000000000" + UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 12);
		String dsmKey6 = "Q14E-201912000000000" + UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 12);

		String controlNumber1 = UUID.randomUUID().toString().substring(0, 9);
		String controlNumber2 = UUID.randomUUID().toString().substring(0, 9);
		String controlNumber3 = UUID.randomUUID().toString().substring(0, 9);
		String controlNumber4 = UUID.randomUUID().toString().substring(0, 9);
		String controlNumber5 = UUID.randomUUID().toString().substring(0, 9);
		String controlNumber6 = UUID.randomUUID().toString().substring(0, 9);

		String senderId = UUID.randomUUID().toString().substring(0, 10);
		String receiverId = UUID.randomUUID().toString().substring(0, 10);

		String aprf = Integer.toString((int)(Math.random()*((999-100)+1))+100);
		String serviceName = UUID.randomUUID().toString().substring(0, 10);

		str = str.replaceAll("var_uuid1", uuid1)
				.replaceAll("var_uuid2", uuid2)
				.replaceAll("var_uuid3", uuid3)
				.replaceAll("var_uuid4", uuid4)
				.replaceAll("var_uuid5", uuid5)
				.replaceAll("var_uuid6", uuid6)
				.replaceAll("var_controlNumber1", controlNumber1)
				.replaceAll("var_controlNumber2", controlNumber2)
				.replaceAll("var_controlNumber3", controlNumber3)
				.replaceAll("var_controlNumber4", controlNumber4)
				.replaceAll("var_controlNumber5", controlNumber5)
				.replaceAll("var_controlNumber6", controlNumber6)
				.replaceAll("var_dsmKey1", dsmKey1)
				.replaceAll("var_dsmKey2", dsmKey2)
				.replaceAll("var_dsmKey3", dsmKey3)
				.replaceAll("var_dsmKey4", dsmKey4)
				.replaceAll("var_dsmKey5", dsmKey5)
				.replaceAll("var_dsmKey6", dsmKey6)
				.replaceAll("var_senderId", senderId)
				.replaceAll("var_receiverId", receiverId)
				.replaceAll("var_aprf", aprf)
				.replaceAll("var_serviceName", serviceName);


		return str;
	}

	/*
	 * public static <T> boolean haveSamePropertyValues (Class<T> type, T t1, T t2){
	 * 
	 * try { BeanInfo beanInfo = Introspector.getBeanInfo(type); for
	 * (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) { Method m =
	 * pd.getReadMethod(); Object o1 = m.invoke(t1); Object o2 = m.invoke(t2); if
	 * (!Objects.equals(o1, o2)) { System.out.println(type.getName() +
	 * " has different values at: " + m.getName() + ", envelope value: " + o1 +
	 * ", envelope value: " + o2); return false; } } return true; } catch(Exception
	 * e) { return false; } }
	 */

	/*
	 * public static <T> String haveSamePropertyValues (Class<T> type, T t1, T t2){
	 * 
	 * try { BeanInfo beanInfo = Introspector.getBeanInfo(type); for
	 * (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) { Method m =
	 * pd.getReadMethod(); Object o1 = m.invoke(t1); Object o2 = m.invoke(t2); if
	 * (!Objects.equals(o1, o2)) { String str = type.getName() +
	 * " has different values at: " + m.getName() + ", envelope value: " + o1 +
	 * ", envelope value: " + o2; return str; } } return ""; } catch(Exception e) {
	 * return e.toString(); } }
	 */

	public static String haveSamePropertyValues(Object o1, Object o2) {
		//determine fields declared in this class only (no fields of superclass)
		Field[] fields = o1.getClass().getDeclaredFields();

		//print field names paired with their values
		for ( Field field : fields  ) {
			field.setAccessible(true);
			Object envelopeValue = null;
			Object payloadValue = null;
			try {
				envelopeValue = field.get(o1);
				payloadValue = field.get(o2);
				if((null == envelopeValue && null != payloadValue) 
						|| (null == payloadValue && null != envelopeValue)
						|| (null != envelopeValue && null != payloadValue && !envelopeValue.equals(payloadValue))){
					String str = o1.getClass().getName() +
							" has different values at: " + field.getName() + 
							", envelope value: " + envelopeValue + ", payload value: " + payloadValue; 
					return str;
				}
			}catch(Exception e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String exceptionAsString = sw.toString();
				return field.getName() + ":  envelope = " + envelopeValue + ", payload = "+ payloadValue + ", " + exceptionAsString;
			}
		}		
		return "";
	}

	public static String haveSamePropertyValuesExcludeFields(Object o1, Object o2, List<String> excludeFieldNames) {
		//determine fields declared in this class only (no fields of superclass)
		Field[] fields = o1.getClass().getDeclaredFields();

		//print field names paired with their values
		for ( Field field : fields  ) {
			//exclude some fields
			boolean needValidate = true;
			Object envelopeValue = null;
			Object payloadValue = null;
			String name =  field.getName();
			for(String ef: excludeFieldNames) {
				if(name.equals(ef)) {
					needValidate = false;
					break;
				}
			}
			if(needValidate) {
				field.setAccessible(true);
				try {
					envelopeValue = field.get(o1);
					payloadValue = field.get(o2);
					if((null == envelopeValue && null != payloadValue) 
							|| (null == payloadValue && null != envelopeValue)
							|| (null != envelopeValue && null != payloadValue && !envelopeValue.equals(payloadValue))){
						String str = o1.getClass().getName() +
								" has different values at: " + field.getName() + 
								", envelope value: " + envelopeValue + ", payload value: " + payloadValue; 
						return str;
					}
				}catch(Exception e) {
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					String exceptionAsString = sw.toString();
					return field.getName() + ":  envelope = " + envelopeValue + ", payload = "+ payloadValue + ", " + exceptionAsString;
				}
			}
		}

		return "";
	}


	public static String toString(Object obj) {
		StringBuilder result = new StringBuilder();
		String newLine = System.getProperty("line.separator");

		result.append( obj.getClass().getName() );
		result.append( " Object {" );
		result.append(newLine);

		//determine fields declared in this class only (no fields of superclass)
		Field[] fields = obj.getClass().getDeclaredFields();

		//print field names paired with their values
		for ( Field field : fields  ) {
			field.setAccessible(true);

			result.append("  ");
			try {
				result.append( field.getName() );
				result.append(": ");
				//requires access to private field:
				result.append( field.get(obj) );
			} catch ( IllegalAccessException ex ) {
				System.out.println(ex);
			}
			result.append(newLine);
		}
		result.append("}");

		return result.toString();
	}

	private static String convertStackTraceToString(Throwable throwable)  {
		try (StringWriter sw = new StringWriter(); 
				PrintWriter pw = new PrintWriter(sw)) 
		{
			throwable.printStackTrace(pw);
			return sw.toString();
		} 
		catch (IOException ioe) 
		{
			throw new IllegalStateException(ioe);
		}
	} 

	public static void main(String[] args) {
		try { 
			String xifFilePath = "src/test/resources/testfiles/xif2.xml";
			String xifXml = new String(Files.readAllBytes(Paths.get(xifFilePath)));
			xifXml = TestHelper.setVariableValuesForXIF(xifXml);
			System.out.println("xifXml: " + xifXml);

			String path = "src/test/resources/testfiles/xifUploaded.xml";
			//Files.writeString(Paths.get(path), xifXml);
			Files.write(Paths.get(path), xifXml.getBytes());


		}catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace(); 
		}
	}
}
