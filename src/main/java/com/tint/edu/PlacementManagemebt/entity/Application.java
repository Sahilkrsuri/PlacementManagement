package com.tint.edu.PlacementManagemebt.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "applications")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String studentName;
    private String companyName;
    private String rollNo;
    private Double cgpa;
    private String activeBackLog;

    public Application() {
    }

    public Application(String studentName, String companyName, String rollNo, Double cgpa, String activeBackLog) {
        this.studentName = studentName;
        this.companyName = companyName;
        this.rollNo = rollNo;
        this.cgpa = cgpa;
        this.activeBackLog = activeBackLog;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
