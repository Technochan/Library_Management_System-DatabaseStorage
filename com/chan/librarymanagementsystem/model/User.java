package com.zsgs.chandru.librarymanagement.model;

public class User {

    private int userId;
    private String userName;
    private String userPhoneNo;
    private String userEmailId;
    private String userAddress;
    private static int uniqueUserId;

    public User() {
    }

    public User(int userId, String name, String phone, String email, String address) {
        this.userId = userId;
        userName = name;
        userPhoneNo = phone;
        userEmailId = email;
        userAddress = address;
    }

    public void setUniqueUserId(int id) {
        uniqueUserId = id;
    }

    public int getUniqueUserId() {
        return uniqueUserId;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId() {
        userId = ++uniqueUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoneNo() {
        return userPhoneNo;
    }

    public void setUserPhoneNo(String userPhoneNo) {
        this.userPhoneNo = userPhoneNo;
    }

    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    @Override
    public String toString() {
        return userId + " \t|  " + userName + " \t|  " + userPhoneNo + " \t| " + userEmailId + " \t| " + userAddress;
    }

}
