package com.tint.edu.PlacementManagemebt.controller;

import com.tint.edu.PlacementManagemebt.dto.CompanyReq;
import com.tint.edu.PlacementManagemebt.entity.Company;
import com.tint.edu.PlacementManagemebt.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }
    @GetMapping
    public ResponseEntity<List<Company>> getAllCompany(){
        return ResponseEntity.ok(companyService.getAllCompany());
    }
    @GetMapping("/search")
    public  ResponseEntity<Company> findByCompanyName(@RequestParam String companyName){
        return ResponseEntity.ok(companyService.findByCompanyName(companyName));
    }
    @GetMapping("/{id}")
    public  ResponseEntity<Company> findByCompanyId(@PathVariable Long id){
        return ResponseEntity.ok(companyService.findByCompanyId(id));
    }
}
