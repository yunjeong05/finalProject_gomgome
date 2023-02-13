package com.ezen.gomgome.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor//기본생성장
@AllArgsConstructor//모든 매개변수를 받는 생성자
@Builder//객체 생성
public class PlanDTO {
	private int planNo;
	private String userId;
	private String planName;
	private String planRegdate;
	private int planViewCnt;
	private String planDelete;
	private String planGoal;
	private int planLikeCnt;
	private String planStartDay;
	private String planEndDay;
	private String planColor;
	private int planCopy;
	private String planDday;
	private String planImage;
	private int planMaincategoryNo;
	private int planStatus;
	private String planShare;
	private String planCalendar;
}
