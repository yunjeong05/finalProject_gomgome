package com.ezen.gomgome.dto;

import java.time.LocalDateTime;

import javax.persistence.Id;
import javax.persistence.Transient;

import com.ezen.gomgome.entity.Notice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeFilesDTO {
	private int noticeNo;
	private int noticeFileNo;
	private String noticeFileNm;
	private String noticeOriginFileNm;
	private String noticeFilePath;
	private String noticeFileRegdate;
	private String noticeFileCate;
	private String noticeFileStatus;
	private String newFileNm;
	
	
	
	
	
	
}
