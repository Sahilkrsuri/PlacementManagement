package com.tint.edu.PlacementManagemebt.dto;


import jakarta.validation.constraints.NotBlank;

public class CompanyReq {
    @NotBlank(message = "Please provide Company  name")
    private String name;
    private String location;

    public CompanyReq() {
    }

    public CompanyReq(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
