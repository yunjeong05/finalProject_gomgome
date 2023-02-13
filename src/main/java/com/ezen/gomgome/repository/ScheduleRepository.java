package com.ezen.gomgome.repository;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ezen.gomgome.common.CamelHashMap;
import com.ezen.gomgome.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
	@Query(value="SELECT * FROM T_GOMG_SCHEDULE WHERE PLAN_NO = :planNo", nativeQuery=true)
	List<Schedule> findByPlanNo(@Param("planNo") int planNo);
	

	@Query(value="SELECT * FROM T_GOMG_SCHEDULE WHERE PLAN_NO = :planNo AND DATE_FORMAT(SCHEDULE_START_TIME, '%Y-%m-%d') <= :localdate"
				+ " AND DATE_FORMAT(SCHEDULE_END_TIME, '%Y-%m-%d') >= :localdate", nativeQuery=true)
	List<Schedule> findByPlanNoAndLocaldate(@Param("planNo") int planNo,
											@Param("localdate") LocalDate localdate);
/*	
	@Query(value="SELECT * FROM T_GOMG_SCHEDULE WHERE USER_ID = :userId AND DATE_FORMAT(SCHEDULE_START_TIME, '%Y-%m-%d') <= :localdate"
				+ " AND DATE_FORMAT(SCHEDULE_END_TIME, '%Y-%m-%d') >= :localdate", nativeQuery=true)
	List<Schedule> findOther(@Param("userId") String userId,
											@Param("localdate") LocalDate localdate);
*/
	@Query(value="SELECT * FROM T_GOMG_SCHEDULE A\r\n"
			+ "					WHERE A.PLAN_NO in (SELECT B.PLAN_NO FROM T_GOMG_PLAN B WHERE B.USER_ID = :userId)\r\n"
			+ "			        AND A.SCHEDULE_START_TIME LIKE CONCAT(DATE_FORMAT(now(), '%Y-%m-%d'), '%') Order by A.SCHEDULE_START_TIME", nativeQuery=true)
	List<Schedule> getAllDaySchedule(@Param("userId") String userId);
	
	@Query(value="DELETE FROM T_GOMG_SCHEDULE WHERE PLAN_NO = :planNo", nativeQuery=true)
	@Modifying
    @Transactional
	void deleteByPlanNo(@Param("planNo") int planNo);
	
	@Query(value="DELETE FROM T_GOMG_SCHEDULE WHERE PLAN_NO = :planNo AND DATE_FORMAT(SCHEDULE_START_TIME, '%Y-%m-%d') <= :focusDate"
				+ " AND DATE_FORMAT(SCHEDULE_END_TIME, '%Y-%m-%d') >= :focusDate", nativeQuery=true)
	@Modifying
    @Transactional
	void deleteAllByDateAndUser(@Param("focusDate") LocalDate focusDate,
			@Param("planNo") int planNo);

	@Query(value="SELECT *"
			+ "		   , (\r\n"
			+ "				SELECT MIN(SCHEDULE_START_TIME) \r\n"
			+ "					FROM T_GOMG_SCHEDULE\r\n"
			+ "					WHERE PLAN_NO=:planNo\r\n"
			+ "			  ) AS START_DATE"
			+ " FROM T_GOMG_SCHEDULE \r\n"
			+ "	WHERE PLAN_NO=:planNo", nativeQuery=true)
	List<CamelHashMap> getPlanScheduleDetail(@Param("planNo") int planNo);

	@Query(value="SELECT * FROM T_GOMG_SCHEDULE \r\n"
			+ "	WHERE PLAN_NO=:planNo \r\n"
			+ "	AND SCHEDULE_START_TIME LIKE :scheduleStartDay%", nativeQuery=true)
	List<Schedule> getPlanScheduleDetailDay(@Param("planNo") int planNo, @Param("scheduleStartDay") String scheduleStartDay);

	
}
