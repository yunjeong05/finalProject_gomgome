package com.ezen.gomgome.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="t_gomg_post")
@Data
@SequenceGenerator(
		name="PostSequenceGenerator",
		sequenceName="post_seq",
		initialValue=1,
		allocationSize=1
)
@NoArgsConstructor//기본생성자
@AllArgsConstructor//모든 매개변수를 받는 생성자
@Builder//객체 생성
@DynamicInsert //insert 구문이 생성될 때 null값인 컬럼은 배제하고 구문생성
@DynamicUpdate //update 변경되지 않은 값들을 제외하고 구문 생성
public class Post {
	@Id
	@GeneratedValue(
			strategy=GenerationType.SEQUENCE,
			generator="PostSequenceGenerator"
	)
	private int postNo;
	private String userId;
	private String postTitle;
	private String postContent;
	private int postCnt = 0;
	private LocalDateTime postRegdate = LocalDateTime.now();
	private int postLike = 0;
	private int maincategoryNo;
}
