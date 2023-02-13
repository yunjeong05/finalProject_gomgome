package com.ezen.gomgome.repository;


import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ezen.gomgome.common.CamelHashMap;
import com.ezen.gomgome.entity.Statistic;

@Transactional
public interface StatisticRepository extends JpaRepository<Statistic, Integer> {
	
	@Query(value="SELECT * FROM T_GOMG_STATISTIC WHERE USER_ID = :userId", nativeQuery=true)
	List<Statistic> findAllByUser(@Param("userId")String userId);
	
	@Modifying
	@Query(value="INSERT INTO T_GOMG_STATISTIC (STATISTIC_DATE, USER_ID, STATISTIC_USER_STUDYHOUR_DATE, STATISTIC_USER_STUDYHOUR_TOTAL) \r\n"
			+ "VALUES (DATE_FORMAT(now(), '%Y-%m-%d'), :userId, 0, (SELECT IFNULL(SUM(D.STATISTIC_USER_STUDYHOUR_TOTAL), 0) AS STATISTIC_USER_STUDYHOUR_TOTAL\r\n"
			+ "	FROM (\r\n"
			+ "			SELECT	CASE\r\n"
			+ "						WHEN A.SCHEDULE_END_TIME IS NULL\r\n"
			+ "						THEN 100\r\n"
			+ "						WHEN A.SCHEDULE_START_TIME IS NULL\r\n"
			+ "						THEN 100\r\n"
			+ "					ELSE TIMESTAMPDIFF(SECOND, A.SCHEDULE_START_TIME, A.SCHEDULE_END_TIME)\r\n"
			+ "					END AS STATISTIC_USER_STUDYHOUR_TOTAL\r\n"
			+ "				FROM (\r\n"
			+ "						  SELECT B.SCHEDULE_END_TIME\r\n"
			+ "							   , B.SCHEDULE_START_TIME\r\n"
			+ "							   , C.USER_ID\r\n"
			+ "							FROM T_GOMG_SCHEDULE B\r\n"
			+ "							   , T_GOMG_PLAN C\r\n"
			+ "							WHERE B.PLAN_NO = C.PLAN_NO\r\n"
			+ "							GROUP BY B.PLAN_NO, B.SCHEDULE_NO\r\n"
			+ "					 ) A\r\n"
			+ "				WHERE USER_ID =:userId\r\n"
			+ "				  AND A.SCHEDULE_END_TIME LIKE CONCAT(DATE_FORMAT(now(), '%Y-%m-%d'), '%')\r\n"
			+ "				  AND A.SCHEDULE_START_TIME LIKE CONCAT(DATE_FORMAT(now(), '%Y-%m-%d'), '%')\r\n"
			+ "			) D))\r\n"
			+ "            ON DUPLICATE KEY UPDATE STATISTIC_USER_STUDYHOUR_TOTAL = (SELECT\r\n"
			+ "            IFNULL(SUM(D.STATISTIC_USER_STUDYHOUR_TOTAL),\r\n"
			+ "            0) AS STATISTIC_USER_STUDYHOUR_TOTAL   \r\n"
			+ "        FROM\r\n"
			+ "            (     SELECT\r\n"
			+ "                CASE        \r\n"
			+ "                    WHEN A.SCHEDULE_END_TIME IS NULL        THEN 100        \r\n"
			+ "                    WHEN A.SCHEDULE_START_TIME IS NULL        THEN 100       \r\n"
			+ "                    ELSE TIMESTAMPDIFF(SECOND,\r\n"
			+ "                    A.SCHEDULE_START_TIME,\r\n"
			+ "                    A.SCHEDULE_END_TIME)       \r\n"
			+ "                END AS STATISTIC_USER_STUDYHOUR_TOTAL      \r\n"
			+ "            FROM\r\n"
			+ "                (          SELECT\r\n"
			+ "                    B.SCHEDULE_END_TIME            ,\r\n"
			+ "                    B.SCHEDULE_START_TIME            ,\r\n"
			+ "                    C.USER_ID         \r\n"
			+ "                FROM\r\n"
			+ "                    T_GOMG_SCHEDULE B            ,\r\n"
			+ "                    T_GOMG_PLAN C         \r\n"
			+ "                WHERE\r\n"
			+ "                    B.PLAN_NO = C.PLAN_NO         \r\n"
			+ "                GROUP BY\r\n"
			+ "                    B.PLAN_NO,\r\n"
			+ "                    B.SCHEDULE_NO        ) A      \r\n"
			+ "            WHERE\r\n"
			+ "                USER_ID =:userId        \r\n"
			+ "                AND A.SCHEDULE_END_TIME LIKE CONCAT(DATE_FORMAT(now(), '%Y-%m-%d'), '%')        \r\n"
			+ "                AND A.SCHEDULE_START_TIME LIKE CONCAT(DATE_FORMAT(now(), '%Y-%m-%d'), '%')     ) D)", 
			nativeQuery=true)
	void saveScheduleLTotalTime(@Param("userId")String userId);

	List<Statistic> findByStatisticDateOrderByStatisticUserStudyhourDateDesc(LocalDate localDate);

	@Query(value="SELECT A.USER_ID, SUM(A.STATISTIC_USER_STUDYHOUR_DATE) AS STATISTIC_USER_STUDYHOUR_DATE "
			+ "FROM T_GOMG_STATISTIC A "
			+ "GROUP BY A.USER_ID "
			+ "ORDER BY STATISTIC_USER_STUDYHOUR_DATE DESC", nativeQuery=true)
	List<CamelHashMap> getTotalRank(); 
	
	List<Statistic> findByStatisticDateOrderByStatisticAchievementRateDesc(LocalDate localDate);
	
	@Query(value="SELECT STATISTIC_ACHIEVEMENT_RATE FROM T_GOMG_STATISTIC A\r\n"
			+ "	WHERE A.USER_ID= :userId AND A.STATISTIC_DATE = DATE_FORMAT(now(), '%Y-%m-%d');", nativeQuery=true)
	String getAchievementRate(@Param("userId") String userId);


}
