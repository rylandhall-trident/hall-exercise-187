/*Author: Ryland Hall
 *Course: CPT187
 *Purpose: To create a checkout wizard program for SodNotZod that will store orders
 *StartDate: 4.16.20
 */

package edu.cpt187.hall.participation;

import java.util.Scanner;

public class MainClass
{
    /* Main Class Constants */
	public static final String[] MAIN_MENU_OPTIONS = {"Login", "Create Account", "Quit"};
	public static final String[] FILE_MENU_OPTIONS = {"Load Inventory", "Create Order", "Return to Main Menu"};
	public static final String[] MENU_CHARS = {"[A]", "[B]", "[Q]"};
	public static final String[] FILE_MENU_CHARS = {"[A]", "[B]", "[R]"};
	public static final String[] SUB_MENU_CHARS = {"[A]", "[B]", "[C]"};
	public static final String MASTER_ACCOUNTS_FILE = "MasterUserFile.dat";
	public static final String MASTER_FILE_NAME = "MasterOrderFile.dat";
	//--------------------
	
	/* Main Method */
	public static void main(String[] args)
	{
		//Class Objects
		//---Scanner
		Scanner input = new Scanner(System.in);
		//---Inventory
		Inventory currentInventory = new Inventory();
		//---SodOrder
		SodOrder mySodOrder = new SodOrder();
		//---OrderWriter
		OrderWriter sodOrders = new OrderWriter(MASTER_FILE_NAME);
		//---UserAccounts
		UserAccounts currentUser = new UserAccounts(MASTER_ACCOUNTS_FILE);
		//--------------------
		
		//Variables
		String userName = "";
		char menuSelection = ' ';
		//--------------------
		
		//Welcome Banner
		displayWelcomeBanner();
		//--------------------
		
		//INPUT
		//Main Menu
		menuSelection = validateMainMenu(input);
		
		//Run-While
		while (menuSelection != 'Q')
		{
			userName = getUserName(input);
			currentUser.setUserAccountArrays();
			if (menuSelection == 'A')
			{
				currentUser.setSearchedIndex(userName, getPassword(input));
				if (currentUser.getSearchedIndex() < 0)
				{
					displayLoginError();
				}//END of if
				else
				{
					menuSelection = validateFileMenu(input);
					while (menuSelection != 'R')
					{
						if (menuSelection == 'A')
						{
							currentInventory.setLoadItems(getFileName(input));
							if (currentInventory.getRecordCount() <= 0)
							{
								displayNotOpen();
							}//END of if
							else
							{
								mySodOrder.setItemCountArray(currentInventory.getMaxRecords());
							}//END of else
						}//END of if
						else
						{
							currentInventory.setSearchIndex(validateSearchValue(input));
							if (currentInventory.getItemSearchIndex() < 0)
							{
								displayNotFound();
							}//END of if
							else
							{
								mySodOrder.setLastItemSelectionIndex(currentInventory.getItemSearchIndex());
								mySodOrder.setItemID(currentInventory.getItemIDs());
								mySodOrder.setItemName(currentInventory.getItemNames());
								mySodOrder.setItemPrice(currentInventory.getItemPrices());
								mySodOrder.setHowMany(validateHowMany(input));
								if (mySodOrder.getInStockCount(currentInventory.getInStockCounts()) < mySodOrder.getHowMany())
								{
									displayOutOfStock();
								}//END of if
								else
								{
									mySodOrder.setDiscountType(validateDiscountMenu(input, currentInventory.getDiscountNames(), currentInventory.getDiscountRates()));
									mySodOrder.setDiscountName(currentInventory.getDiscountNames());
									mySodOrder.setDiscountRate(currentInventory.getDiscountRates());
									mySodOrder.setDecreaseInStock(currentInventory);
									mySodOrder.setPrizeName(currentInventory.getPrizeNames(), currentInventory.getRandomNumber());
									sodOrders.setWriteOrder(mySodOrder.getItemID(), mySodOrder.getItemName(), mySodOrder.getItemPrice(),
											mySodOrder.getHowMany(), mySodOrder.getTotalCost());
									//Item Receipt, Discounted
									if (mySodOrder.getDiscountRate() > 0.0)
									{
										displayItemReceipt(mySodOrder.getItemName(), mySodOrder.getItemPrice(), mySodOrder.getHowMany(), 
												mySodOrder.getDiscountName(), mySodOrder.getDiscountRate(), mySodOrder.getDiscountAmt(),
												mySodOrder.getDiscountPrice(), mySodOrder.getSubTotal(), mySodOrder.getTaxRate(),
												mySodOrder.getTaxAmt(), mySodOrder.getTotalCost(), mySodOrder.getPrizeName(),
												mySodOrder.getInStockCount(currentInventory.getInStockCounts()));
									}//END of if
									//Item Receipt, No Discount
									else
									{
										displayItemReceipt(mySodOrder.getItemName(), mySodOrder.getItemPrice(), mySodOrder.getHowMany(),
												mySodOrder.getSubTotal(), mySodOrder.getTaxRate(), mySodOrder.getTaxAmt(), 
												mySodOrder.getTotalCost(), mySodOrder.getPrizeName(),
												mySodOrder.getInStockCount(currentInventory.getInStockCounts()));
									}//END of else
								}//END of else
							}//END of else
						}//END of else
						menuSelection = validateFileMenu(input);
					}//END of while
				}//END of else
			}//END of if
			else
			{
				currentUser.setSearchedIndex(userName);
				if (currentUser.getSearchedIndex() < 0)
				{
					currentUser.setWriteOneRecord(userName, getPassword(input));
				}//END of if
				else
				{
					displayUserError();
				}//END of else
			}//END of else
			menuSelection = validateMainMenu(input);
		}//END of run-while
		
		//Final Report
		if (mySodOrder.getTotalCost() > 0.0)
		{
			currentInventory.setLoadItems(sodOrders.getFileName(), sodOrders.getRecordCount());
			displayFinalReport(userName, currentInventory.getDiscountNames(), mySodOrder.getDiscountCounts(), 
			currentInventory.getPrizeNames(), mySodOrder.getPrizeCounts(), currentInventory.getItemIDs(),
			currentInventory.getItemNames(), currentInventory.getItemPrices(), currentInventory.getOrderQuantities(),
			currentInventory.getOrderTotal(), currentInventory.getRecordCount());
		}//END of if
		//--------------------
		
		//Farewell Message
		displayFarewellMessage();
		//--------------------
		
		//Closing Input
		input.close();
	}//END of main method
	
