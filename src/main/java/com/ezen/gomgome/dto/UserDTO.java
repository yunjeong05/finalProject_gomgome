package com.ezen.gomgome.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor//기본생성장
@AllArgsConstructor//모든 매개변수를 받는 생성자
@Builder//객체 생성
public class UserDTO {
	private String userId;
	private String userPw;
	private String userName;
	private String userTel;
	private String userEmail;
	private String userBirthday;
	private String userNickname;
	private String userRegdate;
	private String userGender;
	private String userRole;
	private String userDelete;
	private int userReportCnt;
	private String userStatus;
	private String userStudyhourDate;
	private String userStudyhourTotal;
	private int userPlannerTotal;
}
