package com.tint.edu.PlacementManagemebt.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EmailReq {
    @Email
    @NotBlank
    String email;

    public EmailReq() {
    }

    public EmailReq(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
