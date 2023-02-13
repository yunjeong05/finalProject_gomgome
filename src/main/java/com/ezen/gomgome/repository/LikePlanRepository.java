package com.ezen.gomgome.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ezen.gomgome.entity.LikePlan;

public interface LikePlanRepository extends JpaRepository<LikePlan, Integer> {
	
	@Query(value="SELECT * FROM T_GOMG_LIKE_PLAN WHERE USER_ID = :userId", nativeQuery=true)
	List<LikePlan> findAllByUser(@Param("userId") String userId);
    
	@Query(value="SELECT COUNT(*) FROM T_GOMG_LIKE_PLAN\r\n"
			+ "			WHERE PLAN_NO = :planNo and USER_ID = :userId", nativeQuery=true)
	int getPlanLike(@Param("planNo") int planNo, @Param("userId") String userId);
}
