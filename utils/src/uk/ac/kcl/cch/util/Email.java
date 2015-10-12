package uk.ac.kcl.cch.util;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Email 
{   
    public static void send(String to, String from, String subject, String body, boolean bodyIsHTML) throws MessagingException
    {
    	//Get a mail session
    	Properties props = new Properties();
    	props.put("mail.transport.protocol", "smtp");
    	props.put("mail.smtp.host", "smtp.kcl.ac.uk");
    	Session session = Session.getDefaultInstance(props);
    	
    	//Create a message
    	Message message = new MimeMessage(session);
    	message.setSubject(subject);
    	
    	if (bodyIsHTML)
    	{
    		message.setContent(body, "text/html");
    	}
    	else
    	{
    		message.setText(body);
    	}
    	
    	//Address the message
    	Address fromAddress = new InternetAddress(from);
    	Address toAddress   = new InternetAddress(to);
    	message.setFrom(fromAddress);
    	message.setRecipient(Message.RecipientType.TO, toAddress);
    	
    	//Send the message
    	Transport transport = session.getTransport();
    	transport.connect();
    	transport.sendMessage(message, message.getAllRecipients());
    	transport.close();
    }
    
    public static Boolean isValidFormat(String email)
    {
    	return (email.indexOf(' ')  == -1                 &&  //no spaces
    			email.indexOf('\t') == -1                 &&  //no tabs
    			email.replaceAll("[^@]","").length() == 1 &&  //exactly one @
    			email.charAt(0) != '@'                    &&  //has a local part
    			email.charAt(email.length()-1) != '@');       //has a domain part
    }
}
