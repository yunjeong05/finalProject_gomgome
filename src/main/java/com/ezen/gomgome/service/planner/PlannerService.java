package com.ezen.gomgome.service.planner;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ezen.gomgome.common.CamelHashMap;
import com.ezen.gomgome.entity.Note;
import com.ezen.gomgome.entity.Plan;
import com.ezen.gomgome.entity.Schedule;
import com.ezen.gomgome.entity.Statistic;

public interface PlannerService {
	List<Plan> getPlanByUser(String userId);
	
	void insertPlan(Plan plan);
	
	Plan getPlanByNo(int planNo);
	
	void updateGoal(Plan plan);
	
	List<Plan> getPlanAllByDate(LocalDate localDate);
	
	int getTotalTime(String userId);
	
//	int getTotalContactTime(String userId);
	
	void pause(Statistic statistic);
	
	int getTime(String userId);
	
	void insertNote(Note note);
	
	void updateNote(Note note);
	
	List<Plan> getPlanByUserAndCal(String userId);
	
	List<Plan> getPlanCateList();
	
	List<Plan> getPlanCate(int selectVal);

	List<Statistic> getDayRank(LocalDate localDate);

	List<CamelHashMap> getTotalRank();

	List<Statistic> getAchieveRank(LocalDate localDate);

	List<Plan> getLikeRank();

	List<Plan> getPlanViewCntMax();

	List<Plan> getPlanViewCntMaxFemale();

	List<Plan> getPlanViewCntMaxMale();

	Page<Plan> getPagePlanCate(int selectVal, Pageable pageable);

	List<Schedule> getAllDaySchedule(String userId);

	void updatePlanCnt(int planNo);

	int getPlanLike(int planNo, String userId);

	List<CamelHashMap> getPlanScheduleDetail(int planNo);

	String getAchievementRate(String userId);

	Plan getPlanDetail(int planNo);

	List<Schedule> getPlanScheduleDetailDay(int planNo, String scheduleStartDay);
}
