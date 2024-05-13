package com.zsgs.chandru.librarymanagement.admincontrols;

import com.zsgs.chandru.librarymanagement.LibraryManagementMain;
import com.zsgs.chandru.librarymanagement.databasejdbctypestorage.databaseconnectionobject.DatabaseConnection;
import com.zsgs.chandru.librarymanagement.exceptionmesage.ExceptionHandling;
import com.zsgs.chandru.librarymanagement.librarydatabase.LibraryDatabase;
import com.zsgs.chandru.librarymanagement.login.LoginView;
import com.zsgs.chandru.librarymanagement.managebook.ManageBookView;
import com.zsgs.chandru.librarymanagement.manageissuebook.ManageIssueBookView;
import com.zsgs.chandru.librarymanagement.manageuser.ManageUserView;


public class AdminControlView {
    private ManageBookView manageBookView;
    private ManageUserView manageUserView;
    private ManageIssueBookView manageIssueBookView;
    private int librarianId;

    public ManageBookView getManageBookViewInstance() {
        return manageBookView;
    }

    public ManageUserView getManageUserViewInstance() {
        return manageUserView;
    }

    public AdminControlView(int currLibrarianId) {
        manageBookView = new ManageBookView(this);
        manageUserView = new ManageUserView(this);
        manageIssueBookView = new ManageIssueBookView(this);
        librarianId = currLibrarianId;
    }

    public void initialControl() {
        int libraryId = LibraryDatabase.getInstance().getLibraryId(librarianId);
        System.out.println(
                "\n*****************************************************" +
                        "\n\tWelcome  " + LibraryDatabase.getInstance().getLibraryName(libraryId) +
                        " Library Admin Mr. " + LibraryDatabase.getInstance().getLibrarianName(librarianId) +
                        "\n*****************************************************"
        );

        System.out.println(
                """

                        ---------------------------------
                        | 1 | Control Library Details   |
                        | 2 | Control Users Details     |
                        | 3 | Issue The Book            |
                        | 4 | Logout                    |
                        | 5 | Exit                      |
                        ---------------------------------"""
        );
        int choice = ExceptionHandling.getIntInput();
        switch (choice) {
            case 1:
                manageBookView.initiateManage(libraryId);
                break;
            case 2:
                manageUserView.initiateManage();
                break;
            case 3:
                manageIssueBookView.initiateManage();
                break;
            case 4:
                System.out.println("You're logged out successfully");
                new LoginView().initiate();
                break;
            case 5:
                new DatabaseConnection().closeConnection();
                System.out.println("\nThanks for using " + LibraryManagementMain.getInstance().getAppName() + "  - Version :" + LibraryManagementMain.getInstance().getAppVersion());
                System.exit(0);
            default:
                System.out.println("Invalid Input");
                initialControl();
        }
    }
}
