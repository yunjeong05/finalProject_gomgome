package com.ezen.gomgome.controller;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.gomgome.common.CamelHashMap;
import com.ezen.gomgome.dto.CommunityDTO;
import com.ezen.gomgome.entity.Community;
import com.ezen.gomgome.service.community.CommunityService;

@RestController
@RequestMapping("/community")
public class CommunityController {
	@Autowired
	private CommunityService communityService;
	
	@GetMapping("/getPostList")
	public ModelAndView getPostList() {
		return null;
	}
	
	
	@GetMapping("/prList")
	public ModelAndView prList() {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("community/community_prlist.html");
		return mv;
	}
	
	
	
	
//	@GetMapping("/prList")
//	public ModelAndView prList() {
//		ModelAndView mv = new ModelAndView();
//		
//		mv.setViewName("community/community_pr.html");
//		return mv;
//	}
// 확인용 나중에 지울 것  customercenter/  community_pr
	


	
	@GetMapping("/howtoList")
	public ModelAndView howtoList() {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("community/community_howto_list.html");
		return mv;
	}
	
	
	@GetMapping("/bulletinList")
	public ModelAndView bulletinList() {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("community/community_bulletinboardlist.html");
		return mv;
	}
	
	@GetMapping("/pr")
	public ModelAndView pr() {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("community/community_pr.html");
		return mv;
	}
	
	
	
	@GetMapping("/howto")
	public ModelAndView howto() {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("community/community_howto.html");
		return mv;
	}
	
	@GetMapping("/bulletinboard")
	public ModelAndView bulletinboard() {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("community/community_bulletinboard.html");
		return mv;
	}
	

	@GetMapping("/communityReg")
	public ModelAndView communityReg() {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("community/community_reg.html");
		return mv;
	}
	

	
	@GetMapping("/communityModify")
	public ModelAndView communityModify() {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("community/community_modify.html");
		return mv;
	}
	

	
	
	/*
	@GetMapping("/CommunityPr")
	public ModelAndView CommunityPr() {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("community/community_pr.html");
		return mv;
	}
	
	*/
	
	@GetMapping("/getPost")
	public ModelAndView getPost() {
		return null;
	}
	
	@GetMapping("/insertPostView")
	public String insertPostView() {
		return null;
	}
	
	@PostMapping("/insertPost")
	public ResponseEntity<?> insertPost() {
		return null;
	}
	
	@Transactional
	@PutMapping("/updatePost")
	public ResponseEntity<?> updatePost() {
		return null;
	}
	
	@DeleteMapping("/deletePost")
	public ResponseEntity<?> deletePost() {
		return null;
	}
	
	@GetMapping("/getCommunityList")
	public ModelAndView getCommunityList() {
		return null;
	}
	
	@GetMapping("/getCommunity")
	public ModelAndView getCommunity() {
		return null;
	}
	
	@GetMapping("/insertCommunityView")
	public String insertCommunityView() {
		return null;
	}
	
	@PostMapping("/insertCommunity")
	public ResponseEntity<?> insertCommunity() {
		return null;
	}
	
	@Transactional
	@PutMapping("/updateCommunity")
	public ResponseEntity<?> updateCommunity() {
		return null;
	}
	
	@DeleteMapping("/deleteCommunity")
	public ResponseEntity<?> deleteCommunity() {
		return null;
	}
	
	@GetMapping("/getSearchList")
	public ModelAndView getSearchList(CommunityDTO communityDTO) {
		System.out.println("searchKeyword==================================" + communityDTO.getSearchKeyword());
		Community community = Community.builder()
									   .searchKeyword(communityDTO.getSearchKeyword())
									   .build();
		 
		//학습법 1
		List<CamelHashMap> communityList = communityService.getSearchCommunityList(community);

		List<CamelHashMap> prList = new ArrayList<CamelHashMap>();
		List<CamelHashMap> studyList = new ArrayList<CamelHashMap>();
		List<CamelHashMap> freeList = new ArrayList<CamelHashMap>();
		
		int prCnt = 0;
		int studyCnt = 0;
		int freeCnt = 0;
		// 숫자를 맞춰준다.
	    
		for(int i = 0; i < communityList.size(); i++) {						   
			System.out.println(communityList.get(i).toString());
			if(communityList.get(i).get("communityType").toString().equals("1")) {
				prCnt++;
				prList.add(communityList.get(i));
			} else if (communityList.get(i).get("communityType").toString().equals("2")) {
				studyCnt++;
				studyList.add(communityList.get(i));
			} else {
				freeCnt++;
				freeList.add(communityList.get(i));
			}
		}
		
		if(prCnt > 3) {
			prCnt = 3;
		} 
		if(studyCnt > 3) {
			studyCnt = 3;
		} 
		if(freeCnt > 3) {
			freeCnt = 3;
		}
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("searchKeyword",community.getSearchKeyword());
		mv.addObject("prList", prList);
		mv.addObject("studyList",studyList);
		mv.addObject("freeList", freeList);
		mv.addObject("prCnt", prCnt);
		mv.addObject("studyCnt", studyCnt);
		mv.addObject("freeCnt", freeCnt);
		
		mv.setViewName("community/searchList.html");
	
		return mv;
	
	}
}