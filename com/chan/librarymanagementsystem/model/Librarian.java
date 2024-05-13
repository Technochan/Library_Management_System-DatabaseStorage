package com.zsgs.chandru.librarymanagement.model;

public class Librarian {
    private String librarianName;
    private int librarianUserId;
    private String librarianEmailId;
    private String librarianPhoneNo;
    private String librarianAddress;
    private static int librarianUniqueId = 100;

    public Librarian() {
    }

    public Librarian(int id, String name, String email, String phone, String address) {
        librarianUserId = id;
        librarianName = name;
        librarianEmailId = email;
        librarianPhoneNo = phone;
        librarianAddress = address;
    }

    public void setUniqueLibrarianId(int id) {
        if (id != 0) {
            librarianUniqueId = id;
        }
    }

    public int getUniqueLibrarianId() {
        return librarianUniqueId;
    }

    public String getLibrarianName() {
        return librarianName;
    }

    public void setLibrarianName(String librarianName) {
        this.librarianName = librarianName;
    }

    public int getLibrarianUserId() {
        return librarianUserId;
    }

    public void setAdminUserId() {
        librarianUserId = ++librarianUniqueId;
    }

    public String getLibrarianEmailId() {
        return librarianEmailId;
    }

    public void setLibrarianEmailId(String librarianEmailId) {
        this.librarianEmailId = librarianEmailId;
    }

    public String getLibrarianAddress() {
        return librarianAddress;
    }

    public void setLibrarianAddress(String librarianAddress) {
        this.librarianAddress = librarianAddress;
    }

    public String getLibrarianPhoneNo() {
        return librarianPhoneNo;
    }

    public void setLibrarianPhoneNo(String librarianPhoneNo) {
        this.librarianPhoneNo = librarianPhoneNo;
    }
}
