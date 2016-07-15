package com.knemr.forola.standalonefreesmssender;

public interface TwilioService
{
	public String sendSMS(String toNumber, String msgBody, String mediaUrl);
}
