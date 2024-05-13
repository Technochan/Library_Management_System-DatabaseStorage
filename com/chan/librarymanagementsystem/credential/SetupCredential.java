package com.zsgs.chandru.librarymanagement.credential;

public class SetupCredential {
    private String setupName = "zsgs";
    private String setupPassword = "admin";

    public boolean checkSetupName(String setupName) {
        return this.setupName.equalsIgnoreCase(setupName);
    }

    public boolean checkSetupPassword(String setupPassword) {
        return this.setupPassword.equalsIgnoreCase(setupPassword);
    }

    public void setSetupName(String name) {
        setupName = name;
    }

    public void setSetupPassword(String password) {
        setupPassword = password;
    }
}