	/* Void Methods */
	//---display Welcome Banner
	public static void displayWelcomeBanner()
	{
		//Welcome Banner
		System.out.println("-------------------------");
		System.out.println("Welcome to SodNotZod!");
		System.out.println("This program serves as a");
		System.out.println("checkout wizard for cashiers.");
		System.out.println("Software Version: 12.1.0");
		System.out.println("-------------------------");
		//--------------------
	}//END of displayWelcomeBanner()
	
	//---Main Menu
	public static void displayMainMenu()
	{
		int localIndex = 0;
		//Main Menu
		System.out.println("\n<--------MAIN MENU-------->");
		while (localIndex < MAIN_MENU_OPTIONS.length)
		{
			System.out.printf("%-4s%-16s\n", MENU_CHARS[localIndex], MAIN_MENU_OPTIONS[localIndex]);
			localIndex++;
		}//END of while
		System.out.println("<------------------------->");
		System.out.print("Enter your selection here: ");
	}//END of displayMainMenu()
	
	//---File Menu
	public static void displayFileMenu()
	{
		int localIndex = 0;
		//Main Menu
		System.out.println("\n<--------FILE MENU-------->");
		while (localIndex < FILE_MENU_OPTIONS.length)
		{
			System.out.printf("%-4s%-16s\n", FILE_MENU_CHARS[localIndex], FILE_MENU_OPTIONS[localIndex]);
			localIndex++;
		}//END of while
		System.out.println("<------------------------->");
		System.out.print("Enter your selection here: ");
	}//END of displayFileMenu()
	
	//---display Login Error
	public static void displayLoginError()
	{
		System.out.println("User Name or Password is incorrect.");
		System.out.println("Please try again.");
	}//END of displayLoginError()
	
