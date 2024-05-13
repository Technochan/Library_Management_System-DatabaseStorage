package com.zsgs.chandru.librarymanagement.databasejdbctypestorage.databasemanage;

import com.zsgs.chandru.librarymanagement.model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class InsertDataIntoDatabase {

    private static InsertDataIntoDatabase insertDataIntoDatabase;
    private DatabaseManager databaseManager;

    public InsertDataIntoDatabase() {
        databaseManager = new DatabaseManager();
    }

    public static InsertDataIntoDatabase getInstance() {
        if (insertDataIntoDatabase == null) {
            insertDataIntoDatabase = new InsertDataIntoDatabase();
        }
        return insertDataIntoDatabase;
    }

    // Method for inserting LibrarianDetails
    public void insertLibrarianDetails(int librarianId, Librarian librarian) {
        databaseManager.insertData(librarianId, librarian, "LibrarianDetails", "id", "name", "email", "phone", "address");
    }

    // Method for inserting LibrarianCredentials
    public void insertLibrarianCredentials(int librarianId, String pass) {
        databaseManager.insertData(librarianId, pass, "LibrarianCredentials", "librarian_id", "password");
    }

    // Method for inserting LibraryDetails
    public void insertLibraryDetails(int libraryId, Library library) {
        databaseManager.insertData(libraryId, library, "LibraryDetails", "library_id", "librarian_id", "name", "email", "phone", "address");
    }

    // Method for inserting LibrarianIdLibraryId
    public void insertLibrarianIdLibraryId(int librarianId, int libraryId) {
        databaseManager.insertData(librarianId, libraryId, "LibrarianIdLibraryId", "librarian_id", "library_id");
    }

    // Method for inserting UserDetails
    public void insertUserDetails(int userId, User user) {
        databaseManager.insertData(userId, user, "UserDetails", "User_id", "name", "phone", "email", "address");
    }

    public void deleteUserDetails(int userId) {
        databaseManager.deleteUser(userId);
    }

    // Method for inserting book details
    public void insertBookDetails(int bookId, Book book) {
        databaseManager.insertData(bookId, book, "BookDetails", "Book_id", "name", "author", "publisher", "publicationyear", "edition", "genre", "volume", "stock", "library_id");
    }

    public void deleteBookDetails(int bookId) {
        databaseManager.deleteBook(bookId);
    }

    // Method for inserting IssuedBookDetails
    public void insertIssuedBookDetails(int userId, IssueBook issueBook) {
        databaseManager.insertData(userId, issueBook, "IssuedBookDetails", "User_id", "Book_id", "Issued_Date", "Return_Date");
    }

    public void updateBookStock(int bookId, int updatedStock) {
        databaseManager.updateBookStock(bookId, updatedStock);
    }

    public void deleteIssuedBookDetails(int bookId, int userId) {
        databaseManager.deleteIssuedBookRecord(bookId, userId);
    }
}




