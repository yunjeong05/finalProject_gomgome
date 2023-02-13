package com.ezen.gomgome.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ezen.gomgome.entity.Notice;
import com.ezen.gomgome.entity.NoticeFiles;
import com.ezen.gomgome.entity.NoticeFileId;

public interface NoticeFileRepository extends JpaRepository<NoticeFiles, NoticeFileId> {
	//@Query: 원하는 쿼리를 작성할 수 있는 어노테이션
	//nativeQuery: true로 설정하면 원하는 대로 쿼리 작성, 메소드명도 JPA 규칙에서 벗어날 수 있다.
	//value: 쿼리 작성
	@Query(value="SELECT IFNULL(MAX(F.Notice_FILE_NO), 0) + 1 FROM T_GOMG_FILE F WHERE F.Notice_NO=:noticeNo", nativeQuery=true)
	//ServiceImpl에서 넘겨주는 파라미터의 변수명이 받아주는 변수의 이름과 다를 때 해당 파라미터이름을 명시
	//매퍼나 리포지토리에 여러개의 파라미터를 보낼 때 @Param 어노테이션 사용
	int getMaxFileNo(@Param("noticeNo") int noticeNo);
	
	//SELECT * FROM T_BOARD_FILE
	//WHERE BOARD_NO = :boardNo
	List<NoticeFiles> findByNotice(Notice notice);
}
