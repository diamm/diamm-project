package uk.ac.kcl.cch.diamm.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.*;

import uk.ac.kcl.cch.util.Email;

@Entity
@Table(catalog = "diamm_ess")
public class DiammUser {
	//Definitions for map keys 
	public static final String MAP_KEY_USERNAME     = "username";
	public static final String MAP_KEY_DISPLAY_NAME = "displayName";
	public static final String MAP_KEY_PASSWORD     = "password";
	public static final String MAP_KEY_EMAIL        = "email";
	public static final String MAP_KEY_AFFILIATION  = "affiliation";
    public static final String MAP_KEY_TERMS  = "terms";

	//Definitions for error messages
	public static final String MSG_REQUIRED   = "required";
	public static final String MSG_INVALID    = "invalid format";
	public static final String MSG_NO_SPACES  = "spaces not allowed";
	public static final String MSG_MAX_LENGTH = "maximum length is %len character(s)";
	public static final String MSG_MIN_LENGTH = "minimum length is %len character(s)";
    public static final String MSG_TERMS = "Please agree to all terms and conditions";
	
    private int id;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String username;
    
    @Basic
    @Column(name = "username", nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        final int    USERNAME_MAX_LENGTH  = 20;
        final String USERNAME_PATTERN     = "[a-zA-Z0-9\\_\\s]+";
        final String MSG_USERNAME_PATTERN = "valid characters are a-z, 0-9 and underscore";
        
    	Pattern p = Pattern.compile(USERNAME_PATTERN);
    	Matcher m = p.matcher(username);
    	
    	if (username.trim().length() == 0) {
    		errorMessages.put(MAP_KEY_USERNAME, MSG_REQUIRED);
    	}
    	else if (username.length() > USERNAME_MAX_LENGTH) {
    		errorMessages.put(MAP_KEY_USERNAME, MSG_MAX_LENGTH.replace("%len", String.valueOf(USERNAME_MAX_LENGTH)));
    	}
    	else if (!m.matches()) {
    		errorMessages.put(MAP_KEY_USERNAME, MSG_USERNAME_PATTERN);
            this.username = username;
    	}
        else {
    		this.username = username;
    	} 
    }
    
    private String displayName;
    
    @Basic
    @Column(name = "displayName", nullable = false)
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        int DISPLAY_NAME_MAX_LENGTH = 50;
    	
        if (displayName.trim().length() == 0) {
    		errorMessages.put(MAP_KEY_DISPLAY_NAME, MSG_REQUIRED);
    	}
    	else if (displayName.length() > DISPLAY_NAME_MAX_LENGTH) {
    		errorMessages.put(MAP_KEY_DISPLAY_NAME, MSG_MAX_LENGTH.replace("%len", String.valueOf(DISPLAY_NAME_MAX_LENGTH)));
    	}
        else {
    		this.displayName = displayName;
    	} 
    }

    private String password;

    @Basic
    @Column(name = "passwd", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {	
    	this.password = password;
    }
    
    public void validateClearPassword(String clearPassword) {
    	final int CLEAR_PASSWORD_MIN_LENGTH = 6;
    	final int CLEAR_PASSWORD_MAX_LENGTH = 20;
    	
    	if (clearPassword.length() == 0) {
    		errorMessages.put(MAP_KEY_PASSWORD, MSG_REQUIRED);
    	}
    	else if (clearPassword.indexOf(' ') != -1 || password.indexOf('\t') != -1) {
    		errorMessages.put(MAP_KEY_PASSWORD, MSG_NO_SPACES);
    	}
    	else if (clearPassword.length() < CLEAR_PASSWORD_MIN_LENGTH) {
    		errorMessages.put(MAP_KEY_PASSWORD, MSG_MIN_LENGTH.replace("%len", String.valueOf(CLEAR_PASSWORD_MIN_LENGTH)));
    	}
    	else if (clearPassword.length() > CLEAR_PASSWORD_MAX_LENGTH) {
    		errorMessages.put(MAP_KEY_PASSWORD, MSG_MAX_LENGTH.replace("%len", String.valueOf(CLEAR_PASSWORD_MAX_LENGTH)));
    	}
    }

    private String email;

    @Basic
    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }
   
    public void setEmail(String email) {
    	final int EMAIL_MAX_LENGTH = 80;
    	this.email="";
        if (email.trim().length() == 0) {
    		errorMessages.put(MAP_KEY_EMAIL, MSG_REQUIRED);
    	}
    	else if (email.length() > EMAIL_MAX_LENGTH) {
    		errorMessages.put(MAP_KEY_EMAIL, MSG_MAX_LENGTH.replace("%len", String.valueOf(EMAIL_MAX_LENGTH)));
    	}
    	else if (!Email.isValidFormat(email))
    	{
    		errorMessages.put(MAP_KEY_EMAIL, MSG_INVALID);
    	}
        else {
    		this.email = email;
    	} 
    }
    
    private String affiliation;
    
    @Basic
    @Column(name = "affiliation")
    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
    	int AFFILIATION_MAX_LENGTH = 200;
    	
    	if (affiliation.length() > AFFILIATION_MAX_LENGTH) {
    		errorMessages.put(MAP_KEY_AFFILIATION, MSG_MAX_LENGTH.replace("%len", String.valueOf(AFFILIATION_MAX_LENGTH)));
    	}
        else {
        	this.affiliation = affiliation;
    	}
    }
    
    private Map<String, String> errorMessages = new HashMap<String, String>();
	
	@Transient
	public Map<String, String> getErrorMessages()
	{
		return errorMessages;
	}
	
	private List<Collection> collectionList = new ArrayList<Collection>();
	
	@OneToMany(mappedBy="diammUser",
			   targetEntity=Collection.class)
	public List<Collection> getCollectionList() {
		return collectionList;
	}

	public void setCollectionList(List<Collection> collectionList) {
		this.collectionList = collectionList;
	}


    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((affiliation == null) ? 0 : affiliation.hashCode());
		result = prime * result
				+ ((collectionList == null) ? 0 : collectionList.hashCode());
		result = prime * result
				+ ((displayName == null) ? 0 : displayName.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((errorMessages == null) ? 0 : errorMessages.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DiammUser other = (DiammUser) obj;
		if (affiliation == null) {
			if (other.affiliation != null)
				return false;
		} else if (!affiliation.equals(other.affiliation))
			return false;
		if (collectionList == null) {
			if (other.collectionList != null)
				return false;
		} else if (!collectionList.equals(other.collectionList))
			return false;
		if (displayName == null) {
			if (other.displayName != null)
				return false;
		} else if (!displayName.equals(other.displayName))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (errorMessages == null) {
			if (other.errorMessages != null)
				return false;
		} else if (!errorMessages.equals(other.errorMessages))
			return false;
		if (id != other.id)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}
