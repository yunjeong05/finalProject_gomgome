package com.ezen.gomgome.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.gomgome.common.CamelHashMap;
import com.ezen.gomgome.dto.ResponseDTO;
import com.ezen.gomgome.dto.UserDTO;
import com.ezen.gomgome.entity.CustomUserDetails;
import com.ezen.gomgome.entity.User;
import com.ezen.gomgome.service.planner.PlannerService;
import com.ezen.gomgome.service.user.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private PlannerService plannerService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	//회원가입 타입 관련 페이지
	@GetMapping("/joinTypeView")
	public ModelAndView joinTypeView() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/joinTypeView.html");
		return mv;
	}
	
	//회원가입 내용작성 페이지 
	@GetMapping("/joinView")
	public ModelAndView joinView(@AuthenticationPrincipal CustomUserDetails customUser,
			HttpSession session) throws IOException {
		// 일반회원가입 유저
		User user = null; 
		// 소셜로그인
		if(customUser != null)
			user = customUser.getUser();
		
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(null);
		session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/join.html");
		mv.addObject("socialUser", user);

		return mv;
	}
	
	//social 로그인 클릭 시 회원가입 내용작성 페이지
	@GetMapping("/socialLogin")
	public void socialLogin(@AuthenticationPrincipal CustomUserDetails customUser,
			HttpSession session, HttpServletResponse response) throws IOException {
		// 일반회원가입 유저
		User user = null; 
		// 소셜로그인
		if(customUser != null)
			user = customUser.getUser();
		
		// 처음 소셜로그인한 회원이나 일반 회원가입
		if(user == null || user.getJoinYn().equals("N")) {
			response.sendRedirect("/user/joinView");
		} else {
			response.sendRedirect("/planner/planMain");
		}
	}
		
	@PostMapping("/join") 
	public void join(UserDTO userDTO, @RequestParam("cateArray") String cateArray, HttpServletResponse response) throws IOException {
		User user = User.builder()
						.userId(userDTO.getUserId())
						.userPw(passwordEncoder.encode(userDTO.getUserPw()))
						.userName(userDTO.getUserName())
						.userTel(userDTO.getUserTel())
						.userEmail(userDTO.getUserEmail())
						.userBirthday(LocalDate.parse(userDTO.getUserBirthday(), DateTimeFormatter.ISO_DATE))
						.userNickname(userDTO.getUserNickname())
						.userRegdate(LocalDateTime.now())
						.userGender(userDTO.getUserGender())
						.userPlannerTotal(0)
						.userRole("ROLE_USER")
						.userDelete("N")
						.userReportCnt(0)		
						.build();
		
		List<Integer> cateList = new ObjectMapper().readValue(cateArray, new TypeReference<List<Integer>>(){});

		userService.join(user, cateList);			
		
		response.sendRedirect("/user/loginView");	
	}
	
	//로그인 타입 관련 페이지
	@GetMapping("/loginView")
	public ModelAndView loginView() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/login.html");		
		return mv;
	}
	
	@PostMapping("/idCheck")
	public ResponseEntity<?> idCheck(UserDTO userDTO) {
		ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();
		Map<String, String> returnMap = new HashMap<String, String>();
		
		try {
			User user = User.builder()
							.userId(userDTO.getUserId())
							.build();
			
			User checkedUser = userService.idCheck(user);
			
			if(checkedUser != null) {
				returnMap.put("msg", "duplicatedId");
			} else {
				returnMap.put("msg", "isOk");	
			}
			
			responseDTO.setItem(returnMap);
			return ResponseEntity.ok().body(responseDTO);
		
		} catch (Exception e) {
			responseDTO.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(responseDTO);
			
		}
	}
	
	@PostMapping("/nicknameCheck")
	public ResponseEntity<?> nicknameCheck(UserDTO userDTO) {
		ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();
		Map<String, String> returnMap = new HashMap<String, String>();
		
		try {
			User user = User.builder()
							.userNickname(userDTO.getUserNickname())
							.build();
			
			User checkedUser = userService.nicknameCheck(user);
			
			if(checkedUser != null) {
				returnMap.put("msg", "dulicatedNickName");
			} else {
				returnMap.put("msg", "isOk");	
			}
			
			responseDTO.setItem(returnMap);
			return ResponseEntity.ok().body(responseDTO);
		
		} catch (Exception e) {
			responseDTO.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(responseDTO);
			
		}
	}
	
	@GetMapping("/findIdView/{idOrPw}")    ///@PathVariable 왜 쓸 수 없는 가 '/' 다음에 올 수 있음 -> 이동하더라도 원하는 파라미터, 패스값 받아오는게 어려움 
	public ModelAndView findIdView(@PathVariable int idOrPw) {
		ModelAndView mv = new ModelAndView();
		System.out.println("1111111111111111111111111111");
		System.out.println(idOrPw);
		mv.setViewName("user/findIdView.html");
		return mv;
	}
