package com.ezen.gomgome.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class NoticeFileId implements Serializable {
	private int notice;
	private int noticeFileNo;
}
