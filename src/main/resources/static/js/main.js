$(function() {
	
	console.log("**********");
	console.log(likePlanList);
	console.log("**********");

	// 비회원 배너 스와이프 영역
	const swiper = new Swiper('.swiper-container', {
		//loop: true,
		autoplay: {
		  delay: 2500,
		  disableOnInteraction: false,
		},
		
		// If we need pagination
		pagination: {
		  el: '.index-wrapper',
		  type : 'fraction',
		},
		
		// Navigation arrows
		navigation: {
		  nextEl: '.control-next-button',
		  prevEl: '.control-previous-button',
		},
  	});
  	
	var menu = ['GOMGOME 😎','후기 모음','TOP  5 🌱']
	const pagingSwiper = new Swiper('.swiper-container', {
	    pagination: {
	      el: '.swiper-pagination-wrapper',
	      //type: 'progressbar'
	      clickable: true,
	          renderBullet: function (index, className) {
	            return '<span class="' + className + '">' + (menu[index]) + "</span>";
	      },
	    },
	});

	swiper.controller.control = pagingSwiper;
	pagingSwiper.controller.control = swiper;
	
	// 배너 일시정지, 재생 버튼
	$('.control-play-pause-button').on('click', function() {
		
	    if($('.control-play-pause-button').attr("value")=='stop') {
	      swiper.autoplay.stop();
	      $('.control-play-pause-button>svg').css("display", "none");
	      $('.fa-play').css("display", "block");
	      $('.control-play-pause-button').attr("value", "play")
	      console.log($('.control-play-pause-button').attr("value"))
	      
	    } else if($('.control-play-pause-button').attr("value")=='play') {
			console.log("??")
	      swiper.autoplay.start();
	      $('.fa-play').css("display", "none");
	      $('.control-play-pause-button>svg').css("display", "block");
	  	  $('.control-play-pause-button').attr("value", "stop");
	    }

  	});
  	
  	// 공통 플랜 스와이프 영역
	const main_content = new Swiper ('.swiper', {

	    slidesPerView: 4,
	    spaceBetween: 50,
	    slidesPerGroup: 4,
	
	    // pagination: {
	    //   el: '.swiper-pagination',
	    // },
	
	    navigation: {
	      nextEl: '.swiper-button-next',
	      prevEl: '.swiper-button-prev',
	   },
  	});
	
	if(userCheck == null || userCheck == 'anonymousUser') {
		$(".main_content").css("display" ,"block");
		$(".plan_content_like_img").attr("src", "/images/like_yes.png")
	} else {
		
		var achievement = $("#achievementRate").val();
		console.log(achievement)
		if (achievement == null || achievement == "") {			
			console.log("1")
			$("#achFront").css("width" , "0%");
		} else {
			$("#achFront").css("width" , achievement+'%');
			console.log("2")
		}
		
		
		
		
		if($("#achievementRate").val() != null) {
			
		}
		// 로그인 후 인기 플랜 보기
	    $("#main_plan_a").on("click", function() {
		    $("#likePlan_main").css("display", "none");
		    $(".main_content").css("display" ,"block");
		    $("#main_plan_a").css("font-weight", "600");
		    $("#main_like_plan_a").css("font-weight", "");
	    })
	    
	  	// 로그인 후 마음에 든 플랜 보기
	    $("#main_like_plan_a").on("click", function() {
			// 페이징
	    	//addPlanLikeListAndPage(pagelikePlanList)
		    $(".main_content").css("display" ,"none");
		    $("#likePlan_main").css("display", "block");
		    $("#main_like_plan_a").css("font-weight", "600");
		    $("#main_plan_a").css("font-weight", "");
	    })
	    
	    // 좋아요한 플랜에 꽉찬하트 likeImgData=planNo
		var likeImgData = document.getElementsByClassName("plan_content_like_img");
		
		
		//console.log(likeImgData[0].name);
		for(let i=0; i<likeImgData.length; i++){
		  for(let j=0; j<likePlanList.length; j++) {
			  if(likeImgData[i].name == likePlanList[j].planNo){
				  likeImgData[i].src = "/images/like_yes.png"
			  }
		  }
		}
		
		// 좋아요 및 취소
		
		  $(".plan_content_like_img").on("click", function(e) {
			  
			  var likeImg = $(this).attr("src");
			  if(likeImg == "/images/like_no.png") {
					$(this).attr("src", "/images/like_yes.png");
				  	console.log("data: " + $(this).attr("data"));
					$.ajax({
						url: '/mypage/likePlanAPI',
						type: 'post',
						data: {planNo: $(this).attr("data")},
						success: function(obj) {
							console.log("success");
							console.log(obj.items);
							console.log($(e.target).closest('.plan_content_like_cnt'));
							
						 	location.reload();
						},
						error: function(e) {
							console.log(e);
						}
					});
			  } else if(likeImg == "/images/like_yes.png" ) {
					$(this).attr("src", "/images/like_no.png");
					//console.log("data: " + $(this).attr("data"));
					$.ajax({
						url: '/mypage/cancelLikeAPI',
						type: 'delete',
						data: {planNo: $(this).attr("data")},
						success: function(obj) {
							console.log("success");
							//console.log($(e.target).closest('.item'));
							//$(e.target).closest('.item').remove();
							location.reload();
						},
						error: function(e) {
							console.log(e);
						}
					});
			  }
		   });	
			
	}

  
  	
  
});