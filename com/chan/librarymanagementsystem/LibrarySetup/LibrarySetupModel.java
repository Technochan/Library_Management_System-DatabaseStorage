package com.zsgs.chandru.librarymanagement.LibrarySetup;

import com.zsgs.chandru.librarymanagement.librarydatabase.LibraryDatabase;
import com.zsgs.chandru.librarymanagement.model.Library;
import com.zsgs.chandru.librarymanagement.validation.InputValidation;

public class LibrarySetupModel {

    private LibrarySetupView librarySetupView;

    LibrarySetupModel(LibrarySetupView librarySetupView) {
        this.librarySetupView = librarySetupView;
    }

    public void libraryDetailsCheck(String name, String emailId, String phoneNo, String address, int librarianId) {
        if (!InputValidation.getInstance().isValidName(name)) {
            librarySetupView.showAlert("Invalid Library Name - (User Name must contains 3 - 30 characters and Only Alphabets are Allowed)");
        } else if (!InputValidation.getInstance().isValidEmail(emailId)) {
            librarySetupView.showAlert("Invalid Email Id");
        } else if (InputValidation.getInstance().isValidAddress(address)) {
            librarySetupView.showAlert("Invalid Address");
        }
        setupLibrary(name, emailId, phoneNo, address, librarianId);
    }

    public void setupLibrary(String name, String emailId, String phoneNo, String address, int librarianId) {
        Library library = new Library();
        library.setLibraryName(name);
        library.setLibraryEmailId(emailId);
        library.setLibraryPhoneNo(phoneNo);
        library.setLibraryAddress(address);
        library.setLibraryId();
        library.setLibraryInchargeId(librarianId);
        LibraryDatabase.getInstance().insertLibraryDetails(library.getLibraryId(), library);
        librarySetupView.showText("Library Setup Successfully Done");

        LibraryDatabase.getInstance().insertAdminIdLibraryId(librarianId, library.getLibraryId());
        LibraryDatabase.getInstance().insertLibraryDetailsToTable(library.getLibraryId(), library);
        LibraryDatabase.getInstance().insertLibrarianIdLibraryIdToTable(librarianId, library.getLibraryId());
        librarySetupView.onSetupSuccess(library);
    }
}
