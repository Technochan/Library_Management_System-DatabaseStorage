package com.zsgs.chandru.librarymanagement.model;

public class Library {
    private String libraryName;
    private String libraryEmailId;
    private int libraryId;
    private String libraryPhoneNo;
    private String libraryAddress;
    private int libraryInchargeId;
    private static int uniqueLibraryId;

    public Library() {
    }

    public Library(int libraryId, int librarianId, String name, String email, String phone, String address) {
        this.libraryId = libraryId;
        libraryInchargeId = librarianId;
        libraryName = name;
        libraryEmailId = email;
        libraryPhoneNo = phone;
        libraryAddress = address;
    }

    public void setUniqueLibraryId(int id) {
        uniqueLibraryId = id;
    }

    public int getUniqueLibraryId() {
        return uniqueLibraryId;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public void setLibraryId() {
        libraryId = ++uniqueLibraryId;
    }

    public int getLibraryId() {
        return libraryId;
    }

    public String getLibraryPhoneNo() {
        return libraryPhoneNo;
    }

    public void setLibraryPhoneNo(String libraryPhoneNo) {
        this.libraryPhoneNo = libraryPhoneNo;
    }

    public String getLibraryEmailId() {
        return libraryEmailId;
    }

    public void setLibraryEmailId(String libraryEmailId) {
        this.libraryEmailId = libraryEmailId;
    }

    public String getLibraryAddress() {
        return libraryAddress;
    }

    public void setLibraryAddress(String libraryAddress) {
        this.libraryAddress = libraryAddress;
    }


    public int getLibraryInchargeId() {
        return libraryInchargeId;
    }

    public void setLibraryInchargeId(int libraryInchargeId) {
        this.libraryInchargeId = libraryInchargeId;
    }

}