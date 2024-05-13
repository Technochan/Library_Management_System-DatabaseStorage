package com.zsgs.chandru.librarymanagement.model;

import com.zsgs.chandru.librarymanagement.librarydatabase.LibraryDatabase;

public class IdMaintain {

    public void loadUserId() {
        User user = new User();
        int userId = LibraryDatabase.getInstance().getUpdatedId("UserId");
        user.setUniqueUserId(userId);
    }

    public void loadBookId() {
        Book book = new Book();
        int bookId = LibraryDatabase.getInstance().getUpdatedId("BookId");
        book.setUniqueBookId(bookId);
    }

    public void loadLibraryId() {
        Library library = new Library();
        int libraryId = LibraryDatabase.getInstance().getUpdatedId("LibraryId");
        library.setUniqueLibraryId(libraryId);
    }

    public void loadLibrarianId() {
        Librarian librarian = new Librarian();
        int librarianId = LibraryDatabase.getInstance().getUpdatedId("LibrarianId");
        librarian.setUniqueLibrarianId(librarianId);
    }
}
