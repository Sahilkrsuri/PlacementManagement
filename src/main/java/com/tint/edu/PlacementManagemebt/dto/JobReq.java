package com.tint.edu.PlacementManagemebt.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class JobReq {
    @NotBlank(message = "Role is required")
    private String role;

    @NotNull(message = "Salary is required")
    @Positive(message = "Salary must be greater than 0")
    private Double salary;

    @NotNull(message = "Minimum CGPA is required")
    @DecimalMin(value = "0.0", message = "CGPA must be >= 0")
    @DecimalMax(value = "10.0", message = "CGPA must be <= 10")
    private Double minCgpa;

    @NotNull(message = "Last date is required")
    @FutureOrPresent(message = "Last date must be today or future")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate lastDateToApply;

    @NotNull(message = "Company ID is required")
    private String companyName;

    public JobReq() {

    }

    public JobReq(String role, Double salary, Double minCgpa, LocalDate lastDateToApply, String companyName) {
        this.role = role;
        this.salary = salary;
        this.minCgpa = minCgpa;
        this.lastDateToApply = lastDateToApply;
        this.companyName = companyName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Double getMinCgpa() {
        return minCgpa;
    }

    public void setMinCgpa(Double minCgpa) {
        this.minCgpa = minCgpa;
    }

    public LocalDate getLastDateToApply() {
        return lastDateToApply;
    }

    public void setLastDateToApply(LocalDate lastDateToApply) {
        this.lastDateToApply = lastDateToApply;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
