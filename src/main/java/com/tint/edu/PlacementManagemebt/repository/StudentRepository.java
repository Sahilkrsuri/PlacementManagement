package com.tint.edu.PlacementManagemebt.repository;

import com.tint.edu.PlacementManagemebt.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository  extends JpaRepository<Student,Long> {
    List<Student> findByCgpaGreaterThanEqual(Double minCgpa);
}
