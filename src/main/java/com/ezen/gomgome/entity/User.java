package com.ezen.gomgome.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="t_gomg_user")
@Data
@NoArgsConstructor//기본생성자
@AllArgsConstructor//모든 매개변수를 받는 생성자
@Builder//객체 생성
@DynamicInsert //insert 구문이 생성될 때 null값인 컬럼은 배제하고 구문생성
@DynamicUpdate //update 변경되지 않은 값들을 제외하고 구문 생성
public class User {
	@Id
	private String userId;
	private String userPw;
	private String userName;
	private String userTel;
	private String userEmail;
	private LocalDate userBirthday;
	private String userNickname;
	private LocalDateTime userRegdate = LocalDateTime.now();
	private String userGender;
	private String userRole;
	private String userDelete = "N";
	
	private int userReportCnt = 0;
	private String userStatus;
	private String userStudyhourDate;
	private String userStudyhourTotal;
	private int userPlannerTotal = 0;
	
	@Transient
	private String joinYn;
}
