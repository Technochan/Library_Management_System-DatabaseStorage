package com.zsgs.chandru.librarymanagement.manageissuebook;

import com.zsgs.chandru.librarymanagement.admincontrols.AdminControlView;
import com.zsgs.chandru.librarymanagement.colortext.Color;
import com.zsgs.chandru.librarymanagement.exceptionmesage.ExceptionHandling;
import com.zsgs.chandru.librarymanagement.librarydatabase.LibraryDatabase;
import com.zsgs.chandru.librarymanagement.model.IssueBook;
import com.zsgs.chandru.librarymanagement.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ManageIssueBookView {
    private AdminControlView adminControlView;
    private ManageIssueBookModel manageIssueBookModel;
    private Scanner scan;

    public ManageIssueBookView(AdminControlView adminControlView) {
        this.adminControlView = adminControlView;
        manageIssueBookModel = new ManageIssueBookModel(this);
        scan = new Scanner(System.in);
    }

    public void initiateManage() {
        int choice;
        whileLoop:
        while (true) {
            System.out.println(
                    "\n\n---------------------------------" +
                            "\n| 1 | Issue Book                  |" +
                            "\n| 2 | Show Issue Books            |" +
                            "\n| 3 | Show OverDue Books          |" +
                            "\n| 4 | Show Issued Books Users     |" +
                            "\n| 5 | Revoke Book                 |" +
                            "\n| 6 | Back                        |" +
                            "\n----------------------------------"
            );
            choice = ExceptionHandling.getIntInput();
            switch (choice) {
                case 1:
                    issueBook();
                    break;
                case 2:
                    manageIssueBookModel.getIssuedBooks();
                    break;
                case 3:
                    manageIssueBookModel.getOverDueBooks();
                    break;
                case 4:
                    manageIssueBookModel.getIssuedBookUsers();
                    break;
                case 5:
                    manageIssueBookModel.initiateRevokeBook();
                    break;
                case 6:
                    break whileLoop;
                default:
                    System.out.println("Invalid Choice Try Again");
            }
        }
        adminControlView.initialControl();
    }


    public void showText(String text) {
        System.out.println(Color.ANSI_GREEN +
                "\n=================================\n" +
                text +
                "\n=================================" +
                Color.ANSI_RESET);
    }

    public void issueBook() {
        int choice;
        int bookId = 0, userId = 0;
        boolean validbookId = false;
        //For Choose the Book ID
        System.out.println(
                "\n======================================" +
                        "\n=    Did You Remember The Book Id    =" +
                        "\n=         1.YES      2.NO            =" +
                        "\n======================================="
        );
        choice = ExceptionHandling.getIntInput();
        switch (choice) {
            case 1:
                System.out.println("Enter The Book Id\n-----------------");
                while (!validbookId) {
                    try {
                        bookId = scan.nextInt();
                        validbookId = true;
                    } catch (InputMismatchException e) {
                        ExceptionHandling.printErrorMessage("Input Must Be Integer");
                        scan.next();
                        issueBook();
                    }
                }
                break;
            case 2:
                adminControlView.getManageBookViewInstance().getManageBookModelInstance().showAllBooks();
                System.out.println("Choose the Book Id\n-----------------");
                while (!validbookId) {
                    try {
                        bookId = scan.nextInt();
                        validbookId = true;
                    } catch (InputMismatchException e) {
                        ExceptionHandling.printErrorMessage("Input Must Be Integer");
                        scan.next();
                        issueBook();
                    }
                }
                break;
            default:
                System.out.println("Invalid Choice");
                issueBook();
        }
        //For Choose the User ID
        System.out.println(
                "\n======================================" +
                        "\n=      Whom To Issue The Book        =" +
                        "\n=            1.New User              =" +
                        "\n=         2.Existing User            =" +
                        "\n======================================="
        );
        choice = ExceptionHandling.getIntInput();
        boolean validUserId = false;
        switch (choice) {
            case 1:
                adminControlView.getManageUserViewInstance().addNewUser();
                System.out.println("\nEnter the User Id\n-------------------");
                while (!validUserId) {
                    try {
                        userId = scan.nextInt();
                        validUserId = true;
                    } catch (InputMismatchException e) {
                        ExceptionHandling.printErrorMessage("Input Must Be Integer");
                        scan.next();
                        issueBook();
                    }
                }
                break;
            case 2:
                adminControlView.getManageUserViewInstance().showAllUsers();
                System.out.println("\nEnter the User Id\n-------------------");
                while (!validUserId) {
                    try {
                        userId = scan.nextInt();
                        validUserId = true;
                    } catch (InputMismatchException e) {
                        ExceptionHandling.printErrorMessage("Input Must Be Integer");
                        scan.next();
                        issueBook();
                    }
                }
                break;
            default:
                System.out.println("Invalid Choice");
                issueBook();
        }
        manageIssueBookModel.processIssueBook(bookId, userId);
    }

    public void bookIssueSuccess(int bookId) {
        adminControlView.getManageBookViewInstance().getManageBookModelInstance().proceedStockUpdate(1, bookId, 'D');
    }

    public void showIssuedBook(ArrayList<IssueBook> issuedBookDetails) {
        System.out.println(
                "\n---------------------------------------------------------------------------------------------" +
                        "\nBook ID |  Book Name  |  User ID  |  Book Issue Date       |   Book Return Date" +
                        "\n---------------------------------------------------------------------------------------------"
        );
        for (IssueBook issueBook : issuedBookDetails) {
            System.out.println(issueBook.getBookId() + "\t|" + issueBook.getBook().getBookName() + "\t|" + issueBook.getUserId() + "\t|" + issueBook.getUserBookIssueDate() + "\t|" + issueBook.getUserBookReturnDate());
        }
        System.out.println(
                "\n---------------------------------------------------------------------------------------------"
        );
    }

    public void showIssuedBookUsers(HashMap<Integer, ArrayList<IssueBook>> issuedBookDetails) {
        System.out.println("\nIssued Book Users\n-------------------\n" +
                "\n----------------------------------------------------------------------------------------------------" +
                "\nUser ID \t\t| User Name \t\t| Issue Book Count \t\t| User Phone \t\t| User EmailId \t\t| User Address" +
                "\n----------------------------------------------------------------------------------------------------"
        );
        for (int userId : issuedBookDetails.keySet()) {
            ArrayList<IssueBook> records = issuedBookDetails.get(userId);
            User user = LibraryDatabase.getInstance().getUserById(userId);

            System.out.println(userId + "\t\t| " + user.getUserName() + "\t\t| " + records.size() + "\t\t| " + user.getUserPhoneNo() + "\t\t| " + user.getUserEmailId() + "\t\t| " + user.getUserAddress());
        }
        System.out.println(
                "\n-----------------------------------------------------------------------------------------------------"
        );
        // Expand the books
        System.out.println(
                "\n===============================================" +
                        "\n=    Would You Like to See The Book Details   =" +
                        "\n=               1.YES      2.NO               =" +
                        "\n==============================================="
        );
        int choice = ExceptionHandling.getIntInput();
        switch (choice) {
            case 1:
                int userId = 0;
                boolean validUserId = false;
                while (!validUserId) {
                    try {
                        System.out.println("Choose The User Id");
                        userId = scan.nextInt();
                        validUserId = true;
                    } catch (InputMismatchException e) {
                        ExceptionHandling.printErrorMessage("Input Must Be Integer");
                        scan.next();
                    }
                }
                manageIssueBookModel.printParticularBook(userId);
            case 2:
                return;
            default:
                System.out.println("Invalid Input");
        }
    }

    public void initiateRevokeBook() {
        int userId = 0, bookId = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.println("\nChoose User Id & Book Id\n----------------------------");
            try {
                System.out.println("\nEnter User Id\n-----------------");
                userId = scan.nextInt();
                System.out.println("\nEnter Book Id\n-----------------");
                bookId = scan.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                ExceptionHandling.printErrorMessage("Input Must Be Integer");
                scan.next();
                initiateRevokeBook();
            }
        }
        manageIssueBookModel.processRevoke(userId, bookId);
    }


    public void revokeSuccess(int bookId) {
        adminControlView.getManageBookViewInstance().getManageBookModelInstance().proceedStockUpdate(1, bookId, 'U');
    }
}
