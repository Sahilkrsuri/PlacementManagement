package com.tint.edu.PlacementManagemebt.controller;

import com.tint.edu.PlacementManagemebt.dto.ApplicationReq;
import com.tint.edu.PlacementManagemebt.dto.StudentReq;
import com.tint.edu.PlacementManagemebt.entity.Announcement;
import com.tint.edu.PlacementManagemebt.entity.Application;
import com.tint.edu.PlacementManagemebt.entity.Job;
import com.tint.edu.PlacementManagemebt.entity.Student;
import com.tint.edu.PlacementManagemebt.service.AnnouncementService;
import com.tint.edu.PlacementManagemebt.service.ApplicationService;
import com.tint.edu.PlacementManagemebt.service.JobService;
import com.tint.edu.PlacementManagemebt.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    private final StudentService studentService;
    private final JobService jobService;
    private final AnnouncementService announcementService;
    private final ApplicationService applicationService;

    public StudentController(StudentService studentService, JobService jobService,AnnouncementService announcementService,ApplicationService applicationService) {
        this.studentService = studentService;
        this.jobService = jobService;
        this.announcementService=announcementService;
        this.applicationService= applicationService;
    }

    // ================= PROFILE =================

    @PostMapping("/profile")
    public ResponseEntity<Student> createProfile(@Valid @RequestBody StudentReq req) {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();


        String username = authentication.getName(); // logged-in user
        return ResponseEntity.ok(studentService.saveStudent(username, req));
    }
    // ================= JOB VIEW =================
    @GetMapping("/jobs")
    public ResponseEntity<List<Job>> getAllJobs() {
        return ResponseEntity.ok(jobService.showAllJob());
    }

    @GetMapping("/jobs/active")
    public ResponseEntity<List<Job>> getActiveJobs() {
        return ResponseEntity.ok(jobService.activeDrive());
    }
    @GetMapping("/jobs/oldest")
    public ResponseEntity<List<Job>> getAllJobOldest(){
        return ResponseEntity.ok(jobService.showOldest());
    }
    @GetMapping("/jobs/recent")
    public ResponseEntity<List<Job>> getAllJobRecent(){
        return ResponseEntity.ok(jobService.showRecent());
    }
    @GetMapping("/get-all-announcement")
    public ResponseEntity<List<Announcement>> getAllAnnouncement(){
        return ResponseEntity.ok(announcementService.showAllAnnouncement());
    }
    @PostMapping("/application")
    public ResponseEntity<Application> addApplication(@Valid @RequestBody ApplicationReq applicationReq){
        return ResponseEntity.ok(applicationService.saveApplication(applicationReq));
    }
}