package com.tint.edu.PlacementManagemebt.service;

import com.tint.edu.PlacementManagemebt.dto.ApplicationReq;
import com.tint.edu.PlacementManagemebt.entity.Application;
import com.tint.edu.PlacementManagemebt.repository.ApplicationRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {
    private final ApplicationRepo applicationRepo;
    public ApplicationService(ApplicationRepo applicationRepo) {
        this.applicationRepo = applicationRepo;
    }
    public Application saveApplication(ApplicationReq applicationReq){
        Application newApplication=new Application(
                applicationReq.getStudentName(),
                applicationReq.getCompanyName(),
                applicationReq.getRollNo(),
                applicationReq.getCgpa(),
                applicationReq.getActiveBackLog()
        );
        return applicationRepo.save(newApplication);
    }
    public List<Application> findByCompanyName(String companyName){
        return applicationRepo.findByCompanyNameContainingIgnoreCase(companyName);
    }
    public List<Application> findApplications(){
        return applicationRepo.findAll();
    }
}
