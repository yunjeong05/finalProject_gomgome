package com.ezen.gomgome.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ezen.gomgome.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Integer> {
	
	@Query(value="SELECT IFNULL(MAX(A.REPORT_NO), 0) + 1 FROM T_GOMG_REPORT A", nativeQuery=true)
	int getNextReportNo();

}
