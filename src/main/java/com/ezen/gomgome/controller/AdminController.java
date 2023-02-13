package com.ezen.gomgome.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.gomgome.common.FileUtils;
import com.ezen.gomgome.dto.NoticeDTO;
import com.ezen.gomgome.entity.CustomUserDetails;
import com.ezen.gomgome.entity.Notice;
import com.ezen.gomgome.entity.NoticeFiles;
import com.ezen.gomgome.service.admin.AdminService;
import com.ezen.gomgome.service.customercenter.CustomerCenterService;
import com.ezen.gomgome.service.planner.PlannerService;
import com.ezen.gomgome.service.user.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private PlannerService plannerService;
	
	@Autowired
	private CustomerCenterService customerService;
	
	@GetMapping("/adminView")
	public ModelAndView adminView() {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("admin/mainAdmin.html");
		return mv;
	}
	
	@GetMapping("/getUserList")
	public ModelAndView getUserList() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/user_list.html");
		return mv;
	}
	
	
	@PostMapping("/getUser")
	public ModelAndView getUser() {
		return null;
	}
	
	@PutMapping("/updateUser")
	public ResponseEntity<?> updateUser() {
		return null;
	}
	
	@GetMapping("/getNoticeList")
	public ModelAndView getNoticeList() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/notice_list.html");
		return mv;
	}

	
	@GetMapping("/insertNoticeView")
	public ModelAndView insertNoticeView(@AuthenticationPrincipal CustomUserDetails customUser) throws IOException {	
		ModelAndView mv = new ModelAndView();	
		mv.setViewName("admin/notice_insert.html");
		return mv;
	}
//	@GetMapping("/insertNoticeView")
//	public String insertNoticeView() {
//		return null;
//	}
	
	@PostMapping("/insertNotice")
	public void insertNotice(NoticeDTO noticeDTO, MultipartFile[] noticeFile, 
			HttpServletResponse response, HttpServletRequest request) throws IOException {
		Notice notice = Notice.builder()
							  .noticeTitle(noticeDTO.getNoticeTitle())
							  .noticeRegdate(LocalDateTime.now())
							  .noticeContent(noticeDTO.getNoticeContent())
							  .noticeAdmin(noticeDTO.getNoticeAdmin())
							  .noticeRegdate(LocalDateTime.now())
							  .build();
		//DB에 입력될 파일 정보 리스트
				List<NoticeFiles> uploadFileList = new ArrayList<NoticeFiles>();
				
				if(noticeFile.length > 0) {
					String attachPath = request.getSession().getServletContext().getRealPath("/")
							+ "/upload/";
					
					System.out.println("attachPath====================" + attachPath);
					
					File directory = new File(attachPath);
					
					if(!directory.exists()) {
						directory.mkdir();
					}
					
					//multipartFile 형식의 데이터를 DB 테이블에 맞는 구조로 변경
					for(int i = 0; i < noticeFile.length; i++) {
						MultipartFile file = noticeFile[i];
						
						if(!file.getOriginalFilename().equals("") &&
							file.getOriginalFilename() != null) {
							NoticeFiles noticeFiles = new NoticeFiles();
							
							//noticeFiles = FileUtils.parseFileInfo(file, attachPath);
//							uploadFileList.add(noticeFile);
						}
					}
				}
				
				adminService.insertNotice(notice, uploadFileList);
		
				response.sendRedirect("/admin/getNoticeList");
	}
	
//	@PostMapping("/insertNotice")
//	public ResponseEntity<?> insertNotice() {
//		return null;
//	}
	
	@PutMapping("/updateNotice")
	public ResponseEntity<?> updateNotice() {
		return null;
	}
	
	@PutMapping("/hideNotice")
	public ResponseEntity<?> hideNotice() {
		return null;
	}
	
	@PutMapping("/showNotice")
	public ResponseEntity<?> showNotice() {
		return null;
	}
	
	@GetMapping("/getQuestionList")
	public ModelAndView getQuestionList() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/question_list.html");
		return mv;
	}
	
	@GetMapping("/getQuestion")
	public ModelAndView getQUestion() {
		return null;
	}
	
	@PostMapping("/insertQuestion")
	public ResponseEntity<?> insertQuestion() {
		return null;
	}
	
	@PutMapping("/updateQuestion")
	public ResponseEntity<?> updateQuestion() {
		return null;
	}
	
	@DeleteMapping("/deleteAnswer")
	public void deleteAnswer() {
		
	}
	
	@GetMapping("/getReportList")
	public ModelAndView getReportList() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/report_list.html");
		return mv;
	}
	
	@GetMapping("/getReport")
	public ModelAndView getReport() {
		return null;
	}
	
	@PutMapping("/reportUserOut")
	public ResponseEntity<?> reportUserOut() {
		return null;
	}
	
	@PutMapping("/dismissReport")
	public ResponseEntity<?> dismissReport() {
		return null;
	}
	
	@GetMapping("/getFAQList")
	public ModelAndView getFAQList() {
		return null;
	}
	
	@GetMapping("/insertFAQView")
	public String insertFAQView() {
		return null;
	}
	
	@PostMapping("/insertFAQ")
	public ResponseEntity<?> insertFAQ() {
		return null;
	}
	
	@PutMapping("/updateFAQ")
	public ResponseEntity<?> updateFAQ() {
		return null;
	}
	
	@PutMapping("/deleteFAQ")
	public ResponseEntity<?> deleteFAQ() {
		return null;
	}
}
