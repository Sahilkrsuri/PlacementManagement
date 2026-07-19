package com.tint.edu.PlacementManagemebt.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "emails")
public class StudentEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true)
    String email;

    public StudentEmail() {
    }

    public StudentEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
