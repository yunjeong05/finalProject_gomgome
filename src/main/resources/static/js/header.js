/* 전역변수 선언 및 isAdminPage 선언
const url = new URL(window.location.href);
					
const urlParams = url.searchParams;

let isAdminPage;

if(urlParams.get("isAdminPage")){
	isAdminPage = urlParams.get("isAdminPage");
}
*/
$(function(){	
	//돋보기 클릭 시 검색 정보 submit
	$("#searchImg").on("click", function(){
		$("#searchForm").submit();
	});

	/* 호버효과 */
 	$('.header-hover-section').hover(function(){
       $("#bottom_header").css('display','block');   
             
    }, function() {
      $("#bottom_header").css('display','none');
   });
   
   /* 곰곰이 로고 클릭 시 메인페이지 이동 */
   $("#logoImg").on("click", function(){
	   window.location.href = "/planner/planMain";
   });
   
   /* 회원가입 이동 */
   $("#btnJoin").on("click", function() {
		window.location.href = "/user/joinTypeView";
	});
	
	/* 로그인 이동 */
	$("#btnLogin").on("click", function() {
		window.location.href = "/user/loginView";
	});
	
	/* 관리자 페이지 선택 시 관리자 헤더로 변경  -> 헤더 2개로 만들기로 함  
	$("#h_admin").on("click", function(e){
		e.preventDefault();
		$("#h_section04").hide();
		$("#h_section07").show();
		$("#bottom_header").hide();
		
		isAdminPage = 'Y';
		
		location.href="/user/loginView?isAdminPage=Y";
		
		//$("#h_section04").css("display", "none");
		//$("#h_section07").css("display", "block");
	});	
 	
 	if(isAdminPage == 'Y') {
		$("#h_section04").hide();
		$("#h_section07").show();
		$("#bottom_header").hide();
	 } else {
		$("#h_section04").show();
		$("#h_section07").hide();
	 }
	*/
	
	/* 로그아웃 모달창 띄우기 */
	$("#h_userImg").on("click", function(){
		$(".gomgomeLogout").modal('show');
		$(".modal-backdrop").removeClass("show");
		$.ajax({
			url:'/planner/getTime',
			type:'get',
			success: function(obj){
				console.log(obj.item);
				//$(".logout_count").text(obj.item);
				if(obj.item != 0) {
					seconds = obj.item
		      
				    min = Math.floor(seconds/60);
				    hour = Math.floor(min/60);
				    min = min%60;
				        
				    var th = hour;
				    var tm = min;
				        
				    if(th<10){
				      th = "0" + hour;
				    }
				    if(tm < 10){
				      tm = "0" + min;
				    }
				    
					$(".logout_count").text( th + ":" + tm);
					
				} else {
					$(".logout_count").text( '00' + ":" + '00');
				}

			},error: function(e){
				console.log(e);
			}
		});
	});
	
	/* 검색창 작성 후 클릭 */
	$("#h_search_img").on("click", function(){
		$("#searchForm").submit();
	});
});