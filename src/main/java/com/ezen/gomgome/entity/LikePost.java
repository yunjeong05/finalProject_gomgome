package com.ezen.gomgome.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="t_gomg_like_post")
@Data
@NoArgsConstructor//기본생성자
@AllArgsConstructor//모든 매개변수를 받는 생성자
@Builder//객체 생성
public class LikePost {
	@Id
	private String userId;
	private int postNo;
}
