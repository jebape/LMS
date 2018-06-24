/**
 * 
 */
package com.gcit.lms.jdbc;

import java.sql.SQLException;
import java.util.*;

import com.gcit.lms.entity.Branch;
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

	}

	private static void admin() {

	}

	private static void librarian() {
		Boolean run = true;
		
		System.out.printf("\n 1) Enter Branch you manage\n 2) Quite to previous\n");
		selection = consoleInput.nextLine();

		switch (selection) {
		case "1":
			// brach menu
			LibrarianService ls = new LibrarianService();
			try {
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
					Integer branchId = branches.get(i).getId();
					
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
