package com.zsgs.chandru.librarymanagement.librarydatabase;

//import com.google.gson.reflect.TypeToken;

import com.zsgs.chandru.librarymanagement.databasejdbctypestorage.JdbcDatabaseCheck;
//import com.zsgs.chandru.librarymanagement.databasejsontypestorage.DataHandlingHelper;
import com.zsgs.chandru.librarymanagement.databasejdbctypestorage.databasemanage.InsertDataIntoDatabase;
import com.zsgs.chandru.librarymanagement.databasejdbctypestorage.databasemanage.RetrieveDataFromDatabase;
import com.zsgs.chandru.librarymanagement.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class LibraryDatabase {

    private static LibraryDatabase libraryDatabase;
    private InsertDataIntoDatabase InsertDataIntoDatabase;
    private RetrieveDataFromDatabase retrieveDataFromDatabase;

    private LibraryDatabase() {
        librarianDetails = new HashMap<>();
        librarianCredentials = new HashMap<>();
        libraryDetails = new HashMap<>();
        adminIdLibraryId = new HashMap<>();
        booksInLibrary = new HashMap<>();
        userIdUserDetails = new HashMap<>();
        issuedBookDetails = new HashMap<>();
        idMaintainMap = new HashMap<>();

        new JdbcDatabaseCheck().checkDatabase();
        InsertDataIntoDatabase = new InsertDataIntoDatabase();
        retrieveDataFromDatabase = new RetrieveDataFromDatabase();
    }

    //Singleton Database class Instance
    public static LibraryDatabase getInstance() {
        if (libraryDatabase == null) {
            libraryDatabase = new LibraryDatabase();
        }
        return libraryDatabase;
    }


    // ------------------------------   Overall Data Structures used ------------------------------------------------
    private HashMap<Integer, Librarian> librarianDetails; // admin id , admin details
    private HashMap<Integer, String> librarianCredentials; // librarian id , librarian password
    private HashMap<Integer, Library> libraryDetails; // Library id , Library details
    private HashMap<Integer, Integer> adminIdLibraryId; // librarian id, library id
    private HashMap<Integer, Book> booksInLibrary; // Book id , Book Details
    private HashMap<Integer, User> userIdUserDetails; // user id , user Details
    private HashMap<Integer, ArrayList<IssueBook>> issuedBookDetails; //User Id , IssueBookDetails (Book,User)
    private HashMap<String, Integer> idMaintainMap; // All ID Maintaining

    // ------------------------------------------   END   ---------------------------------------------------------------


    //---------------------------------JDBC Database Storage START -----------------------------------------------------

    public void insertLibrarianDetailsToTable(int librarianId, Librarian librarian) {
        InsertDataIntoDatabase.insertLibrarianDetails(librarianId, librarian);
    }

    public void loadLibrarianDetailsfromTable() {
        librarianDetails = retrieveDataFromDatabase.loadLibrarianDetails();
    }

    // Method for inserting LibrarianCredentials
    public void insertLibrarianCredentialsToTable(int librarianId, String pass) {
        InsertDataIntoDatabase.insertLibrarianCredentials(librarianId, pass);
    }

    public void loadLibrarianCredentialsFromTable() {
        librarianCredentials = retrieveDataFromDatabase.loadLibrarianCredentialsDetail();
    }

    // Method for inserting LibraryDetails
    public void insertLibraryDetailsToTable(int libraryId, Library library) {
        InsertDataIntoDatabase.insertLibraryDetails(libraryId, library);
    }

    public void loadLibraryDetailsFromTable() {
        libraryDetails = retrieveDataFromDatabase.loadLibraryDetails();
    }

    // Method for inserting LibrarianIdLibraryId
    public void insertLibrarianIdLibraryIdToTable(int librarianId, int libraryId) {
        InsertDataIntoDatabase.insertLibrarianIdLibraryId(librarianId, libraryId);
    }

    public void loadLibrarianIdLibraryIdFromTable() {
        adminIdLibraryId = retrieveDataFromDatabase.loadLibrarianIdLibrary();
    }

    // Method for inserting UserDetails
    public void insertUserDetailsToTable(int userId, User user) {
        InsertDataIntoDatabase.insertUserDetails(userId, user);
    }

    public void deleteUserDetailsInTable(int userId) {
        InsertDataIntoDatabase.deleteUserDetails(userId);
    }

    public void loadUserDetailsFromTable() {
        userIdUserDetails = retrieveDataFromDatabase.loadUserDetails();
    }

    // Method for inserting BootDetails
    public void insertBookDetailsToTable(int bookId, Book book) {
        InsertDataIntoDatabase.insertBookDetails(bookId, book);
    }

    public void loadBookDetailsFromTable() {
        booksInLibrary = retrieveDataFromDatabase.loadBookDetails();
    }

    public void updateStockInTable(int bookId) {
        InsertDataIntoDatabase.updateBookStock(bookId, booksInLibrary.get(bookId).getBookAvailabilityStock());
    }

    public void deleteBookDetailsInTable(int bookId) {
        InsertDataIntoDatabase.deleteBookDetails(bookId);
    }

    // Method for inserting IssuedBookDetails
    public void insertIssuedBookDetailsToTable(int userId, IssueBook issueBook) {
        InsertDataIntoDatabase.insertIssuedBookDetails(userId, issueBook);
    }

    public void loadIssuedBookDetailsFromTable() {
        issuedBookDetails = retrieveDataFromDatabase.loadIssuedBookDetails();
    }

    public void deleteIssuedBookDetailsInTable(int bookId, int userId) {
        InsertDataIntoDatabase.deleteIssuedBookDetails(bookId, userId);
    }


    //---------------------------------JDBC Database Storage END -----------------------------------------------------


    // ----------------------------- Data Retrieve and Updation Process Start-------------------------------------------------

    // Librarian Details
    public boolean setupCheck() {
        return librarianDetails.isEmpty();
    }

    public void insertLibrarianDetails(int librarianId, Librarian librarian) {
        librarianDetails.put(librarianId, librarian);
    }

    public String getLibrarianName(int librarianId) {
        if (librarianDetails.containsKey(librarianId)) {
            return librarianDetails.get(librarianId).getLibrarianName();
        }
        return "No Admin Found";
    }


    // Librarian Credentials
    public void insertLibrarianCredentials(int librarianId, String password) {
        librarianCredentials.put(librarianId, password);
    }

    public boolean checkLibrarianCredentials(int librarianId, String password) {
        return librarianCredentials.entrySet().stream()
                .anyMatch(entry -> entry.getKey() == librarianId && entry.getValue().equals(password));

    }


    // Library Details
    public void insertLibraryDetails(int libraryId, Library library) {
        libraryDetails.put(libraryId, library);
    }

    public String getLibraryName(int libraryId) {
        return libraryDetails.entrySet().stream()
                .filter(entry -> entry.getKey() == libraryId)
                .findFirst()
                .map(entry -> entry.getValue().getLibraryName())
                .orElse("No Library Found");
    }

    public boolean isLibraryEmpty() {
        return libraryDetails.isEmpty();
    }


    // Librarian Id , Library Id

    public void insertAdminIdLibraryId(int adminId, int libraryId) {
        adminIdLibraryId.put(adminId, libraryId);
    }

    public int getLibraryId(int librarianId) {
        return adminIdLibraryId.getOrDefault(librarianId, 0);
    }


    //Book id , Books details
    public boolean checkBooksAreEmpty() {
        return booksInLibrary.isEmpty();
    }

    public void insertBookDetails(int bookId, Book book) {
        booksInLibrary.put(bookId, book);
    }

    public boolean checkDuplicateBookInDatabase(Book book) {
        return booksInLibrary.values().stream()
                .anyMatch(entry -> entry.getBookName().equals(book.getBookName()) &&
                        entry.getBookAuthor().equals(book.getBookAuthor()) &&
                        entry.getBookVolume() == book.getBookVolume());
    }

    public ArrayList<Book> searchBookInDatabase(String guessName) {
        return booksInLibrary.values().stream()
                .filter(book -> book.getBookName().contains(guessName))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public boolean deleteBookInDatabase(int bookId) {
        if (booksInLibrary.containsKey(bookId)) {
            booksInLibrary.remove(bookId);
            deleteBookDetailsInTable(bookId);
            return true;
        } else {
            return false;
        }
    }

    public Book updateBookStockInDatabase(int bookId) {
        return booksInLibrary.values().stream()
                .filter(book -> book.getBookId() == bookId)
                .findFirst()
                .orElse(null);
    }

    public boolean isBookAvailableInDatabase(int bookId) {
        return booksInLibrary.values().stream()
                .filter(book -> book.getBookId() == bookId)
                .map(Book::getBookAvailabilityStock)
                .findFirst()
                .orElse(0) > 0;
    }

    public HashMap<Integer, Book> getBooksInLibrary() {
        return booksInLibrary;
    }

    public Book getBookById(int bookId) {
        return booksInLibrary.get(bookId);
    }

    public boolean checkValidBookIdInDatabase(int bookId) {
        return booksInLibrary.containsKey(bookId);
    }


    //User details

    public void insertUserDetails(int userId, User user) {
        userIdUserDetails.put(userId, user);
    }

    public HashMap<Integer, User> getUserDetails() {
        return userIdUserDetails;
    }

    public User getUserById(int id) {
        return userIdUserDetails.get(id);
    }

    public ArrayList<User> searchUserInDatabase(String username) {
        ArrayList<User> users = userIdUserDetails.values().stream()
                .filter(user -> user.getUserName().contains(username))
                .collect(Collectors.toCollection(ArrayList::new));
        return users;

    }

    public boolean checkUserById(int userId) {
        return userIdUserDetails.containsKey(userId);
    }

    public boolean checkExistingUserByPhoneNo(String phoneNo) {
        return userIdUserDetails.values().stream().anyMatch(user -> user.getUserPhoneNo().equals(phoneNo));
    }

    public void deleteUser(int userId) {
        userIdUserDetails.remove(userId);
        deleteUserDetailsInTable(userId);
    }


    // Issue Book Section
    public void insertIssueBookDetails(int userId, IssueBook record) {
        ArrayList<IssueBook> records = issuedBookDetails.computeIfAbsent(userId, k -> new ArrayList<>());
        records.add(record);
    }

    public HashMap<Integer, ArrayList<IssueBook>> getIssuedBookDetails() {
        return issuedBookDetails;
    }

    public ArrayList<IssueBook> getParticularBookDetailsInDatabase(int userId) {
        return issuedBookDetails.get(userId);
    }

    public boolean isIssuedBookListEmpty() {
        return issuedBookDetails.isEmpty();
    }


    //Id Maintaining

    public void setUpdatedId(String idType, int id) {
        idMaintainMap.put(idType, id);
    }

    public int getUpdatedId(String idType) {
        return idMaintainMap.getOrDefault(idType, 0);
    }


    // ----------------------------- Data Retrieve and Updation Process End-------------------------------------------------


}
