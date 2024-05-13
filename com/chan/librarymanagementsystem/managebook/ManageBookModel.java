package com.zsgs.chandru.librarymanagement.managebook;

import com.zsgs.chandru.librarymanagement.librarydatabase.LibraryDatabase;
import com.zsgs.chandru.librarymanagement.model.Book;
import com.zsgs.chandru.librarymanagement.model.IdMaintain;

import java.util.ArrayList;
import java.util.HashMap;


public class ManageBookModel {

    private ManageBookView manageBookView;

    public ManageBookModel(ManageBookView manageBookView) {
        this.manageBookView = manageBookView;
        LibraryDatabase.getInstance().loadBookDetailsFromTable();
        new IdMaintain().loadBookId();
    }

    public void proceedAddNewBook(String name, String author, String publisher, int publicationYear, String edition, String genre, int volume, int stock, int libraryId) {
        Book book = new Book();
        book.setBookId();
        book.setBookName(name);
        book.setBookAuthor(author);
        book.setBookPublisher(publisher);
        book.setBookPublicationYear(publicationYear);
        book.setBookEdition(edition);
        book.setBookGenre(genre);
        book.setBookVolume(volume);
        book.setBookAvailabilityStock(stock);
        book.setLibraryId(libraryId);
        if (checkDuplicateBook(book)) {
            manageBookView.showText("The Book " + book.getBookName() + " is Already in Database");
            manageBookView.duplicateBookFound();
        } else {
            LibraryDatabase.getInstance().insertBookDetails(book.getBookId(), book);
            manageBookView.showText("Book Updated in the Library");
            LibraryDatabase.getInstance().insertBookDetailsToTable(book.getBookId(), book);
        }
    }

    public boolean checkDuplicateBook(Book book) {
        return LibraryDatabase.getInstance().checkDuplicateBookInDatabase(book);
    }

    public void proceedSearchBook(String guessName) {
        ArrayList<Book> searchResult = LibraryDatabase.getInstance().searchBookInDatabase(guessName);
        if (searchResult.isEmpty()) {
            manageBookView.showText("Sorry No Book Found");
        } else {
            manageBookView.proceedShowAllBooks(searchResult);
        }
    }

    public void showAllBooks() {
        HashMap<Integer, Book> booksInLibrary = LibraryDatabase.getInstance().getBooksInLibrary();
        ArrayList<Book> books = new ArrayList<>(booksInLibrary.values());
        if (books.isEmpty()) {
            manageBookView.showText("Book List is Empty");
        } else {
            manageBookView.proceedShowAllBooks(books);
        }
    }

    public void proceedDeleteBook(int bookId) {
        if (LibraryDatabase.getInstance().deleteBookInDatabase(bookId)) {
            manageBookView.showText("Book Deleted Successfully");

        } else {
            manageBookView.showText("No Book Found In The Given Id");
        }
    }

    public void proceedStockUpdate(int stockCount, int bookId, char updation) {
        Book book = LibraryDatabase.getInstance().updateBookStockInDatabase(bookId);
        if (updation == 'U') {
            book.setBookAvailabilityStock(book.getBookAvailabilityStock() + stockCount);
            manageBookView.showText("Stock Update Done");
            LibraryDatabase.getInstance().updateStockInTable(bookId);
        } else {
            if (book.getBookAvailabilityStock() > 0) {
                book.setBookAvailabilityStock(book.getBookAvailabilityStock() - stockCount);
                manageBookView.showText("Stock Update Done");
                LibraryDatabase.getInstance().updateStockInTable(bookId);
            } else {
                manageBookView.showText("Book is Already Out of Stock");
            }
        }
    }

    public boolean checkValidBookId(int bookId) {
        return LibraryDatabase.getInstance().checkValidBookIdInDatabase(bookId);
    }

    public void stockUpdate() {
        if (LibraryDatabase.getInstance().checkBooksAreEmpty()) {
            manageBookView.showText("No Book Are Available");
        } else {
            manageBookView.stockUpdateDetails();
        }
    }
}
