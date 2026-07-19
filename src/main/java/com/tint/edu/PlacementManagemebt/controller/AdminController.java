package com.tint.edu.PlacementManagemebt.controller;

import com.tint.edu.PlacementManagemebt.dto.AnnouncementReq;
import com.tint.edu.PlacementManagemebt.dto.EmailReq;
import com.tint.edu.PlacementManagemebt.entity.*;
import com.tint.edu.PlacementManagemebt.repository.AnnouncementRepo;
import com.tint.edu.PlacementManagemebt.service.*;

import com.tint.edu.PlacementManagemebt.dto.CompanyReq;
import com.tint.edu.PlacementManagemebt.dto.JobReq;
import com.tint.edu.PlacementManagemebt.service.CompanyService;
import com.tint.edu.PlacementManagemebt.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final CompanyService companyService;
    private final JobService jobService;
    private final StudentService studentService;
    private final UserService userService;
    private final AnnouncementService announcementService;
    private final ApplicationService applicationService;
    private  final StudentMailService studentMailService;

    public AdminController(CompanyService companyService,
                           JobService jobService,
                           StudentService studentService,
                           UserService userService,
                           AnnouncementService announcementService,
                           ApplicationService applicationService,
                           StudentMailService studentMailService) {
        this.companyService = companyService;
        this.jobService = jobService;
        this.studentService = studentService;
        this.userService = userService;
        this.announcementService=announcementService;
        this.applicationService=applicationService;
        this.studentMailService=studentMailService;
    }

    // ================= COMPANY =================

    @PostMapping("/company")
    public ResponseEntity<Company> addCompany(@RequestBody CompanyReq req) {
        return ResponseEntity.ok(companyService.saveCompany(req));
    }

    @GetMapping("/company")
    public ResponseEntity<List<Company>> getAllCompanies() {
        return ResponseEntity.ok(companyService.getAllCompany());
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.findByCompanyId(id));
    }

    // ================= JOB =================

    @PostMapping("/job")
    public ResponseEntity<Job> createJob(@RequestBody JobReq req) {
        return ResponseEntity.ok(jobService.saveJob(req));
    }

    @DeleteMapping("/job/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id) {
        jobService.deleteJobById(id);
        return ResponseEntity.ok("Job deleted successfully");
    }

    @GetMapping("/job")
    public ResponseEntity<List<Job>> getAllJobs() {
        return ResponseEntity.ok(jobService.showAllJob());
    }

    @GetMapping("/job/active")
    public ResponseEntity<List<Job>> getActiveJobs() {
        return ResponseEntity.ok(jobService.activeDrive());
    }

    // ================= STUDENT =================

    @GetMapping("/students/filter")
    public ResponseEntity<?> filterStudents(@RequestParam Double minCgpa) {
        return ResponseEntity.ok(studentService.filterStudent(minCgpa));
    }

    // ================= USERS =================

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }
    //============Announcement=====
    @PostMapping("/add-announcement")
    public ResponseEntity<Announcement> addAnnonuncement(@RequestBody AnnouncementReq announcementReq){
        return ResponseEntity.ok(announcementService.saveAnnouncement(announcementReq));
    }
    @GetMapping("/get-all-announcement")
    public ResponseEntity<List<Announcement>> getAllAnnouncement(){
        return ResponseEntity.ok(announcementService.showAllAnnouncement());
    }
    @GetMapping("/get-all-application")
    public ResponseEntity<List<Application>> showAllApplication(){
        return ResponseEntity.ok(applicationService.findApplications());
    }
    @GetMapping("/get-application")
    public ResponseEntity<List<Application>> showAllApplication(@RequestParam String companyName){
        return ResponseEntity.ok(applicationService.findByCompanyName(companyName));
    }
    @PostMapping("/add-email")
    public ResponseEntity<StudentEmail> addEmail(@Valid @RequestBody EmailReq emailReq){
        return ResponseEntity.ok(studentMailService.saveEmail(emailReq));
    }
}
