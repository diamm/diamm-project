package uk.ac.kcl.cch.facet.ui;

public class RecordNavigator
{
	private Integer totalRecordCount;
	private Integer selectedPage;
	private Integer selectedRecord;

    public RecordNavigator(Integer totalRecordCount, Integer selectedPage, Integer positionInPage, Integer rowsPerPage)
    {
    	this.totalRecordCount = totalRecordCount;
    	this.selectedPage     = selectedPage;
    	this.selectedRecord   = (selectedPage - 1) * rowsPerPage + positionInPage;
    }
    
    public RecordNavigator(Integer totalRecordCount, Integer selectedPage, Integer selectedRecord)
    {
    	this.totalRecordCount = totalRecordCount;
    	this.selectedPage     = selectedPage;
    	this.selectedRecord   = selectedRecord;
    }

	public Integer getFirstRecord() 
	{
		return 1;
	}

	public Integer getTotalRecordCount() 
	{
		return totalRecordCount;
	}
	
	public Integer getSelectedPage() 
	{
		return selectedPage;
	}

	public Integer getSelectedRecord() 
	{
		return selectedRecord;
	}

	public Integer getPrevRecord() 
	{
		return (selectedRecord.equals(1) ? selectedRecord : selectedRecord - 1);
	}

	public Integer getNextRecord() 
	{
		return (selectedRecord < totalRecordCount ? selectedRecord + 1 : selectedRecord);
	}

	public Boolean getOnFirstRecord() 
	{
		return (selectedRecord.equals(1));
	}

	public Boolean getOnLastRecord() 
	{
		return (selectedRecord.equals(totalRecordCount));
	}
	
	public Boolean getHasMultipleRecords()
	{
		return (totalRecordCount > 1);
	}
}