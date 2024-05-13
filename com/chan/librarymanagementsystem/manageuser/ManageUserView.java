package com.zsgs.chandru.librarymanagement.manageuser;

import com.zsgs.chandru.librarymanagement.admincontrols.AdminControlView;
import com.zsgs.chandru.librarymanagement.colortext.Color;
import com.zsgs.chandru.librarymanagement.exceptionmesage.ExceptionHandling;
import com.zsgs.chandru.librarymanagement.model.User;

import java.util.ArrayList;
import java.util.Scanner;

public class ManageUserView {
    private AdminControlView adminControlView;
    private ManageUserModel manageUserModel;
    private Scanner scan;

    public ManageUserView(AdminControlView adminControlView) {
        this.adminControlView = adminControlView;
        manageUserModel = new ManageUserModel(this);
        scan = new Scanner(System.in);

    }

    // Initial Menu For Controlling the users
    public void initiateManage() {
        int choice;
        do {
            System.out.println(
                    "\n\n---------------------------------" +
                            "\n| 1 | Add New User                |" +
                            "\n| 2 | Search User                 |" +
                            "\n| 3 | Delete User                 |" +
                            "\n| 4 | Show All Users              |" +
                            "\n| 5 | Go Back                     |" +
                            "\n----------------------------------"
            );
            choice = ExceptionHandling.getIntInput();
            switch (choice) {
                case 1:
                    addNewUser();
                    break;
                case 2:
                    searchUser();
                    break;
                case 3:
                    deleteUser();
                    break;
                case 4:
                    showAllUsers();
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Invalid Choice Try Again");
            }
        } while (choice != 5);
        adminControlView.initialControl();
    }

    //For just Displaying the message about the process
    public void showText(String text) {
        System.out.println(Color.ANSI_GREEN +
                "\n=================================\n" +
                text +
                "\n=================================" +
                Color.ANSI_RESET);
    }

    //For show the alert messages while initial account setup
    public void showAlert(String alertText) {
        System.out.println(Color.ANSI_RED +
                "\n=================================\n" +
                alertText +
                "\n=================================" +
                Color.ANSI_RESET);
        userAccountCreationFailed();
    }

    //To handle the userAccountCreationFailed because of some invalid inputs
    public void userAccountCreationFailed() {
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
                    addNewUser();
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Invalid Choice");
            }
        } while (choice != 2);
    }

    //Adding new User - Getting the details about the users
    public void addNewUser() {
        try {
            System.out.println("\n\nEnter the User Name:");
            String name = scan.next();
            System.out.println("Enter the User PhoneNo:");
            String phoneNo = scan.next();
            System.out.println("Enter the user EmailId:");
            String emailId = scan.next();
            System.out.println("Enter the user Address:");
            String address = scan.next();
            manageUserModel.validateUserDetails(name, phoneNo, emailId, address);
        } catch (Exception e) {
            ExceptionHandling.printErrorMessage(e.getMessage());
            scan.next();
            addNewUser();
        }
    }

    //Proceed to delete the user from the database
    public void deleteUser() {
        int userId = 0;
        System.out.println(
                "\n==================================" +
                        "\n=  Did You Remember The User Id  =" +
                        "\n=       1.YES      2.NO          =" +
                        "\n=================================="
        );

        int choice = ExceptionHandling.getIntInput(); // Exceptions are handled in separate class

        switch (choice) {
            case 1:
                System.out.println("\nEnter the User Id\n-----------------");
                userId = ExceptionHandling.getIntInput();
                break;
            case 2:
                System.out.println("\n1.Search User \t2.ShowAllUser\n------------ \t-------------");
                int select = ExceptionHandling.getIntInput();
                switch (select) {
                    case 1:
                        searchUser();
                        break;
                    case 2:
                        showAllUsers();
                        break;
                    default:
                        System.out.println("Invalid choice");
                        deleteUser();
                        return; // Exit the method if choice is invalid
                }
                System.out.println("\nChoose the User Id\n-----------------");
                userId = ExceptionHandling.getIntInput();
                break;
            default:
                System.out.println("Invalid Choice");
                deleteUser();
                return; // Exit the method if choice is invalid
        }

        manageUserModel.proceedToDeleteUser(userId);
    }

    //For Search the user from the database
    public void searchUser() {
        System.out.println("\nEnter the some letters in the User Name\n---------------------------------------");
        String username = scan.next();
        manageUserModel.proceedToSearchUser(username);
    }


    public void showAllUsers() {
        manageUserModel.proceedToShowAll();
    }

    public void proceedToShowAllUsers(ArrayList<User> users) {
        System.out.println(
                "\n---------------------------------------------------------------------" +
                        "\nUser ID  |  USer Name  |  User Phone  |  User Email  |  User Address" +
                        "\n---------------------------------------------------------------------"
        );
        for (User user : users) {
            System.out.println(user);
        }
        System.out.println("---------------------------------------------------------------------");
    }

}
