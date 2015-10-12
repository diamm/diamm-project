package uk.ac.kcl.cch.facet.ui;

public class PageNavigator
{
    //TBA
    //public static enum SortField {};
	public static enum SortDirection {ASC, DESC};

	private Integer totalRecordCount;
	private Integer rowsPerPage;
	private Integer totalPageCount;
	private Integer selectedPage;
	private Integer firstPageInRange;
	private Integer lastPageInRange;
	private Boolean extendLeft;
	private Boolean extendRight;
	private Integer firstRecordInPage;
	private Integer lastRecordInPage;
    private SortDirection sortDirection;

    public PageNavigator(Integer rowsPerPage, Integer totalRecordCount, Integer pageRange, Integer selectedPage)
    {
    	this.selectedPage     = selectedPage;
    	this.rowsPerPage      = rowsPerPage;
    	this.totalRecordCount = totalRecordCount;

    	firstRecordInPage = ((selectedPage - 1) * rowsPerPage) + 1;
    	lastRecordInPage  = (selectedPage * rowsPerPage > totalRecordCount ? totalRecordCount: selectedPage * rowsPerPage);

    	totalPageCount = totalRecordCount / rowsPerPage;
        if (totalRecordCount.equals(0) || (totalRecordCount % rowsPerPage != 0))
        {
        	totalPageCount++;
        }

        Integer rangeCount = totalPageCount / pageRange;
        if (totalPageCount % pageRange != 0)
        {
        	rangeCount++;
        }

        Integer selectedRangeNo = selectedPage / pageRange;
        if (selectedPage % pageRange != 0)
        {
        	selectedRangeNo++;
        }

        extendLeft  = (selectedRangeNo > 1 ? true : false);
        extendRight = (selectedRangeNo < rangeCount ? true : false);

        firstPageInRange = 1 + ((selectedRangeNo - 1) * pageRange);
        lastPageInRange  = pageRange * selectedRangeNo;

        if (lastPageInRange > totalPageCount)
        {
    	    lastPageInRange = totalPageCount;
        }
    }
    
    public Integer getTotalRecordCount()
	{
		return totalRecordCount;
	}

    public Integer getRowsPerPage()
	{
		return rowsPerPage;
	}
    
    public Integer getSelectedPage()
	{
		return selectedPage;
	}
    
	public Integer getFirstPageInRange()
	{
		return firstPageInRange;
	}

	public Integer getLastPageInRange()
	{
		return lastPageInRange;
	}

	public Boolean getExtendLeft()
	{
		return extendLeft;
	}

	public Boolean getExtendRight()
	{
		return extendRight;
	}

	public Integer getFirstRecordInPage()
	{
		return firstRecordInPage;
	}

	public Integer getLastRecordInPage()
	{
		return lastRecordInPage;
	}
	
	public SortDirection getReverseSortDirection()
	{
		return (sortDirection.equals(SortDirection.DESC) ? SortDirection.ASC : SortDirection.DESC);
	}
	
	public Boolean getOnFirstPage()
	{
		return (selectedPage.equals(1));
	}
	
	public Boolean getOnLastPage()
	{
		return (selectedPage.equals(totalPageCount));
	}
	
	public Integer getLastPage()
	{
		return totalPageCount;
	}
	
	public Boolean getHasRecords()
	{
		return (totalRecordCount > 0);
	}
	
	public Boolean getHasMultiplePages()
	{
		return (totalPageCount > 1);
	}
	
	public Integer getFirstPage()
	{
		return 1;
	}
	
	public Integer getPrevPage()
	{
		return (selectedPage.equals(1) ? selectedPage : selectedPage - 1);
	}
	
	public Integer getNextPage()
	{
		return (selectedPage.equals(totalPageCount) ? selectedPage : selectedPage + 1);
	}
	
	public Integer getExtendLeftPage()
	{
		return (firstPageInRange - 1);
	}
	
	public Integer getExtendRightPage()
	{
		return (lastPageInRange + 1);
	}

    public SortDirection getSortDirection()
	{
		return sortDirection;
	}

	public void setSortDirection(SortDirection sortDirection)
	{
		this.sortDirection = sortDirection;
	}
}