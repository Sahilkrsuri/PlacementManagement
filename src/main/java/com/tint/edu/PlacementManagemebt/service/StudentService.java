package com.tint.edu.PlacementManagemebt.service;

import com.tint.edu.PlacementManagemebt.dto.StudentReq;
import com.tint.edu.PlacementManagemebt.entity.Student;
import com.tint.edu.PlacementManagemebt.entity.User;
import com.tint.edu.PlacementManagemebt.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final CompanyService companyService;
    private final StudentRepository studentRepository;
    private final UserService userService;

    public StudentService(CompanyService companyService, StudentRepository studentRepository, UserService userService) {
        this.companyService = companyService;
        this.studentRepository = studentRepository;
        this.userService = userService;
    }

    public Student saveStudent(String userName, StudentReq req) {
        User user = userService.findByUserName(userName);
        Student student = new Student();
        student.setBranch(req.getBranch());
        student.setCgpa(req.getCgpa());
        student.setResumeUrl(req.getResumeUrl());
        student.setUser(user);
        return studentRepository.save(student);
    }

    public List<Student> filterStudent(Double minCgpa) {
        return studentRepository.findByCgpaGreaterThanEqual(minCgpa);
    }


}
