package com.zsgs.chandru.librarymanagement.manageuser;

import com.zsgs.chandru.librarymanagement.librarydatabase.LibraryDatabase;
import com.zsgs.chandru.librarymanagement.model.IdMaintain;
import com.zsgs.chandru.librarymanagement.model.User;
import com.zsgs.chandru.librarymanagement.validation.InputValidation;

import java.util.ArrayList;
import java.util.HashMap;

public class ManageUserModel {
    private ManageUserView manageUserView;

    public ManageUserModel(ManageUserView manageUserView) {
        this.manageUserView = manageUserView;
        LibraryDatabase.getInstance().loadUserDetailsFromTable();
        new IdMaintain().loadUserId();
    }

    // Validating the user details
    public void validateUserDetails(String name, String phoneNo, String emailId, String address) {
        if (!InputValidation.getInstance().isValidName(name)) {
            manageUserView.showAlert("Invalid Name - (Must be in alphabets)");
            return;
        } else if (!InputValidation.getInstance().isValidPhoneNo(phoneNo)) {
            manageUserView.showAlert("Invalid Phone No - (Must start with 6-9 and 10 digits)");
            return;
        } else if (LibraryDatabase.getInstance().checkExistingUserByPhoneNo(phoneNo)) {
            manageUserView.showAlert("User Already Found in the Database");
            return;
        } else if (!InputValidation.getInstance().isValidEmail(emailId)) {
            manageUserView.showAlert("Invalid Email Id");
            return;
        } else if (InputValidation.getInstance().isValidAddress(address)) {
            manageUserView.showAlert("Invalid Address");
            return;
        }
        proceedToAddUser(name, phoneNo, emailId, address);
    }

    //After the validation adding the users into the database
    public void proceedToAddUser(String name, String phoneNo, String emailId, String address) {
        User user = new User();
        user.setUserId();
        user.setUserName(name);
        user.setUserPhoneNo(phoneNo);
        user.setUserEmailId(emailId);
        user.setUserAddress(address);
        LibraryDatabase.getInstance().insertUserDetails(user.getUserId(), user);
        LibraryDatabase.getInstance().insertUserDetailsToTable(user.getUserId(), user);
        manageUserView.showText("User Account Creation Successfully \n\tUser Id : " + user.getUserId() + " User Name : " + user.getUserName());
    }

    // After getting the userId proceed to delete the user from the database
    public void proceedToDeleteUser(int userId) {
        if (LibraryDatabase.getInstance().checkUserById(userId)) {
            LibraryDatabase.getInstance().deleteUser(userId);
            manageUserView.showText("User Removed Successfully");
        } else {
            manageUserView.showText("No User Found");
        }
    }

    //After getting some characters from the username search the matching name in the database
    public void proceedToSearchUser(String username) {
        ArrayList<User> users = LibraryDatabase.getInstance().searchUserInDatabase(username);
        if (users.isEmpty()) {
            manageUserView.showText("No Users Found");
        } else {
            manageUserView.proceedToShowAllUsers(users);
        }
    }

    //Getting all the users from the database then showing the details
    public void proceedToShowAll() {
        HashMap<Integer, User> userIdUserDetails = LibraryDatabase.getInstance().getUserDetails();
        ArrayList<User> users = new ArrayList<>(userIdUserDetails.values());
        if (users.isEmpty()) {
            manageUserView.showText("No Users Found");
        } else {
            manageUserView.proceedToShowAllUsers(users);
        }
    }
}
