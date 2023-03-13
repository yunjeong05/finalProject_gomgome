# finalProject_gomgome 
<br>![GOMGOME 시현](https://user-images.githubusercontent.com/110507768/224604884-d7e10b3b-1d23-4e3a-9c12-47e4b013944a.gif)
<h3>Gomgome, 일상을 계획하고 공유 플래너 사이트</h3>
<p>시험부터 운동까지 사용자들이 서로의 계획을 공유하여 목표를 보다 성공적으로 달성할 수 있도록 플래너 기능을 제공하고 있습니다.</p>



<h2>:calendar: 프로젝트 기간</h2>
2022.12.05 ~ 2023.01.20 (46일)
<br>

<h2>:hammer_and_pick: Tools</h2>
<div align="center">
   <img src="https://img.shields.io/badge/Spring-6DB33F?style=flat&logo=Spring&logoColor=white"/>
   <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=MySQL&logoColor=white"/>
   <img src="https://img.shields.io/badge/Visual Studio Code-007ACC?style=flat&logo=Visual Studio Code&logoColor=white"/>
   <img src="https://img.shields.io/badge/GitHub-181717?style=flat&logo=GitHub&logoColor=white"/>
   <img src="https://img.shields.io/badge/Apache Tomcat-F8DC75?style=flat&logo=Apache Tomcat&logoColor=white"/>
   <img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=flat&logo=Thymeleaf&logoColor=white"/>
</div>
<br>

<h2>:books: Skills</h2>
<div align="center">
   <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
   <img src="https://img.shields.io/badge/HTML5-E34F26?style=flat&logo=HTML5&logoColor=white"/>
   <img src="https://img.shields.io/badge/CSS3-1572B6?style=flat&logo=CSS3&logoColor=white"/>
   <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=flat&logo=JavaScript&logoColor=white"/>
   <img src="https://img.shields.io/badge/jQuery-0769AD?style=flat&logo=jQuery&logoColor=white"/>
</div>
<br>

<h2>:globe_with_meridians: 오픈 API</h2>
<li>Kakao 로그인 api</li>
<br>

<h2>:sound: 프로젝트 설명</h2>
<li>Spring Boot를 사용한 프로젝트입니다.</li>
<li>일정을 등록, 삭제, 복사 기능이 가능하고 다른 사람의 일정을 복사하여 본인의 일정에 추가할 수 있습니다.</li>
<li>챗봇 기능으로 스톱워치, 미니 달력으로 시간 측정 할 수 있고 본인의 계획을 간략히 볼 수 있습니다.</li>
<li>부가적으로 비밀번호를 잃어버렸을 경우 이메일로 임시 인증번호를 발급해주고 3분 내 인증번호 확인 후 비밀번호 변경 기능을 구현하였습니다.</li>
<br>

# 👩‍💻 담당 구현부분
<h2>회원 관련 페이지</h2>
<h4>회원가입, 로그인, 로그아웃, 내정보수정, 회원탈퇴, 아이디/비밀번호 찾기, 커뮤니티 관련 통합검색</h4>
<li><strong>카카오 api</strong>을 사용해 회원가입 구현</li>
<li>SecurityConfiguration으로 로그인/로그아웃(modal) 구현</li>
<li>회원정보 <strong>수정 시, ajax를 이용해 백단에 전송</strong></li>
<li>회원 <strong>탈퇴 시, ajax를 이용해 백단에 전송</strong></li>
<li>아이디/비밀번호 <strong>찾기 시, ajax를 이용해 백단에 전송</strong></li>
<li>비밀번호 찾기 시, <strong>인증번호 발송 기능 구현</strong></li> 
<li>searchCondition, searchKeyword 속성을 클래스에 추가하여 <strong>검색기능 구현</strong></li>
