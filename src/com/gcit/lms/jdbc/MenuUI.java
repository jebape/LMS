/**
 * 
 */
package com.gcit.lms.jdbc;

import java.sql.SQLException;
import java.util.*;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Branch;
import com.gcit.lms.entity.Loan;
import com.gcit.lms.entity.Publisher;
import com.gcit.lms.service.AdminService;
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
		String cardNo = null;
		BorrowerService bs = new BorrowerService();
		Borrower user = null;
		while (!valid) {
			System.out.print("Enter your Card Number: ");
			cardNo = consoleInput.nextLine();
			try {
				user = bs.getBorrower(cardNo);
				if (user != null)
					valid = true;
				else {
					System.out.print("Invalid ID. ");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Signed in as: " + user.getName());
		valid = true;
		while (valid) {
			System.out.printf("\n 1) Check out a book\n 2) Return a book\n 3) Quit to previous\n");
			selection = consoleInput.nextLine();
			int i;
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

				for (i = 1; i <= branches.size(); i++) {
					System.out.print((i) + ") ");
					System.out.println(branches.get(i - 1).getName());
				}
				System.out.println(i + ") Quit to previous");
				selection = consoleInput.nextLine();
				if (selection.equals(Integer.toString(i))) {
					break;
				} else {
					Branch branchId = branches.get(Integer.parseInt(selection) - 1);
					try {
						List<Book> books = bs.getAllBooksFromBranch(branchId.getId());
						if (!books.isEmpty() && books != null) {
							for (i = 1; i <= books.size(); i++) {
								System.out.print((i) + ") ");
								System.out.println(books.get(i - 1).getTitle() + " by "
										+ books.get(i - 1).getAuthors().get(0).getName());
							}
							System.out.println(i + ") Quit to cancel operation");
							selection = consoleInput.nextLine();
							if (selection.equals(Integer.toString(i))) {
								break;
							} else {
								Book selectedBook = books.get(Integer.parseInt(selection) - 1);
								bs.checkOutBook(selectedBook, branchId, cardNo);

							}
						} else {
							System.out.println("There are not available copies for this branch");
							break;
						}
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}

				}
				break;
			case "2":
				// return a book
				List<Book> books = null;
				try {
					books = bs.getAllLoansFromUser(cardNo);

					if (!books.isEmpty() && books != null) {
						for (i = 1; i <= books.size(); i++) {
							System.out.print((i) + ") ");
							System.out.println(books.get(i - 1).getTitle() + " by "
									+ books.get(i - 1).getAuthors().get(0).getName());
						}
						System.out.println(i + ") Quit to cancel operation");
						selection = consoleInput.nextLine();
						if (selection.equals(Integer.toString(i))) {
							break;
						} else {
							Book selectedBook = books.get(Integer.parseInt(selection) - 1);
							bs.checkInBook(selectedBook, cardNo);
						}
					} else {
						System.out.println("You do not have checkout books at this time.");
						break;
					}
				} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				break;
			case "3":
				valid = false;
				break;
			default:
				System.out.println("Invalid input");

			}
		}
	}

	private static void librarian() {
		Boolean runOutter = true, runInner;

		while (runOutter) {
			runInner = true;
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
					for (i = 1; i <= branches.size(); i++) {
						System.out.print((i) + ") ");
						System.out.println(branches.get(i - 1).getName());
					}
					System.out.println(i + ") Quit to previous");
					selection = consoleInput.nextLine();
					if (selection.equals(Integer.toString(i))) {
						break;
					} else {
						Integer branchId = branches.get(Integer.parseInt(selection) - 1).getId();
						while (runInner) {
							System.out.printf(
									"\n 1) Update the details of the Library\n 2) Add copies to the Branch\n 3) Quite to previous\n");
							selection = consoleInput.nextLine();
							switch (selection) {
							case "1":
								// Update library
								System.out.printf(
										"You have chosen to update the Branch with Branch Id: %d and Branch Name: %s.\n",
										branchId, branches.get(branchId - 1).getName());
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
										System.out.println(booksFromBranch.get(i - 1).getTitle() + " by "
												+ booksFromBranch.get(i - 1).getAuthors().get(0).getName());
									}
									System.out.println(i + ") Quit to cancel operation");
									selection = consoleInput.nextLine();
									if (selection.equals(Integer.toString(i))) {
										break;
									} else {
										Integer selectedBook = booksFromBranch.get(Integer.parseInt(selection) - 1)
												.getId();
										System.out.println("Existing number of copies: "
												+ ls.getNumberCopiesFrom(selectedBook, branchId));
										System.out.print("Enter new number of copies: ");
										ls.updateNoOfCopies(selectedBook, branchId,
												Integer.parseInt(consoleInput.nextLine()));

									}
								} else {
									System.out.println("There are not books for this branch");
									break;
								}

								break;
							case "3":
								runInner = false;
								break;
							default:
								System.out.println("Wrong input.");
							}
						}
					}
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				break;

			case "2":
				runOutter = false;
				break;
			default:
				System.out.println("Wrong input.");

			}
		}
	}

	private static void admin() {
		AdminService as = new AdminService();
		Boolean run = true;
		while (run) {
			System.out.println("Enter item to manage:");
			System.out.printf(
					"\n 1) Book & Author\n 2) Publishers\n 3) Branches\n 4) Borrowers\n 5) Loans\n 6) Quit to previous\n");
			selection = consoleInput.nextLine();
			try {
				switch (selection) {
				case "1":
					// Book & Author

					while (adminBA(as));

					break;
				case "2":
					// Publishers
					while (adminP(as));
					break;
				case "3":
					// Branches
					while (adminBr(as));
					break;
				case "4":
					// Borrowers
					while (adminBo(as));
					break;
				case "5":
					// Loans
					while (adminL(as));
					break;
				case "6":
					// Quit
					run = false;
					break;
				default:
					System.out.println("Wrong input");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static Boolean adminBA(AdminService as) throws SQLException {
		int i;
		List<Book> books;

		System.out.println("What task would you like to perform?");
		System.out.printf("\n 1) Add\n 2) Update\n 3) Delete\n 4) Read\n 5) Quit\n");
		selection = consoleInput.nextLine();
		switch (selection) {
		case "1":
			System.out.print("Insert book title: ");
			String bookTitle = consoleInput.nextLine();
			System.out.print("Insert author name: ");
			String authorName = consoleInput.nextLine();
			System.out.println("Choose publisher:");
			List<Publisher> publishers = as.getAllPublishers();
			if (!publishers.isEmpty() && publishers != null) {
				for (i = 1; i <= publishers.size(); i++) {
					System.out.print((i) + ") ");
					System.out.println(publishers.get(i - 1).getName());
				}
				System.out.println(i + ") Quit to cancel operation");
				selection = consoleInput.nextLine();
				if (selection.equals(Integer.toString(i))) {
					return true;
				} else {
					Book b = new Book();
					Author a = new Author();
					b.setTitle(bookTitle);
					b.setPublisher(publishers.get(Integer.parseInt(selection) - 1));
					a.setName(authorName);
					try {
						as.saveBook(b);
						as.saveAuthor(a);
						System.out.println("Book successfully saved.");
					} catch (SQLException e) {
						e.printStackTrace();
					}

				}
			} else {
				System.out.println("There are not publishers");
				return true;
			}

			break;
		case "2":
			books = as.getAllBooks();
			System.out.println("Select book to update:");
			for (i = 1; i <= books.size(); i++) {
				System.out.print((i) + ") ");
				System.out.println(books.get(i - 1).getTitle());
			}
			System.out.println(i + ") Quit to previous");
			selection = consoleInput.nextLine();
			if (selection.equals(Integer.toString(i))) {
				return true;
			} else {
				Integer bookId = books.get(Integer.parseInt(selection) - 1).getId();
				System.out.println("Please enter new book title or enter ‘quit’ to cancel operation");
				String title = consoleInput.nextLine();
				if ("quit".equalsIgnoreCase(title)) {
					return true;
				}

				Book editedBook = new Book();
				editedBook.setId(bookId);
				editedBook.setTitle(title);
				as.editBook(editedBook);
				System.out.println("Book successfully updated.");
			}
			break;
		case "3":
			books = as.getAllBooks();
			System.out.println("Select book to delete:");

			for (i = 1; i <= books.size(); i++) {
				System.out.print((i) + ") ");
				System.out.println(books.get(i - 1).getTitle());
			}
			System.out.println(i + ") Quit to previous");
			selection = consoleInput.nextLine();
			if (selection.equals(Integer.toString(i))) {
				return true;
			} else {
				Book book = books.get(Integer.parseInt(selection) - 1);
				as.deleteBook(book);
				System.out.println("Book successfully deleted.");
			}
			break;
		case "4":
			books = as.getAllBooks();
			System.out.println("Select book to read:");

			for (i = 1; i <= books.size(); i++) {
				System.out.print((i) + ") ");
				System.out.println(books.get(i - 1).getTitle());
			}
			System.out.println(i + ") Quit to previous");
			selection = consoleInput.nextLine();
			if (selection.equals(Integer.toString(i))) {
				return true;
			} else {
				Book book = books.get(Integer.parseInt(selection) - 1);
				System.out.println("ID: " + book.getId());
				System.out.println("Title:" + book.getTitle());
				if (!book.getAuthors().isEmpty() && book.getAuthors() != null)
					System.out.println("Author: " + book.getAuthors().get(0).getName());
				System.out.println("Publisher: " + book.getPublisher().getName());
			}
			break;
		case "5":
			return false;
		default:
			System.out.println("Wrong input");
		}
		return false;
	}

	private static Boolean adminP(AdminService as) throws SQLException {
		int i;
		List<Publisher> publishers;

		System.out.println("What task would you like to perform?");
		System.out.printf("\n 1) Add\n 2) Update\n 3) Delete\n 4) Read\n 5) Quit\n");
		selection = consoleInput.nextLine();
		switch (selection) {
		case "1":
			System.out.print("Insert Publisher Name: ");
			String publisherName = consoleInput.nextLine();
			System.out.print("Insert Publisher Address: ");
			String publisherAddr = consoleInput.nextLine();
			System.out.print("Insert Publisher Phone: ");
			String publisherPhone = consoleInput.nextLine();
			Publisher b = new Publisher();
			b.setName(publisherName);
			b.setAddress(publisherAddr);
			b.setPhone(publisherPhone);
			try {
				as.savePublisher(b);
				System.out.println("Publisher successfully saved.");
			} catch (SQLException e) {
				e.printStackTrace();
			}

			break;
		case "2":
			publishers = as.getAllPublishers();
			System.out.println("Select publisher to update:");
			for (i = 1; i <= publishers.size(); i++) {
				System.out.print((i) + ") ");
				System.out.println(publishers.get(i - 1).getName());
			}
			System.out.println(i + ") Quit to previous");
			selection = consoleInput.nextLine();
			if (selection.equals(Integer.toString(i))) {
				return true;
			} else {
				Publisher publisher = publishers.get(Integer.parseInt(selection) - 1);
				System.out.println("Enter ‘quit’ at any prompt to cancel operation.");
				System.out.println("Please enter new publisher name or enter N/A for no change:");
				String name = consoleInput.nextLine();
				Publisher editedPubl = new Publisher();
				editedPubl.setId(publisher.getId());
				if ("quit".equalsIgnoreCase(name)) {
					return true;
				} else if ("N/A".equalsIgnoreCase(name)) {
					editedPubl.setName(publisher.getName());
				} else {
					editedPubl.setName(name);
				}
				System.out.println("Please enter new publisher address or enter N/A for no change:");
				String addr = consoleInput.nextLine();
				if ("quit".equalsIgnoreCase(addr)) {
					return true;
				} else if ("N/A".equalsIgnoreCase(addr)) {
					editedPubl.setAddress(publisher.getAddress());
				} else {
					editedPubl.setAddress(addr);
				}
				System.out.println("Please enter new publisher phone or enter N/A for no change:");
				String phone = consoleInput.nextLine();
				if ("quit".equalsIgnoreCase(phone)) {
					return true;
				} else if ("N/A".equalsIgnoreCase(phone)) {
					editedPubl.setPhone(publisher.getPhone());
				} else {
					editedPubl.setPhone(phone);
				}

				as.editPublisher(editedPubl);
				System.out.println("Publisher successfully updated.");
			}
			break;
		case "3":
			publishers = as.getAllPublishers();
			System.out.println("Select publisher to delete:");

			for (i = 1; i <= publishers.size(); i++) {
				System.out.print((i) + ") ");
				System.out.println(publishers.get(i - 1).getName());
			}
			System.out.println(i + ") Quit to previous");
			selection = consoleInput.nextLine();
			if (selection.equals(Integer.toString(i))) {
				return true;
			} else {
				Publisher publisher = publishers.get(Integer.parseInt(selection) - 1);
				as.deletePublisher(publisher);
				System.out.println("Publisher successfully deleted.");
			}
			break;
		case "4":
			publishers = as.getAllPublishers();
			System.out.println("Select Publisher to read:");

			for (i = 1; i <= publishers.size(); i++) {
				System.out.print((i) + ") ");
				System.out.println(publishers.get(i - 1).getName());
			}
			System.out.println(i + ") Quit to previous");
			selection = consoleInput.nextLine();
			if (selection.equals(Integer.toString(i))) {
				return true;
			} else {
				Publisher publisher = publishers.get(Integer.parseInt(selection) - 1);
				System.out.println("ID: " + publisher.getId());
				System.out.println("Name:" + publisher.getName());
				System.out.println("Address: " + publisher.getAddress());
				System.out.println("Phone: " + publisher.getPhone());
			}
			break;
		case "5":
			return false;
		default:
			System.out.println("Wrong input");
		}
		return false;

	}

	private static Boolean adminBr(AdminService as) throws SQLException {
		int i;
		List<Branch> branches;

		System.out.println("What task would you like to perform?");
		System.out.printf("\n 1) Add\n 2) Update\n 3) Delete\n 4) Read\n 5) Quit\n");
		selection = consoleInput.nextLine();
		switch (selection) {
		case "1":
			System.out.print("Insert Branch Name: ");
			String branchName = consoleInput.nextLine();
			System.out.print("Insert Branch Address: ");
			String branchAddr = consoleInput.nextLine();

			Branch b = new Branch();
			b.setName(branchName);
			b.setAddress(branchAddr);
			try {
				as.saveBranch(b);
				System.out.println("Branch successfully saved.");
			} catch (SQLException e) {
				e.printStackTrace();
			}

			break;
		case "2":
			branches = as.getAllBranches();
			System.out.println("Select Branch to update:");
			for (i = 1; i <= branches.size(); i++) {
				System.out.print((i) + ") ");
				System.out.println(branches.get(i - 1).getName());
			}
			System.out.println(i + ") Quit to previous");
			selection = consoleInput.nextLine();
			if (selection.equals(Integer.toString(i))) {
				return true;
			} else {
				Branch branch = branches.get(Integer.parseInt(selection) - 1);
				System.out.println("Enter ‘quit’ at any prompt to cancel operation.");
				System.out.println("Please enter new Branch name or enter N/A for no change:");
				String name = consoleInput.nextLine();
				Branch editedBr = new Branch();
				editedBr.setId(branch.getId());
				if ("quit".equalsIgnoreCase(name)) {
					return true;
				} else if ("N/A".equalsIgnoreCase(name)) {
					editedBr.setName(branch.getName());
				} else {
					editedBr.setName(name);
				}
				System.out.println("Please enter new Branch address or enter N/A for no change:");
				String addr = consoleInput.nextLine();
				if ("quit".equalsIgnoreCase(addr)) {
					return true;
				} else if ("N/A".equalsIgnoreCase(addr)) {
					editedBr.setAddress(branch.getAddress());
				} else {
					editedBr.setAddress(addr);
				}

				as.editBranch(editedBr);
				System.out.println("Branch successfully updated.");
			}
			break;
		case "3":
			branches = as.getAllBranches();
			System.out.println("Select Branch to delete:");

			for (i = 1; i <= branches.size(); i++) {
				System.out.print((i) + ") ");
				System.out.println(branches.get(i - 1).getName());
			}
			System.out.println(i + ") Quit to previous");
			selection = consoleInput.nextLine();
			if (selection.equals(Integer.toString(i))) {
				return true;
			} else {
				Branch branch = branches.get(Integer.parseInt(selection) - 1);
				as.deleteBranch(branch);
				System.out.println("Branch successfully deleted.");
			}
			break;
		case "4":
			branches = as.getAllBranches();
			System.out.println("Select Branch to read:");

			for (i = 1; i <= branches.size(); i++) {
				System.out.print((i) + ") ");
				System.out.println(branches.get(i - 1).getName());
			}
			System.out.println(i + ") Quit to previous");
			selection = consoleInput.nextLine();
			if (selection.equals(Integer.toString(i))) {
				return true;
			} else {
				Branch branch = branches.get(Integer.parseInt(selection) - 1);
				System.out.println("ID: " + branch.getId());
				System.out.println("Name:" + branch.getName());
				System.out.println("Address: " + branch.getAddress());
			}
			break;
		case "5":
			return false;
		default:
			System.out.println("Wrong input");
		}
		return false;
	}

	private static Boolean adminBo(AdminService as) throws SQLException {
		int i;
		List<Borrower> borrowers;

		System.out.println("What task would you like to perform?");
		System.out.printf("\n 1) Add\n 2) Update\n 3) Delete\n 4) Read\n 5) Quit\n");
		selection = consoleInput.nextLine();
		switch (selection) {
		case "1":
			System.out.print("Insert Borrower Name: ");
			String borrowerName = consoleInput.nextLine();
			System.out.print("Insert Borrower Address: ");
			String borrowerAddr = consoleInput.nextLine();
			System.out.print("Insert Borrower Phone: ");
			String borrowerPhone = consoleInput.nextLine();
			Borrower b = new Borrower();
			b.setName(borrowerName);
			b.setAddress(borrowerAddr);
			b.setPhone(borrowerPhone);
			try {
				as.saveBorrower(b);
				System.out.println("Borrower successfully saved.");
			} catch (SQLException e) {
				e.printStackTrace();
			}

			break;
		case "2":
			borrowers = as.getAllBorrowers();
			System.out.println("Select Borrower to update:");
			for (i = 1; i <= borrowers.size(); i++) {
				System.out.print((i) + ") ");
				System.out.println(borrowers.get(i - 1).getName());
			}
			System.out.println(i + ") Quit to previous");
			selection = consoleInput.nextLine();
			if (selection.equals(Integer.toString(i))) {
				return true;
			} else {
				Borrower borrower = borrowers.get(Integer.parseInt(selection) - 1);
				System.out.println("Enter ‘quit’ at any prompt to cancel operation.");
				System.out.println("Please enter new Borrower name or enter N/A for no change:");
				String name = consoleInput.nextLine();
				Borrower editedBorrow = new Borrower();
				editedBorrow.setId(borrower.getId());
				if ("quit".equalsIgnoreCase(name)) {
					return true;
				} else if ("N/A".equalsIgnoreCase(name)) {
					editedBorrow.setName(borrower.getName());
				} else {
					editedBorrow.setName(name);
				}
				System.out.println("Please enter new Borrower address or enter N/A for no change:");
				String addr = consoleInput.nextLine();
				if ("quit".equalsIgnoreCase(addr)) {
					return true;
				} else if ("N/A".equalsIgnoreCase(addr)) {
					editedBorrow.setAddress(borrower.getAddress());
				} else {
					editedBorrow.setAddress(addr);
				}
				System.out.println("Please enter new Borrower phone or enter N/A for no change:");
				String phone = consoleInput.nextLine();
				if ("quit".equalsIgnoreCase(phone)) {
					return true;
				} else if ("N/A".equalsIgnoreCase(phone)) {
					editedBorrow.setPhone(borrower.getPhone());
				} else {
					editedBorrow.setPhone(phone);
				}

				as.editBorrower(editedBorrow);
				System.out.println("Borrower successfully updated.");
			}
			break;
		case "3":
			borrowers = as.getAllBorrowers();
			System.out.println("Select Borrower to delete:");

			for (i = 1; i <= borrowers.size(); i++) {
				System.out.print((i) + ") ");
				System.out.println(borrowers.get(i - 1).getName());
			}
			System.out.println(i + ") Quit to previous");
			selection = consoleInput.nextLine();
			if (selection.equals(Integer.toString(i))) {
				return true;
			} else {
				Borrower publisher = borrowers.get(Integer.parseInt(selection) - 1);
				as.deleteBorrower(publisher);
				System.out.println("Borrower successfully deleted.");
			}
			break;
		case "4":
			borrowers = as.getAllBorrowers();
			System.out.println("Select Borrower to read:");

			for (i = 1; i <= borrowers.size(); i++) {
				System.out.print((i) + ") ");
				System.out.println(borrowers.get(i - 1).getName());
			}
			System.out.println(i + ") Quit to previous");
			selection = consoleInput.nextLine();
			if (selection.equals(Integer.toString(i))) {
				return true;
			} else {
				Borrower publisher = borrowers.get(Integer.parseInt(selection) - 1);
				System.out.println("ID: " + publisher.getId());
				System.out.println("Name:" + publisher.getName());
				System.out.println("Address: " + publisher.getAddress());
				System.out.println("Phone: " + publisher.getPhone());
			}
			break;
		case "5":
			return false;
		default:
			System.out.println("Wrong input");
		}
		return false;

	}

	private static Boolean adminL(AdminService as) {
		System.out.println("Pick up Branch to find loans:");
		List<Branch> branches = null;
		try {
			branches = as.getAllBranches();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		int i;
		for (i = 1; i <= branches.size(); i++) {
			System.out.print((i) + ") ");
			System.out.println(branches.get(i - 1).getName());
		}
		System.out.println(i + ") Quit to previous");
		selection = consoleInput.nextLine();
		if (selection.equals(Integer.toString(i))) {
			return false;
		} else {
			Branch branchId = branches.get(Integer.parseInt(selection) - 1);
			try {
				List<Loan> loans = as.getAllLoansFromBranch(branchId.getId());
				if (!loans.isEmpty() && loans != null) {
					for (i = 1; i <= loans.size(); i++) {
						System.out.print((i) + ") ");
						System.out.println(loans.get(i - 1).getBook().getTitle() + " checked out by "
								+ loans.get(i - 1).getBorrower().getName());
						System.out.printf("\tDue Date: %s\n", loans.get(i - 1).getDueDate());
					}
					System.out.println(i + ") Quit to cancel operation");
					selection = consoleInput.nextLine();
					if (selection.equals(Integer.toString(i))) {
						return true;
					} else {
						Loan loan = loans.get(Integer.parseInt(selection) - 1);
						System.out.print("Insert new Due Date [yyyy-mm-dd]: ");
						String date = consoleInput.nextLine();
						as.updateDueDate(loan.getBook(), loan.getBranch(), loan.getBorrower(), date);
						System.out.println("Due Date updated.");

					}
				} else {
					System.out.println("There are not available copies for this branch");
					return false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;

	}

}
