package com.zsgs.chandru.librarymanagement.databasejdbctypestorage.databasemanage;

import com.zsgs.chandru.librarymanagement.databasejdbctypestorage.databaseconnectionobject.DatabaseConnection;
import com.zsgs.chandru.librarymanagement.exceptionmesage.ExceptionHandling;
import com.zsgs.chandru.librarymanagement.model.Librarian;
import com.zsgs.chandru.librarymanagement.model.Book;
import com.zsgs.chandru.librarymanagement.model.IssueBook;
import com.zsgs.chandru.librarymanagement.model.Library;
import com.zsgs.chandru.librarymanagement.model.User;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DatabaseManager {


    private DatabaseConnection databaseConnection;
    private final String DB_NAME = "libraryManagementSystem";

    public DatabaseManager() {
        databaseConnection = new DatabaseConnection();
    }

    public <T> void insertData(int id, T record, String tableName, String... columnNames) {
        String query = generateQuery(tableName, columnNames);
        try {
            Connection connection = databaseConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.executeUpdate("USE " + DB_NAME);
            ps.setInt(1, id);

            if (record instanceof Librarian) {
                setLibrarianValues(ps, (Librarian) record);
            } else if (record instanceof String) {
                ps.setString(2, (String) record);   // For Librarian Credentials
            } else if (record instanceof Library) {
                setLibraryValues(ps, (Library) record);
            } else if (record instanceof Integer) {
                ps.setInt(2, (int) record);  // For Librarian Id and Library Id
            } else if (record instanceof User) {
                setUserValues(ps, (User) record);
            } else if (record instanceof Book) {
                setBookValues(ps, (Book) record);
            } else if (record instanceof IssueBook) {
                setIssuedBookValues(ps, (IssueBook) record);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandling.printErrorMessage(e.getMessage());
        }
    }

    // For Dynamically create a needed query
    private String generateQuery(String tableName, String... columnNames) {
        StringBuilder queryBuilder = new StringBuilder("INSERT INTO ");
        queryBuilder.append(tableName).append(" (");

        for (String columnName : columnNames) {
            queryBuilder.append(columnName).append(", ");
        }
        queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length()); // Remove the last comma and space
        queryBuilder.append(") VALUES (");
        for (int i = 0; i < columnNames.length; i++) {
            queryBuilder.append("?, ");
        }
        queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length()); // Remove the last comma and space
        queryBuilder.append(")");

        return queryBuilder.toString();
    }

    // For Librarian Details
    private void setLibrarianValues(PreparedStatement ps, Librarian librarian) throws SQLException {
        ps.setString(2, librarian.getLibrarianName());
        ps.setString(3, librarian.getLibrarianEmailId());
        ps.setString(4, librarian.getLibrarianPhoneNo());
        ps.setString(5, librarian.getLibrarianAddress());
    }

    // For Library Details
    private void setLibraryValues(PreparedStatement ps, Library library) throws SQLException {
        ps.setInt(2, library.getLibraryInchargeId());
        ps.setString(3, library.getLibraryName());
        ps.setString(4, library.getLibraryEmailId());
        ps.setString(5, library.getLibraryPhoneNo());
        ps.setString(6, library.getLibraryAddress());
    }

    // For User Details
    private void setUserValues(PreparedStatement ps, User user) throws SQLException {
        ps.setString(2, user.getUserName());
        ps.setString(3, user.getUserPhoneNo());
        ps.setString(4, user.getUserEmailId());
        ps.setString(5, user.getUserAddress());
    }

    // For Book Details
    private void setBookValues(PreparedStatement ps, Book book) throws SQLException {
        ps.setString(2, book.getBookName());
        ps.setString(3, book.getBookAuthor());
        ps.setString(4, book.getBookPublisher());
        ps.setInt(5, book.getBookPublicationYear());
        ps.setString(6, book.getBookEdition());
        ps.setString(7, book.getBookGenre());
        ps.setInt(8, book.getBookVolume());
        ps.setInt(9, book.getBookAvailabilityStock());
        ps.setInt(10, book.getLibraryId());
    }

    // For Issued Book Details
    private void setIssuedBookValues(PreparedStatement ps, IssueBook issueBook) throws SQLException {
        ps.setInt(2, issueBook.getBookId());
        ps.setString(3, issueBook.getUserBookIssueDate());
        ps.setString(4, issueBook.getUserBookReturnDate());
    }

    // For Update The New Book Stock
    public void updateBookStock(int bookId, int updatedStock) {
        Connection connection = databaseConnection.getConnection();
        String updateQuery = "UPDATE BookDetails SET stock = ? WHERE Book_id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(updateQuery);
            ps.executeUpdate("USE " + DB_NAME);
            ps = connection.prepareStatement(updateQuery);
            ps.setInt(1, updatedStock);
            ps.setInt(2, bookId);
            ps.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandling.printErrorMessage(e.getMessage());
        }

    }

    // For Delete the User in Table
    public void deleteUser(int userId) {
        Connection connection = databaseConnection.getConnection();
        String deleteQuery = "DELETE FROM UserDetails WHERE User_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(deleteQuery);
            ps.executeUpdate("USE " + DB_NAME);
            ps = connection.prepareStatement(deleteQuery);
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandling.printErrorMessage(e.getMessage());
        }

    }

    // For Delete the Book in Table
    public void deleteBook(int bookId) {
        Connection connection = databaseConnection.getConnection();
        String deleteQuery = "DELETE FROM BookDetails WHERE Book_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(deleteQuery);
            ps.executeUpdate("USE " + DB_NAME);
            ps = connection.prepareStatement(deleteQuery);
            ps.setInt(1, bookId);
            ps.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandling.printErrorMessage(e.getMessage());
        }
    }

    // For Delete the Issued Book Record in Table
    public void deleteIssuedBookRecord(int bookId, int userId) {
        Connection connection = databaseConnection.getConnection();
        String deleteQuery = "DELETE FROM IssuedBookDetails WHERE User_id = ? AND Book_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(deleteQuery);
            ps.executeUpdate("USE " + DB_NAME);
            ps = connection.prepareStatement(deleteQuery);
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            ps.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandling.printErrorMessage(e.getMessage());
        }
    }
}
