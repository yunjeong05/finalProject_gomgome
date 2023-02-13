package com.ezen.gomgome.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.gomgome.entity.CustomUserDetails;
import com.ezen.gomgome.entity.User;
import com.ezen.gomgome.oauth.provider.KakaoUserInfo;
import com.ezen.gomgome.oauth.provider.OAuth2UserInfo;
import com.ezen.gomgome.repository.UserRepository;

import lombok.Builder;

@Service
@Builder
public class Oauth2UserService extends DefaultOAuth2UserService {
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//DB접속하기 위해 
	@Autowired
	private UserRepository userRepository;
	
	//토큰 발행 후 사용자 정보에 대한 처리
	//소셜 로그인 버튼 클릭 -> 사용자 동의 창 -> 사용자 동의 후 로그인 완료 -> code값 리턴 ->
	//토큰수령 -> 토큰을 통해서 사용자 정보를 취득 -> loadUser 메소드가 자동 호출 -> 사용자 정보를 커스터마이징 
	@Override 
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
		System.out.println("1111111111111111111111111111"); // 이 부분을 타지 않을때 어플리케이션.프로퍼티스 token-uri주소 봐야함
		OAuth2User oAuth2User = super.loadUser(userRequest); //부모에 있는 노드 유저를 호출해서
		
		String userName = "";
		String providerId= "";
		
		OAuth2UserInfo oAuth2UserInfo = null;
		
		//소셜 카테고리 검증 
		if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
			oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
			providerId = oAuth2UserInfo.getProviderId();
			userName = oAuth2UserInfo.getName();
		} else {
			System.out.println("카카오 로그인만 지원합니다.");
		}
		
		String provider = oAuth2UserInfo.getProvider();
		
		//DB에 담을 userId = email
		String userId = oAuth2UserInfo.getEmail();
		String password = passwordEncoder.encode(oAuth2UserInfo.getName());
		String email = oAuth2UserInfo.getEmail();
		String role = "";	//role social 넣어서 일반유저와 차이점을 둘 수 있다-> 어디서 넣야할지 ..
		//사용자가 이미 소셜 로그인한 기록이 있는 지 검사할 객체
		User user;//엔티티 
		
		if(userRepository.findById(userId).isPresent()) {
			//이미 소셜 로그인한 기록이 존재하면 
			//정보를 user 엔티티에 담아줌 
			user = userRepository.findById(userId).get();
			user.setJoinYn("Y");
		} else {
			//소셜로그인한 기록이 없으면 null로 리턴하여 회원가입 처리
			user = null;
		}
			
		//회원가입처리 
		if(user == null) {
			user = User.builder()
					   .userId(userId)
					   .userPw(password)
					   .userEmail(email)
					   .userRole(role)
					   .joinYn("N")
					   .build();
			//추가 입력사항이 있으면 User엔티티를 가지고 
			//추가 입력 페이지로 이동 
			//추가 입력 사항이 입력된 후 회원가입 처리하면 된다. ex) 전화번호, 주소 등등
			//userRepository.save(user);// 1226 회원가입에서 세이브 처리하면 되니깐 삭제해야함
		}
		
		//SecurityContext에 인증정보 저장 
		return CustomUserDetails.builder()
								.user(user)
								.attributes(oAuth2User.getAttributes())
								.build();
		// 모델앤뷰로 리턴... 
	}	
}
