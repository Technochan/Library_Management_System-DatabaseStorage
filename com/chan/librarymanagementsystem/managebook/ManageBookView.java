package com.zsgs.chandru.librarymanagement.managebook;

import com.zsgs.chandru.librarymanagement.admincontrols.AdminControlView;
import com.zsgs.chandru.librarymanagement.colortext.Color;
import com.zsgs.chandru.librarymanagement.exceptionmesage.ExceptionHandling;
import com.zsgs.chandru.librarymanagement.model.Book;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class ManageBookView {
    private AdminControlView adminControlView;
    private ManageBookModel manageBookModel;
    private int libraryId;
    private Scanner scan;

    public ManageBookView(AdminControlView adminControlView) {
        this.adminControlView = adminControlView;
        manageBookModel = new ManageBookModel(this);
        scan = new Scanner(System.in);
    }

    public ManageBookModel getManageBookModelInstance() {
        return manageBookModel;
    }

    public void initiateManage(int libraryId) {
        this.libraryId = libraryId;
        int choice;
        whileLoop:
        while (true) {
            System.out.println(
                    "\n\n---------------------------------" +
                            "\n| 1 | Add New Book              |" +
                            "\n| 2 | Search Book               |" +
                            "\n| 3 | Delete Book               |" +
                            "\n| 4 | Show All Book             |" +
                            "\n| 5 | Books Stock Update        |" +
                            "\n| 6 | Go Back                   |" +
                            "\n---------------------------------"
            );
            choice = ExceptionHandling.getIntInput();
            switch (choice) {
                case 1:
                    addNewBook();
                    break;
                case 2:
                    searchBook();
                    break;
                case 3:
                    deleteBook();
                    break;
                case 4:
                    manageBookModel.showAllBooks();
                    break;
                case 5:
                    manageBookModel.stockUpdate();
                    break;
                case 6:
                    break whileLoop;
                default:
                    System.out.println("\nInvalid Choice try Again");
            }
        }
        adminControlView.initialControl();
    }

    public void showText(String text) {
        System.out.println(Color.ANSI_GREEN +
                "\n=================================\n" +
                "\t" + text +
                "\n=================================" +
                Color.ANSI_RESET);
    }

    public void addNewBook() {
        try {
            System.out.println("\n\nEnter the Book Name:");
            String name = scan.next();
            System.out.println("Enter the Book Author:");
            String author = scan.next();
            System.out.println("Enter the Book Publisher:");
            String publisher = scan.next();
            System.out.println("Enter the Book Edition:");
            String edition = scan.next();
            System.out.println("Enter the Book Genre:");
            String genre = scan.next();
            System.out.println("Enter the Book Publication Year - (YYYY)");
            int year = scan.nextInt();
            System.out.println("Enter the Book Volume: - (Integer value)");
            int volume = scan.nextInt();
            System.out.println("Enter the Book Availability Stock Count: - (Integer value)");
            int stock = scan.nextInt();
            manageBookModel.proceedAddNewBook(name, author, publisher, year, edition, genre, volume, stock, libraryId);
        } catch (InputMismatchException e) {
            ExceptionHandling.printErrorMessage(e.getMessage());
            addNewBook();
        }
    }

    public void duplicateBookFound() {
        int choice;
        do {
            System.out.println(
                    "\n======================================" +
                            "\n=  Would You Like To Update The Stock =" +
                            "\n=          1.YES      2.NO            =" +
                            "\n======================================="
            );

            choice = ExceptionHandling.getIntInput();
            switch (choice) {
                case 1:
                    System.out.println("\n\nChoose the Book Id");
                    manageBookModel.showAllBooks();
                    int bookId = scan.nextInt();
                    System.out.println("\nEnter the New Stock Count:");
                    int stockCount = scan.nextInt();
                    manageBookModel.proceedStockUpdate(stockCount, bookId, 'U');
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Invalid Choice");
            }
        } while (choice != 1);
    }

    public void searchBook() {
        System.out.println("\nEnter the word that you remember\n--------------------------------");
        String guessName = scan.next();
        manageBookModel.proceedSearchBook(guessName);
    }

    public void deleteBook() {
        int bookId;
        int choice;
        do {
            System.out.println(
                    "\n======================================" +
                            "\n=    Did You Remember The Book Id    =" +
                            "\n=         1.YES      2.NO            =" +
                            "\n======================================="
            );
            choice = ExceptionHandling.getIntInput();
            switch (choice) {
                case 1:
                    System.out.println("Enter the Book Id");
                    bookId = scan.nextInt();
                    manageBookModel.proceedDeleteBook(bookId);
                    break;
                case 2:
                    manageBookModel.showAllBooks();
                    System.out.println("\nChoose the Book Id");
                    bookId = scan.nextInt();
                    manageBookModel.proceedDeleteBook(bookId);
                    break;
                default:
                    System.out.println("\nInvalid Choice");
            }
        } while (choice != 1 && choice != 2);
    }


    public void stockUpdateDetails() {
        int bookId = 0;
        int updateCount;
        int choice;
        System.out.println(
                "\n======================================" +
                        "\n=    Did You Remember The Book Id    =" +
                        "\n=         1.YES      2.NO            =" +
                        "\n======================================="
        );
        choice = ExceptionHandling.getIntInput();
        switch (choice) {
            case 1:
                System.out.println("Enter the Book Id");
                bookId = scan.nextInt();
                break;
            case 2:
                manageBookModel.showAllBooks();
                System.out.println("\nChoose the Book Id");
                bookId = scan.nextInt();
                break;
            default:
                System.out.println("\nInvalid Choice");
                stockUpdateDetails();
        }
        if (manageBookModel.checkValidBookId(bookId)) {
            System.out.println(
                    "\n======================================" +
                            "\n=       1.Increase The Stock         =" +
                            "\n=       2.Decrease The Stock         =" +
                            "\n======================================="
            );
            choice = ExceptionHandling.getIntInput();
            switch (choice) {
                case 1:
                    System.out.println("Enter the Increase Count");
                    updateCount = scan.nextInt();
                    manageBookModel.proceedStockUpdate(updateCount, bookId, 'U');
                    break;
                case 2:
                    System.out.println("Enter the Decrease Count");
                    updateCount = scan.nextInt();
                    manageBookModel.proceedStockUpdate(updateCount, bookId, 'D');
                    break;
                default:
                    System.out.println("Invalid Choice");
                    stockUpdateDetails();
            }
        } else {
            System.out.println("Invalid Book Id");
            stockUpdateDetails();
        }
    }

    public void proceedShowAllBooks(ArrayList<Book> books) {
        System.out.println(
                "----------------------------------------------------------------------------------------------------------------------------------------------" +
                        "\nBookId \t| LibraryId \t| BookName \t| BookAuthor \t| BookPublisher \t| Publication Year \t| BookEdition \t| BookGenre \t| BookVolume \t| BookAvailabilityCount" +
                        "\n-------------------------------------------------------------------------------------------------------------------------------"
        );
        for (Book book : books) {
            System.out.println(book);
        }
        System.out.println(
                "----------------------------------------------------------------------------------------------------------------------------------------------"
        );
    }

}
