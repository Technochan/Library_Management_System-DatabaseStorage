package com.zsgs.chandru.librarymanagement.databasejdbctypestorage.databasemanage;

import com.zsgs.chandru.librarymanagement.databasejdbctypestorage.databaseconnectionobject.DatabaseConnection;
import com.zsgs.chandru.librarymanagement.exceptionmesage.ExceptionHandling;
import com.zsgs.chandru.librarymanagement.librarydatabase.LibraryDatabase;
import com.zsgs.chandru.librarymanagement.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RetrieveDataFromDatabase {
    private final String DB_NAME = "libraryManagementSystem";

    public HashMap<Integer, Librarian> loadLibrarianDetails() {
        HashMap<Integer, Librarian> librarianMap = new HashMap<>();
        int max = 0;
        try {
            Connection connection = new DatabaseConnection().getConnection();

            String sql = "SELECT id, name, email, phone, address FROM LibrarianDetails";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate("USE " + DB_NAME);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");
                Librarian librarian = new Librarian(id, name, email, phone, address);
                librarianMap.put(id, librarian);
                max = Math.max(max, id);
            }

        } catch (SQLException e) {
            ExceptionHandling.printErrorMessage(e.getMessage());
        }
        LibraryDatabase.getInstance().setUpdatedId("LibrarianId", max);
        return librarianMap;
    }

    public HashMap<Integer, String> loadLibrarianCredentialsDetail() {
        HashMap<Integer, String> credentialsMap = new HashMap<>();
        try {
            Connection connection = new DatabaseConnection().getConnection();
            String sql = "SELECT librarian_id, password FROM LibrarianCredentials";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate("USE " + DB_NAME);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int librarianId = resultSet.getInt("librarian_id");
                String password = resultSet.getString("password");
                credentialsMap.put(librarianId, password);
            }

        } catch (SQLException e) {
            ExceptionHandling.printErrorMessage(e.getMessage());
        }
        return credentialsMap;
    }

    public HashMap<Integer, Library> loadLibraryDetails() {
        HashMap<Integer, Library> libraryMap = new HashMap<>();
        int max = 0;
        try {
            Connection connection = new DatabaseConnection().getConnection();
            String sql = "SELECT library_id, librarian_id, name, email, phone, address FROM LibraryDetails";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate("USE " + DB_NAME);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int libraryId = resultSet.getInt("library_id");
                int librarianId = resultSet.getInt("librarian_id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");
                Library library = new Library(libraryId, librarianId, name, email, phone, address);
                libraryMap.put(libraryId, library);
                max = Math.max(max, libraryId);
            }
        } catch (SQLException e) {
            ExceptionHandling.printErrorMessage(e.getMessage());
        }
        LibraryDatabase.getInstance().setUpdatedId("LibraryId", max);
        return libraryMap;
    }

    public HashMap<Integer, Integer> loadLibrarianIdLibrary() {
        HashMap<Integer, Integer> librarianIdLibraryMap = new HashMap<>();
        try {
            Connection connection = new DatabaseConnection().getConnection();
            String sql = "SELECT librarian_id, library_id FROM LibrarianIdLibraryId";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate("USE " + DB_NAME);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int librarianId = resultSet.getInt("librarian_id");
                int libraryId = resultSet.getInt("library_id");
                librarianIdLibraryMap.put(librarianId, libraryId);

            }
        } catch (SQLException e) {
            ExceptionHandling.printErrorMessage(e.getMessage());
        }
        return librarianIdLibraryMap;
    }

    public HashMap<Integer, User> loadUserDetails() {
        HashMap<Integer, User> userMap = new HashMap<>();
        int max = 0;
        try {
            Connection connection = new DatabaseConnection().getConnection();
            String sql = "SELECT User_id, name, phone, email, address FROM UserDetails";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate("USE " + DB_NAME);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt("User_id");
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                User user = new User(userId, name, phone, email, address);
                userMap.put(userId, user);
                max = Math.max(max, userId);
            }
        } catch (SQLException e) {
            ExceptionHandling.printErrorMessage(e.getMessage());
        }
        LibraryDatabase.getInstance().setUpdatedId("UserId", max);
        return userMap;
    }

    public HashMap<Integer, Book> loadBookDetails() {
        HashMap<Integer, Book> bookMap = new HashMap<>();
        int max = 0;
        try {
            Connection connection = new DatabaseConnection().getConnection();
            String sql = "SELECT Book_id, name, author, publisher, publicationyear, edition, genre, volume, stock, library_id FROM BookDetails";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate("USE " + DB_NAME);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int bookId = resultSet.getInt("Book_id");
                String name = resultSet.getString("name");
                String author = resultSet.getString("author");
                String publisher = resultSet.getString("publisher");
                int publicationYear = resultSet.getInt("publicationyear");
                String edition = resultSet.getString("edition");
                String genre = resultSet.getString("genre");
                int volume = resultSet.getInt("volume");
                int stock = resultSet.getInt("stock");
                int libraryId = resultSet.getInt("library_id");
                Book book = new Book(bookId, name, author, publisher, publicationYear, edition, genre, volume, stock, libraryId);
                bookMap.put(bookId, book);
                max = Math.max(max, bookId);
            }

        } catch (SQLException e) {
            ExceptionHandling.printErrorMessage(e.getMessage());
        }
        LibraryDatabase.getInstance().setUpdatedId("BookId", max);
        return bookMap;
    }

    public HashMap<Integer, ArrayList<IssueBook>> loadIssuedBookDetails() {
        HashMap<Integer, ArrayList<IssueBook>> issuedBookMap = new HashMap<>();
        try {
            Connection connection = new DatabaseConnection().getConnection();
            String sql = "SELECT User_id, Book_id, Issued_Date, Return_Date FROM IssuedBookDetails";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate("USE " + DB_NAME);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt("User_id");
                int bookId = resultSet.getInt("Book_id");
                String issuedDate = resultSet.getString("Issued_Date");
                String returnDate = resultSet.getString("Return_Date");
                IssueBook issueBook = new IssueBook(userId, bookId, issuedDate, returnDate);

                // Check if the user already has entries in the map
                if (!issuedBookMap.containsKey(userId)) {
                    issuedBookMap.put(userId, new ArrayList<>());
                } else {
                    ArrayList<IssueBook> issuedBooks = issuedBookMap.get(userId);
                    issuedBooks.add(issueBook);
                    issuedBookMap.put(userId, issuedBooks);
                }

            }

            // Now, iterate over the issued books map and load book details for each book
            for (Map.Entry<Integer, ArrayList<IssueBook>> entry : issuedBookMap.entrySet()) {
                ArrayList<IssueBook> issuedBooks = entry.getValue();
                for (IssueBook issueBook : issuedBooks) {
                    loadBookDetails(connection, issueBook);
                }
            }
        } catch (SQLException e) {
            ExceptionHandling.printErrorMessage(e.getMessage());
        }
        return issuedBookMap;
    }

    private void loadBookDetails(Connection connection, IssueBook issueBook) throws SQLException {
        String bookQuery = "SELECT name, author, publisher, publicationyear, edition, genre, volume, stock, library_id FROM BookDetails WHERE Book_id = ?";
        try (PreparedStatement bookStatement = connection.prepareStatement(bookQuery)) {
            bookStatement.setInt(1, issueBook.getBookId());
            ResultSet bookResultSet = bookStatement.executeQuery();
            if (bookResultSet.next()) {
                String name = bookResultSet.getString("name");
                String author = bookResultSet.getString("author");
                String publisher = bookResultSet.getString("publisher");
                int publicationYear = bookResultSet.getInt("publicationyear");
                String edition = bookResultSet.getString("edition");
                String genre = bookResultSet.getString("genre");
                int volume = bookResultSet.getInt("volume");
                int stock = bookResultSet.getInt("stock");
                int libraryId = bookResultSet.getInt("library_id");

                Book book = new Book(issueBook.getBookId(), name, author, publisher, publicationYear, edition, genre, volume, stock, libraryId);
                issueBook.setBook(book);
            }
        }
    }
}
