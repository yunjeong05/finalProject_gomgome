package com.ezen.gomgome.service.admin;

import java.util.List;

import com.ezen.gomgome.entity.NoticeFiles;
import com.ezen.gomgome.entity.Notice;

public interface AdminService {

	void insertNotice(Notice notice, List<NoticeFiles> uploadFileList);

}
