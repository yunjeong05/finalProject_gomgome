package com.ezen.gomgome.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ezen.gomgome.entity.CustomUserDetails;
import com.ezen.gomgome.entity.User;
import com.ezen.gomgome.repository.UserRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println(username);
		//정보를 가져올 유저 객체 생성
		/*
		if(userRepository.findById(username).isEmpty()) {
			return null;
		} else {
			User user = userRepository.findById(username).get();
			
			return CustomUserDetails.builder()
									.user(user)
									.build();
		}
		*/
		if(userRepository.findById(username).isEmpty()){
			System.out.println("11111111111111111111111111");
			return null;
		} else {
			if(userRepository.findById(username).get().getUserDelete().equals("Y") ) {
				return null;
				
			} else {
				System.out.println("3333333333333333333333333");
				User user = userRepository.findById(username).get();
				
				return CustomUserDetails.builder()
										.user(user)
										.build();
			}
		
		}
	}
	
}
