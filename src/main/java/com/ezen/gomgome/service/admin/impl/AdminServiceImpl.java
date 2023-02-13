package com.ezen.gomgome.service.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ezen.gomgome.entity.NoticeFiles;
import com.ezen.gomgome.entity.Notice;
import com.ezen.gomgome.repository.NoticeFileRepository;
import com.ezen.gomgome.repository.NoticeRepository;
import com.ezen.gomgome.service.admin.AdminService;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private NoticeRepository noticeRepository;
	
	@Autowired
	private NoticeFileRepository noticeFileRepository;
	
	@Override
	public void insertNotice(Notice notice, List<NoticeFiles> uploadFileList) {
		noticeRepository.save(notice);
		noticeRepository.flush();
		
		for(NoticeFiles noticeFile : uploadFileList) {
			noticeFile.setNotice(notice);
			
			int noticeFileNo = noticeFileRepository.getMaxFileNo(notice.getNoticeNo());
			noticeFile.setNoticeFileNo(noticeFileNo);
			
			noticeFileRepository.save(noticeFile);
		}
	}

}