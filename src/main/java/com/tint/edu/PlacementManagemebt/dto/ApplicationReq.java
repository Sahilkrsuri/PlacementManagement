package com.tint.edu.PlacementManagemebt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ApplicationReq {
    @NotBlank(message = "Please provide Student  name")
    private String studentName;
    @NotBlank(message = "Please provide Company  name")
    private String companyName;
    @NotBlank(message = "Please provide roll number")
    private String rollNo;
    @Positive(message = "cgpa must be greater then 0")
    private Double cgpa;
    @NotNull(message = "Please provide yes or no")
    private String activeBackLog;

    public ApplicationReq() {
    }

    public ApplicationReq(String studentName, String companyName, String rollNo, Double cgpa, String activeBackLog) {
        this.studentName = studentName;
        this.companyName = companyName;
        this.rollNo = rollNo;
        this.cgpa = cgpa;
        this.activeBackLog = activeBackLog;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public Double getCgpa() {
        return cgpa;
    }

    public void setCgpa(Double cgpa) {
        this.cgpa = cgpa;
    }

    public String getActiveBackLog() {
        return activeBackLog;
    }

    public void setActiveBackLog(String activeBackLog) {
        this.activeBackLog = activeBackLog;
    }
}
