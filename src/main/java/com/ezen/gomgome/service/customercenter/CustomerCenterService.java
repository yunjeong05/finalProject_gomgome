package com.ezen.gomgome.service.customercenter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ezen.gomgome.entity.Notice;
import com.ezen.gomgome.entity.Question;

//import java.util.List;

//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//
//import com.ezen.gomgomge.entity.Board;
//import com.ezen.gomgomge.entity.BoardFile;

public interface CustomerCenterService {

	Page<Notice> getpageNoticeList(Pageable pageable);

	Notice getNotice(int noticeNo);

	Page<Question> getpageInquirylist(Pageable pageable);

	Question getQuestion(int questionNo);
	

	

	
	
//	Board getBoard(int boardNo);
//	
//	List<Board> getBoardList(Board board);
//	
//	void insertBoard(Board board, List<BoardFile> uploadFileList);
//	
//	Board updateBoard(Board board, List<BoardFile> uFileList);
//	
//	void deleteBoard(int boardNo);
//	
//	void updateBoardCnt(int boardNo);
//	
//	List<BoardFile> getBoardFileList(int boardNo);
//	
//	Page<Board> getPageBoardList(Board board, Pageable pageable);
	
	

}
