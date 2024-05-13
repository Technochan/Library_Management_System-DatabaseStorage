package com.zsgs.chandru.librarymanagement.LibrarySetup;

import com.zsgs.chandru.librarymanagement.admincontrols.AdminControlView;
import com.zsgs.chandru.librarymanagement.colortext.Color;
import com.zsgs.chandru.librarymanagement.exceptionmesage.ExceptionHandling;
import com.zsgs.chandru.librarymanagement.model.Library;
import com.zsgs.chandru.librarymanagement.validation.InputValidation;

import java.util.Scanner;

public class LibrarySetupView {
    private LibrarySetupModel librarySetupModel;
    private Scanner scan;
    private int librarianId;

    public LibrarySetupView() {
        librarySetupModel = new LibrarySetupModel(this);
        scan = new Scanner(System.in);
    }

    public void initiateLibrarySetup(int librarianId) {
        System.out.println(
                "\n****************************************" +
                        "\n**    Welcome To The Library Setup    **" +
                        "\n****************************************"
        );
        this.librarianId = librarianId;
        getLibraryDetails();
    }

    public void getLibraryDetails() {
        System.out.println("\n\nEnter Library Details");
        try {
            System.out.println("\nEnter Library Name:");
            String name = scan.next();

            System.out.println("\nEnter Library Email Id:");
            String emailId = scan.next();

            System.out.println("\nEnter Library Phone No:");
            String phoneNo = scan.next();
            if (!InputValidation.getInstance().isValidPhoneNo(phoneNo)) {
                throw new IllegalArgumentException("Invalid phone number format. Please enter a 10-digit number starting with 6-9.");
            }
            System.out.println("\nEnter Library Address:");
            String address = scan.next();
            librarySetupModel.libraryDetailsCheck(name, emailId, phoneNo, address, librarianId);
        } catch (Exception e) {
            ExceptionHandling.printErrorMessage(e.getMessage());
            getLibraryDetails();
        }
    }

    public void showAlert(String alertText) {
        System.out.println(Color.ANSI_RED +
                "\n=================================\n" +
                alertText +
                "\n=================================" +
                Color.ANSI_RESET);
        librarySetupFailed();
    }

    public void showText(String text) {
        System.out.println(Color.ANSI_GREEN +
                "\n=================================\n" +
                text +
                "\n=================================" +
                Color.ANSI_RESET);
    }

    public void librarySetupFailed() {
        int choice;
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
                    getLibraryDetails();
                    break;
                case 2:
                    System.exit(0);
                default:
                    System.out.println("Invalid Choice");
            }
        } while (true);
    }

    public void onSetupSuccess(Library library) {
        System.out.println(
                "\n--------------------------------------------------------------------------------------------------------------------------------" +
                        "\n  Library Id \t\t Library Admin Id \t\t Library Name \t\t Library Email Id \t\t Library PhoneNo \t\t Library Address\n" +
                        "\n--------------------------------------------------------------------------------------------------------------------------------" +
                        "\n \t\t" + library.getLibraryId() + "\t\t\t\t" + library.getLibraryInchargeId() + "\t\t\t\t\t\t" + library.getLibraryName() + "\t\t\t\t" + library.getLibraryEmailId() + "\t\t\t" + library.getLibraryPhoneNo() + "\t\t\t\t" + library.getLibraryAddress() +
                        "\n--------------------------------------------------------------------------------------------------------------------------------"

        );
        new AdminControlView(librarianId).initialControl();
    }
}
