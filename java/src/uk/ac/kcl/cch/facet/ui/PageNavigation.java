package uk.ac.kcl.cch.facet.ui;


public class PageNavigation
{
    //Deprecated for the moment
    //public static enum SortField {WINDOW_NO, WINDOW_DESC, PANEL_NO,COUNTY};
	public static enum SortDirection {ASC, DESC};

    private Integer firstPage;
	private Integer lastPage;
	private Boolean extendLeft;
	private Boolean extendRight;
	private Integer selectedPage;
	private Integer pageCount;
	private Integer rowsPerPage;
	private Integer recordCount;
	private Integer pageFirstIndex;
	private Integer pageLastIndex;
    private SortDirection sortDirection;

    public PageNavigation(Integer rowsPerPage, Integer recordCount, Integer pageRange, Integer selectedPage)
    {
    	this.selectedPage = selectedPage;
    	this.rowsPerPage  = rowsPerPage;
    	this.recordCount  = recordCount;

    	pageFirstIndex = ((selectedPage - 1) * rowsPerPage) + 1;
    	pageLastIndex  = (selectedPage * rowsPerPage > recordCount ? recordCount: selectedPage * rowsPerPage);

    	pageCount = recordCount / rowsPerPage;
        if ((recordCount == 0) || (recordCount % rowsPerPage != 0))
        {
        	pageCount++;
        }

        Integer rangeCount = pageCount / pageRange;
        if (pageCount % pageRange != 0)
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

        firstPage = 1 + ((selectedRangeNo - 1) * pageRange);
        lastPage  = pageRange * selectedRangeNo;

        if (lastPage > pageCount)
        {
    	    lastPage = pageCount;
        }
    }

	public Integer getFirstPage()
	{
		return firstPage;
	}

	public Integer getLastPage()
	{
		return lastPage;
	}

	public Boolean getExtendLeft()
	{
		return extendLeft;
	}

	public Boolean getExtendRight()
	{
		return extendRight;
	}

	public Integer getSelectedPage()
	{
		return selectedPage;
	}

	public Integer getPageCount()
	{
		return pageCount;
	}

	public Integer getRowsPerPage()
	{
		return rowsPerPage;
	}

	public Integer getRecordCount()
	{
		return recordCount;
	}

	public Integer getPageFirstIndex()
	{
		return pageFirstIndex;
	}

	public Integer getPageLastIndex()
	{
		return pageLastIndex;
	}

    public SortDirection getSortDirection()
	{
		return sortDirection;
	}

	public void setSortDirection(SortDirection sortDirection)
	{
		this.sortDirection = sortDirection;
	}

	public SortDirection getReverseSortDirection()
	{
		return (sortDirection.equals(SortDirection.DESC) ? SortDirection.ASC : SortDirection.DESC);
	}
}