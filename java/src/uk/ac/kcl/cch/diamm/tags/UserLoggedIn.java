package uk.ac.kcl.cch.diamm.tags;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class UserLoggedIn extends SimpleTagSupport {
    public void doTag() throws JspException, IOException {
    	PageContext pageContext = (PageContext) getJspContext();
    	String username = ((HttpServletRequest) pageContext.getRequest()).getRemoteUser();
    	
    	if (username != null) {
    		getJspBody().invoke(null);
    	}
    }
}
