package com.ezen.gomgome.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.gomgome.dto.InsertPlanDTO;
import com.ezen.gomgome.dto.MyscheduleDTO;
import com.ezen.gomgome.dto.NoteDTO;
import com.ezen.gomgome.dto.ResponseDTO;
import com.ezen.gomgome.dto.ScheduleDTO;
import com.ezen.gomgome.dto.UpdateGoalDTO;
import com.ezen.gomgome.entity.CustomUserDetails;
import com.ezen.gomgome.entity.LikePlan;
import com.ezen.gomgome.entity.Maincategory;
import com.ezen.gomgome.entity.Note;
import com.ezen.gomgome.entity.Plan;
import com.ezen.gomgome.entity.Schedule;
import com.ezen.gomgome.entity.Statistic;
import com.ezen.gomgome.entity.User;
import com.ezen.gomgome.service.mypage.MyPageService;
import com.ezen.gomgome.service.planner.PlannerService;
import com.ezen.gomgome.service.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/mypage")
public class MyPageController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private PlannerService plannerService;
	
	@Autowired
	private MyPageService mypageService;
	
	@GetMapping("/getMypage")
	public ModelAndView getMypage() {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("/mypage/mypage_home.html");
		
		return mv;
	}
	
	//public ModelAndView insertBoardView(@AuthenticationPrincipal CustomUserDetails customUser) throws IOException
	//customUser.getUsername() -> Id
	
	// 전체 계획 - 내플랜 메인
	// (in 1) 로그인한 유저
	// (out 1) 유저가 추가한 모든 플랜 이름 : plan
	// (out 2) 유저의 누적 공부 시간        : user > userStudyhourTotal
	// (out 3) 공부시간 전체 순위           : user > rankStudyhourTotal
	// (out 4) dday                         : plan
	// (out 5) 목표 성공리스트              : plan
	// (out 6) 과거 플랜                    : plan
	//@GetMapping("/getMyplanAll")
	//public ModelAndView getMyplanAll(@RequestParam String userId) {
	@GetMapping("/total_plan")
	public ModelAndView getMyplanAll(MyscheduleDTO myscheduleDTO,
			@AuthenticationPrincipal CustomUserDetails customUser) {
		ModelAndView mv = new ModelAndView();
		
		//String userId = "testId01";
		
		LocalDate currentDateTime = null;
		
		if (myscheduleDTO.getScheduleDate() == null) {
			 currentDateTime = LocalDate.now();			
		} else {
			String tmpString = myscheduleDTO.getScheduleYear() +  "-" +
					myscheduleDTO.getScheduleMonth() + "-" +
					myscheduleDTO.getScheduleDate();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			//LocalDate tmpDate = LocalDate.parse(tmpString, formatter);
			currentDateTime = LocalDate.parse(tmpString, formatter);
			
			//currentDateTime = tmpDate.atStartOfDay();
		}
		System.out.println("date: " + currentDateTime);
		
		System.out.println("userName: " + customUser.getUsername());
		String userId = customUser.getUsername();
		
		// 유저의 "누적 공부 시간"과 "누적 공부 시간 전체 순위"
		List<User> userList = new ArrayList<User>();
		userList = userService.getUserOrderByStudyhourTotal();
		
		System.out.println("userList: " + userList);
		int rankStudyhourTotal = 0;
		String userStudyhourTotal = "none";

		//System.out.println("user: " + userList.get(0).getUserId());
		
		for (int i = 0; i < userList.size(); i++) {
			if (userList.get(i).getUserId().equals(userId)) {
				rankStudyhourTotal = i+1;
				userStudyhourTotal = userList.get(i).getUserStudyhourTotal();
				break;
			}
		}
		
		// 유저가 추가한 모든 플랜 이름, dday, 목표 성공리스트, 과거 플랜
		List<Plan> planList = mypageService.getPlanByDateAndUser(currentDateTime, userId);
		List<Plan> goalList = mypageService.getPlanByGoal(userId);
		List<Plan> closedList = mypageService.getPlanByPast(currentDateTime, userId);
		
		System.out.println("goalList: " + goalList);
		
		List<Schedule> scheduleList = new ArrayList<Schedule>();
		for (Plan plan : planList) {
			int planNo = plan.getPlanNo();
			scheduleList.addAll(mypageService.getScheduleByPlanNoAndDate(planNo, currentDateTime));
		}
		System.out.println("scheduleList: " + scheduleList);
		
		//System.out.println("planList: " + planList);
		
		// 플랜리스트 가져옴 -> 원하는 정보들에 따라 분류
		
		mv.addObject("rankStudyhourTotal", rankStudyhourTotal);
		mv.addObject("userStudyhourTotal", userStudyhourTotal);
		mv.addObject("planUsersList", planList);
		mv.addObject("goalList", goalList);
		mv.addObject("scheduleList", scheduleList);
		mv.addObject("closedList", closedList);
		
		mv.setViewName("mypage/total_plan.html");
		
		return mv;
	}
	
	@PostMapping("/insertMyplan")
	//public ResponseEntity<?> insertMyplan(InsertPlanDTO insertPlanDTO,
	public void insertMyplan(InsertPlanDTO insertPlanDTO,
			HttpServletResponse response,
			@AuthenticationPrincipal CustomUserDetails customUser) throws IOException {
		
		//String userId = "testId01";
		String userId = customUser.getUsername();
		
		// 대분류 카테고리 번호 가져오기
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		Plan plan = Plan.builder()
						.planMaincategoryNo(insertPlanDTO.getPlanMaincategoryNo())
						.planName(insertPlanDTO.getPlanName())
						.planStartDay(
								insertPlanDTO.getPlanStart() == null ||
										insertPlanDTO.getPlanStart().equals("") ?
								null : LocalDate.parse(insertPlanDTO.getPlanStart(), formatter))
						.planEndDay(
								insertPlanDTO.getPlanEnd() == null ||
										insertPlanDTO.getPlanEnd().equals("") ?
								null : LocalDate.parse(insertPlanDTO.getPlanEnd(), formatter))
						.planDday(
								insertPlanDTO.getPlanDday() == null ||
										insertPlanDTO.getPlanDday().equals("") ?
								null : LocalDate.parse(insertPlanDTO.getPlanDday(), formatter))
						.planColor(insertPlanDTO.getPlanColor())
						.userId(userId)
						.build();
		
		System.out.println("insert plan: " + plan.toString());
	
		plannerService.insertPlan(plan);
		
		response.sendRedirect("/mypage/total_plan");
	}
	
	@PostMapping("/updateGoal")
	//public ResponseEntity<?> insertPlanGoal() {
	public void updateGoal(UpdateGoalDTO updateGoalDTO,
			HttpServletResponse response,
			@AuthenticationPrincipal CustomUserDetails customUser) throws IOException {
		
		//String userId = "testId01";
		String userId = customUser.getUsername();
		
		//int planNo = Integer.parseInt(updateGoalDTO.getPlanNo());
		int planNo = updateGoalDTO.getPlanNo();
		
//		System.out.println("planNo: " + planNo);
		
		Plan plan = plannerService.getPlanByNo(planNo);
		
//		System.out.println("updatePlan: " +  plan);
		
		plan.setPlanGoal(updateGoalDTO.getPlanGoal());
		plan.setPlanName(updateGoalDTO.getPlanName());
		plan.setPlanStatus(updateGoalDTO.getPlanStatus());
		
		System.out.println("update plan: " + plan);
		
		plannerService.updateGoal(plan);
		
		response.sendRedirect("/mypage/total_plan");
	}
	
