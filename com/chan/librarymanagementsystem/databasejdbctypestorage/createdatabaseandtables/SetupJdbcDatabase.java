package com.zsgs.chandru.librarymanagement.databasejdbctypestorage.createdatabaseandtables;

import com.zsgs.chandru.librarymanagement.databasejdbctypestorage.databaseconnectionobject.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SetupJdbcDatabase {


    // SQL statement to create the LibrarianDetails table
    private final String CREATE_LIBRARIAN_DETAILS_TABLE = "CREATE TABLE LibrarianDetails (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(50), email VARCHAR(50), phone VARCHAR(10), address VARCHAR(50))";

    // SQL statement to create the LibrarianCredentials table with a foreign key constraint referencing LibrarianDetails
    private final String CREATE_LIBRARIAN_CREDENTIAL_TABLE = "CREATE TABLE LibrarianCredentials (librarian_id INT, password VARCHAR(50), FOREIGN KEY (librarian_id) REFERENCES LibrarianDetails(id))";

    // SQL statement to create the LibraryDetails table with a foreign key constraint referencing LibrarianDetails for Library Incharge
    private final String CREATE_LIBRARY_DETAILS_TABLE = "CREATE TABLE LibraryDetails (library_id INT AUTO_INCREMENT PRIMARY KEY, librarian_id INT, name VARCHAR(50), email VARCHAR(50), phone VARCHAR(10), address VARCHAR(50), FOREIGN KEY (librarian_id) REFERENCES LibrarianDetails(id))";

    // SQL statement to create the LibrarianIdLibraryId table
    private final String LIBRARIAN_ID_LIBRARY_ID_TABLE = "CREATE TABLE LibrarianIdLibraryId (librarian_id INT, library_id INT, FOREIGN KEY (librarian_id) REFERENCES LibrarianDetails(id), FOREIGN KEY (library_id) REFERENCES LibraryDetails(library_id))";

    // SQL statement to create the UserDetails table
    private final String USER_ID_USER_DETAILS_TABLE = "CREATE TABLE UserDetails (User_id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(50), phone VARCHAR(10), email VARCHAR(50), address VARCHAR(50))";

    // SQL statement to create the BookDetails table
    private final String BOOKS_IN_LIBRARY_TABLE = "CREATE TABLE BookDetails (Book_id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(50), author VARCHAR(50), publisher VARCHAR(50), publicationyear INT, edition VARCHAR(30), genre VARCHAR(50), volume INT, stock INT, library_id INT, FOREIGN KEY (library_id) REFERENCES LibraryDetails(library_id))";

    // SQL statement to create the IssuedBookDetails table
    private final String ISSUED_BOOK_DETAILS_TABLE = "CREATE TABLE IssuedBookDetails (User_id INT, Book_id INT, Issued_Date VARCHAR(50), Return_Date VARCHAR(50), FOREIGN KEY (Book_id) REFERENCES BookDetails(Book_id), FOREIGN KEY (User_id) REFERENCES UserDetails(User_id))";

//    private final String ALL_ID_MAINTAIN_TABLE = "CREATE TABLE IdMaintain (User_id int, Book_id int, )"


    public void createDatabaseSetup(String dbName) {
        try (Connection connection = new DatabaseConnection().getConnection();
             Statement statement = connection.createStatement();) {
            statement.executeUpdate("CREATE DATABASE " + dbName);
            statement.executeUpdate("USE " + dbName);
            createTableSetup(statement,
                    CREATE_LIBRARIAN_DETAILS_TABLE,
                    CREATE_LIBRARIAN_CREDENTIAL_TABLE,
                    CREATE_LIBRARY_DETAILS_TABLE,
                    LIBRARIAN_ID_LIBRARY_ID_TABLE,
                    USER_ID_USER_DETAILS_TABLE,
                    BOOKS_IN_LIBRARY_TABLE,
                    ISSUED_BOOK_DETAILS_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void createTableSetup(Statement statement, String... tableCreationQueries) throws SQLException {
        for (String query : tableCreationQueries) {
            statement.executeUpdate(query);
        }
        System.out.println("\nTables Created Successfully");
    }
}
