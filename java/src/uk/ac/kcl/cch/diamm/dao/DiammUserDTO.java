package uk.ac.kcl.cch.diamm.dao;

public class DiammUserDTO 
{
	//User fields
    private String username;
    private String displayName;
    private String password;
    private String email;
    private String affiliation;
    private String id;

    //Fields relating to password change
    private String newPassword1;
    private String newPassword2;
    private String agree1;
    private String agree2;
    private String agree3;
    private String agree4;
    
	public String getUsername() 
	{
		return username;
	}
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public String getDisplayName() 
	{
		return displayName;
	}
	
	public void setDisplayName(String displayName) 
	{
		this.displayName = displayName;
	}
	
	public String getPassword() 
	{
		return password;
	}
	
	public void setPassword(String password) 
	{
		this.password = password;
	}
	
	public String getEmail() 
	{
		return email;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public String getAffiliation() 
	{
		return affiliation;
	}
	
	public void setAffiliation(String affiliation) 
	{
		this.affiliation = affiliation;
	}

	public String getNewPassword1() 
	{
		return newPassword1;
	}

	public void setNewPassword1(String newPassword1)
	{
		this.newPassword1 = newPassword1;
	}

	public String getNewPassword2() 
	{
		return newPassword2;
	}

	public void setNewPassword2(String newPassword2) 
	{
		this.newPassword2 = newPassword2;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAgree1() {
        return agree1;
    }

    public void setAgree1(String agree1) {
        this.agree1 = agree1;
    }

    public String getAgree2() {
        return agree2;
    }

    public void setAgree2(String agree2) {
        this.agree2 = agree2;
    }

    public String getAgree3() {
        return agree3;
    }

    public void setAgree3(String agree3) {
        this.agree3 = agree3;
    }

    public String getAgree4() {
        return agree4;
    }

    public void setAgree4(String agree4) {
        this.agree4 = agree4;
    }
}
