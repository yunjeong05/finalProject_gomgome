package com.ezen.gomgome.service.community.impl;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ezen.gomgome.common.CamelHashMap;
import com.ezen.gomgome.entity.Community;
import com.ezen.gomgome.entity.PostImage;
import com.ezen.gomgome.entity.Report;
import com.ezen.gomgome.repository.CommunityRepository;
import com.ezen.gomgome.repository.PostImageRepository;
import com.ezen.gomgome.repository.ReportRepository;
import com.ezen.gomgome.service.community.CommunityService;

@Service
public class CommunityServiceImpl implements CommunityService {
	@Autowired
	private CommunityRepository communityRepository;
	
	@Autowired
	private PostImageRepository postImageRepository;
	
	@Autowired
	private ReportRepository reportRepository;

	@Override
	public Community getPr(int communityNo) {
		// TODO Auto-generated method stub
		return communityRepository.findById(communityNo).get();
	}
	
	
	@Override
	public Community getHowto(int communityNo) {
		// TODO Auto-generated method stub
		return communityRepository.findById(communityNo).get();
	}
	
	@Override
	public Community getBulletin(int communityNo) {
		// TODO Auto-generated method stub
		return communityRepository.findById(communityNo).get();
	}

	@Override
	public void communityReg(Community communityReg, List<PostImage> uploadFileList) {
		// TODO Auto-generated method stub
		communityRepository.save(communityReg);
		communityRepository.flush();
		
		for(PostImage postImage : uploadFileList) {
			postImage.setPostNo(communityReg.getCommunityNo());
			int postImgNo = postImageRepository.getNextPostImgNo(communityReg.getCommunityNo());
			postImage.setPostImageNo(postImgNo);
			postImage.setPostImageRegdate(LocalDateTime.now());
			
			postImageRepository.save(postImage);
		}
	}
	

	
	@Override
	public Page<Community> getpagePrList(Pageable pageable) {
		// TODO Auto-generated method stub
		//return boardMapper.getBoardList();	
		
		return communityRepository.findAllByCommunityType(1, pageable);
	}
	
	@Override
	public Page<Community> getpageHowtoList(Pageable pageable) {
		// TODO Auto-generated method stub
		//return boardMapper.getBoardList();	
		
		return communityRepository.findAllByCommunityType(2, pageable);
	}
	
	
	@Override
	public Page<Community> getpageBulletinList(Pageable pageable) {
		// TODO Auto-generated method stub
		//return boardMapper.getBoardList();	
		
		return communityRepository.findAllByCommunityType(3, pageable);
	}


	@Override
	public void deleteBoard(int communityNo) {
		// TODO Auto-generated method stub
		
		System.out.println("3333333333333333333333333333" + communityNo);
		communityRepository.deleteById(communityNo);
		System.out.println("3333333333333333333333333333");
	}


	@Override
	public void insertReportReg(Report reportReg) {
		// TODO Auto-generated method stub
		reportRepository.save(reportReg);
		reportRepository.flush();
		
	}


	@Override
	public List<CamelHashMap> getSearchCommunityList(Community community) {
		// TODO Auto-generated method stub
		//return communityRepository.getSearchCommunityList(community.getSearchKeyword());
		return null;
	}


	public List<PostImage> getFileList(int communityNo) {
		// TODO Auto-generated method stub
		return postImageRepository.findByPostNo(communityNo);
	}


	@Override
	public void communityModify(Community community) {
		// TODO Auto-generated method stub
		communityRepository.communityModify(community);
	}

	
}
