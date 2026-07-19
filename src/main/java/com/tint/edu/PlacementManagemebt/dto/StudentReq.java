package com.tint.edu.PlacementManagemebt.dto;

import com.tint.edu.PlacementManagemebt.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class StudentReq {
    @NotBlank(message = "Branch can not be null")
    private String branch;
    @NotNull(message = "Minimum CGPA is required")
    @DecimalMin(value = "0.0", message = "CGPA must be >= 0")
    @DecimalMax(value = "10.0", message = "CGPA must be <= 10")
    private Double cgpa;

    private String resumeUrl;

    public StudentReq() {
    }

    public StudentReq(String branch, Double cgpa, String resumeUrl) {
        this.branch = branch;
        this.cgpa = cgpa;
        this.resumeUrl = resumeUrl;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Double getCgpa() {
        return cgpa;
    }

    public void setCgpa(Double cgpa) {
        this.cgpa = cgpa;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }
}
