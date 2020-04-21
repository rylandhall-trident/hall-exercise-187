/*Author: Ryland Hall
 *Course: CPT187
 *Purpose: To store orders made in files by writing to them
 *StartDate: 4.15.20
 */

package edu.cpt187.hall.participation;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class OrderWriter
{
	//Class Attributes
	private final boolean FOUND = true;
	private final boolean NOT_FOUND = false;
	private boolean fileFoundFlag = false;
	private String masterFileName = "";
	private int recordCount = 0;
	//--------------------
	
	//Class Constructor
	public OrderWriter(String borrowedFileName)
	{
		masterFileName = borrowedFileName;
	}//END of OrderWriter()
	
	//Class Setters
	public void setWriteOrder(int borrowedItemID, String borrowedItemName, double borrowedItemPrice, int borrowedQuantity, double borrowedOrderCost)
	{
		try
		{
			PrintWriter filePW = new PrintWriter(new FileWriter(masterFileName, true));
			filePW.printf("%n%d\t%s\t%f\t%d\t%f%n", borrowedItemID, borrowedItemName, borrowedItemPrice, borrowedQuantity, borrowedOrderCost);
			fileFoundFlag = FOUND;
			recordCount++;
			filePW.close();
		}//END of try
		catch(IOException ex)
		{
			fileFoundFlag = NOT_FOUND;
		}//END of catch
	}//END of setWriteOrder()
	
	//Class Getters
	public boolean getFileFoundFlag()
	{
		return fileFoundFlag;
	}//END of getFileFoundFlag()
	
	public String getFileName()
	{
		return masterFileName;
	}//END of getFileName()
	
	public int getRecordCount()
	{
		return recordCount;
	}//END of getRecordCount()
}//END of OrderWriter()
