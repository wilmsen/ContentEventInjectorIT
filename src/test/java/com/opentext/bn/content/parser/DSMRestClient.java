package com.opentext.bn.content.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DSMRestClient{
	final static String dsmhost = "q14dsm.qa.gxsonline.net";
	final static String dsmport = "7300";
	final static String dsmuser = "mdr";
	final static String dsmpass = "password";


	public String uploadXIFFileFromDSM(String sourceFile) {
		try	{
			
			File file = new File(sourceFile);
			//String senderFileName = file.getName();
			
			FileInputStream fis = new FileInputStream(file);
			URL url = new URL("http://" + dsmhost + ":" + dsmport + "/TGMSDSMREST/rest/v1");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/octet-stream");
			connection.setRequestProperty("Transfer-Encoding", "chunked");
			connection.setChunkedStreamingMode(1024);
			connection.setConnectTimeout(120000);
			connection.setReadTimeout(120000);
			
			String userpass = dsmuser + ":" + dsmpass;
			String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());

			connection.setRequestProperty ("Authorization", basicAuth);
			OutputStream os = connection.getOutputStream();
			
			byte[] buf = new byte[1024];
			int bytesRead = -1;
			
			while((bytesRead = fis.read(buf)) != -1){
				os.write(buf, 0, bytesRead);
			}
			
			os.close();
			fis.close();
			
			int responseCode = connection.getResponseCode();
			if(responseCode != HttpURLConnection.HTTP_CREATED) {
				System.out.println("Unexpected response code of: " + responseCode + " returned from data post to url: " + url);
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				String line = null;
				while((line= reader.readLine()) != null){
					System.out.println(line);
				}
				
				reader.close();
				return null;
				
			}else {
				String dsmLocation = connection.getHeaderField("Location");
				// Location: http://d1dsm.qa.gxsonline.net:7300/TGMSDSMREST/rest/v1/Q14E-201911000000000165054405​
				return dsmLocation.substring(dsmLocation.indexOf("v1/") + 3);
			}
			
		}catch(Exception e)	{
			System.out.println("Error pushing source file into DSM. Error: " + e);
			e.printStackTrace();
			return null;
		}
	}

}
