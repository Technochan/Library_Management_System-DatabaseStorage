package com.zsgs.chandru.librarymanagement.login;

import com.zsgs.chandru.librarymanagement.credential.LibrarianCredentials;
import com.zsgs.chandru.librarymanagement.credential.SetupCredential;
import com.zsgs.chandru.librarymanagement.librarydatabase.LibraryDatabase;
import com.zsgs.chandru.librarymanagement.model.IdMaintain;
import com.zsgs.chandru.librarymanagement.model.Librarian;
import com.zsgs.chandru.librarymanagement.validation.InputValidation;

public class LoginModel {

    private LoginView loginView;
    private SetupCredential setupCredential;

    LoginModel(LoginView loginView) {
        this.loginView = loginView;
        setupCredential = new SetupCredential();
        LibraryDatabase.getInstance().loadLibrarianCredentialsFromTable();
        LibraryDatabase.getInstance().loadLibraryDetailsFromTable();
        new IdMaintain().loadLibraryId();
    }

    public void checkCredentials(int id, String password) {
        if (LibraryDatabase.getInstance().checkLibrarianCredentials(id, password)) {
            ;
            LibraryDatabase.getInstance().loadLibrarianIdLibraryIdFromTable();
            loginView.onSuccessComplete(id);
        } else {
            loginView.showAlert("Invalid User Id or Password");
            loginView.proceedLogin();
        }
    }

    public void checkInitialCredential(String name, String password) {
        if (setupCredential.checkSetupName(name) && setupCredential.checkSetupPassword(password)) {
            loginView.showText("Successfully Logged In\nWelcome New Librarian :)");
            loginView.createAccount();
        } else {
            loginView.showAlert("Invalid UserName or Password");
            loginView.loginFailed();
        }
    }

    public void checkAdminDetails(String name, String emailId, String phoneNo, String address, String password) {
        if (!InputValidation.getInstance().isValidName(name)) {
            loginView.showAlert("Invalid Name \n Name must contains 3 - 30 characters \n Only Alphabets are Allowed)");
            return;
        } else if (!InputValidation.getInstance().isValidEmail(emailId)) {
            loginView.showAlert("Invalid Email Id");
            return;
        } else if (InputValidation.getInstance().isValidAddress(address)) {
            loginView.showAlert("Invalid Address");
            return;
        } else if (InputValidation.getInstance().isValidPassword(password)) {
            loginView.showAlert("Invalid Password \n Length must be 3 - 30");
            return;
        }
        validCredentials(name, emailId, phoneNo, address, password);
    }

    public void validCredentials(String name, String emailId, String phoneNo, String address, String password) {
        Librarian librarian = new Librarian();
        librarian.setLibrarianName(name);
        librarian.setLibrarianEmailId(emailId);
        librarian.setLibrarianPhoneNo(phoneNo);
        librarian.setLibrarianAddress(address);
        librarian.setAdminUserId();
        LibraryDatabase.getInstance().insertLibrarianDetails(librarian.getLibrarianUserId(), librarian);
        loginView.showText("Account Setup Successfully Done \n\tYour Account ID : " + librarian.getLibrarianUserId());

        LibrarianCredentials librarianCredential = new LibrarianCredentials();
        librarianCredential.setLibrarianId(librarian.getLibrarianUserId());
        librarianCredential.setLibrarianIdPassword(password);
        LibraryDatabase.getInstance().insertLibrarianCredentials(librarian.getLibrarianUserId(), password);
        LibraryDatabase.getInstance().insertLibrarianDetailsToTable(librarian.getLibrarianUserId(), librarian);
        LibraryDatabase.getInstance().insertLibrarianCredentialsToTable(librarian.getLibrarianUserId(), password);
        loginView.proceedLogin();
    }

    public boolean isLibraryEmpty() {
        return LibraryDatabase.getInstance().isLibraryEmpty();
    }
}
