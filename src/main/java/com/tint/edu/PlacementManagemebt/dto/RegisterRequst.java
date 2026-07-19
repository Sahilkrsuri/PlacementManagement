package com.tint.edu.PlacementManagemebt.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequst {
    @NotBlank(message = "Username is required")
    @Size(min = 3,max=100,message = "Username must be in 3-100 character")
    @Email
    private String username;
    @NotBlank(message = "email is required")
    @Email(message = "Invailid email format")
    private  String email;
    @NotBlank(message = "password is required")
    @Size(min=5,max=50,message = "password must be in 5- 50 character")
    private String password;
    @Size(min = 4,max = 100,message = "full name max char 100")
    private String fullName;
    public RegisterRequst(){

    }
    public RegisterRequst(String username, String email, String password, String fullName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPasswprd(String passwprd) {
        this.password = passwprd;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


}
