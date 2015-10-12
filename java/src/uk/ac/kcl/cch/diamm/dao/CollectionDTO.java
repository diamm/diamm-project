package uk.ac.kcl.cch.diamm.dao;

import uk.ac.kcl.cch.util.StringUtil;

public class CollectionDTO 
{
	private String  selfParent;
	private String  mode;
	private String  action;
	private String  username;
    private String  createdCollTitle;
    private String  createdCollDescription;
    private Integer origCollId;
	private String  origCollTitle;
	private String  origCollDescription;
    private String  editedCollTitle;
    private String  editedCollDescription;
    private Integer linkId;
    private String  linkType;
    private Integer collIdToLink;
    private Integer unlinkId;
    private String  unlinkType;
    private Integer defaultCollId;
    
    public enum LinkType 
    {
        SOURCE("source", "Source"),
        ITEM  ("item",   "Item"),
        IMAGE ("image",  "Image");
        
        private final String enumType;
        private final String enumTitle;
        
        LinkType(String type, String title) 
        {
            this.enumType  = type;
            this.enumTitle = title;
        }
        
        public String getEnumType()   
        { 
        	return enumType; 
        }
        
        public String getEnumTitle() 
        { 
        	return enumTitle; 
        }
    }
    
    public String getSelfParent() {
		return selfParent;
	}

	public void setSelfParent(String selfParent) {
		this.selfParent = selfParent;
	}

	public String getMode() 
    {
		return mode;
	}

	public void setMode(String mode)
	{
		this.mode = mode;
	}

	public String getAction() 
	{
		return action;
	}

	public void setAction(String action) 
	{
		this.action = action;
	}

	public String getUsername() 
	{
		return username;
	}

	public void setUsername(String username) 
	{
		this.username = username;
	}

	public String getCreatedCollTitle() 
	{
		return createdCollTitle;
	}
	
	public void setCreatedCollTitle(String createdCollTitle)
	{
		this.createdCollTitle = StringUtil.squeeze(createdCollTitle);
	}
	
	public String getCreatedCollDescription() 
	{
		return createdCollDescription;
	}
	
	public void setCreatedCollDescription(String createdCollDescription) 
	{
		this.createdCollDescription = StringUtil.squeeze(createdCollDescription);
	}

	public Integer getOrigCollId()
	{
		return origCollId;
	}

	public void setOrigCollId(Integer origCollId)
	{
		this.origCollId = origCollId;
	}

	public String getOrigCollTitle()
	{
		return origCollTitle;
	}

	public void setOrigCollTitle(String origCollTitle)
	{
		this.origCollTitle = origCollTitle;
	}

	public String getOrigCollDescription()
	{
		return origCollDescription;
	}

	public void setOrigCollDescription(String origCollDescription)
	{
		this.origCollDescription = origCollDescription;
	}
	
	public String getEditedCollTitle() 
	{
		return editedCollTitle;
	}

	public void setEditedCollTitle(String editedCollTitle) 
	{
		this.editedCollTitle = editedCollTitle;
	}

	public String getEditedCollDescription() 
	{
		return editedCollDescription;
	}

	public void setEditedCollDescription(String editedCollDescription)
	{
		this.editedCollDescription = editedCollDescription;
	}

	public Integer getLinkId()
	{
		return linkId;
	}

	public void setLinkId(Integer linkId)
	{
		this.linkId = linkId;
	}

	public String getLinkType()
	{
		return linkType;
	}

	public void setLinkType(String linkType)
	{
		this.linkType = linkType;
	}

	public Integer getCollIdToLink()
	{
		return collIdToLink;
	}

	public void setCollIdToLink(Integer collIdToLink)
	{
		this.collIdToLink = collIdToLink;
	}

	public Integer getUnlinkId() 
	{
		return unlinkId;
	}

	public void setUnlinkId(Integer unlinkId)
	{
		this.unlinkId = unlinkId;
	}

	public String getUnlinkType()
	{
		return unlinkType;
	}

	public void setUnlinkType(String unlinkType) 
	{
		this.unlinkType = unlinkType;
	}
	
	public Integer getDefaultCollId() 
	{
		return defaultCollId;
	}

	public void setDefaultCollId(Integer defaultCollId)
	{
		this.defaultCollId = defaultCollId;
	}

	public Boolean hasChanged()
	{
		String tempOrigDesc   = (origCollDescription   == null ? "" : origCollDescription);
		String tempEditedDesc = (editedCollDescription == null ? "" : editedCollDescription);
		
		return (!origCollTitle.equals(editedCollTitle) || !tempOrigDesc.equals(tempEditedDesc));
	}
}
