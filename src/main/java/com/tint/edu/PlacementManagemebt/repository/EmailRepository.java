package com.tint.edu.PlacementManagemebt.repository;

import com.tint.edu.PlacementManagemebt.entity.StudentEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<StudentEmail,Long> {
    boolean existsByEmailIgnoreCase(String email);
}
