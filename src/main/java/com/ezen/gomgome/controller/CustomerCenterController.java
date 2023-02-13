package com.ezen.gomgome.controller;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.gomgome.dto.NoticeDTO;
import com.ezen.gomgome.dto.QuestionDTO;
import com.ezen.gomgome.entity.Notice;
import com.ezen.gomgome.entity.Question;
import com.ezen.gomgome.service.customercenter.CustomerCenterService;

@RestController
@RequestMapping("/customercenter")
public class CustomerCenterController {

	
	@Autowired
	private CustomerCenterService customercenterService;
	
	
	
	@GetMapping("/getNoticeList")
	public ModelAndView getNoticeList() {
		return null;
	}
	
	
	@GetMapping("/faqlist")
	public ModelAndView faqlist() {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("customercenter/cscenter_faq.html");
		return mv;
	}
	
	

	/*
	@GetMapping("/noticeList")
	public ModelAndView noticeList() {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("customercenter/cscenter_noticelist.html");
		return mv;
	}
	*/
	
	
	@GetMapping("/noticeList")
	public ModelAndView noticeList(@PageableDefault(page = 0, size = 10) Pageable pageable) {
		ModelAndView mv = new ModelAndView();
		
		
		Page<Notice> pageNoticeList = customercenterService.getpageNoticeList(pageable);
		
		Page<NoticeDTO> pageNoticeDTOList = pageNoticeList.map(pageBoard -> 
															   NoticeDTO.builder()
																		.noticeNo(pageBoard.getNoticeNo())
																		.noticeAdmin(pageBoard.getNoticeAdmin())
																		.noticeTitle(pageBoard.getNoticeTitle())
																		.noticeRegdate(pageBoard.getNoticeRegdate() == null ?
																			   	null :
																			   		pageBoard.getNoticeRegdate().toString())
																		.noticeContent(pageBoard.getNoticeContent())
																		.noticeFile(pageBoard.getNoticeFile())
																		.noticeHide(pageBoard.getNoticeTitle())
																		.build()
															);
		System.out.println(pageNoticeDTOList.getContent().size());
		mv.addObject("getNoticeList",pageNoticeDTOList);
		mv.setViewName("customercenter/cscenter_noticelist.html");
		return mv;
	}
	
	
	
	@GetMapping("/noticeList/{noticeNo}")
	public ModelAndView noticeList(@PathVariable int noticeNo) {
		
		
		ModelAndView mv = new ModelAndView();
		
		Notice notice = customercenterService.getNotice(noticeNo);
		
		System.out.println(notice);
		mv.addObject("notice", notice);
		mv.setViewName("community/#");
		return mv;
	}
	
	
	
		
	
	@GetMapping("/notice")
	public ModelAndView notice() {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("customercenter/cscenter_notice.html");
		return mv;
	}
	
	
	
	@GetMapping("/inquirylist")
	public ModelAndView inquirylist(@PageableDefault(page = 0, size = 10) Pageable pageable) {
		ModelAndView mv = new ModelAndView();
	
		Page<Question> pageInquirylist = customercenterService.getpageInquirylist(pageable);
		
		Page<QuestionDTO> pageInquiryDTOList = pageInquirylist.map(pageBoard -> 
															 QuestionDTO.builder()
																		.questionNo(pageBoard.getQuestionNo())
																		.userId(pageBoard.getUserId())
																		.questionTitle(pageBoard.getQuestionTitle())
																		.questionContent(pageBoard.getQuestionContent())
																		.questionReply(pageBoard.getQuestionReply())																	
																		.questionReplyRegdate(pageBoard.getQuestionReplyRegdate() == null ?
																			   	null :
																			   		pageBoard.getQuestionReplyRegdate().toString())
																		.questionType(pageBoard.getQuestionType())
																		.questionSecret(pageBoard.getQuestionSecret())
																		.build()
															);
		System.out.println(pageInquiryDTOList.getContent().size());
		mv.addObject("getInquiryList",pageInquiryDTOList);
		mv.setViewName("customercenter/cscenter_inquirylist.html");
		return mv;
	}
	
	@GetMapping("/inquirylist/{questionNo}")
	public ModelAndView inquiryList(@PathVariable int questionNo) {
		
		
		ModelAndView mv = new ModelAndView();
		
		
		Question question = customercenterService.getQuestion(questionNo);
		
		System.out.println(question);
		mv.addObject("question", question);
		mv.setViewName("community/#");
		return mv;
	}
	
	
	@GetMapping("/inquiry")
	public ModelAndView inquiry() {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("customercenter/cscenter_inquiry.html");
		return mv;
	}
	
	
	
	
	@GetMapping("/inquiryreg")
	public ModelAndView inquiryreg() {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("customercenter/cscenter_inquiry_reg.html");
		return mv;
	}
	
	
	
	
	
	
	
	
	
	@GetMapping("/getNotice")
	public ModelAndView getNotice() {
		return null;
	}
	
	@GetMapping("/getFaq")
	public ModelAndView getFaq() {
		return null;
	}
	
	@GetMapping("/getQuestionList")
	public ModelAndView getQuestionList() {
		return null;
	}
	
	@GetMapping("/getQuestion")
	public ModelAndView getQuestion() {
		return null;
	}
	
	@PostMapping("/insertQuestionView")
	public String insertQuestionView() {
		return null;
	}
	
	@PostMapping("/insertQuestion")
	public ResponseEntity<?> insertQuestion() {
		return null;
	}
	
	@Transactional
	@PutMapping("/updateQuestion")
	public ResponseEntity<?> updateQuestion() {
		return null;
	}
	
	@DeleteMapping("/deleteQuestion")
	public ResponseEntity<?> deleteQuestion() {
		return null;
	}
}
