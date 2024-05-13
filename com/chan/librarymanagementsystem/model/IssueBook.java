package com.zsgs.chandru.librarymanagement.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class IssueBook {
    private int bookId;
    private int userId;
    private String userBookIssueDate;
    private String userBookReturnDate;
    private Book book;

    public IssueBook() {
    }

    public IssueBook(int userId, int bookId, String issuedDate, String returnDate) {
        this.userId = userId;
        this.bookId = bookId;
        userBookIssueDate = issuedDate;
        userBookReturnDate = returnDate;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserBookIssueDate() {
        return userBookIssueDate;
    }

    public void setUserBookIssueDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        userBookIssueDate = now.format(formatter);
    }

    public String getUserBookReturnDate() {
        return userBookReturnDate;
    }

    public void setUserBookReturnDate() {
        int returnDateCount = 5; // Modify here or else in future update i will update like ask while issuing the book
        LocalDateTime now = LocalDateTime.now().plusDays(returnDateCount);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        userBookReturnDate = now.format(formatter);
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String toString() {
        return bookId + " \t| " + book.getBookName() + " \t| " + userId + " \t| " + userBookIssueDate + " \t| " + userBookReturnDate;
    }

}
