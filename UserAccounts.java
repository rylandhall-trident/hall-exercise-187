/*Author: Ryland Hall
 *Course: CPT187
 *Purpose: To manage User Accounts with a User Name and Password
 *StartDate: 4.14.20
 */

package edu.cpt187.hall.participation;

import java.util.Scanner;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

public class UserAccounts
{
	//Class Attributes
	private final boolean FILE_FOUND = true;
	private final boolean FILE_NOT_FOUND = false;
	private final int RESET_VALUE = 0;
	private final int NOT_FOUND = -1;
	private final int MAXIMUM_RECORDS = 50;
	private String[] userNames = new String[MAXIMUM_RECORDS];
	private String[] passwords = new String[MAXIMUM_RECORDS];
	private String masterFileName = "";
	private boolean fileFoundFlag = false;
	private int recordCount = 0;
	private int searchedIndex = 0;
	//--------------------
	
	//Class Constructor
	public UserAccounts(String borrowedFileName)
	{
		masterFileName = borrowedFileName;
	}//END of UserAccounts()
	//--------------------
	
	//Class Behaviors
	//Setters
	//---set User Account Arrays
	public void setUserAccountArrays()
	{
		recordCount = RESET_VALUE;
		try
		{
			Scanner infile = new Scanner(new FileInputStream(masterFileName));
			while (recordCount < MAXIMUM_RECORDS && infile.hasNext() == true)
			{
				userNames[recordCount] = infile.next();
				passwords[recordCount] = infile.next();
				recordCount++;
			}//END of while
			fileFoundFlag = FILE_FOUND;
			infile.close();
		}//END of try
		catch (IOException ex)
		{
			fileFoundFlag = FILE_NOT_FOUND;
		}//END of catch
	}//END of setUserAccountArrays()
	
	//---set Searched Index
	public void setSearchedIndex(String borrowedUserName)
	{
		searchedIndex = getSeqSearch(borrowedUserName);
	}//END of setSearchedIndex()
	
	//---set Searched Index
	public void setSearchedIndex(String borrowedUserName, String borrowedPassword)
	{
		searchedIndex = getSeqSearch(borrowedUserName);
		if (searchedIndex >= RESET_VALUE && getPasswordMatch(borrowedPassword) == false)
		{
			searchedIndex = NOT_FOUND;
		}//END of if
	}//END of setSearchedIndex()
	
	//---set Write One Record
	public void setWriteOneRecord(String borrowedUserName, String borrowedPassword)
	{
		recordCount = RESET_VALUE;
		try
		{
			PrintWriter filePW = new PrintWriter(new FileWriter(masterFileName, true));
			filePW.printf("%n%s\t%s%n", borrowedUserName, borrowedPassword);
			fileFoundFlag = FILE_FOUND;
			recordCount++;
			filePW.close();
		}//END of try
		catch (IOException ex)
		{
			fileFoundFlag = FILE_NOT_FOUND;
		}//END of catch
	}//END of setWriteOneRecord()
	
	//--------------------
	
	//Getters
	//---get Sequential Search
	public int getSeqSearch(String borrowedUserName)
	{
		int localIndex = 0;
		int found = 0;
		found = NOT_FOUND;
		while (localIndex < recordCount)
		{
			if (borrowedUserName.equals(userNames[localIndex]) == true)
			{
				found = localIndex;
				localIndex = recordCount;
			}//END of if
			else
			{
				localIndex++;
			}//END of else
		}//END of while
		return found;
	}//END of getSeqSearch()
	
	//---get Password Match
	public boolean getPasswordMatch(String borrowedBorrowedPassword)
	{
		return borrowedBorrowedPassword.equals(passwords[searchedIndex]);
	}//END of getPasswordMatch()
	
	//---get File Name
	public String getFileName()
	{
		return masterFileName;
	}//END of getFIleName()
	
	//---get Record Count
	public int getRecordCount()
	{
		return recordCount;
	}//END of getRecordCount()
	
	//---get Searched Index
	public int getSearchedIndex()
	{
		return searchedIndex;
	}//END of getSearchedIndex()
	
	//---get File FOund Flag
	public boolean getFileFoundFlag()
	{
		return fileFoundFlag;
	}//END of getFileFoundFlag()
	
	//--------------------
}//END of UserAccounts
