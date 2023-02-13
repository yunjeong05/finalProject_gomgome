


$(function() {
	
	console.log("***************");
	console.log(likePlanList);
	console.log(categoryList);
	console.log(likeList);
	console.log("***************");
	

	
	for (let i = 0; i < likePlanList.length; i++) {
		let plan = likePlanList[i];
		
		let planType = categoryList[plan.planMaincategoryNo - 1].maincategoryName;
		let planName = plan.planName;
		let userName = plan.userId;
		let planStart = plan.planStartDay;
		let planEnd = plan.planEndDay;
		let planNo = plan.planNo;
		
		let tmpStr = `
			<div class="eachPlan c${planNo}">
				<nav class="imageBox">
					<img src="/images/planCover.jpg" alt="표지이미지">
					<input type="checkbox" value="${planNo}">
					<div class="imageText">${planType}</div>
				</nav>
				<div class="planTop"><span>제목: </span><span class="planTitle c-${planNo}">${planName}</span></div>
				<div class="planContent">
					<img src="/images/user.png" alt="유저이미지">
					<span class="userNickname">${userName}</span><br>
					<span class="planStart">${planStart}</span>
					<span> ~ </span>
					<span class="planEnd">${planEnd}</span>
				</div>
			</div>
		`;
		
		$(".planContainer").append(tmpStr);
	}
	
	$(".planTitle").on("click", function() {
		let tmp = $(this).attr("class").split("-");
		let iden = tmp[1];
		
		this.href = "/planner/plan/" + iden;
		window.open(this.href, '_blank', 'width=1100px, height=900px');
	});

	$(".deleteLike").on("click", function() {
		//console.log($("input[type=checkbox]").checked);
		let checkBox = $("input[type=checkbox]");
		let cancelList = new Array();
		for (let i = 0; i < checkBox.length; i++) {
			//console.log(checkBox[i].checked);
			if (checkBox[i].checked == true) {
				console.log(checkBox[i].value);
				cancelList.push(checkBox[i].value);
			}
		}
		
		$.ajax({
			url: '/mypage/cancelLikesAPI',
			type: 'delete',
			data: {
				cancelList: cancelList
			},
			success: function(obj) {
				console.log("success");
				location.reload();
			},
			error: function(e) {
				console.log(e);
			}
		});
	});
	
	$(".shareLike").on("click", function() {
		let checkBox = $("input[type=checkbox]");
		let cancelList = new Array();
		for (let i = 0; i < checkBox.length; i++) {
			//console.log(checkBox[i].checked);
			if (checkBox[i].checked == true) {
				console.log(checkBox[i].value);
				cancelList.push(checkBox[i].value);
			}
		}
		
		
	});
	
});