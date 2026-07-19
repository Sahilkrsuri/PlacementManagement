package com.tint.edu.PlacementManagemebt.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="announcements")
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String info;
    private LocalDateTime publishTime;
    @PrePersist
    protected  void addTime(){
        this.publishTime=LocalDateTime.now();

    }

    public Announcement() {
    }

    public Announcement(String info) {
        this.info = info;
    }

    public Announcement(String info, LocalDateTime publishTime) {
        this.info = info;
        this.publishTime = publishTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(LocalDateTime publishTime) {
        this.publishTime = publishTime;
    }
}