// ****************************
// **       오늘의할일       **
// ****************************
	@GetMapping("/todo_date")
	public ModelAndView getMyscheduleAll(//@RequestParam("today") String today,
			MyscheduleDTO myscheduleDTO,
			@AuthenticationPrincipal CustomUserDetails customUser) {
		
		String userId = customUser.getUsername();
		
//		System.out.println("today: " + today);
		
		ModelAndView mv = new ModelAndView();
		LocalDate currentDateTime = null;
		
		if (myscheduleDTO.getScheduleDate() == null) {
			 currentDateTime = LocalDate.now();			
		} else {
			String tmpString = myscheduleDTO.getScheduleYear() +  "-" +
					String.format("%0" + 2 + "d", Integer.parseInt(myscheduleDTO.getScheduleMonth())) + "-" +
					String.format("%0" + 2 + "d", Integer.parseInt(myscheduleDTO.getScheduleDate()));
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			//LocalDate tmpDate = LocalDate.parse(tmpString, formatter);
			currentDateTime = LocalDate.parse(tmpString, formatter);
			
			//currentDateTime = tmpDate.atStartOfDay();
		}
		
		System.out.println("DTO: " + myscheduleDTO);
		System.out.println("current: " +  currentDateTime);
		
		List<Plan> planList = mypageService.getPlanByDateAndUser(currentDateTime, userId);
		
		System.out.println("plan: " + planList);
		
		
		
		Map<Integer, List<Schedule>> scheduleMap = new HashMap<Integer, List<Schedule>>();
		
		
		for (Plan plan : planList) {
			int planNo = plan.getPlanNo();
			List<Schedule> scheduleList = mypageService.getScheduleByPlanNo(planNo); 
			if (scheduleList != null && !scheduleList.isEmpty()) {
				scheduleMap.put(planNo, scheduleList);
			}
		}
		
		mv.setViewName("mypage/todo_date.html");
		mv.addObject("planList", planList);
		mv.addObject("scheduleMap", scheduleMap);
		mv.addObject("focusDate", currentDateTime);
		
		System.out.println(scheduleMap);
		
		return mv;
	}
	
	// ****************************
	// **          플랜n         **@RequestParam("planNo") 
	// ****************************
	@GetMapping("/getMyschedule")
	public ModelAndView getMyschedule(int planNo, String focusDate,
			@AuthenticationPrincipal CustomUserDetails customUser) {
		
		ModelAndView mv = new ModelAndView();
		
		String userId = customUser.getUsername();
		
		LocalDate currenttDate = null;
		
		
		System.out.println("planNo: " + planNo);
		System.out.println("focusDate: " + focusDate);
		
		System.out.println("localdate now: " + LocalDate.now());
		
		if (focusDate == null) {
			currenttDate = LocalDate.now();			
		} else {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			//LocalDate tmpDate = LocalDate.parse(tmpString, formatter);
			currenttDate = LocalDate.parse(focusDate, formatter);
		}
		
		System.out.println("current: " + currenttDate);
		
		Plan plan = plannerService.getPlanByNo(planNo);
		List<Schedule> otherScheduleList = mypageService.getOtherSchedule(planNo, userId, currenttDate);
		List<Schedule> scheduleList = mypageService.getScheduleByPlanNoAndDate(planNo, currenttDate);
		
		mv.addObject("plan", plan);
		mv.addObject("otherList", otherScheduleList);
		mv.addObject("scheduleList", scheduleList);
		mv.addObject("focusDate", currenttDate);
		mv.setViewName("mypage/add_plan.html");
		
		System.out.println("plan: " + plan);
		
		return mv;
	}
	
	@GetMapping("/closed")
	public ModelAndView getMyschedule(@AuthenticationPrincipal CustomUserDetails customUser) {
		
		ModelAndView mv = new ModelAndView();
		
		String userId = customUser.getUsername();
		

		mv.setViewName("mypage/closed.html");

		
		return mv;
	}
