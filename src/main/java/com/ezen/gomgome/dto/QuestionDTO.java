package com.ezen.gomgome.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor//기본생성장
@AllArgsConstructor//모든 매개변수를 받는 생성자
@Builder//객체 생성
public class QuestionDTO {
	private int questionNo;
	private String userId;
	private String questionTitle;
	private String questionContent;
	private String questionReply;
	private String questionReplyRegdate;
	private String questionType;
	private int questionSecret;
}
