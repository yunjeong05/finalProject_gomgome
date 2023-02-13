package com.ezen.gomgome.service.planner.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ezen.gomgome.common.CamelHashMap;
import com.ezen.gomgome.entity.Note;
import com.ezen.gomgome.entity.Plan;
import com.ezen.gomgome.entity.Schedule;
import com.ezen.gomgome.entity.Statistic;
import com.ezen.gomgome.repository.LikePlanRepository;
import com.ezen.gomgome.repository.NoteRepository;
import com.ezen.gomgome.repository.PlanRepository;
import com.ezen.gomgome.repository.ScheduleRepository;
import com.ezen.gomgome.repository.StatisticRepository;
import com.ezen.gomgome.repository.UserPlanRepository;
import com.ezen.gomgome.repository.UserRepository;
import com.ezen.gomgome.service.planner.PlannerService;

@Service
public class PlannerServiceImpl implements PlannerService {
	@Autowired
	private PlanRepository planRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserPlanRepository userPlanRepository;
	
	@Autowired
	private StatisticRepository statisticRepository;
	
	@Autowired
	private NoteRepository noteRepository;
	
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@Autowired
	private LikePlanRepository likePlanRepository;
	
	@Override
	public List<Plan> getPlanByUser(String userId) {
		return planRepository.findByUserIdContaining(userId);
	}
	
	@Override
	public void insertPlan(Plan plan) {
		planRepository.save(plan);
		planRepository.flush();
		
		String userId = plan.getUserId();
		
		userRepository.addNumberOfPlan(userId);
	}
	
	@Override
	public Plan getPlanByNo(int planNo) {
		//return planRepository.findByPlanNoContaining(planNo);
		return planRepository.findById(planNo);
	}
	
	@Override
	public void updateGoal(Plan plan) {
		planRepository.save(plan);
	}
	
	@Override
	public List<Plan> getPlanAllByDate(LocalDate localDate) {
		return planRepository.findByLocalDateBetween(localDate);
	}

	@Override
	public int getTotalTime(String userId) {
		return planRepository.getTotalTime(userId);
	}

//	@Override
//	public int getTotalContactTime(String userId) {
//		return planRepository.getTotalContactTime(userId);
//	}

	@Override
	public void pause(Statistic statistic) {
		statisticRepository.save(statistic);
	}

	@Override
	public int getTime(String userId) {
		return planRepository.getTime(userId);
	}

	@Override
	public void insertNote(Note  note) {
		noteRepository.save(note);
	}
	
	@Override
	public void updateNote(Note note) {
		noteRepository.save(note);
	}

	@Override
	public List<Plan> getPlanByUserAndCal(String userId) {
		return planRepository.getPlanByUserAndCal(userId);
	}
	
	@Override
	public List<Plan> getPlanCateList() {
		return planRepository.findAll();
	}
	
	@Override
	public List<Plan> getPlanCate(int selectVal) {
		return planRepository.getPlanCate(selectVal);
	}

	@Override
	public List<Statistic> getDayRank(LocalDate localDate) {
		return statisticRepository.findByStatisticDateOrderByStatisticUserStudyhourDateDesc(localDate);
	}

	@Override
	public List<CamelHashMap> getTotalRank() {
		return statisticRepository.getTotalRank();
	}

	@Override
	public List<Statistic> getAchieveRank(LocalDate localDate) {
		return statisticRepository.findByStatisticDateOrderByStatisticAchievementRateDesc(localDate);
	}

	@Override
	public List<Plan> getLikeRank() {
		return planRepository.findAllByOrderByPlanLikeCntDesc();
	}

	@Override
	public List<Plan> getPlanViewCntMax() {
		return planRepository.getPlanViewCntMax();
	}

	@Override
	public List<Plan> getPlanViewCntMaxFemale() {
		return planRepository.getPlanViewCntMaxFemale();
	}

	@Override
	public List<Plan> getPlanViewCntMaxMale() {
		return planRepository.getPlanViewCntMaxMale();
	}

	@Override
	public Page<Plan> getPagePlanCate(int selectVal, Pageable pageable) {
		return planRepository.getPagePlanCate(selectVal, pageable);
	}

	@Override
	public List<Schedule> getAllDaySchedule(String userId) {
		return scheduleRepository.getAllDaySchedule(userId);
	}

	@Override
	public void updatePlanCnt(int planNo) {
		planRepository.updatePlanCnt(planNo);
	}

	@Override
	public int getPlanLike(int planNo, String userId) {
		return likePlanRepository.getPlanLike(planNo, userId);
	}

	@Override
	public List<CamelHashMap> getPlanScheduleDetail(int planNo) {
		return scheduleRepository.getPlanScheduleDetail(planNo);
	}

	@Override
	public String getAchievementRate(String userId) {
		return statisticRepository.getAchievementRate(userId);
	}

	@Override
	public Plan getPlanDetail(int planNo) {
		return planRepository.findById(planNo);
	}

	@Override
	public List<Schedule> getPlanScheduleDetailDay(int planNo, String scheduleStartDay) {
		return scheduleRepository.getPlanScheduleDetailDay(planNo, scheduleStartDay);
	}
}
