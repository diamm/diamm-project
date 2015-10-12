package uk.ac.kcl.cch.diamm.model;

import java.io.File;
import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: Elliot
 * Date: 04/09/12
 * Time: 10:03
 * To change this template use File | Settings | File Templates.
 * Copied from John Lee's cvma dbmi code by EH
 */
public class FileComparator implements Comparator<File>
{
	public int compare(File file1, File file2)
	{
        return (file2.getName()).compareTo((file1).getName());
	}
}
