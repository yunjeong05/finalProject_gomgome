package com.ezen.gomgome.service.user;

import java.util.List;
import java.util.Map;

import com.ezen.gomgome.common.CamelHashMap;
import com.ezen.gomgome.dto.UserDTO;
import com.ezen.gomgome.entity.User;

public interface UserService {
	List<User> getUserOrderByStudyhourTotal();

	void join(User user, List<Integer> cateList);

	User idCheck(User user);

	User nicknameCheck(User user);

	boolean checkPw(User user);
	
	User findId(User user);

	User getUserInfo(String userId);

	List<CamelHashMap> getCateInfoList(String userId);

	void updateUserInfo(User user, List<Integer> cateList);

	void findLoginPasswd(User userInfo);

	int pwChange(User user);

	int deleteInfo(User user);

	void updateTemPw(User user);
}
