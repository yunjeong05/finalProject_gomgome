$(function(){
	
    const swiper = new Swiper('.swiper', {
  // Optional parameters
  //direction: 'vertical',
  		slidesPerView: 3,
  		spaceBetween: 51,
  		slidesPerGroup: 3,

 	 	navigation: {
    		nextEl: '.swiper-button-next',
   			prevEl: '.swiper-button-prev',
	 	},
  	});
  	
  	//console.log(Number($(".selectCate").val()));
	// selectBox에 따른 플랜 내용 변경 		
  	$(".selectCate").on("change", function() {
		  if($(".selectCate").val()==0) {
			  $(".planCate_select").css("display", "none");
			  $(".planCate_main").css("display", "block");  
		  } else {		  
			  $(".planCate_select").css("display", "block");
			  $(".planCate_main").css("display", "none");
			  getPlanCate(0);
	  	  }
	 })
	 // selectBox의 플랜의 페이징 버튼 눌렀을 때 동작
	 $(document).on("click", ".pagination_button a", function(e) {
		 e.preventDefault();
		 const page = $(this).attr("href");
		 console.log(page);
		 getPlanCate(page);
	 })
	 
	 addLikeDelteLike($("#selectCate").val());
	 
	 
	 
	 
});

function getPlanCate(page) {
	$.ajax ({
		  url: "/planner/getPlanCate/" + $(".selectCate").val(),
		  type: "get",
		  data: {
			  page: page
		  },
		  success: function(obj) {
			  //$("#selectCate").val(obj.pageItems.content[0].planMaincategoryNo);
			  //console.log(obj)
			  //console.log(obj.item)
			  //console.log(obj.items[1].planNo);
			  //console.log(obj.pageItems);
			  console.log(obj.pageItems)
			  let htmlStr = "";
			  for(let i=0; i<obj.pageItems.content.length; i++) {
				  htmlStr += `
				  	<div class="item">
				  		<div class="plan_image" style="height: 70%;"><img src="/images/planCover.jpg" style="width: 100%"></div>
				  		<div>
					  		<div class="plan_content_title"><a href="/planner/updatePlanCnt/${obj.pageItems.content[i].planNo}"  
					  		onclick="window.open(this.href, '_blank', 'width=1100px, height=900px'); return false;">${obj.pageItems.content[i].planName}</a></div>
					  		<div class="plan_content_cnt_like">
						  		<div class="plan_content_cnt">조회수 ${obj.pageItems.content[i].planViewCnt}</div>
						  		<div class="plan_content_like_btn">
						  			<div class="plan_content_like_cnt">${obj.pageItems.content[i].planLikeCnt}</div>
						  			<img class="plan_content_like_img" name="${obj.pageItems.content[i].planNo}" data="${obj.pageItems.content[i].planNo}" src="/images/like_no.png" alt="하트자리">
						  		</div>
						  	</div>	
				  		</div>
				  	</div>
				  	`;		
			  }
			  //$("#sub_container").children().not(':first').remove();
			  //$("#sub_container").append(htmlStr);
			  $("#sub_container").html(htmlStr);
			  
			  htmlStr = "";
			  
			  let pageNumber = obj.pageItems.pageable.pageNumber;
			  let pageSize = obj.pageItems.pageable.pageSize;
			  let totalPages = obj.pageItems.totalPages;
			  let startPage = Math.floor(pageNumber / pageSize) * pageSize + 1;
			  let tempEndPage = startPage + pageSize - 1;
			  let endPage = tempEndPage > totalPages ? totalPages : tempEndPage;
			  
			  //console.log(pageNumber);
			  //console.log(pageSize);
			  //console.log(totalPages);
			  //console.log(startPage);
			  //console.log(tempEndPage);
			  //console.log(endPage);
			  
			  if(pageNumber + 1 > startPage) {
				  htmlStr += `
				  	<li class="pagination_button">
						<a href="${pageNumber - 1}">이전</a>
					</li>
				  `;
			  }
			  
			  for(let i = startPage; i <= endPage; i++) {
				  htmlStr += `
				  	<li class="pagination_button">
						<a href="${i - 1}">${i}</a>
					</li>
				  `;
			  }
			  
			  if(pageNumber + 1 < endPage) {
				  htmlStr += `
				  	<li class="pagination_button">
						<a href="${pageNumber + 1}">다음</a>
					</li>
				  `;
			  }
			  
			  $(".pagination").html(htmlStr);
			  
			  
			  addLikeDelteLike(obj.pageItems.content[0].planMaincategoryNo);
		  }
	  });
}

function addLikeDelteLike(selectCate) {
	
	if(userCheck == null || userCheck == 'anonymousUser') {
		$(".plan_content_like_img").attr("src", "/images/like_yes.png")
	} else {
	    // 좋아요한 플랜에 꽉찬하트 likeImgData=planNo
		var likeImgData = document.getElementsByClassName("plan_content_like_img");
		console.log(likePlanList);
		
		for(let i=0; i<likeImgData.length; i++){
			//console.log(likeImgData.length);
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
						
						 	$("#selectCate").val(selectCate);
						 	//location.reload();
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
	 
}
