package com.ezen.gomgome.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="t_gomg_like_plan")
@Data
@IdClass(LikePlanId.class)
@NoArgsConstructor//기본생성자
@AllArgsConstructor//모든 매개변수를 받는 생성자
@Builder//객체 생성
public class LikePlan {
	@Id
	@Column(name="userId")
	private String userId;
	@Id
	@Column(name="planNo")
	private int planNo;
	private int copy;
}
