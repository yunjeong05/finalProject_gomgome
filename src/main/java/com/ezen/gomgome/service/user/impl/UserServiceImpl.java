package com.ezen.gomgome.service.user.impl;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.groovy.util.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ezen.gomgome.common.CamelHashMap;
import com.ezen.gomgome.dto.UserDTO;
import com.ezen.gomgome.entity.User;
import com.ezen.gomgome.entity.UserCategory;
import com.ezen.gomgome.repository.UserCategoryRepository;
import com.ezen.gomgome.repository.UserRepository;
import com.ezen.gomgome.service.mail.MailService;
import com.ezen.gomgome.service.user.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserCategoryRepository userCategoryRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private MailService mailService;
	
	@Override
	public List<User> getUserOrderByStudyhourTotal() {
		return userRepository.findAll(Sort.by(Sort.Direction.DESC, "userStudyhourTotal"));
		//return userRepository.findAllOrderByUserStudyhourTotalDesc();
	}

	@Override
	public void join(User user, List<Integer> cateList) {
		// TODO Auto-generated method stub
		userRepository.save(user);
		
		userRepository.flush();
		
		for(int i = 0; i < cateList.size(); i++) {
			UserCategory userCate = UserCategory.builder()
												.userId(user.getUserId())
												.maincategoryNo(cateList.get(i))
												.build();
			
			userCategoryRepository.save(userCate);
			
			userCategoryRepository.flush();
		}
	}
	
	@Override
	public User idCheck(User user) { 
		if(!userRepository.findById(user.getUserId()).isEmpty())
			return userRepository.findById(user.getUserId()).get();
		else 
			return null;
	}
	
	@Override
	public User nicknameCheck(User user) { 
		if(!userRepository.findByUserNickname(user.getUserNickname()).isEmpty())
			return userRepository.findByUserNickname(user.getUserNickname()).get();
		else 
			return null;
	}

	@Override
	public boolean checkPw(User user) {
		// TODO Auto-generated method stub
		User userInfo = userRepository.findById(user.getUserId()).get(); //dbuser
		
		String dbPw = userInfo.getUserPw();
		System.out.println(dbPw);
		
	    // 비교 
		return passwordEncoder.matches(user.getUserPw(), dbPw);
		
	  
	}

	@Override
	public User findId(User user) {
		// TODO Auto-generated method stub
		if(userRepository.findByUserNameAndUserEmail(user.getUserName(), user.getUserEmail()).isEmpty()) {
			return null;
		} else {
			return userRepository.findByUserNameAndUserEmail(user.getUserName(), user.getUserEmail()).get();
		}
	}

	@Override
	public User getUserInfo(String userId) {
		// TODO Auto-generated method stub
		return userRepository.findById(userId).get();
	
	}

	@Override
	public List<CamelHashMap> getCateInfoList(String userId) {
		// TODO Auto-generated method stub
		return userCategoryRepository.getCateInfoList(userId);
	}

	@Override
	public void updateUserInfo(User user, List<Integer> cateList) {
		// TODO Auto-generated method stub     
		userRepository.save(user);

		userCategoryRepository.deleteByUserId(user.getUserId());
		userCategoryRepository.flush();
		//for문  cateLIst i  -> 
		for(int i= 0; i < cateList.size(); i++) {
			UserCategory usercate = UserCategory.builder()
                    .userId(user.getUserId())
                    .maincategoryNo(cateList.get(i))
                    .build();
			userCategoryRepository.save(usercate);
		}		
	}

	@Override
	public void findLoginPasswd(User userInfo) {
		// TODO Auto-generated method stub
		char[] charSet = new char[] 
	            { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
	            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
	             'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 
	            'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 
	            'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 
	            's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '!', '@', '#', 
	            '$', '%', '^', '&' }; 
	      
	      StringBuffer sb = new StringBuffer();
	      SecureRandom sr = new SecureRandom(); 
	      sr.setSeed(new Date().getTime()); 
	      int idx = 0; int len = charSet.length; 
	      for (int i=0; i<10; i++) { 
	         idx = sr.nextInt(len); // 강력한 난수를 발생시키기 위해 SecureRandom을 사용한다. 
	         sb.append(charSet[idx]); 
	      }

	      String tempLoginPasswd = sb.toString();
	      
	      //인증번호 발송
	      String mailTitle = userInfo.getUserName() + "님, 당신의 계정(" + userInfo.getUserId() + ")의 인증번호 입니다.";
	      String mailBody = "인증번호 : " + tempLoginPasswd;
	      mailService.send(userInfo.getUserEmail(), mailTitle, mailBody);
	      
	      //암호화된 인증번호 비밀번호로 설정
	      userRepository.saveTempPw(userInfo.getUserId(), passwordEncoder.encode(tempLoginPasswd));
	}

	@Override
	public int pwChange(User user) {
		// TODO Auto-generated method stub
		return userRepository.pwChange(user.getUserId(), user.getUserPw());
	}

	@Override
	public int deleteInfo(User user) {
		// TODO Auto-generated method stub
		return userRepository.deleteInfo(user.getUserId());
	}

	@Override
	public void updateTemPw(User user) {
		// TODO Auto-generated method stub
		char[] charSet = new char[] 
	            { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
	            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
	             'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 
	            'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 
	            'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 
	            's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '!', '@', '#', 
	            '$', '%', '^', '&' }; 
	      
	      StringBuffer sb = new StringBuffer();
	      SecureRandom sr = new SecureRandom(); 
	      sr.setSeed(new Date().getTime()); 
	      int idx = 0; int len = charSet.length; 
	      for (int i=0; i<10; i++) { 
	         idx = sr.nextInt(len); // 강력한 난수를 발생시키기 위해 SecureRandom을 사용한다. 
	         sb.append(charSet[idx]); 
	      }

	      String tempLoginPasswd = sb.toString();
	      
	      userRepository.saveTempPw(user.getUserId(), passwordEncoder.encode(tempLoginPasswd));
	      
	}

}
