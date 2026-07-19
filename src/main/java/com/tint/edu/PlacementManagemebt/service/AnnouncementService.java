package com.tint.edu.PlacementManagemebt.service;

import com.tint.edu.PlacementManagemebt.dto.AnnouncementReq;
import com.tint.edu.PlacementManagemebt.entity.Announcement;
import com.tint.edu.PlacementManagemebt.repository.AnnouncementRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

@Service
public class AnnouncementService {
    @Value("${spring.mail.username}")
    private  String email;
    private final AnnouncementRepo announcementRepo;
    private  final JavaMailSender javaMailSender;

    public AnnouncementService(AnnouncementRepo announcementRepo, JavaMailSender javaMailSender) {
        this.announcementRepo = announcementRepo;
        this.javaMailSender = javaMailSender;
    }


    public Announcement saveAnnouncement(AnnouncementReq req){
        String info=req.getInfo();
        Announcement announcement=new Announcement(info);
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(email);
        message.setTo("pratham.kumar.it.2022@tint.edu.in");
        message.setSubject("Placement Cell Announcement 📣");
        message.setText(info);
        javaMailSender.send(message);
        return announcementRepo.save(announcement);
    }
    public List<Announcement> showAllAnnouncement(){
        List<Announcement> allAnnouncement=announcementRepo.findAll();
        Queue<Announcement> pq=new PriorityQueue<>((a,b)->b.getPublishTime().compareTo(a.getPublishTime()));
        pq.addAll(allAnnouncement);
        List<Announcement> sortedList = new ArrayList<>();

        while (!pq.isEmpty()) {
            sortedList.add(pq.poll()); // always gives latest first
        }
        return sortedList;
    }
}