	//---User Creation Error
	public static void displayUserError()
	{
		System.out.println("That User already exists.");
		System.out.println("Please enter a different User Name.");
	}//END of displayUserError()
	
	//---File Not Found
	public static void displayNotOpen()
	{
		System.out.println("The File you entered was not found or failed to open.");
		System.out.println("Please make sure you entered a valid file name.");
	}//END of displayNotOpen()
	
	//---Item Not Found
	public static void displayNotFound()
	{
		System.out.println("The Item ID you entered was not found.");
		System.out.println("Please enter another or valid Item ID.");
	}//END of displayNotFound()
	
	//---Out Of Stock
	public static void displayOutOfStock()
	{
		System.out.println("That Item is currently Out of Stock,");
		System.out.println("OR you entered more than available.");
		System.out.println("Please choose another item.");
	}//END of displayOutOfStock()
	
	//---Discount Menu
	public static void displayDiscountMenu(String[] borrowedDiscountNames, double[] borrowedDiscountRates)
	{
		int localIndex = 0;
		//Membership Menu (Discount Menu)
		System.out.println("\n<------Discount Menu------>");
		while (localIndex < borrowedDiscountNames.length)
		{
			System.out.printf("%-4s%-16s%5.1f%2s\n", SUB_MENU_CHARS[localIndex], borrowedDiscountNames[localIndex], (borrowedDiscountRates[localIndex] * 100), "%");
			localIndex++;
		}//END of while
		System.out.println("<------------------------->");
		System.out.print("Enter your selection here: ");
	}//END of displayDiscountMenu()
	
	//---Item Receipt
	public static void displayItemReceipt(String borrowedItemName, double borrowedItemPrice, 
			int borrowedHowMany, double borrowedSubTotal, double borrowedTaxRate, 
			double borrowedTax, double borrowedTotalCost, String borrowedPrizeName, int borrowedInStockCount)
	{
		//Item Receipt (No Discount)
		System.out.println("\n<--------SodNotZod-------->");
		System.out.printf("%-17s%1s%8.2f\n", " " + borrowedItemName, "$", borrowedItemPrice);
		System.out.printf("%-17s%9d\n", " Quantity", borrowedHowMany);
		System.out.println(" -------------------------");
		System.out.printf("%-17s%1s%8.2f\n", " Sub-Total", "$", borrowedSubTotal);
		System.out.printf("%-17s%1s%8.2f\n", " Tax: " + (borrowedTaxRate * 100) + "%", "$", borrowedTax);
		System.out.printf("%-17s%1s%8.2f\n", " Total", "$", borrowedTotalCost);
		System.out.println(" -------------------------");
		System.out.println(" You won: " + borrowedPrizeName);
		System.out.println(" " + borrowedItemName);
		System.out.printf("%-17s%1s%8d\n", " Stock Left", ":", borrowedInStockCount);
		System.out.println(" -------------------------");
		System.out.println("    Thanks for shopping");
		System.out.println("       at SodNotZod!");
		System.out.println("<------------------------->");
	}//END of displayItemReceipt()
	
	//---Item Receipt (Discount)
	public static void displayItemReceipt(String borrowedItemName, double borrowedItemPrice, 
			int borrowedHowMany, String borrowedDiscountName, double borrowedDiscountRate,
			double borrowedDiscountAmt, double borrowedDiscountPrice, double borrowedSubTotal,
			double borrowedTaxRate, double borrowedTax, double borrowedTotalCost, String borrowedPrizeName, int borrowedInStockCount)
	{
		//Item Receipt
		System.out.println("\n<--------SodNotZod-------->");
		System.out.println(" Member Status: " + borrowedDiscountName);
		System.out.println(" -------------------------");
		System.out.printf("%-17s%1s%8.2f\n", " " + borrowedItemName, "$", borrowedItemPrice);
		System.out.printf("%-17s%9d\n", " Quantity", borrowedHowMany);
		System.out.printf("%-17s%1s%8.2f\n", " Discount Price", "$", borrowedDiscountPrice);
		System.out.println(" -------------------------");
		System.out.printf("%-17s%1s%8.2f\n", " Sub-Total", "$", borrowedSubTotal);
		System.out.printf("%-17s%1s%8.2f\n", " Tax: " + (borrowedTaxRate * 100) + "%", "$", borrowedTax);
		System.out.printf("%-17s%1s%8.2f\n", " Total", "$", borrowedTotalCost);
		System.out.println(" -------------------------");
		System.out.println(" You won: " + borrowedPrizeName);
		System.out.println(" " + borrowedItemName);
		System.out.printf("%-17s%1s%8d\n", " Stock Left", ":", borrowedInStockCount);
		System.out.println(" -------------------------");
		System.out.println("    Thanks for shopping");
		System.out.println("       at SodNotZod!");
		System.out.println("<------------------------->");
	}//END of displayItemReceipt()
	
