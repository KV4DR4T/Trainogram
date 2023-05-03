package com.example.trainogram.repository;

import com.example.trainogram.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findAllByReportedUser(Long reportedUserId);
}
