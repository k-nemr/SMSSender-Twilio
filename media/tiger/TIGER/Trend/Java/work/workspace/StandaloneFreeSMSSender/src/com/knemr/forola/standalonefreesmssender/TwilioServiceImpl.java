package com.knemr.forola.standalonefreesmssender;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

public class TwilioServiceImpl implements TwilioService {

	private static final String CHAT_FILE_NAME = "chat_room";
	private static final String CHAT_FILE_EXTEN = ".txt";
	private final String TWILIO_PHONE_NUMBER;
	private final String TWILIO_ACCOUNT_SID;
	private final String TWILIO_AUTH_TOKEN;

	public TwilioServiceImpl() {
		/*this.TWILIO_PHONE_NUMBER = "+12014643842";
         this.TWILIO_ACCOUNT_SID = "ACd7d51f5f73ba50a0623c8514837d7eb2";
         this.TWILIO_AUTH_TOKEN = "1ff6fb716c156b87029f93583fdb1e74";*/
		/*this.TWILIO_PHONE_NUMBER = "+12628854088";
         this.TWILIO_ACCOUNT_SID = "AC7dc072a853d281b6310a14c2383009aa";
         this.TWILIO_AUTH_TOKEN = "5050a1f3190c81770fd7b651b8dc51e8";*/
		this.TWILIO_PHONE_NUMBER = "+16304266235";
		this.TWILIO_ACCOUNT_SID = "ACca010f0daa52c900e8913289cde9ab0d";
		this.TWILIO_AUTH_TOKEN = "a1d8b7a6538f750351304e7ac576a6ff";
	}

	public TwilioServiceImpl(String TWILIO_PHONE_NUMBER, String TWILIO_ACCOUNT_SID, String TWILIO_AUTH_TOKEN) {
		this.TWILIO_PHONE_NUMBER = TWILIO_PHONE_NUMBER;
		this.TWILIO_ACCOUNT_SID = TWILIO_ACCOUNT_SID;
		this.TWILIO_AUTH_TOKEN = TWILIO_AUTH_TOKEN;
	}

	public String sendSMS(String toNumber, String msgBody, String mediaUrl) {
		System.out.println("=========================================");
		System.out.println("TwilioService.sendSMS: Start sending SMS.");
		String result;
		try {
			TwilioRestClient client = new TwilioRestClient(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
			// Build the parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("From", TWILIO_PHONE_NUMBER));
			params.add(new BasicNameValuePair("To", toNumber));
			params.add(new BasicNameValuePair("Body", msgBody));
			if (mediaUrl != null) {
				params.add(new BasicNameValuePair("MediaUrl", mediaUrl));
			}

			MessageFactory messageFactory = client.getAccount().getMessageFactory();
			Message message = messageFactory.create(params);

			System.out.println("TwilioService.sendSMS: SMS sent successfully with Sid: " + message.getSid() + ".");
			Date date = new Date();
			String msgDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(date);
			String line = msgDate + " -- " + msgBody;
			System.out.println("The SMS message was:");
			System.out.println("--------------------");
			System.out.println(line);
			System.out.println("--------------------");
			String fileDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
			String fullFileName = CHAT_FILE_NAME + "_" + fileDate + CHAT_FILE_EXTEN;
			writeTxtToFile(line, fullFileName);
			result = "TwilioServiceImpl.sendSMS: Sending SMS completed.";
			result += "\nSMS sent successfully with Sid: " + message.getSid() + ".";

		} catch (Exception e) {
			e.printStackTrace();
			result = "TwilioService.sendSMS: Error occured while sending SMS.";
			result += "\nError msg is: " + e.getMessage();
			return result;
		}
		System.out.println("=========================================");
		return result;
	}

	public void writeTxtToFile(String txt, String fileName) {
		try {
			FileWriter file = new FileWriter(fileName, true);
			file.write(txt);
			file.write(System.lineSeparator());
			file.write(System.lineSeparator());
			file.flush();
			file.close();

		} catch (IOException e) {
			System.err.println("Error occur while writing the message to the chat file!");
			System.err.println("Error msg is: " + e.getMessage());
			e.printStackTrace();
			return;
		}
		System.out.println("The message written to the chat file successfully.");
	}

}
