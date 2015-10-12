package uk.ac.kcl.cch.util.test;

import javax.mail.MessagingException;
import uk.ac.kcl.cch.util.Email;
import uk.ac.kcl.cch.util.Password;

public class TestHarness 
{
	public static void main(String[] args)
    {
		TestHarness harness = new TestHarness();
		//harness.mailTest();
    	harness.passwordEncryptTest();
    }
	
	private void mailTest()
	{
		final String EMAIL_TO      = "john.lee@kcl.ac.uk";
		final String EMAIL_FROM    = "cch-sysadmin@kcl.ac.uk";
    	final String EMAIL_SUBJECT = "Retrieval of your DIAMM username and password";
    	final String EMAIL_MESSAGE = "Dear %d,\n\n"   +
    	                             EMAIL_SUBJECT    +
    	                             "\n\n"             +
    	                             "Username: %u\n" +
    	                             "Password: %p\n\nwww.diamm.ac.uk";
    	
		String emailMessage = EMAIL_MESSAGE.replace("%d", "John Lee");
		emailMessage = emailMessage.replace("%u", "jlee");
		emailMessage = emailMessage.replace("%p", "muppet500");
		
		try 
    	{
			Email.send(EMAIL_TO, EMAIL_FROM, EMAIL_SUBJECT, emailMessage, false);
			System.out.println("No Exception");
		}
    	catch (MessagingException e) 
    	{
			e.printStackTrace();
		}	
	}
	
	private void passwordTest()
	{
		for (int i=0; i<10; i++)
		{
			System.out.println(Password.generate(8));
		}
	}
	
	private void passwordEncryptTest()
	{
//		for (int i=0; i<10; i++)
//		{
//			String clearPassword = Password.generate(8);
//			System.out.println(clearPassword + "\t" + Password.encrypt(clearPassword));
//		}
		
	}
}
