/**
 * 
 */
package com.gcit.lms.jdbc;

import java.sql.SQLException;
import java.util.*;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Branch;
import com.gcit.lms.service.BorrowerService;
import com.gcit.lms.service.LibrarianService;


/**
 * @author Jesús Peral
 *
 */
public class MenuUI {

	static Scanner consoleInput = new Scanner(System.in);
	static String selection;

	public static void main(String[] args) {
		mainUI();
	}

	public static void mainUI() {
		System.out.print("Welcome to the GCIT Library Management System. ");
		Boolean run = true;
		while (run) {
			System.out.println("Which category of user are you?");
			System.out.printf("\n 1) Librarian\n 2) Admin\n 3) Borrower\n 4) Quit\n");
			selection = consoleInput.nextLine();

			switch (selection) {
			case "1":
				// Librarian
				librarian();
				break;

			case "2":
				// Admin
				admin();
				break;

			case "3":
				// Borrower
				borrower();
				break;
			case "4":
				System.out.println("Bye!");
				run = false;
				break;
			default:
				System.out.println("Wrong input.");
			}
		}
	}

	private static void borrower() {
		
		Boolean valid = false;
		String cardNo;
		BorrowerService bs = new BorrowerService();
		Borrower user = null;
		System.out.print("Enter your Card Number: ");
		while (!valid) {
			cardNo = consoleInput.nextLine();
			try {
				user = bs.getBorrower(cardNo);
				if (user != null)
					valid = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Signed in as: "+user.getName());
		System.out.printf("\n 1) Check out a book\n 2) Return a book\n 3) Quit to previous\n");
		selection = consoleInput.nextLine();
		switch (selection) {
		case "1":
			// checkout a book
			System.out.println("Pick the Branch you want to check out from:");
			List<Branch> branches = null;
			try {
				branches = bs.getAllBranches();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			int i;
			for(i = 1; i <= branches.size(); i++) {
				System.out.print((i)+") ");
				System.out.println(branches.get(i-1).getName());
			}
			System.out.println(i+") Quit to previous");
			selection = consoleInput.nextLine();
			if(selection.equals(Integer.toString(i))){
				return;
			}else {
				Integer branchId = branches.get(Integer.parseInt(selection)-1).getId();
				bs.
			}
			break;
		case "2":
			// checkout a book
			
			break;
		case "3":
			return;
		default:
			System.out.println("Invalid input");

		}
	}

	private static void admin() {

	}

	private static void librarian() {
		Boolean run = true;
		
		System.out.printf("\n 1) Enter Branch you manage\n 2) Quit to previous\n");
		selection = consoleInput.nextLine();

		switch (selection) {
		case "1":
			// brach menu
			LibrarianService ls = new LibrarianService();
			try {
				List<Book> booksFromBranch;
				List<Branch> branches = ls.getAllBranches();

				int i;
				for(i = 1; i <= branches.size(); i++) {
					System.out.print((i)+") ");
					System.out.println(branches.get(i-1).getName());
				}
				System.out.println(i+") Quit to previous");
				selection = consoleInput.nextLine();
				if(selection.equals(Integer.toString(i))){
					return;
				}else {
					Integer branchId = branches.get(Integer.parseInt(selection)-1).getId();
					System.out.printf("\n 1) Update the details of the Library\n 2) Add copies to the Branch\n 3) Quite to previous\n");
					selection = consoleInput.nextLine();
					switch(selection) {
					case "1":
						// Update library
						System.out.printf("You have chosen to update the Branch with Branch Id: %d and Branch Name: %s.\n",branchId, branches.get(branchId-1).getName() );
						System.out.println("Enter ‘quit’ at any prompt to cancel operation.");
						System.out.println("Please enter new branch name or enter N/A for no change:");
						String branchName = consoleInput.nextLine();
						if ("quit".equalsIgnoreCase(branchName)) {
							break;
						}
						System.out.println("Please enter new branch address or enter N/A for no change:");
						String branchAddress = consoleInput.nextLine();
						if ("quit".equalsIgnoreCase(branchAddress)) {
							break;
						}
						ls.updateBranchByID(branchId, branchName, branchAddress);
						System.out.println("Branch successfully updated.");
						break;
					case "2":
						// add copies
						System.out.println("Pick the Book you want to add copies to your branch:");
						booksFromBranch = ls.getAllBooksFromBranch(branchId);
						if (!booksFromBranch.isEmpty() && booksFromBranch != null) {
							for (i = 1; i <= booksFromBranch.size(); i++) {
								System.out.print((i) + ") ");
								System.out.println(booksFromBranch.get(i - 1).getTitle()+" by "+booksFromBranch.get(i - 1).getAuthors().get(0).getName());
							}
							System.out.println(i + ") Quit to cancel operation");
							selection = consoleInput.nextLine();
							if (selection.equals(Integer.toString(i))) {
								break;
							} else {
								Integer selectedBook = booksFromBranch.get(Integer.parseInt(selection)-1).getId();
								System.out.println("Existing number of copies: "+ls.getNumberCopiesFrom(selectedBook, branchId));
								System.out.print("Enter new number of copies: ");
								ls.updateNoOfCopies(selectedBook, branchId, Integer.parseInt(consoleInput.nextLine()));
								
							}
						}
						else {
							System.out.println("There are not books for this branch");
							break;
						}
						
						break;
					case "3":
						//quit
						break;
					default:
						System.out.println("Wrong input.");	
					}
				}
				
				
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;

		case "2":
			return;
		default:
			System.out.println("Wrong input.");
		

		}
		
		
		
	}

}