//	
//	@GetMapping("/findPwView")
//	public ModelAndView findPwView() {
//		ModelAndView mv = new ModelAndView();
//		mv.setViewName("user/findIdView.html");
//		return mv;
//	}	
	
	@PostMapping("/findId")
	public ResponseEntity<?> findId(UserDTO userDTO) {
		ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();
		Map<String, String> returnMap = new HashMap<String, String>();
		
		try {
			User user = User.builder()
					        .userName(userDTO.getUserName())
					        .userEmail(userDTO.getUserEmail())
					        .build();
			
			User dbResult = userService.findId(user); 
			
			String resultMsg = "";
			
			if(dbResult != null) {
				resultMsg = dbResult.getUserId();
			} else {
				resultMsg = "idNone";
			}
			
			returnMap.put("resultMsg", resultMsg);
			responseDTO.setItem(returnMap);
			return ResponseEntity.ok().body(responseDTO);
			
		} catch (Exception e) {
			responseDTO.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(responseDTO);	
		}
	}
	
	/*
	@PostMapping("/findPw")
	public ResponseEntity<?> findPw(UserDTO userDTO) {
		ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();
		Map<String, String> returnMap = new HashMap<String, String>();
		
		try {
			User user = User.builder()
					        .userName(userDTO.getUserName())
					        .userId(userDTO.getUserId())
					        .userEmail(userDTO.getUserEmail())
					        .build();
			
			User dbResult = userService.findPw(user); 
			
			String resultMsg = "";
			
			if(dbResult != null) {
				resultMsg = "Member";
			} else {
				resultMsg = "notMember";
			}
			
			returnMap.put("resultMsg", resultMsg);
			responseDTO.setItem(returnMap);
			return ResponseEntity.ok().body(responseDTO);
			
		} catch (Exception e) {
			responseDTO.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(responseDTO);	
		}
	}
	*/
	//등록된 이메일로 임시비밀번호를 발송하고 발송된 임시비밀번호로 사용자의 pw를 변경하는 컨트롤러
	@PostMapping("/searchPw")
    public ResponseEntity<?> doFindLoginPasswd(UserDTO userDTO, HttpServletResponse response) throws IOException {
		ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();
		Map<String, String> returnMap = new HashMap<String, String>();
		
		try {
	         User userInfo = User.builder()
	    		   				    .userId(userDTO.getUserId())
	    		   				    .userName(userDTO.getUserName())
	    		   				    .userEmail(userDTO.getUserEmail())
									.build();
	         //1. idCheck
	         User checkedUser = userService.idCheck(userInfo);
	         
	         if(checkedUser == null) {
	        	 returnMap.put("pwMsg", "idFail");
	         } else {
	        	 if(!userDTO.getUserName().equals(checkedUser.getUserName())) {
	        		 returnMap.put("pwMsg", "nameFail");
	        	 } else if(!userDTO.getUserEmail().equals(checkedUser.getUserEmail())) {
	        		 returnMap.put("pwMsg", "emailFail");
	        	 } else {
	        		 userService.findLoginPasswd(userInfo);
	        		 
	        		 returnMap.put("pwMsg", "sendMail");
	        	 }
	         }
	         
	         responseDTO.setItem(returnMap);
	         return ResponseEntity.ok().body(responseDTO);
		} catch(Exception e) {
			responseDTO.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(responseDTO);
		}
    }
	//임시비밀번호 받은 후에 비밀번호 화면으로 이동하는 메소드도 작성 해야하는지
	@PostMapping("/checkTemPw")
	public ResponseEntity<?> checkTemPw(UserDTO userDTO){
		ResponseDTO<Map<String, String>> response = new ResponseDTO<>();
		Map<String, String> returnMap = new HashMap<String, String>();
		
		try {
			User userInfo = User.builder()
   				    .userId(userDTO.getUserId())
   				    .userPw(userDTO.getUserPw())
					.build();
			
			//1. idCheck
	         User checkedUser = userService.idCheck(userInfo);
	         
	         if(checkedUser == null) {
	        	 returnMap.put("pwMsg", "idFail");
	         } else {
	        	 if(passwordEncoder.matches(userDTO.getUserPw(), checkedUser.getUserPw())) {
	        		 returnMap.put("pwMsg", "pwCheckSuccess");
	        	 } else {
	        		 returnMap.put("pwMsg", "pwFail");
	        	 }
	         }
	         
	         response.setItem(returnMap);
	         return ResponseEntity.ok().body(response);
		} catch (Exception e) { 
			response.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		
		
	}
	
	@GetMapping("/infoView")
	public ModelAndView infoPw() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/infoView.html");
		return mv;
	}
	
	@PostMapping("/pwCheck")
	public ResponseEntity<?> pwCheck(UserDTO userDTO, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
		ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();
		Map<String, String> returnMap = new HashMap<String, String>();
		
		try {
			User user = User.builder()		        
						.userId(customUserDetails.getUsername())
				        .userPw(userDTO.getUserPw())
				        .build();
	
			// ture/false값 
			boolean dbResult = userService.checkPw(user); 
			
			String resultMsg = "";
	
			if(dbResult == true) {
				resultMsg = "pwOk";
			} else {
				resultMsg = "pwFail";
			}
			
			returnMap.put("resultMsg", resultMsg);
			responseDTO.setItem(returnMap);
			return ResponseEntity.ok().body(responseDTO);
			
		} catch (Exception e) {
			responseDTO.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(responseDTO);
			
		}
		
	}
	
	@GetMapping("/updateUser")
	public ModelAndView updateUser(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
		// 쿼리 불러오기로 하면 user builder 할 필요없음
		User userInfo = userService.getUserInfo(customUserDetails.getUsername());
	
		UserDTO userDTO = UserDTO.builder()
								 .userId(userInfo.getUserId())
								 .userName(userInfo.getUserName())
								 .userNickname(userInfo.getUserNickname())
								 .userGender(userInfo.getUserGender())
								 .userBirthday(userInfo.getUserBirthday().toString())
								 .userTel(userInfo.getUserTel())
								 .userEmail(userInfo.getUserEmail())
								 .userDelete(userInfo.getUserDelete())
								 .userRegdate(userInfo.getUserRegdate().toString())
								 .userReportCnt(userInfo.getUserReportCnt())
								 .userRole(userInfo.getUserRole())
								 .userStatus(userInfo.getUserStatus())
								 .userPlannerTotal(userInfo.getUserPlannerTotal())
								 .userStudyhourDate(userInfo.getUserStudyhourDate())
								 .userStudyhourTotal(userInfo.getUserStudyhourTotal())
								 .build();
		
		List<CamelHashMap> cateInfoList = userService.getCateInfoList(customUserDetails.getUsername());

		ModelAndView mv = new ModelAndView();
		mv.addObject("userInfo", userDTO);
		mv.addObject("cateInfoList", cateInfoList);
		mv.setViewName("user/updateUser.html");
		
		return mv;
	}

	@PostMapping("/updateUserInfo")
	public void updateUserInfo(UserDTO userDTO, @RequestParam("cateArray") String cateArray, @AuthenticationPrincipal CustomUserDetails customUserDetails,
								HttpServletResponse response) throws IOException {
		
		System.out.println(customUserDetails.getUser().getUserPw());
		
		User user = User.builder()
						.userId(userDTO.getUserId())
						.userPw(userDTO.getUserPw() == null || userDTO.getUserPw().equals("") ?
								customUserDetails.getUser().getUserPw() : passwordEncoder.encode(userDTO.getUserPw()))
						.userName(userDTO.getUserName())
						.userBirthday(LocalDate.parse(userDTO.getUserBirthday()))
						.userNickname(userDTO.getUserNickname())
						.userGender(userDTO.getUserGender())
						.userTel(userDTO.getUserTel())
						.userEmail(userDTO.getUserEmail())
						.userDelete(customUserDetails.getUser().getUserDelete())
						.userRegdate(LocalDateTime.parse(userDTO.getUserRegdate()))
						.userReportCnt(customUserDetails.getUser().getUserReportCnt())
						.userRole(customUserDetails.getUser().getUserRole())
						.userStatus(customUserDetails.getUser().getUserStatus())
						.userPlannerTotal(customUserDetails.getUser().getUserPlannerTotal())
						.userStudyhourDate(customUserDetails.getUser().getUserStudyhourDate())
						.userStudyhourTotal(customUserDetails.getUser().getUserStudyhourTotal())
						.build();
		
		List<Integer> cateList = new ObjectMapper().readValue(cateArray, new TypeReference<List<Integer>>(){});
		
		userService.updateUserInfo(user, cateList);
		
		
		response.sendRedirect("/user/updateUser");
	}

	@PostMapping("/pwChangeView")
	public ModelAndView pwChangeView(UserDTO userDTO) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/pwChangeView.html");
		mv.addObject("user", userDTO);
		return mv;
	}
	
	@PostMapping("/pwChange") 
	public ResponseEntity<?> pwChange(UserDTO userDTO){
		System.out.println("userDTO==================================" + userDTO.toString());
		ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();
		Map<String, String> returnMap = new HashMap<String, String>();
		
		try {
			User user = User.builder()
							.userId(userDTO.getUserId())
							.userPw(passwordEncoder.encode(userDTO.getUserPw()))					
							.build();
			
			int updateOk = userService.pwChange(user);
			
			if(updateOk == 0) {
				returnMap.put("updateMsg", "0" );
			} else {
				returnMap.put("updateMsg", "1");
			}
		  	
			responseDTO.setItem(returnMap);
			return ResponseEntity.ok().body(responseDTO);
		} catch (Exception e) {
			responseDTO.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
	
	@PostMapping("/deleteInfo")
	public ResponseEntity<?> deleteInfo(UserDTO userDTO) {
		ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();
		Map<String, String> returnMap = new HashMap<String, String>();
		System.out.println("userDTO==================================" + userDTO.toString());
		
		try {
			User user = User.builder()
							.userId(userDTO.getUserId())
							.userDelete(userDTO.getUserDelete())
							.build();
	
			int deleteOk = userService.deleteInfo(user);
			
			if(deleteOk == 0) {
				returnMap.put("deleteMsg", "0" );
			} else {
				SecurityContextHolder.clearContext(); // 'Y'로 변하면서 
				returnMap.put("deleteMsg", "1");
			}
		  	
			responseDTO.setItem(returnMap);
			return ResponseEntity.ok().body(responseDTO);
			
		} catch (Exception e) {
			responseDTO.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(responseDTO);
		}
		
	}
	
	@PostMapping("/updateTemPw")
	public void updateTemPw (UserDTO userDTO) {
		User user = User.builder()
				        .userId(userDTO.getUserId()) 
				        .build();
		
		userService.updateTemPw(user);
	}
	
}
