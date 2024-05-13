package com.zsgs.chandru.librarymanagement.model;

public class Book {

    private int bookId;
    private String bookName;
    private String bookAuthor;
    private String bookPublisher;
    private String bookEdition;
    private String bookGenre;
    private int bookVolume;
    private int bookPublishYear;
    private int bookAvailabilityStock;
    private int libraryId;
    private static int uniqueBookId = 1000;

    public Book() {
    }

    public Book(int bookId, String name, String author, String publisher, int publicationYear, String edition, String genre, int volume, int stock, int libraryId) {
        this.bookId = bookId;
        this.libraryId = libraryId;
        bookName = name;
        bookAuthor = author;
        bookPublisher = publisher;
        bookPublishYear = publicationYear;
        bookEdition = edition;
        bookGenre = genre;
        bookVolume = volume;
        bookAvailabilityStock = stock;
    }

    public void setUniqueBookId(int id) {
        if (id != 0) {
            uniqueBookId = id;
        }
    }

    public int getUniqueBookId() {
        return uniqueBookId;
    }


    public void setBookId() {
        bookId = ++uniqueBookId;
    }

    public int getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(int libraryId) {
        this.libraryId = libraryId;
    }

    public int getBookId() {
        return bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public int getBookPublicationYear() {
        return bookPublishYear;
    }

    public void setBookPublicationYear(int bookPublishYear) {
        this.bookPublishYear = bookPublishYear;
    }

    public String getBookEdition() {
        return bookEdition;
    }

    public void setBookEdition(String bookEdition) {
        this.bookEdition = bookEdition;
    }

    public String getBookGenre() {
        return bookGenre;
    }

    public void setBookGenre(String bookGenre) {
        this.bookGenre = bookGenre;
    }

    public int getBookVolume() {
        return bookVolume;
    }

    public void setBookVolume(int bookVolume) {
        this.bookVolume = bookVolume;
    }

    public int getBookAvailabilityStock() {
        return bookAvailabilityStock;
    }

    public void setBookAvailabilityStock(int bookAvailabilityStock) {
        this.bookAvailabilityStock = bookAvailabilityStock;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }


    public String toString() {
        return bookId + " \t\t|\t " + libraryId + "\t \t| " + bookName + "  \t|\t " + bookAuthor + " \t|\t" + bookPublisher + " \t\t| " + bookPublishYear + " \t\t|\t " + bookEdition + " \t|\t " + bookGenre + " \t|\t " + bookVolume + " \t\t|\t " + bookAvailabilityStock;
    }

}
