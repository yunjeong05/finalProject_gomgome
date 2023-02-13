package com.ezen.gomgome.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.gomgome.entity.Plan;

public interface PlanRepository extends JpaRepository<Plan, Integer> {
	List<Plan> findByUserIdContaining(String userId);
	
	//Plan findByPlanNoContaining(@Param("planNo") int planNo);
	Plan findById(int planNo);
	
	@Query(value="SELECT * FROM T_GOMG_PLAN WHERE PLAN_START_DAY <= :localDate AND PLAN_END_DAY >= :localDate", nativeQuery=true)
	List<Plan> findByLocalDateBetween(@Param("localDate")LocalDate localDate);
	
	@Query(value="SELECT * FROM T_GOMG_PLAN WHERE PLAN_START_DAY <= :localDate AND PLAN_END_DAY >= :localDate AND USER_ID = :userId", nativeQuery=true)
	List<Plan> findByUserAndLocalDateBetween(@Param("localDate") LocalDate localDate,
											 @Param("userId") String userId);
	

	@Query(value="SELECT * FROM T_GOMG_PLAN WHERE PLAN_COPY = 1 AND USER_ID = :userId", nativeQuery=true)
	List<Plan> findLikePlanByUser (@Param("userId") String userId);
	

	@Transactional
	@Modifying //데이터의 변경이 일어나는 @Query을 사용할 때는 @Modifying을 붙여준다.
	@Query(value="UPDATE T_GOMG_PLAN SET PLAN_LIKE_CNT = PLAN_LIKE_CNT + 1 WHERE PLAN_NO = :planNo", nativeQuery=true)
	void addCountLike(@Param("planNo") int planNo);

	@Query(value="SELECT CASE\r\n"
			+ "		WHEN COUNT(*) = 0\r\n"
			+ "        THEN 0\r\n"
			+ "        ELSE B.STATISTIC_USER_STUDYHOUR_TOTAL\r\n"
			+ "	   END AS STATISTIC_TOTAL_HOUR\r\n"
			+ "	FROM (\r\n"
			+ "			SELECT A.STATISTIC_USER_STUDYHOUR_TOTAL AS STATISTIC_USER_STUDYHOUR_TOTAL\r\n"
			+ "				FROM T_GOMG_STATISTIC A\r\n"
			+ "				WHERE A.USER_ID = :userId\r\n"
			+ "				  AND A.STATISTIC_DATE = DATE_FORMAT(now(), '%Y-%m-%d')\r\n"
			+ "		 ) B",
			nativeQuery=true)
	int getTotalTime(@Param("userId") String userId);
	
	@Query(value="SELECT   \r\n"
	         + "      CASE\r\n"
	         + "         WHEN COUNT(A.STATISTIC_USER_STUDYHOUR_DATE) = 0\r\n"
	         + "            THEN 0\r\n"
	         + "            ELSE A.STATISTIC_USER_STUDYHOUR_DATE\r\n"
	         + "      END AS STATISTIC_USER_STUDYHOUR_DATE\r\n"
	         + "    FROM\r\n"
	         + "        T_GOMG_STATISTIC A   \r\n"
	         + "    WHERE\r\n"
	         + "        A.USER_ID= :userId \r\n"
	         + "        AND A.STATISTIC_DATE = DATE_FORMAT(now(), '%Y-%m-%d')",
	         nativeQuery=true)
	int getTime(@Param("userId") String userId);
	
	@Query(value="SELECT * FROM T_GOMG_PLAN WHERE USER_ID = :userId AND PLAN_STATUS = 1", nativeQuery=true)
	List<Plan> findGoalByUserId(@Param("userId") String userId);
	
	@Query(value="SELECT * FROM T_GOMG_PLAN WHERE PLAN_DDAY < :localDate AND USER_ID = :userId", nativeQuery=true)
	List<Plan> findPlanByPast(@Param("localDate") LocalDate localDate,
											 @Param("userId") String userId);

	@Query(value="SELECT * FROM T_GOMG_PLAN A WHERE A.USER_ID = :userId", nativeQuery=true)
	List<Plan> getPlanByUserAndCal(@Param("userId") String userId);
	

