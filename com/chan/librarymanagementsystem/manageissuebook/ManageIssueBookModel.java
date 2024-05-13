package com.zsgs.chandru.librarymanagement.manageissuebook;

import com.zsgs.chandru.librarymanagement.librarydatabase.LibraryDatabase;
import com.zsgs.chandru.librarymanagement.model.IdMaintain;
import com.zsgs.chandru.librarymanagement.model.IssueBook;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class ManageIssueBookModel {
    private ManageIssueBookView manageIssueBookView;

    public ManageIssueBookModel(ManageIssueBookView manageIssueBookView) {
        LibraryDatabase.getInstance().loadIssuedBookDetailsFromTable();
        this.manageIssueBookView = manageIssueBookView;
    }

    public void processIssueBook(int bookId, int userId) {
        if (isBookAvailable(bookId)) {
            if (isUserValidToGetBook(userId)) {
                manageIssueBookView.showText("Book Issued Successfully");
                updateIssueBookList(bookId, userId);
            } else {
                manageIssueBookView.showText("User is Already Borrow Limited Book \n\tTell Him to Return the Book First :)");
            }

        } else {
            manageIssueBookView.showText("Book Is Out Of Stock or Not Available");
        }

    }

    public boolean isUserValidToGetBook(int userId) {
        HashMap<Integer, ArrayList<IssueBook>> issuedBookDetails = LibraryDatabase.getInstance().getIssuedBookDetails();

        return !issuedBookDetails.containsKey(userId) || issuedBookDetails.get(userId).size() < 5;
    }

    public void updateIssueBookList(int bookId, int userId) {
        IssueBook issueBook = new IssueBook();
        issueBook.setBookId(bookId);
        issueBook.setUserId(userId);
        issueBook.setUserBookIssueDate();
        issueBook.setUserBookReturnDate();
        issueBook.setBook(LibraryDatabase.getInstance().getBookById(bookId));
//        issueBook.setUser(LibraryDatabase.getInstance().getUserDetails().get(userId));
        LibraryDatabase.getInstance().insertIssueBookDetails(userId, issueBook);
        LibraryDatabase.getInstance().insertIssuedBookDetailsToTable(userId, issueBook);

        manageIssueBookView.bookIssueSuccess(bookId);
    }

    public boolean isBookAvailable(int bookId) {
        return LibraryDatabase.getInstance().isBookAvailableInDatabase(bookId);
    }

    public void getIssuedBooks() {
        HashMap<Integer, ArrayList<IssueBook>> issuedBookDetails = LibraryDatabase.getInstance().getIssuedBookDetails();
        if (issuedBookDetails.isEmpty()) {
            manageIssueBookView.showText("No Books Are Issued");
        } else {
            ArrayList<IssueBook> issuedBooks = new ArrayList<>();
            for (ArrayList<IssueBook> issueBooks : issuedBookDetails.values()) {
                issuedBooks.addAll(issueBooks);
            }
            manageIssueBookView.showIssuedBook(issuedBooks);
        }

    }

    public void getIssuedBookUsers() {
        if (LibraryDatabase.getInstance().isIssuedBookListEmpty()) {
            manageIssueBookView.showText("No User Are Issued The Books");
        } else {
            manageIssueBookView.showIssuedBookUsers(LibraryDatabase.getInstance().getIssuedBookDetails());
        }
    }

    public void initiateRevokeBook() {
        if (LibraryDatabase.getInstance().isIssuedBookListEmpty()) {
            manageIssueBookView.showText("No User Are Issued The Books So No More Revoke");
        } else {
            manageIssueBookView.showIssuedBookUsers(LibraryDatabase.getInstance().getIssuedBookDetails());
            manageIssueBookView.initiateRevokeBook();
        }
    }

    // It Revoke the book then update the stock in the library
    public void processRevoke(int userId, int bookId) {
        HashMap<Integer, ArrayList<IssueBook>> issuedBookDetails = LibraryDatabase.getInstance().getIssuedBookDetails();
        if (issuedBookDetails.containsKey(userId)) {
            ArrayList<IssueBook> records = issuedBookDetails.get(userId);
            Iterator<IssueBook> iterator = records.iterator();
            while (iterator.hasNext()) {
                IssueBook record = iterator.next();
                if (record.getBookId() == bookId) {
                    iterator.remove();
                    manageIssueBookView.showText("Book revoked successfully");
                    LibraryDatabase.getInstance().deleteIssuedBookDetailsInTable(bookId, userId);

                    if (records.isEmpty()) {
                        issuedBookDetails.remove(userId);
                        System.out.println("The User Id" + record.getUserId() + " Doesn't have any Other Books");
                    }
                    manageIssueBookView.revokeSuccess(bookId);
                    LibraryDatabase.getInstance().updateStockInTable(bookId);
                    return;
                }
            }
            manageIssueBookView.showText("Book not found for the specified user ID and book ID");
        } else {
            manageIssueBookView.showText("User ID not found in issued book details");
        }
    }

    public void printParticularBook(int userId) {
        ArrayList<IssueBook> books = LibraryDatabase.getInstance().getParticularBookDetailsInDatabase(userId);
        if (books != null) {
            manageIssueBookView.showIssuedBook(books);
        } else {
            manageIssueBookView.showText("The User Has No Books");
        }
    }

    public void getOverDueBooks() {
        HashMap<Integer, ArrayList<IssueBook>> issuedBooks = LibraryDatabase.getInstance().getIssuedBookDetails();
        LocalDateTime now = LocalDateTime.now();
        ArrayList<IssueBook> overdueBooks = new ArrayList<>();

        System.out.println("Overdue Books:");
        for (ArrayList<IssueBook> issueBooks : issuedBooks.values()) {
            for (IssueBook issueBook : issueBooks) {
                LocalDateTime returnDate = parseDateTime(issueBook.getUserBookReturnDate());
                if (returnDate.isBefore(now)) {
                    overdueBooks.add(issueBook);
                }
            }
        }
        if (!overdueBooks.isEmpty()) {
            manageIssueBookView.showIssuedBook(overdueBooks);
        } else {
            manageIssueBookView.showText("No Over Due Books");
        }

    }

    private LocalDateTime parseDateTime(String dateTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateTimeStr, formatter);
    }
}
