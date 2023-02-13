package com.ezen.gomgome.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.gomgome.common.CamelHashMap;
import com.ezen.gomgome.dto.NoteDTO;
import com.ezen.gomgome.dto.PlanDTO;
import com.ezen.gomgome.dto.ResponseDTO;
import com.ezen.gomgome.entity.CustomUserDetails;
import com.ezen.gomgome.entity.Note;
import com.ezen.gomgome.entity.Plan;
import com.ezen.gomgome.entity.Schedule;
import com.ezen.gomgome.entity.Statistic;
import com.ezen.gomgome.service.mypage.MyPageService;
import com.ezen.gomgome.service.planner.PlannerService;

@RestController
@RequestMapping("/planner")
public class PlannerController {
	@Autowired
	private PlannerService plannerService;
	
	@Autowired
	private MyPageService myPageService;
	
	// 메인 페이지 플래너, 스케쥴, 좋아요 플랜 가져오기
	@GetMapping("/planMain")
	public ModelAndView planMain(@AuthenticationPrincipal CustomUserDetails customUser, 
			@PageableDefault(page=0, size=9) Pageable pageable) {

		ModelAndView mv = new ModelAndView();
		

		if(customUser != null) {
			
			String userId = customUser.getUsername();
			// 달성율
			String achievementRate = plannerService.getAchievementRate(userId);
			System.out.println(achievementRate);
			// 오늘의 모든 스케줄
			List<Schedule> allDaySchedule = plannerService.getAllDaySchedule(userId);
			// 좋아요 플랜 목록
			List<Plan> likePlanList = myPageService.getLikePlanByUser(userId);
			
			Page<Plan> pagelikePlanList = myPageService.getPagelikePlanList(userId, pageable);
			
			System.out.println("=========================================" + pagelikePlanList.getTotalElements());
			mv.addObject("allDaySchedule", allDaySchedule);
			mv.addObject("likePlanList", likePlanList);
			mv.addObject("pagelikePlanList", pagelikePlanList);
			mv.addObject("achievementRate", achievementRate);
			
		} 
		
		List<Plan> planViewCntMax = plannerService.getPlanViewCntMax();
		List<Plan> planViewCntMaxFemale = plannerService.getPlanViewCntMaxFemale();
		List<Plan> planViewCntMaxMale = plannerService.getPlanViewCntMaxMale();
		
		mv.addObject("planViewCntMax", planViewCntMax);
		mv.addObject("planViewCntMaxFemale", planViewCntMaxFemale);
		mv.addObject("planViewCntMaxMale", planViewCntMaxMale);
		
		mv.setViewName("planner/main.html");
		return mv;
	}
	
	@GetMapping("/getScheduleList")
	public ModelAndView getScheduleList() {
		return null;
	}
	
	@GetMapping("/getPlanList")
	public ModelAndView getPlanList() {
		return null;
	}
	// 메인에서 동일한 카테고리 전체 메뉴 플랜 리스트
	@GetMapping("/getPlanCateList")
	public ModelAndView getPlanCateList(@AuthenticationPrincipal CustomUserDetails customUser) {
		ModelAndView mv = new ModelAndView();
		if(customUser != null) {
			String userId = customUser.getUsername();
			// 좋아요 플랜 목록
			List<Plan> likePlanList = myPageService.getLikePlanByUser(userId);
			mv.addObject("likePlanList", likePlanList);
		} 
		
		List<Plan> planViewCntMax = plannerService.getPlanViewCntMax();
		List<Plan> planViewCntMaxFemale = plannerService.getPlanViewCntMaxFemale();
		List<Plan> planViewCntMaxMale = plannerService.getPlanViewCntMaxMale();
		
		
		
		mv.addObject("planViewCntMax", planViewCntMax);
		mv.addObject("planViewCntMaxFemale", planViewCntMaxFemale);
		mv.addObject("planViewCntMaxMale", planViewCntMaxMale);
		mv.setViewName("planner/planCate.html");
		return mv;
	}
	
