package com.ezen.gomgome.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.ezen.gomgome.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {
	
	@Query(value="SELECT * FROM t_gomg_notice", nativeQuery=true)
	List<Notice> getNoticeList();
	

}
