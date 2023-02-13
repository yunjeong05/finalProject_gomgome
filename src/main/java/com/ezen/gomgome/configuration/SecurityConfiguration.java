package com.ezen.gomgome.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.ezen.gomgome.handler.LoginFailureHandler;
import com.ezen.gomgome.oauth.Oauth2UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
   @Autowired
   private LoginFailureHandler loginFailureHandler;
   
   @Autowired
   private Oauth2UserService oauth2UserService;
   
   @Bean 
   public static PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }
   @Bean 
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

      //로그인 안해도 모든 사람 접근 가능 
      http.authorizeRequests().antMatchers("/").permitAll()
                           .antMatchers("/css/**").permitAll()
                           .antMatchers("/js/**").permitAll()
                           .antMatchers("/images/**").permitAll()
                           .antMatchers("/upload/**").permitAll()
                           .antMatchers("/user/joinTypeView").permitAll()
                           .antMatchers("/user/joinView").permitAll()
                           .antMatchers("/user/join").permitAll()
                           .antMatchers("/user/login").permitAll()
                           .antMatchers("/user/loginView").permitAll()
                           .antMatchers("/user/findIdView/**").permitAll()
                           .antMatchers("/user/findId").permitAll()
                           .antMatchers("/user/idCheck").permitAll()
                           .antMatchers("/user/loginProc").permitAll()
                           .antMatchers("/user/nicknameCheck").permitAll()
                           //.antMatchers("/user/updateView").permitAll()
                           //.antMatchers("/user/updateUserInfo").permitAll()
                           //.antMatchers("/user/updateUser").permitAll()
                           .antMatchers("/user/findPw").permitAll()
                           .antMatchers("/user/searchPw").permitAll()
                           .antMatchers("/user/checkTemPw").permitAll()
                           .antMatchers("/user/pwChangeView").permitAll()
                           .antMatchers("/user/pwChange").permitAll()
                           .antMatchers("/user/updateTemPw").permitAll()
                           //.antMatchers("/user/deleteInfo").permitAll()
                           .antMatchers("/planner/planMain").permitAll()
                           .antMatchers("/planner/plan/**").permitAll()
                           .antMatchers("/planner/updatePlanCnt/**").permitAll()
                           .antMatchers("/planner/getPlanCateList").permitAll()
                           .antMatchers("/planner/getStatistic").permitAll()
                           .antMatchers("/community/getSearchList").permitAll()  // 헤더 검색창
                           .antMatchers("/planner/getPlanCate/**").permitAll()
                           .antMatchers("/community/prList").permitAll()                           
                           .antMatchers("/community/howtoList").permitAll()                           
                           .antMatchers("/community/bulletinList").permitAll()                           
                           .antMatchers("/customercenter/faqlist").permitAll()      
                           .antMatchers("/customercenter/noticeList").permitAll()      
                           .antMatchers("/customercenter/inquirylist").permitAll()      
                           // 소셜로그인 클릭 후 회원 가입 페이지 이동 시 init.css가 안먹을 때 작성해주었음
                           .antMatchers("/oauth2/authorization/kakao").permitAll()
                           .antMatchers("/topic/public").permitAll()
                           .antMatchers("/app/sendMessage").permitAll()
                           .antMatchers("/ws/**").permitAll()
                           .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                           //위에 설정하는 요청 주소 제외한 나머지 요청 리소스는 인증된 사용자만 접근가능. 
                           .anyRequest().authenticated();
      
      //로그인, 로그아웃 설정 
      http.formLogin()
          .loginPage("/user/loginView")
          .usernameParameter("userId")
         .passwordParameter("userPw")
         .loginProcessingUrl("/user/loginProc")
         .defaultSuccessUrl("/planner/planMain")
          .failureHandler(loginFailureHandler) 
          .and()
          .oauth2Login()
          .loginPage("/user/loginView")
          //socialLogion 메소드 생성
          .defaultSuccessUrl("/user/socialLogin")
          .userInfoEndpoint()
          .userService(oauth2UserService);
      http.logout()
         .logoutUrl("/user/logout")
         .invalidateHttpSession(true)
         //.logoutSuccessUrl("/user/loginView");
         .logoutSuccessUrl("/planner/planMain");
      
      //기본으로 크로스도메인 방지
      http.csrf().disable();
         
      return http.build(); 
   }
}