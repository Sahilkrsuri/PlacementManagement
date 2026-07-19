package com.tint.edu.PlacementManagemebt.repository;

import com.tint.edu.PlacementManagemebt.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepo extends JpaRepository<Application,Long> {
    List<Application> findByCompanyNameContainingIgnoreCase(String companyName);
}
