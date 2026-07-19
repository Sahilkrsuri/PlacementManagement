package com.tint.edu.PlacementManagemebt.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String branch;
    private Double cgpa;
    @Column(unique = true)
    private String resumeUrl;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="job_id")
    private  Job job;

    public Student() {
    }

    public Student(Long id, User user, String branch, Double cgpa, String resumeUrl, Job job) {
        this.id = id;
        this.user = user;
        this.branch = branch;
        this.cgpa = cgpa;
        this.resumeUrl = resumeUrl;
        this.job = job;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}