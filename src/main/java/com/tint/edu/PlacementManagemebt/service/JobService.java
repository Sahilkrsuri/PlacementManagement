package com.tint.edu.PlacementManagemebt.service;

import com.tint.edu.PlacementManagemebt.dto.JobReq;
import com.tint.edu.PlacementManagemebt.entity.Company;
import com.tint.edu.PlacementManagemebt.entity.Job;
import com.tint.edu.PlacementManagemebt.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class JobService {
    private final JobRepository jobRepository;
    private final CompanyService companyService;

    public JobService(JobRepository jobRepository, CompanyService companyService) {
        this.jobRepository = jobRepository;
        this.companyService = companyService;
    }

    public Job saveJob(JobReq req) {
        Company company = companyService.findByCompanyName(req.getCompanyName());
        Job job = new Job();
        job.setRole(req.getRole());
        job.setCompany(company);
        job.setLastDate(req.getLastDateToApply());
        job.setSalary(req.getSalary());
        job.setMinCgpa(req.getMinCgpa());
        return jobRepository.save(job);
    }

    public void deleteJobById(Long id){
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with id " + id));

        jobRepository.delete(job);
    }
    public List<Job> showAllJob() {
        return jobRepository.findAll();
    }

    public List<Job> activeDrive() {
        List<Job> jobs = jobRepository.findByLastDateGreaterThanEqual(LocalDate.now());

        if (jobs.isEmpty()) {
            throw new RuntimeException("No active drives available");
        }
        return jobs;
    }
    public List<Job> showRecent(){
        List<Job> jobs=jobRepository.findAllByOrderByLastDateDesc();
        if (jobs.isEmpty()) {
            throw new RuntimeException("No active drives available");
        }
        return jobs;
    }
    public List<Job> showOldest(){
        List<Job> jobs=jobRepository.findAllByOrderByLastDateAsc();
        if (jobs.isEmpty()) {
            throw new RuntimeException("No active drives available");
        }
        return jobs;
    }
}
