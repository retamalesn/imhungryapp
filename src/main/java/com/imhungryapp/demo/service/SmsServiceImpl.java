package com.imhungryapp.demo.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imhungryapp.demo.config.GeneralProperties;

@Service
public class SmsServiceImpl implements SmsService {
	
	@Autowired
	private GeneralProperties app;

	public String sendSms(String to, String senderMsg, String msg) {
		
		try {
			// Construct data
			String apiKey = "apikey=" + app.getSmsApiKey();
			String message = "&message=" + msg;
			String sender = "&sender=" + senderMsg;
			String numbers = "&numbers=" + to;
			
			// Send data
			HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
			String data = apiKey + numbers + message + sender;
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line);
			}
			rd.close();
			
			return stringBuffer.toString();
		} catch (Exception e) {
			System.out.println("Error SMS "+e);
			return "Error "+e;
		}
	}

}
