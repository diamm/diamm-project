package uk.ac.kcl.cch.util;

public class StringUtil 
{
	public static String squeeze(String origString) 
	{
		String squeezedString = null;
		
		if (origString != null && origString.trim().length() > 0)
		{
			char[] a = origString.toCharArray();
		    int n = 1;
		    
		    for (int i = 1; i < a.length; i++) 
		    { 
		        a[n] = a[i];
		        if (a[n]   != ' ') 
		        {
		        	n++;
		        }
		        else if (a[n-1] != ' ') 
		        {
		        	n++;
		        }
		    }
		    squeezedString = new String(a, 0, n);
		}
		
	    return squeezedString;   
	}
}
