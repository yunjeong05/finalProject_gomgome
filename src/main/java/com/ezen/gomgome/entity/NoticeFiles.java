package com.ezen.gomgome.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="T_gomg_FILE")
@Data
@IdClass(NoticeFileId.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeFiles {
	@Id
	@ManyToOne
	@JoinColumn(name="Notice_NO")
	private Notice notice;
	@Id
	private int noticeFileNo;
	private String noticeFileNm;
	private String noticeOriginFileNm;
	private String noticeFilePath;
	private LocalDateTime noticeFileRegdate = LocalDateTime.now();
	private String noticeFileCate;
	@Transient
	private String noticeFileStatus;
	@Transient
	private String newFileNm;
	public int length;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
