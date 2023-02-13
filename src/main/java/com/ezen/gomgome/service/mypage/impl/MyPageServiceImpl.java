package com.ezen.gomgome.service.mypage.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ezen.gomgome.entity.LikePlan;
import com.ezen.gomgome.entity.Maincategory;
import com.ezen.gomgome.entity.Note;
import com.ezen.gomgome.entity.Plan;
import com.ezen.gomgome.entity.Schedule;
import com.ezen.gomgome.entity.Statistic;
import com.ezen.gomgome.repository.LikePlanRepository;
import com.ezen.gomgome.repository.MaincategoryRepository;
import com.ezen.gomgome.repository.NoteRepository;
import com.ezen.gomgome.repository.PlanRepository;
import com.ezen.gomgome.repository.ScheduleRepository;
import com.ezen.gomgome.repository.StatisticRepository;
import com.ezen.gomgome.service.mypage.MyPageService;

@Service
public class MyPageServiceImpl implements MyPageService {
	
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@Autowired
	private PlanRepository planRepository;
	
	@Autowired
	private StatisticRepository statisticRepository;
	
	@Autowired
	private LikePlanRepository likePlanRepository;
	
	@Autowired
	private NoteRepository noteRepository;
	
	@Autowired
	private MaincategoryRepository maincategoryRepository;
	
	@Override
	public List<Schedule> getScheduleByPlanNo(int planNo) {
		//return scheduleRepository.findByPlanNoContaining(planNo);
		
		return scheduleRepository.findByPlanNo(planNo);
	}
/*	
	@Override
	public void saveScheduleList(List<Map<String, Object>> updateScheduleList) {
		
		for (int i = 0; i < updateScheduleList.size(); i++) {
			String scheduleStatus = String.valueOf(updateScheduleList.get(i).get("scheduleStatus"));
			
			if (scheduleStatus.equals("U")) {
				Schedule uSchedule = Schedule.builder()
						.build();
				
				scheduleRepository.save(uSchedule);
			} else if (scheduleStatus.equals("I")) {
				Schedule iSchedule = Schedule.builder()
											.build();
				
				scheduleRepository.save(iSchedule);
			} else if (scheduleStatus.equals("D")) {
				Schedule dSchedule = Schedule.builder()
											.build();
				
				scheduleRepository.delete(dSchedule);
			}
		}
	}
*/
	
	@Override
	public List<Plan> getPlanByDateAndUser(LocalDate localDate, String userId) {
		return planRepository.findByUserAndLocalDateBetween(localDate, userId);
	}
	
	@Override 
	public List<Statistic> getStatisticByUser(String userId) {
		return statisticRepository.findAllByUser(userId);
	}
	
	@Override
	public void insertStatistic(Statistic statistic) {
		statisticRepository.save(statistic);
	}
	
	@Override
	public void updateStatistic(Statistic statistic) {
		statisticRepository.save(statistic);
	}
	
	@Override
	public List<Plan> getLikePlanByUser(String userId) {
		//return planRepository.findLikePlanByUser(userId);
		List<LikePlan> likeList = likePlanRepository.findAllByUser(userId);
		
		System.out.println("like: " + likeList);
		
		List<Plan> planList = new ArrayList<Plan>();
		
		for (LikePlan like : likeList) {
			int planNo = like.getPlanNo();
			Plan plan = planRepository.findById(planNo);
			planList.add(plan);
		}
		
		return planList;
	}
	
	@Override
	public void addCountLike(int planNo) {
		planRepository.addCountLike(planNo);
	}
	
	@Override
	public void insertLikePlan(LikePlan likePlan) {
		likePlanRepository.save(likePlan);
	}
	
	@Override
	public List<LikePlan> getLikeByUser(String userId) {
		return likePlanRepository.findAllByUser(userId);
	}
	
	@Override
	public void cancelLikePlan(LikePlan likePlan) {
		likePlanRepository.delete(likePlan);
	}
	
	@Override
	public List<Note> getNoteByUser(String userId) {
		return noteRepository.findAllByUser(userId);
	}
	
	@Override
	public void deleteNote(int noteNo) {
		noteRepository.deleteById(noteNo);
	}

	@Override
	public List<Schedule> getScheduleByPlanNoAndDate(int planNo, LocalDate localdate) {
		return scheduleRepository.findByPlanNoAndLocaldate(planNo, localdate);
	}
	
	@Override
	public List<Plan> getPlanByGoal(String userId) {
		return planRepository.findGoalByUserId(userId);
	}
	
	@Override
	public List<Plan> getPlanByPast(LocalDate localDate, String userId) {
		return planRepository.findPlanByPast(localDate, userId);
	}
	
	@Override
	public void saveScheduleLTotalTime(String userId) {
		statisticRepository.saveScheduleLTotalTime(userId);
	}
	
	@Override
	public List<Schedule> getOtherSchedule(int planNo, String userId, LocalDate localdate) {
		
		List<Schedule> returnList = new ArrayList<Schedule>();
		
		List<Integer> planNoList = planRepository.findPlanNoByUser(userId);
		
		for (int i : planNoList) {
			if (i == planNo) continue;
			returnList.addAll(scheduleRepository.findByPlanNo(i));
		
		}
		
		return returnList;
	}
	
	@Override
	public void deletePlan(int planNo) {
		scheduleRepository.deleteByPlanNo(planNo);
		planRepository.deleteById(planNo);
	}


	@Override
	public void subCountLike(int planNo) {
		planRepository.subCountLike(planNo);
	}

	@Override
	public Page<Plan> getPagelikePlanList(String userId, Pageable pageable) {
		return planRepository.getPagelikePlanList(userId, pageable);
	}
	
	@Override
	public void saveScheduleList(List<Map<String, Object>> updateList, String focusDate, String userId) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		int planNo = Integer.parseInt(String.valueOf(updateList.get(0).get("planNo")));
		
		scheduleRepository.deleteAllByDateAndUser(LocalDate.parse(focusDate, formatter), planNo);
		
		formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		for (int i = 0; i < updateList.size(); i++) {
		
			//System.out.println("&&&&&&&&&&&&&&&&&&&");
			//System.out.println(updateList.get(i).get("scheduleNo"));
			Map<String, Object> map = updateList.get(i);
			
			//System.out.println(map);
			
			Schedule schedule = Schedule.builder()
										.planNo(Integer.parseInt(String.valueOf(map.get("planNo"))))
										.scheduleType(String.valueOf(map.get("scheduleType")))
										.scheduleContent(String.valueOf(map.get("scheduleContent")))
										.scheduleSource(String.valueOf(map.get("scheduleSource")))
										.scheduleStartTime(LocalDateTime.parse(String.valueOf(map.get("scheduleStartTime")), formatter))
										.scheduleEndTime(LocalDateTime.parse(String.valueOf(map.get("scheduleEndTime")), formatter))
										.build();
			
			if (Integer.parseInt(String.valueOf(map.get("scheduleNo"))) > 0) {
				schedule.setScheduleNo(Integer.parseInt(String.valueOf(map.get("scheduleNo"))));
			}
			
			//System.out.println(schedule);
			
			scheduleRepository.save(schedule);
			
		}

	}
	
	@Override
	public List<Maincategory> getCategory() {
		return maincategoryRepository.findAll();
	}
}



