	// 카테고리 메뉴에서 select박스로 이동한 플랜 리스트 보기
	@GetMapping("/getPlanCate/{selectVal}")
	public ResponseEntity<?> getPlanCate(@PathVariable("selectVal") int selectVal, 
			@PageableDefault(page=0, size=9) Pageable pageable) {
		//System.out.println("selectVal=====================" + selectVal);
		ResponseDTO<Plan> responseDTO = new ResponseDTO<>();
		
		List<Plan> planCate = plannerService.getPlanCate(selectVal);
		
		Page<Plan> pagePlanCate = plannerService.getPagePlanCate(selectVal, pageable);
		
		try {
			responseDTO.setItems(planCate);
			responseDTO.setPageItems(pagePlanCate);
			//System.out.println(responseDTO);
			return ResponseEntity.ok().body(responseDTO);
		} catch(Exception e) {
			responseDTO.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
	
	@GetMapping("/getSchedule")
	public ModelAndView getSchedule() {
		return null;
	}
	
	// 순위 메뉴에서 각 랭크 불러오기
	@GetMapping("/getStatistic")
	public ModelAndView getStatistic() {
		
		LocalDate currentDateTime = null;
		currentDateTime = LocalDate.now();
		
		
		List<Statistic> dayRank = plannerService.getDayRank(currentDateTime);
		List<CamelHashMap> totalRank = plannerService.getTotalRank();
		System.out.println("=================totalRank"+ totalRank);
		List<Statistic> achieveRank = plannerService.getAchieveRank(currentDateTime);
		List<Plan> LikeRank = plannerService.getLikeRank();
		
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("dayRank", dayRank);
		mv.addObject("totalRank", totalRank);
		mv.addObject("achieveRank", achieveRank);
		mv.addObject("LikeRank", LikeRank);		
		mv.setViewName("planner/planStatistic.html");
		return mv;
	}
	
	@GetMapping("/stopwatch")
	public ModelAndView stopwatch() {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("planner/stopwatch.html");
		return mv;
	}
	
	// 달력 이동 및 개인 일정 가져오기
	@GetMapping("/calendar")
	public ModelAndView calendar(@AuthenticationPrincipal CustomUserDetails customUser, PlanDTO planDTO) {
		
		// 유저아이디, 플랜넘버, 시작날짜, 끝날짜, 플랙색깔
		String userId = customUser.getUsername();
		List<Plan> getPlanCalList = plannerService.getPlanByUserAndCal(userId);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("getPlanCalList", getPlanCalList);
		mv.setViewName("planner/calendar.html");
		return mv;
	}
	
	// 챗봇 이동
	@GetMapping("/chatbot")
	public ModelAndView chatbot() {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("planner/chatbot.html");
		return mv;
	}
	
	// 스탑워치 일시정지 클릭시 값 저장
	@PostMapping("/pause")
	public void pause(@RequestParam("time") int time,
			@AuthenticationPrincipal CustomUserDetails customUser) {		
		Statistic statistic = Statistic.builder()
									   .statisticDate(LocalDate.now())
									   .userId(customUser.getUsername())
									   //.statisticAchievementRate(String.valueOf(Math.round((time + plannerService.getTotalTime(customUser.getUsername())) / plannerService.getTotalContactTime(customUser.getUsername()))))
									   .statisticAchievementRate(plannerService.getTotalTime(customUser.getUsername()) == 0 ? "0" : String.valueOf(Math.round((double)time / plannerService.getTotalTime(customUser.getUsername()) * 100)))
									   .statisticUserStudyhourDate(time)
									   .statisticUserStudyhourTotal(plannerService.getTotalTime(customUser.getUsername()))
									   .build();
									   
		plannerService.pause(statistic);
	}
	
	// 스탑워치 - 저장된 오늘 공부시간 가져오기
	@GetMapping("/getTime")
	public ResponseEntity<?> getTime(@AuthenticationPrincipal CustomUserDetails customUser) {
		ResponseDTO<Integer> response = new ResponseDTO<>();
		
		try {
			response.setItem(plannerService.getTime(customUser.getUsername()));

			return ResponseEntity.ok().body(response);
		} catch(Exception e) {
			response.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	// 메모 저장하기
	@PostMapping("/insertNote")
	public void insertNote(@RequestParam("noteContent") String noteContent, NoteDTO noteDTO,
			@AuthenticationPrincipal CustomUserDetails customUser) {
		Note note = Note.builder()
						.userId(customUser.getUsername())
						.noteContent(noteContent)
						.noteRegdate(LocalDateTime.now())
						.build();
		plannerService.insertNote(note);
		
	}
	
	// 플래너 클릭시 해당 클래너로 이동
	@GetMapping("/plan/{planNo}")
	public ModelAndView getPlan(@PathVariable("planNo") int planNo) {
		ModelAndView mv = new ModelAndView();
		Plan planDetail = plannerService.getPlanDetail(planNo);
		List<CamelHashMap> planScheduleDetail = plannerService.getPlanScheduleDetail(planNo);
		System.out.println("=========================planScheduleDetail" + planScheduleDetail);
		System.out.println("=========================planDetail" + planDetail);
		
		mv.addObject("planScheduleDetail", planScheduleDetail);
		mv.addObject("planDetail", planDetail);	
		mv.setViewName("planner/planDetail.html");
		return mv;
	}
	
	// 플래너에서 < >버튼 클릭시 해당 스케줄로 이동
	@GetMapping("/plan/{planNo}/{scheduleStartDay}")
	public ResponseEntity<?> getPlanSchedule(@PathVariable("planNo") int planNo,
			@PathVariable("scheduleStartDay") String scheduleStartDay) {
		System.out.println(scheduleStartDay);
		ResponseDTO<Schedule> responseDTO = new ResponseDTO<>();
		
		List<Schedule> planScheduleDetailDay = plannerService.getPlanScheduleDetailDay(planNo, scheduleStartDay);
		System.out.println("=========================planScheduleDetailDay" + planScheduleDetailDay);
		
		
		try {
			responseDTO.setItems(planScheduleDetailDay);
			System.out.println(responseDTO);
			return ResponseEntity.ok().body(responseDTO);
		} catch(Exception e) {
			responseDTO.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
	
	// 플래너 조회수 증가
	@GetMapping("/updatePlanCnt/{planNo}")
	public void updatePlanCnt (@PathVariable("planNo") int planNo, 
			HttpServletResponse response) throws IOException {
		plannerService.updatePlanCnt(planNo);
		
		response.sendRedirect("/planner/plan/" + planNo);
	}
}
