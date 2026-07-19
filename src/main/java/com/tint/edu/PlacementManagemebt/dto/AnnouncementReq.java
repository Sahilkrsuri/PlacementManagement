package com.tint.edu.PlacementManagemebt.dto;

import jakarta.persistence.Column;

import java.time.LocalDateTime;

public class AnnouncementReq {
    private String info;

    public AnnouncementReq() {
    }

    public AnnouncementReq(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
