package com.imhungryapp.demo.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.imhungryapp.demo.config.GeneralProperties;
import com.imhungryapp.demo.dto.ResponseService;
import com.imhungryapp.demo.util.Constants;

@Service
public class SmsServiceImpl implements SmsService {
	
	final static Logger logger = Logger.getLogger(SmsServiceImpl.class);
	
	@Autowired
	private GeneralProperties app;

	public void sendSms(String to, String senderMsg, String msg) {
		
		ResponseService response = new ResponseService();
		Gson gson = new Gson();
		
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
			response.setStatus(Constants.SUCCESS);
			response.setMessage(stringBuffer.toString());
			logger.info("This is error : " + (gson.toJson(response)));
		} catch (Exception e) {
			response.setStatus(Constants.FAIL);
			response.setMessage(e.getMessage());
			logger.error("This is error : " + (gson.toJson(response)));
		}
	}
}
