package com.ezen.gomgome.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="t_gomg_plan")
@Data
@SequenceGenerator(
		name="PlanSequenceGenerator",
		sequenceName="plan_seq",
		initialValue=1,
		allocationSize=1
)
@NoArgsConstructor//기본생성자
@AllArgsConstructor//모든 매개변수를 받는 생성자
@Builder//객체 생성
@DynamicInsert //insert 구문이 생성될 때 null값인 컬럼은 배제하고 구문생성
@DynamicUpdate //update 변경되지 않은 값들을 제외하고 구문 생성
public class Plan {
	@Id
	@GeneratedValue(
			strategy=GenerationType.SEQUENCE,
			generator="PlanSequenceGenerator"
	)
	private int planNo;
	private String userId;
	private String planName;
	private LocalDateTime planRegdate = LocalDateTime.now();
	private int planViewCnt = 0;
	private String planDelete = "N";
	private String planGoal;
	private int planLikeCnt = 0;
	private LocalDate planStartDay;
	private LocalDate planEndDay;
	private String planColor;
	private int planCopy = 0;
	private LocalDate planDday;
	private String planImage;
	private int planMaincategoryNo;
	private int planStatus = 0;
	@Column
	@ColumnDefault("'Y'")
	private String planShare;
	private String planCalendar = "N";
}