	@Query(value="SELECT PLAN_NO FROM T_GOMG_PLAN A WHERE A.USER_ID = :userId", nativeQuery=true)
	List<Integer> findPlanNoByUser(@Param("userId") String userId);

	@Query(value="SELECT * FROM T_GOMG_PLAN A WHERE PLAN_MAINCATEGORY_NO= :selectVal AND PLAN_SHARE = 'Y'", nativeQuery=true)
	List<Plan> getPlanCate(@Param("selectVal") int selectVal);
	
	List<Plan> findAllByOrderByPlanLikeCntDesc();
	
	@Query(value="SELECT * FROM T_GOMG_PLAN WHERE PLAN_SHARE='Y' ORDER BY PLAN_VIEW_CNT DESC LIMIT 10", nativeQuery=true)
	List<Plan> getPlanViewCntMax();

	@Query(value="SELECT * FROM T_GOMG_PLAN A\r\n"
			+ "	INNER JOIN T_GOMG_USER B\r\n"
			+ "    ON A.USER_ID = B.USER_ID\r\n"
			+ "    WHERE B.USER_GENDER = 'FEMALE' AND A.PLAN_SHARE = 'Y'\r\n"
			+ "    ORDER BY PLAN_LIKE_CNT DESC LIMIT 10;", nativeQuery=true)
	List<Plan> getPlanViewCntMaxFemale();
	
	@Query(value="SELECT * FROM T_GOMG_PLAN A\r\n"
			+ "	INNER JOIN T_GOMG_USER B\r\n"
			+ "    ON A.USER_ID = B.USER_ID\r\n"
			+ "    WHERE B.USER_GENDER = 'MALE' AND A.PLAN_SHARE = 'Y'\r\n"
			+ "    ORDER BY PLAN_LIKE_CNT DESC LIMIT 10;", nativeQuery=true)
	List<Plan> getPlanViewCntMaxMale();
	
	@Transactional
	@Modifying //데이터의 변경이 일어나는 @Query을 사용할 때는 @Modifying을 붙여준다.
	@Query(value="UPDATE T_GOMG_PLAN SET PLAN_VIEW_CNT = PLAN_VIEW_CNT + 1 WHERE PLAN_NO = :planNo", nativeQuery=true)
	void updatePlanCnt(@Param("planNo") int planNo);
	
	@Transactional
	@Modifying //데이터의 변경이 일어나는 @Query을 사용할 때는 @Modifying을 붙여준다.
	@Query(value="UPDATE T_GOMG_PLAN SET PLAN_LIKE_CNT = PLAN_LIKE_CNT - 1 WHERE PLAN_NO = :planNo", nativeQuery=true)
	void subCountLike(@Param("planNo") int planNo);
	
	@Query(value="SELECT * FROM T_GOMG_PLAN A WHERE PLAN_MAINCATEGORY_NO= :selectVal AND PLAN_SHARE = 'Y'",
			countQuery="SELECT COUNT(*) FROM (SELECT * FROM T_GOMG_PLAN A WHERE PLAN_MAINCATEGORY_NO= :selectVal AND PLAN_SHARE = 'Y') B", nativeQuery=true)
	Page<Plan> getPagePlanCate(@Param("selectVal") int selectVal, Pageable pageable);
	
	@Query(value="SELECT * FROM T_GOMG_PLAN A WHERE PLAN_SHARE = 'Y' AND A.PLAN_NO IN ("
			+ "SELECT B.PLAN_NO FROM T_GOMG_LIKE_PLAN B WHERE B.USER_ID = :userId)",
			countQuery="SELECT COUNT(*) FROM (SELECT * FROM T_GOMG_PLAN A WHERE PLAN_SHARE = 'Y' AND A.PLAN_NO IN ("
					+ "SELECT B.PLAN_NO FROM T_GOMG_LIKE_PLAN B WHERE B.USER_ID = :userId)) B", nativeQuery=true)
	Page<Plan> getPagelikePlanList(@Param("userId") String userId, Pageable pageable);
}
