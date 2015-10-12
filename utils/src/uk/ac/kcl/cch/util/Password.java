package uk.ac.kcl.cch.util;

import org.apache.catalina.realm.RealmBase;

public class Password 
{
	public static String generate(int length) 
	{
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuffer password = new StringBuffer();
        
        for (int x = 0; x < length; x++) 
        {
            int i = (int) Math.floor(Math.random() * 62);
            password.append(chars.charAt(i));
        }
        
        return password.toString();
    }
	
	public static String encrypt(String password)
	{
		return RealmBase.Digest(password, "MD5", null);
	}
}
