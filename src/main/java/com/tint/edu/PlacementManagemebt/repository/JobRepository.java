package com.tint.edu.PlacementManagemebt.repository;

import com.tint.edu.PlacementManagemebt.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job,Long> {
    List<Job> findByLastDateGreaterThanEqual(LocalDate data);
    List<Job> findAllByOrderByLastDateDesc();
    List<Job> findAllByOrderByLastDateAsc();
}
