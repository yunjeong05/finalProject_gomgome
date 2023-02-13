package com.ezen.gomgome.service.customercenter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ezen.gomgome.entity.Notice;
import com.ezen.gomgome.entity.Question;
import com.ezen.gomgome.repository.NoticeRepository;
import com.ezen.gomgome.repository.QuestionRepository;
import com.ezen.gomgome.service.customercenter.CustomerCenterService;

@Service
public class CustomerCenterServiceImpl implements CustomerCenterService {
	
	@Autowired
	private NoticeRepository noticeRepository;
	
	@Autowired
	private QuestionRepository questionRepository;
	

	@Override
	public Page<Notice> getpageNoticeList(Pageable pageable) {
		// TODO Auto-generated method stub
		return noticeRepository.findAll(pageable);
	}

	@Override
	public Notice getNotice(int noticeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Question> getpageInquirylist(Pageable pageable) {
		// TODO Auto-generated method stub
		return questionRepository.findAll(pageable);
	}

	@Override
	public Question getQuestion(int questionNo) {
		// TODO Auto-generated method stub
		return null;
	}

}
