package com.zsgs.chandru.librarymanagement.login;

import com.zsgs.chandru.librarymanagement.LibraryManagementMain;
import com.zsgs.chandru.librarymanagement.LibrarySetup.LibrarySetupView;
import com.zsgs.chandru.librarymanagement.admincontrols.AdminControlView;
import com.zsgs.chandru.librarymanagement.colortext.Color;
import com.zsgs.chandru.librarymanagement.exceptionmesage.ExceptionHandling;
import com.zsgs.chandru.librarymanagement.librarydatabase.LibraryDatabase;
import com.zsgs.chandru.librarymanagement.validation.InputValidation;

import java.util.InputMismatchException;
import java.util.Scanner;

public class LoginView {

    private LoginModel loginModel;
    private static Scanner scan;

    public LoginView() {
        loginModel = new LoginModel(this);
        scan = new Scanner(System.in);
    }

    public void initiate() {
        System.out.println("************************************************");
        System.out.println("\t Welcome to " + LibraryManagementMain.getInstance().getAppName() + "\t\t");
        System.out.println("\t\t\tVersion - " + LibraryManagementMain.getInstance().getAppVersion() + "\t\t");
        System.out.println("************************************************");
        setupAccount();
    }

    private void setupAccount() {
        if (LibraryDatabase.getInstance().setupCheck()) {
            proceedSetupAccount();
        } else {
            proceedLogin();
        }
    }

    private void proceedSetupAccount() {
        System.out.println("\n\nEnter the Pre-Installed UserName :");
        String name = scan.next();
        System.out.println("\nEnter the Pre-Installed Password :");
        String password = scan.next();
        loginModel.checkInitialCredential(name, password);
    }

    public void showAlert(String alertText) {
        System.out.println(Color.ANSI_RED +
                "\n=================================\n" +
                "\t" + alertText +
                "\n=================================" +
                Color.ANSI_RESET);

    }

    public void showText(String text) {
        System.out.println(Color.ANSI_GREEN +
                "\n=================================\n" +
                text +
                "\n=================================" +
                Color.ANSI_RESET);
    }

    public void loginFailed() {
        int choice = 0;
        do {
            System.out.println(
                    "\n=================================" +
                            "\n=  Would You Like To Try Again  =" +
                            "\n=       1.YES      2.NO         =" +
                            "\n================================="
            );
            choice = ExceptionHandling.getIntInput();
            switch (choice) {
                case 1:
                    proceedSetupAccount();
                    break;
                case 2:
                    initiate();
                    break;
                default:
                    System.out.println("Invalid Choice");
            }
        } while (choice != 2);
    }

    public void createAccount() {
        try {
            System.out.println("\nEnter Your Name:");
            String name = scan.next();

            System.out.println("Enter Your Email Id:");
            String emailId = scan.next();

            System.out.println("Enter Your Phone No:");
            String phoneNo = scan.next();

            if (!InputValidation.getInstance().isValidPhoneNo(phoneNo)) {
                throw new IllegalArgumentException("Invalid phone number format. Please enter a 10-digit number starting with 6-9.");
            }

            System.out.println("Enter Your Address:");
            String address = scan.next();

            System.out.println("Enter Your Password:");
            String password = scan.next();

            loginModel.checkAdminDetails(name, emailId, phoneNo, address, password);
        } catch (Exception e) {
            ExceptionHandling.printErrorMessage(e.getMessage());
            accountCreationFailed();
        }

    }

    public void accountCreationFailed() {
        int choice = 0;
        do {
            System.out.println(
                    "\n=================================" +
                            "\n=  Would You Like To Try Again  =" +
                            "\n=       1.YES      2.NO         =" +
                            "\n================================="
            );
            choice = ExceptionHandling.getIntInput();
            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    initiate();
                    break;
                default:
                    System.out.println("Invalid Choice");
            }
        } while (choice != 2);
    }

    public void proceedLogin() {
        int id = 0;
        boolean validInput = false;
        System.out.println("\n\nEnter your User Id :\n---------------------");
        while (!validInput) {
            try {
                id = scan.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                ExceptionHandling.printErrorMessage("Input Must Be Integer");
                scan.next();
                proceedLogin();
            }
        }
        System.out.println("Enter your password :\n---------------------");
        String password = scan.next();
        loginModel.checkCredentials(id, password);
    }

    public void onSuccessComplete(int id) {
        if (loginModel.isLibraryEmpty()) {
            new LibrarySetupView().initiateLibrarySetup(id);
        } else {
            new AdminControlView(id).initialControl();
        }
    }
}
