package com.ezen.gomgome.service.community;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ezen.gomgome.common.CamelHashMap;
import com.ezen.gomgome.entity.Community;
import com.ezen.gomgome.entity.PostImage;
import com.ezen.gomgome.entity.Report;


public interface CommunityService {

	Community getPr(int communityNo);
	
	Community getHowto(int communityNo);

	Community getBulletin(int communityNo);

	// (insertboard)communityReg
	void communityReg(Community communityReg, List<PostImage> uploadFileList);
	
	Page<Community> getpageHowtoList(Pageable pageable);

	Page<Community> getpageBulletinList(Pageable pageable);

	Page<Community> getpagePrList(Pageable pageable);

	void deleteBoard(int communityNo);

	void insertReportReg(Report reportReg);

	List<PostImage> getFileList(int communityNo);

	void communityModify(Community community);



	List<CamelHashMap> getSearchCommunityList(Community community);

}