	//---Final Report
	public static void displayFinalReport(String borrowedUserName,
			String[] borrowedDiscountNames, int[] borrowedDiscountCounts, 
			String[] borrowedPrizeNames, int[] borrowedPrizeCounts,
			int[] borrowedItemIDs, String[] borrowedOrderItems, double[] borrowedItemPrices,
			int[] borrowedOrderQuantity, double[] borrowedOrderTotal, int borrowedRecordCount)
	{
		final int INDEX_RESET = 0;
		int localIndex = 0;
		double localTotal = 0.0;
		//Display of Final Report
		System.out.println("\n<------Final Report------->");
		System.out.println("Here is the Report, " + borrowedUserName);
		System.out.println("---------------------------");
		while (localIndex < borrowedDiscountNames.length)
		{
			System.out.printf("%-19s%-2s%5d\n", borrowedDiscountNames[localIndex] + "s", ":", borrowedDiscountCounts[localIndex]);
			localIndex++;
		}//END of while
		localIndex = INDEX_RESET;
		System.out.println("---------------------------");
		while (localIndex < borrowedPrizeNames.length)
		{
			System.out.printf("%-19s%-2s%5d\n", borrowedPrizeNames[localIndex], ":", borrowedPrizeCounts[localIndex]);
			localIndex++;
		}//END of while
		System.out.println("---------------------------");
		System.out.println("-------Order Details-------");
		System.out.printf("%12s%2s%4d\n", "Orders Made", ":", borrowedRecordCount);
		localIndex = INDEX_RESET;
		while (localIndex < borrowedRecordCount)
		{
			System.out.printf("%-5d%-21s\n", borrowedItemIDs[localIndex], borrowedOrderItems[localIndex]);
			System.out.printf("%-8s%-3d%-7s%8.2f\n", "Amount:", borrowedOrderQuantity[localIndex], "Price $", borrowedItemPrices[localIndex]);
			System.out.printf("%-16s%2s%8.2f\n", "Order Total:", "$", borrowedOrderTotal[localIndex]);
			System.out.println("---------------------------");
			localIndex++;
		}//END of while
		localIndex = INDEX_RESET;
		while (localIndex < borrowedRecordCount)
		{
			localTotal = localTotal + borrowedOrderTotal[localIndex];
			localIndex++;
		}//END of while
		System.out.printf("%12s%2s%8.2f\n", "Grand Total", "$", localTotal);
		System.out.println("<------------------------->");
	}//END of displayFinalReport()
	
	//---Farewell Message
	public static void displayFarewellMessage()
	{
		//Farewell Message
		System.out.println("\nThank you for using the SodNotZod");
		System.out.println("checkout wizard program.");
		
		System.out.println("\nThis program has ended.\n");
	}//END of displayFarewellMessage()
	
	//--------------------
	
	/* VR Methods */
	//---get User Name
	public static String getUserName(Scanner borrowedInput)
	{
		//Declares local variable "localUserName"
		String localUserName = "";

		//Asks for the User's Name
		System.out.print("User Name: ");
		localUserName = borrowedInput.next();

		//returns localUserName to main method
		return localUserName;
	}//END of getUserName()
	
