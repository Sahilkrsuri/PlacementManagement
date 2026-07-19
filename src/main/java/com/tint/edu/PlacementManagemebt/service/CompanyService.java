package com.tint.edu.PlacementManagemebt.service;

import com.tint.edu.PlacementManagemebt.dto.CompanyReq;
import com.tint.edu.PlacementManagemebt.entity.Company;
import com.tint.edu.PlacementManagemebt.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    private  final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }
    public Company saveCompany(CompanyReq req){
        Company company= new Company(req.getName().strip().toLowerCase(), req.getLocation());
        return companyRepository.save(company);
    }
    public List<Company> getAllCompany(){
        return  companyRepository.findAll();
    }
    public Company findByCompanyId(Long id){
        return companyRepository.findById(id).orElseThrow(()->new RuntimeException("Company with "+id+" not found"));
    }
    public Company findByCompanyName(String name){
        return companyRepository.findByNameIgnoreCase(name)
                .orElseThrow(()->new RuntimeException("Company with "+name+" not found"));
    }



}
