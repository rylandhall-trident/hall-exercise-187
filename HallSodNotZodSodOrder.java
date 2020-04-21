/*Author: Ryland Hall
 *Course: CPT187
 *Purpose: To create and manage the current Order
 *StartDate: 4.16.20
 */

package edu.cpt187.hall.participation;

public class SodOrder
{
	//Class Attributes
	private final double ZERO_TOTAL = 0.0;
	private final double TAX_RATE = .075;
	private int[] itemCounts;
	private int[] discountCounts;
	private int[] prizeCounts;
	private char discountType = ' ';
	private int itemID = 0;
	private String itemName = "";
	private double itemPrice = 0.0;
	private String discountName = "";
	private double discountRate = 0.0;
	private int howMany = 0;
	private int lastItemSelectedIndex = 0;
	private String prizeName = "";
	//--------------------
	
	//Class Constructor
	public SodOrder()
	{
	}//END of SodOrder()
	//--------------------
	
	//Class Setters
	//---set Item Count Array
	public void setItemCountArray(int borrowedMaxRecords)
	{
		itemCounts = new int[borrowedMaxRecords];
	}//END of setItemCountArray()
	
	//---set Last Item Selected
	public void setLastItemSelectionIndex(int borrowedSearchIndex)
	{
		lastItemSelectedIndex = borrowedSearchIndex;
	}//END of setLastItemSelectionIndex()
	
	//---set Item ID
	public void setItemID(int[] borrowedItemIDs)
	{
		itemID = borrowedItemIDs[lastItemSelectedIndex];
	}//END of setItemID()
	
	//---set Item Name
	public void setItemName(String[] borrowedItemNames)
	{
		itemName = borrowedItemNames[lastItemSelectedIndex];
		itemCounts[lastItemSelectedIndex]++;
	}//END of setItemName()
	
	//---set Item Price
	public void setItemPrice(double[] borrowedItemPrices)
	{
		itemPrice = borrowedItemPrices[lastItemSelectedIndex];
	}//END of setItemPrice()
	
	//---set How Many
	public void setHowMany(int borrowedHowMany)
	{
		howMany = borrowedHowMany;
	}//END of setHowMany()
	
	//---set Discount Type
	public void setDiscountType(char borrowedMenuSelection)
	{
		discountType = borrowedMenuSelection;
	}//END of setDiscountType()
	
	//---set Discount Name
	public void setDiscountName(String[] borrowedDiscountNames)
	{
		if (discountCounts == null)
		{
			discountCounts = new int[borrowedDiscountNames.length];
		}//END of if
		if (discountType == 'A')
		{
			discountName = borrowedDiscountNames[0];
			discountCounts[0]++;
		}//END of if
		else if (discountType == 'B')
		{
			discountName = borrowedDiscountNames[1];
			discountCounts[1]++;
		}//END of else if
		else
		{
			discountName = borrowedDiscountNames[2];
			discountCounts[2]++;
		}//END of else
	}//END of setDiscountName()
	
	//---set Discount Rate
	public void setDiscountRate(double[] borrowedDiscountRates)
	{
		if (discountType == 'A')
		{
			discountRate = borrowedDiscountRates[0];
		}//END of if
		else if (discountType == 'B')
		{
			discountRate = borrowedDiscountRates[1];
		}//END of else if
		else
		{
			discountRate = borrowedDiscountRates[2];
		}//END of else
	}//END of setDiscountRate()
	
	//---set Prize Name
	public void setPrizeName(String[] borrowedPrizeNames, int borrowedPrizeIndex)
	{
		if (prizeCounts == null)
		{
			prizeCounts = new int[borrowedPrizeNames.length];
		}
		prizeName = borrowedPrizeNames[borrowedPrizeIndex];
		prizeCounts[borrowedPrizeIndex]++;
	}//END of setPrizeName()
	
	//---set Decrease Stock
	public void setDecreaseInStock(Inventory borrowedInventoryObject)
	{
		borrowedInventoryObject.setReduceStock(howMany);
	}//END of setDecreaseInStock()
	
	//--------------------
	
	//Class Getters
	//---get In Stock Count
	public int getInStockCount(int[] borrowedInStockCounts)
	{
		return borrowedInStockCounts[lastItemSelectedIndex];
	}//END of getInStockCount()
	
	//---get Item ID
	public int getItemID()
	{
		return itemID;
	}//END of getItemID()
	
	//---get Item Name
	public String getItemName()
	{
		return itemName;
	}//END of getItemName()
	
	//---get Item Price
	public double getItemPrice()
	{
		return itemPrice;
	}//END of getItemPrice()
	
	//---get Item Counts
	public int[] getItemCounts()
	{
		return itemCounts;
	}//END of getItemCounts()
	
	//---get How Many
	public int getHowMany()
	{
		return howMany;
	}//END of getHowMany()
	
	//---get Discount Name
	public String getDiscountName()
	{
		return discountName;
	}//END of getDiscountName()
	
	//---get Discount Rate
	public double getDiscountRate()
	{
		return discountRate;
	}//END of getDiscountRate()
	
	//---get Discount Counts
	public int[] getDiscountCounts()
	{
		return discountCounts;
	}//END of getDiscountCounts()
	
	//---get Discount Amount
	public double getDiscountAmt()
	{
		return itemPrice * discountRate;
	}//END of getDiscountAmt()
	
	//---get Discount Price
	public double getDiscountPrice()
	{
		return itemPrice - getDiscountAmt();
	}//END of getDiscountPrice()
	
	//---get Prize Name
	public String getPrizeName()
	{
		return prizeName;
	}//END of getPrizeName()
	
	//---get Prize Counts
	public int[] getPrizeCounts()
	{
		return prizeCounts;
	}//END of getPrizeCounts()
	
	//---get Sub Total
	public double getSubTotal()
	{
		return getDiscountPrice() * howMany;
	}//END of getSubTotal()
	
	//---get Tax Rate
	public double getTaxRate()
	{
		return TAX_RATE;
	}//END of getTaxRate()
	
	//---get Tax Amount
	public double getTaxAmt()
	{
		return getSubTotal() * TAX_RATE;
	}//END of getTaxAmt()
	
	//---get Total Cost
	public double getTotalCost()
	{
		if (discountType == ' ')
		{
			return ZERO_TOTAL;
		}//END of if
		else
		{
			return getSubTotal() + getTaxAmt();
		}//END of else
	}//END of getTotalCost()
	
	//--------------------
}//END of SodOrder
