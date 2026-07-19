package com.tint.edu.PlacementManagemebt.service;

import com.tint.edu.PlacementManagemebt.dto.EmailReq;
import com.tint.edu.PlacementManagemebt.entity.StudentEmail;
import com.tint.edu.PlacementManagemebt.repository.EmailRepository;
import com.tint.edu.PlacementManagemebt.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentMailService {
    private final EmailRepository emailRepository;

    public StudentMailService(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }
    public boolean emailExists(EmailReq email){
        return emailRepository.existsByEmailIgnoreCase(normalizeEmail(email.getEmail()));
    }
    public StudentEmail saveEmail(EmailReq emailReq){
        if(emailExists(emailReq)){
            throw  new RuntimeException("Email already uploaded in system");
        }
        return emailRepository.save(new StudentEmail(normalizeEmail(emailReq.getEmail())));
    }

    private String normalizeEmail(String email) {
        return email == null ? "" : email.strip().toLowerCase();
    }
}
