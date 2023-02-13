package com.ezen.gomgome.service.mypage;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ezen.gomgome.entity.LikePlan;
import com.ezen.gomgome.entity.Maincategory;
import com.ezen.gomgome.entity.Note;
import com.ezen.gomgome.entity.Plan;
import com.ezen.gomgome.entity.Schedule;
import com.ezen.gomgome.entity.Statistic;

public interface MyPageService {
	List<Schedule> getScheduleByPlanNo(int planNo);
	
	//void saveScheduleList(List<Map<String, Object>> updateScheduleList);
	
	List<Plan> getPlanByDateAndUser(LocalDate localDate, String userId);
	
	List<Statistic> getStatisticByUser(String userId);
	
	void insertStatistic(Statistic statistic);
	
	void updateStatistic(Statistic statistic);
	
	List<Plan> getLikePlanByUser(String userId);
	
	void addCountLike(int planNo);
	
	void insertLikePlan(LikePlan likePlan);
	
	List<LikePlan> getLikeByUser(String userId);
	
	void cancelLikePlan(LikePlan likePlan);
	
	List<Note> getNoteByUser(String userId);
	
	void deleteNote(int noteNo);
	

	List<Schedule> getScheduleByPlanNoAndDate(int planNo, LocalDate localdate);
	
	List<Plan> getPlanByGoal(String userId);
	
	List<Plan> getPlanByPast(LocalDate localDate, String userId);

	void saveScheduleLTotalTime(String userId);
	
	List<Schedule> getOtherSchedule(int planNo, String userId, LocalDate localdate);


	void deletePlan(int planNo);

	void subCountLike(int planNo);

	Page<Plan> getPagelikePlanList(String userId, Pageable pageable);
	
	void saveScheduleList(List<Map<String, Object>> updateList, String focusDate, String userId);
	
	List<Maincategory> getCategory();
}
