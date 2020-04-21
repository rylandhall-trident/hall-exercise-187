/*Author: Ryland Hall
 *Course: CPT187
 *Purpose: To manage Inventory stored in files
 *StartDate: 4.16.20
 */

package edu.cpt187.hall.participation;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Inventory
{
	//Class Attributes
	private final String[] DISCOUNT_NAMES = {"Member", "Senior", "No Discount"};
	private final double[] DISCOUNT_RATES = {0.25, 0.15, 0.0};
	private final String[] PRIZE_NAMES = {"Grand Prize", "Great Prize", "Normal Prize"};
	private final int MAX_RECORDS = 5;
	private final int NOT_FOUND = -1;
	private final int RESET_VALUE = 0;
	private int[] itemIDs = new int[MAX_RECORDS];
	private String[] itemNames = new String[MAX_RECORDS];
	private double[] itemPrices = new double[MAX_RECORDS];
	private int[] orderQuantity = new int[MAX_RECORDS];
	private double[] orderTotal = new double[MAX_RECORDS];
	private int[] inStockCounts = new int[MAX_RECORDS];
	private int itemSearchIndex = 0;
	private int recordCount = 0;
	private Random prizeGenerator = new Random();
	//--------------------
	
	//Class Constructor
	public Inventory()
	{
	}//END of Inventory()
	//--------------------
	
	//Class Setters
	//---set Reduce Stock
	public void setReduceStock(int borrowedHowMany)
	{
		inStockCounts[itemSearchIndex] = inStockCounts[itemSearchIndex] - borrowedHowMany;
	}//END of setReduceStock()
	
	//---set Load Items
	public void setLoadItems(String borrowedFileName)
	{
		recordCount = RESET_VALUE;
		try
		{
			Scanner infile = new Scanner(new FileInputStream(borrowedFileName));
			while (infile.hasNext() == true && recordCount < MAX_RECORDS)
			{
				itemIDs[recordCount] = infile.nextInt();
				itemNames[recordCount] = infile.next();
				itemPrices[recordCount] = infile.nextDouble();
				inStockCounts[recordCount] = infile.nextInt();
				recordCount++;
			}//END of while
			infile.close();
			setBubbleSort();
		}//END of try
		catch (IOException ex)
		{
			recordCount = NOT_FOUND;
		}//END of catch
	}//END of setLoadItems()
	
	//---set Load Items
	public void setLoadItems(String borrowedFileName, int borrowedSize)
	{
		recordCount = RESET_VALUE;
		try
		{
			Scanner infile = new Scanner(new FileInputStream(borrowedFileName));
			while (infile.hasNext() == true && recordCount < MAX_RECORDS && recordCount < borrowedSize)
			{
				itemIDs[recordCount] = infile.nextInt();
				itemNames[recordCount] = infile.next();
				itemPrices[recordCount] = infile.nextDouble();
				orderQuantity[recordCount] = infile.nextInt();
				orderTotal[recordCount] = infile.nextDouble();
				recordCount++;
			}//END of while
			infile.close();
			setBubbleSort();
		}//END of try
		catch (IOException ex)
		{
			recordCount = NOT_FOUND;
		}//END of catch
	}//END of setLoadItems()
	
	//---set Search Index
	public void setSearchIndex(int borrowedID)
	{
		itemSearchIndex = getSearchResults(borrowedID);
	}//END of setSearchIndex()
	
	//---set Bubble Sort
	public void setBubbleSort()
	{
		int last = 0;
		boolean swap = false;
		int localIndex = 0;
		
		last = recordCount - 1;
		while (last > RESET_VALUE)
		{
			localIndex = RESET_VALUE;
			swap = false;
			while (localIndex < last)
			{
				if (itemIDs[localIndex] > itemIDs[localIndex + 1])
				{
					setSwapArrayElements(localIndex);
					swap = true;
				}//END of if
				localIndex++;
			}//END of while
			if (swap == false)
			{
				last = RESET_VALUE;
			}//END of if
			else
			{
				last = last - 1;
			}//END of else
		}//END of while
	}//END of setBubbleSort()
	
	//---set Swap Array Elements
	public void setSwapArrayElements(int borrowedIndex)
	{
		int localItemID = 0;
		String localItemName = "";
		double localItemPrice = 0.0;
		int localInStockCount = 0;
		int localOrderQuantity = 0;
		double localOrderTotal = 0.0;
		
		localItemID = itemIDs[borrowedIndex];
		localItemName = itemNames[borrowedIndex];
		localItemPrice = itemPrices[borrowedIndex];
		localOrderQuantity = orderQuantity[borrowedIndex];
		localInStockCount = inStockCounts[borrowedIndex];
		localOrderTotal = orderTotal[borrowedIndex];
		
		itemIDs[borrowedIndex] = itemIDs[borrowedIndex + 1];
		itemIDs[borrowedIndex + 1] = localItemID;
		itemNames[borrowedIndex] = itemNames[borrowedIndex + 1];
		itemNames[borrowedIndex + 1] = localItemName;
		itemPrices[borrowedIndex] = itemPrices[borrowedIndex + 1];
		itemPrices[borrowedIndex + 1] = localItemPrice;
		inStockCounts[borrowedIndex] = inStockCounts[borrowedIndex + 1];
		inStockCounts[borrowedIndex + 1] = localInStockCount;
		orderQuantity[borrowedIndex] = orderQuantity[borrowedIndex + 1];
		orderQuantity[borrowedIndex + 1] = localOrderQuantity;
		orderTotal[borrowedIndex] = orderTotal[borrowedIndex + 1];
		orderTotal[borrowedIndex + 1] = localOrderTotal;
	}//END of setSwapArrayElements()
	
	//--------------------
	
	//Class Getters
	//---get Search Results
	public int getSearchResults(int borrowedBorrowedID)
	{
		int last = 0;
		int mid = 0;
		int first = 0;
		boolean found = false;
		last = recordCount--;
		
		while (first <= last && found == false)
		{
			mid = ((first + last) / 2);
			if (itemIDs[mid] == borrowedBorrowedID)
			{
				found = true;
			}//END of if
			else
			{
				
				if (itemIDs[mid] < borrowedBorrowedID)
				{
					first = mid + 1;
				}//END of if
				else
				{
					last = mid - 1;
				}//END of else
			}//END of else
		}//END of while
		if (found == false)
		{
			mid = NOT_FOUND;
		}//END of if
		return mid;
	}//END of getSearchResults()
	
	//---get In Stock Counts
	public int[] getInStockCounts()
	{
		return inStockCounts;
	}//END of getInStockCounts()
	
	//---get Item IDs
	public int[] getItemIDs()
	{
		return itemIDs;
	}//END of getItemIDs()
	
	//---get Item Names
	public String[] getItemNames()
	{
		return itemNames;
	}//END of getItemNames()
	
	//---get Item Prices
	public double[] getItemPrices()
	{
		return itemPrices;
	}//END of getItemPrices()
	
	//---get Discount Names
	public String[] getDiscountNames()
	{
		return DISCOUNT_NAMES;
	}//END of getDiscountNames()
	
	//---get Discount Prices
	public double[] getDiscountRates()
	{
		return DISCOUNT_RATES;
	}//END of getDiscountRates()
	
	//---get Order Quantities
	public int[] getOrderQuantities()
	{
		return orderQuantity;
	}//END of getOrderQuantities()
	
	//---get Order Totals
	public double[] getOrderTotal()
	{
		return orderTotal;
	}//END of getOrderTotal()
	
	//---get Prize Names
	public String[] getPrizeNames()
	{
		return PRIZE_NAMES;
	}//END of getPrizeNames()
	
	//---get Random Number
	public int getRandomNumber()
	{
		return prizeGenerator.nextInt(PRIZE_NAMES.length);
	}//END of getRandomNumber()
	
	//---get Max Records
	public int getMaxRecords()
	{
		return MAX_RECORDS;
	}//END of getMaxRecords()
	
	//---get Item Search Index
	public int getItemSearchIndex()
	{
		return itemSearchIndex;
	}//END of getItemSearchIndex()
	
	//---get Record Count
	public int getRecordCount()
	{
		return recordCount;
	}//END of getRecordCount()
	
	//--------------------
}//END of Inventory