	//---get Password
	public static String getPassword(Scanner borrowedInput)
	{
		//Declares local variable "localPassword"
		String localPassword = "";

		//Asks for the User's Name
		System.out.print("Password: ");
		localPassword = borrowedInput.next();

		//returns localUserName to main method
		return localPassword;
	}//END of getPassword()
	
	//---get File Name
	public static String getFileName(Scanner borrowedInput)
	{
		String localFileName = "";
		System.out.print("Please Enter the Inventory File Name: ");
		localFileName = borrowedInput.next();
		return localFileName;
	}//END of getFileName()
	
	//---validate Main Menu
	public static char validateMainMenu(Scanner borrowedInput)
	{
		//New local variable
		char localSelection = ' ';
		
		//Main Menu
		displayMainMenu();
		
		//Take Input
		localSelection = borrowedInput.next().toUpperCase().charAt(0);
		
		//Validation loop
		while (localSelection != 'A' && localSelection != 'B' && localSelection != 'Q')
		{
			//Error Message
			System.out.println("Invalid Input. Enter Valid Input.");
			
			//Display Main Menu
			displayMainMenu();
			
			//Take Input
			localSelection = borrowedInput.next().toUpperCase().charAt(0);
		}//END of while validation
		
		//return to calling method
		return localSelection;
	}//END of validateMainMenu()
	
	public static char validateFileMenu(Scanner borrowedInput)
	{
		//New local variable
		char localSelection = ' ';
		
		//Main Menu
		displayFileMenu();
		
		//Take Input
		localSelection = borrowedInput.next().toUpperCase().charAt(0);
		
		//Validation loop
		while (localSelection != 'A' && localSelection != 'B' && localSelection != 'R')
		{
			//Error Message
			System.out.println("Invalid Input. Enter Valid Input.");
			
			//Display Main Menu
			displayFileMenu();
			
			//Take Input
			localSelection = borrowedInput.next().toUpperCase().charAt(0);
		}//END of while validation
		
		//return to calling method
		return localSelection;
	}//END of validateMainMenu()
	
	//---validate Item Search ID
	public static int validateSearchValue(Scanner borrowedInput)
	{
		int localSearchValue = 0;
		System.out.print("Enter Item ID: ");
		localSearchValue = borrowedInput.nextInt();
		while (localSearchValue <= 0)
		{
			System.out.println("Please enter an ID greater than Zero.");
			System.out.print("Enter Valid Item ID: ");
			localSearchValue = borrowedInput.nextInt();
		}//END of while
		return localSearchValue;
	}//END of validateSearchValue()
	
	//---validate How Many
	public static int validateHowMany(Scanner borrowedInput)
	{
		//Creating local variable "localHowMany"
		int localHowMany = ' ';
		
		//prompts for User Input
		System.out.print("How Many: ");
		localHowMany = borrowedInput.nextInt();
		
		while (localHowMany <= 0)
		{
			//Displays Error
			System.out.println("Invalid Input. Please Enter valid input.");
			
			//Prompts User for Input
			System.out.print("How Many: ");
			localHowMany = borrowedInput.nextInt();
		}//END of while
		
		//return localHowMany to main method
		return localHowMany;
	}//END of validateHowMany()
	
	//---validate Discount Menu
	public static char validateDiscountMenu(Scanner borrowedInput, String[] borrowedDiscountNames, double[] borrowedDiscountRates)
	{
		//Creating local variable "localSelection"
		char localSelection = ' ';
		
		//displays Main Menu
		displayDiscountMenu(borrowedDiscountNames, borrowedDiscountRates);
		
		//Taking user Input
		localSelection = borrowedInput.next().toUpperCase().charAt(0);
		
		//Validates
		while (localSelection != 'A' && localSelection != 'B' && localSelection != 'C')
		{
			//Error Message
			System.out.println("Invalid Input. Please input valid input.");
			
			//Displays Main Menu
			displayDiscountMenu(borrowedDiscountNames, borrowedDiscountRates);
			
			//Asks for Input
			localSelection = borrowedInput.next().toUpperCase().charAt(0);
		}//END of while
		
		//returns localSelection to main method
		return localSelection;
	}//END of validateDiscountMenu()
	
	//--------------------
	
}//END of MainClass
