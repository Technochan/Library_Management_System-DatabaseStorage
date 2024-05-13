package com.zsgs.chandru.librarymanagement;

import com.zsgs.chandru.librarymanagement.librarydatabase.LibraryDatabase;
import com.zsgs.chandru.librarymanagement.login.LoginView;
import com.zsgs.chandru.librarymanagement.model.IdMaintain;

public class LibraryManagementMain {

    private static volatile LibraryManagementMain libraryManagementMain;

    private String appName = "Library Management System";
    private String appVersion = "0.1.0";

     public static LibraryManagementMain getInstance() {
        if (libraryManagementMain == null) {
            libraryManagementMain = new LibraryManagementMain();
        }
        return libraryManagementMain;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppName() {
        return appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void initialize() {
        LibraryDatabase.getInstance();
        LibraryDatabase.getInstance().loadLibrarianDetailsfromTable();
        new IdMaintain().loadLibrarianId();

        // Start login view
        LoginView loginView = new LoginView();
        loginView.initiate();
    }


    public static void main(String[] args) {
        LibraryManagementMain.getInstance().initialize();
    }
}
