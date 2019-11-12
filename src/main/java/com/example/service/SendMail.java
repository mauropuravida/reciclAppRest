package com.example.service;
import com.sun.mail.smtp.SMTPTransport;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class SendMail {

	// for example, smtp.mailgun.org
    private String SMTP_SERVER = "smtp.gmail.com";
    private String USERNAME = "your email";
    private String PASSWORD = "pass";

    private String EMAIL_FROM = "your email";
    private String EMAIL_TO = "";
    private String EMAIL_TO_CC = "";

    private String EMAIL_SUBJECT = "Código para reestablecer contraseña";
    private String EMAIL_TEXT = "";
    
    public SendMail(String emailTo, String token) {

	    Properties prop = System.getProperties();
	    EMAIL_TO= emailTo;
	    EMAIL_TO_CC=emailTo;
	    EMAIL_TEXT=token;
	    prop.put("mail.smtp.host", SMTP_SERVER); //optional, defined in SMTPTransport
	    prop.put("mail.smtp.auth", "true");
	    prop.put("mail.smtp.starttls.enable","true");
	    prop.put("mail.smtp.port", "25"); // default port 25
	
	    Session session = Session.getInstance(prop, null);
	    Message msg = new MimeMessage(session);
	
	    try {
		
			// from
	        msg.setFrom(new InternetAddress(EMAIL_FROM));
	
			// to 
	        msg.setRecipients(Message.RecipientType.TO,
	                InternetAddress.parse(EMAIL_TO, false));
	
			// cc
	        msg.setRecipients(Message.RecipientType.CC,
	                InternetAddress.parse(EMAIL_TO_CC, false));
	
			// subject
	        msg.setSubject(EMAIL_SUBJECT);
			
			// content 
	        msg.setText(EMAIL_TEXT);
			
	        msg.setSentDate(new Date());
	
			// Get SMTPTransport
	        SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
			
			// connect
	        t.connect(SMTP_SERVER, USERNAME, PASSWORD);
			
			// send
	        t.sendMessage(msg, msg.getAllRecipients());
	
	        //System.out.println("Response: " + t.getLastServerResponse());
	
	        t.close();
	
	    } catch (MessagingException e) {
	        e.printStackTrace();
	    }
    }
}