/*	
	@PostMapping("/updateMyschedule")
	//public ResponseEntity<?> updateMyschedule(List<ScheduleDTO> scheduleDTO) {
	//public void updateMyschedule(List<ScheduleDTO> scheduleDTO) {
	public ResponseEntity<?> updateMyschedule(@RequestParam("updateSchedules") String updateSchedules, @AuthenticationPrincipal CustomUserDetails customUser) throws JsonMappingException, JsonProcessingException{

		ResponseDTO<ScheduleDTO> response = new ResponseDTO<>();
		List<Map<String, Object>> updateScheduleList = new ObjectMapper().readValue(updateSchedules,
												new TypeReference<List<Map<String, Object>>>() {});
		
		
		try {
			mypageService.saveScheduleList(updateScheduleList);
			mypageService.saveScheduleLTotalTime(customUser.getUsername());
			return ResponseEntity.ok().body(response);
		} catch(Exception e) {
			response.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}
*/	
	@PostMapping("/saveSchedule")
	public ResponseEntity<?> saveSchedule(@RequestParam("param") String param,
			@RequestParam("focusDate") String focusDate,
			@AuthenticationPrincipal CustomUserDetails customUser) throws JsonMappingException, JsonProcessingException {
		
		ResponseDTO<ScheduleDTO> response = new ResponseDTO<>();
		List<Map<String, Object>> updateList = new ObjectMapper().readValue(param,
				new TypeReference<List<Map<String, Object>>>() {});
		String userId = customUser.getUsername();
		
		System.out.println("^^^^^^^^^^^^^^^^^^^^^");
		//System.out.println(updateList);
		System.out.println(focusDate);
		
		try {
			
			mypageService.saveScheduleList(updateList, focusDate, userId);
			mypageService.saveScheduleLTotalTime(customUser.getUsername());
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			response.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PostMapping("/insertMyschedule")
	public ResponseEntity<?> insertMyschedule() {

		return null;
	}
	
	@DeleteMapping("/deleteMyschedule")
	public ResponseEntity<?> deleteMyschedule() {
		return null;
	}
	
	@GetMapping("/statistic_calendar")
	public ModelAndView getStatisticMonth(MyscheduleDTO myscheduleDTO,
			@AuthenticationPrincipal CustomUserDetails customUser) {
		
		ModelAndView mv = new ModelAndView();
		
		String userId = customUser.getUsername();

		LocalDate currentDateTime = null;
		
		if (myscheduleDTO.getScheduleDate() == null) {
			 currentDateTime = LocalDate.now();			
		} else {
			String tmpString = myscheduleDTO.getScheduleYear() +  "-" +
					myscheduleDTO.getScheduleMonth() + "-" +
					myscheduleDTO.getScheduleDate();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			//LocalDate tmpDate = LocalDate.parse(tmpString, formatter);
			currentDateTime = LocalDate.parse(tmpString, formatter);
			
			//currentDateTime = tmpDate.atStartOfDay();
		}
		
		// 유저가 추가한 모든 플랜 이름, dday, 목표 성공리스트, 과거 플랜
		List<Plan> planList = new ArrayList<Plan>();
		planList = mypageService.getPlanByDateAndUser(currentDateTime, userId);
		
		//System.out.println("planList: " + planList);
		
		// 플랜리스트 가져옴 -> 원하는 정보들에 따라 분류
		mv.addObject("planList", planList);
		
		mv.setViewName("mypage/statistic_calendar.html");
		
		return mv;
	}
	
	@GetMapping("/statistic_graph")
	public ModelAndView getStatisticGraph(@AuthenticationPrincipal CustomUserDetails customUser) {
		ModelAndView mv = new ModelAndView();
		
		String userId = customUser.getUsername();
		
		List<Statistic> statisticList = mypageService.getStatisticByUser(userId);
		
		System.out.println("statisticList: " + statisticList);
		
		mv.setViewName("mypage/statistic_graph.html");
		return mv;
	}
	
	@GetMapping("/like_plan")
	public ModelAndView getLikePlan(@AuthenticationPrincipal CustomUserDetails customUser) {
		ModelAndView mv = new ModelAndView();
		
		String userId = customUser.getUsername();
		
		//List<LikePlan> likeList = mypageService.getLikeByUser(userId);
		
		List<Plan> likePlanList = mypageService.getLikePlanByUser(userId);
		List<Maincategory> categoryList = mypageService.getCategory();
		List<LikePlan> likeList = mypageService.getLikeByUser(userId);
		
		System.out.println("likePlanList: " + likePlanList);
		
		mv.addObject("likeList", likeList);
		mv.addObject("likePlanList", likePlanList);
		mv.addObject("categoryList", categoryList);
		mv.setViewName("mypage/like_plan.html");
		return mv;
	}
	
	// 플랜 테이블 업데이트: copy 컬럼 0 -> 복사해온 planNo로 변경
	// api 호출 전에 유저의 플랜과 다른지 확인?
	@PostMapping("/likePlanAPI")
	public ResponseEntity<?> likePlan(int planNo,
			@AuthenticationPrincipal CustomUserDetails customUser) {
		
		ResponseDTO<Plan> responseDTO = new ResponseDTO<>();
		String userId = customUser.getUsername();
		
		try {
			
			LikePlan likePlan = LikePlan.builder()
										.userId(userId)
										.planNo(planNo)
										.copy(0)
										.build();
			
			// 좋아요 추가
			mypageService.insertLikePlan(likePlan);
			
			// 좋아요 카운트 증가
			mypageService.addCountLike(planNo);
			
			// 좋아요목록 다시붙이고 좋아요 수 업데이트
			List<Plan> getPlanLike = mypageService.getLikePlanByUser(userId);
			
			responseDTO.setItems(getPlanLike);
			return ResponseEntity.ok().body(responseDTO);
		} catch (Exception e) {
			responseDTO.setErrorMessage(e.getMessage());
			
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
	
	@PostMapping("/copyPlanAPI")
	public ResponseEntity<?> copyPlan(//int planNo,
			@RequestParam(value="copyList[]") List<Integer> copyList,
			@AuthenticationPrincipal CustomUserDetails customUser) {
		
		ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();
		String userId = customUser.getUsername();
		
		try {
			for (int i = 0; i < copyList.size(); i++) {
				
				int planNo = copyList.get(i);
				// 원본 플랜 가져온다
				Plan plan = plannerService.getPlanByNo(planNo);
				// 복제본에 저장, 유저아이디 바꿈, 복제 여부 표시
		//		plan.setUserId(userId);
		//		plan.setPlanCopy(1);
				
				// 복제본을 테이블에 저장
				Plan planCopy = plan;
						
				planCopy.setPlanCopy(planNo);
				planCopy.setPlanName("[복사] " + plan.getPlanName());
				planCopy.setUserId(userId);
				System.out.println("copy: " + planCopy);
				
//				plannerService.insertPlan(planCopy);
			}
			
			
			return ResponseEntity.ok().body(responseDTO);
		} catch (Exception e) {
			responseDTO.setErrorMessage(e.getMessage());
			
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
	
	@DeleteMapping("/cancelLikeAPI")
	public ResponseEntity<?> cancelLikePlan(int planNo,
			@AuthenticationPrincipal CustomUserDetails customUser) {
		
		ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();
		String userId = customUser.getUsername();
		
		
		try {
			
			
			LikePlan likePlan = LikePlan.builder()
										.userId(userId)
										.planNo(planNo)
										.build();
			
			mypageService.cancelLikePlan(likePlan);
			System.out.println("cancel: " + likePlan);
			// 좋아요 카운트 감소
		    mypageService.subCountLike(planNo);
		
			
			return ResponseEntity.ok().body(responseDTO);
		} catch (Exception e) {
			responseDTO.setErrorMessage(e.getMessage());
			
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
	
	@DeleteMapping("/cancelLikesAPI")
	public ResponseEntity<?> cancelLikePlan(//@RequestParam(value="") List<Integer> planNoList,
			@RequestParam(value="cancelList[]") List<Integer> cancelList,
			@AuthenticationPrincipal CustomUserDetails customUser) {
		
		ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();
		String userId = customUser.getUsername();
		
		
		try {
			
			for (int i = 0; i < cancelList.size(); i++) {
				
				int planNo = cancelList.get(i);

				LikePlan likePlan = LikePlan.builder()
											.userId(userId)
											.planNo(planNo)
											.build();
				
				mypageService.cancelLikePlan(likePlan);
				System.out.println("cancel: " + likePlan);
				// 좋아요 카운트 감소
			    mypageService.subCountLike(planNo);
			}
			
			return ResponseEntity.ok().body(responseDTO);
		} catch (Exception e) {
			responseDTO.setErrorMessage(e.getMessage());
			
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
	
	@GetMapping("/my_note")
	public ModelAndView getNoteList(@AuthenticationPrincipal CustomUserDetails customUser) {
		ModelAndView mv = new ModelAndView();
		 
		String userId = customUser.getUsername();
		
		List<Note> noteList = mypageService.getNoteByUser(userId);
		
		System.out.println("noteList: " + noteList);
		
		mv.addObject("noteList", noteList);
		mv.setViewName("mypage/my_note.html");
		return mv;
	}
	
	@GetMapping("/getNote")
	public ModelAndView getNote() {
		
		
		
		return null;
	}
	
	@PostMapping("/insertNote")
	public ResponseEntity<?> insertNote(NoteDTO noteDTO,
			@AuthenticationPrincipal CustomUserDetails customUser) {
		
		ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();
		String userId = customUser.getUsername();
		
		try {
			
			Note note = Note.builder()
							.userId(userId)
							.noteContent(noteDTO.getNoteContent())
							.build();
			
			plannerService.insertNote(note);
			
			return ResponseEntity.ok().body(responseDTO);
		} catch (Exception e) {
			responseDTO.setErrorMessage(e.getMessage());
			
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
	
	@Transactional
	@PutMapping("/updateNoteAPI")
	public ResponseEntity<?> updateNote(@RequestParam("note") String note,
			@AuthenticationPrincipal CustomUserDetails customUser) throws JsonMappingException, JsonProcessingException {
		
		ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();
		String userId = customUser.getUsername();
		
		Map<String, Object> noteMap = new ObjectMapper().readValue(note, 
								new TypeReference<Map<String, Object>>() {});
		
		try {
			
			Note uNote = Note.builder()
							.noteNo(Integer.parseInt(noteMap.get("noteNo").toString()))
							.userId(userId)
							.noteContent(noteMap.get("noteContent").toString())
							.noteRegdate(LocalDateTime.now())
							.build();

			System.out.println("note: " + uNote);
			plannerService.updateNote(uNote);
			
			return ResponseEntity.ok().body(responseDTO);
		} catch (Exception e) {
			responseDTO.setErrorMessage(e.getMessage());
			
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
	

	
	@DeleteMapping("/deleteNoteList")
	public ResponseEntity<?> deleteNote(//
			@RequestParam(value="deleteList[]") List<Integer> deleteList,
			@AuthenticationPrincipal CustomUserDetails customUser) {
		
		ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();
		String userId = customUser.getUsername();
		
		try {
			for (int i = 0; i < deleteList.size(); i++) {
				
				int noteNo = deleteList.get(i);
				
				mypageService.deleteNote(noteNo);

			}

			return ResponseEntity.ok().body(responseDTO);
		} catch (Exception e) {
			responseDTO.setErrorMessage(e.getMessage());
			
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
	
	@DeleteMapping("/deleteNoteAPI")
	public ResponseEntity<?> deleteNote(int noteNo,
			@AuthenticationPrincipal CustomUserDetails customUser) {
		
		ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();
		String userId = customUser.getUsername();
		
		System.out.println("noteNo: " + noteNo);
		try {
			
			mypageService.deleteNote(noteNo);

			return ResponseEntity.ok().body(responseDTO);
		} catch (Exception e) {
			responseDTO.setErrorMessage(e.getMessage());
			
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
	
	@RequestMapping("/deletePlan")
	public void deletePlan(int planNo, String focusDate, HttpServletResponse response) throws IOException {
		
		mypageService.deletePlan(planNo);
		
		String[] tmp = focusDate.split("-");
		response.sendRedirect("/mypage/todo_date?scheduleYear=" + tmp[0]
				+ "&scheduleMonth=" + tmp[1]
				+ "&scheduleDate=" + tmp[2]);
	}
	
	@RequestMapping("/getCategory")
	public ResponseEntity<?>  getCategory() {
		
		ResponseDTO<Maincategory> responseDTO = new ResponseDTO<>();
		
		try {
			
			List<Maincategory> categoryList = mypageService.getCategory();

			responseDTO.setItems(categoryList);
			
			return ResponseEntity.ok().body(responseDTO);
		} catch (Exception e) {
			responseDTO.setErrorMessage(e.getMessage());
			
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
	
}





















