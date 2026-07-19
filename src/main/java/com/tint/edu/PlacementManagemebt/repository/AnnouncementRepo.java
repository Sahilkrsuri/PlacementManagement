package com.tint.edu.PlacementManagemebt.repository;

import com.tint.edu.PlacementManagemebt.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepo extends JpaRepository<Announcement,Long> {
}
