$(function() {
	
	let sumTime = 0;
	let todayHour = 0;
	let todayMinute = 0;
	
	console.log(planUsersList);
	
	scheduleList.forEach(function(item) {
		//console.log(item.scheduleEndTime.getTime() - item.scheduleStartTime.getTime());
		const date01 = new Date(item.scheduleEndTime);
		const date02 = new Date(item.scheduleStartTime);
		
		const subTime = (date01.getTime() - date02.getTime()) / (1000*60); 

		sumTime += subTime;
	});

	todayHour = parseInt(sumTime / 60);
	todayMinute = sumTime % 60;
	
	//$("#totalplanLeftStudyhour span").html(todayHour + "시 " + todayMinute + "분");

	console.log(userStudyhourTotal);
	todayHour = parseInt(userStudyhourTotal / 60);
	todayMinute = userStudyhourTotal % 60;
	$("#totalplanLeftTotalstudyhour span").html(todayHour + "시간 " + todayMinute + "분");
	
	
	console.log("rank: " + rankStudyhourTotal);
	$("#totalplanLeftRank span").html(rankStudyhourTotal + "위");
	
	planUsersList.forEach(function(item) {
		const planDday = new Date(item.planDday);
		const planName = item.planName;
		const now = Date.now();
		
		let subTime = parseInt((planDday.getTime() - now) / (1000*60*60*24));
		if (subTime >= 0) {
			const tmpDdayStr = `
					<div style="background-color: ${item.planColor}">
						<span class="ddayContentDday">D-${subTime}</span>
						<span class="ddayContentName">${item.planName}</span><br>
					</div>
				`;
			$("#totalplanRightDdayContent").append(tmpDdayStr);
		}
	});
	
	console.log(goalList.length);
	if (goalList.length == 0) {
		const tmpStr = `<div>달성한 목표 없음</div>`;
		$("#totalplanRightGoalContent").append(tmpStr);
		
	} else {
		goalList.forEach(function(item) {
			const planDday = new Date(item.planDday);
			const planGoal = item.planGoal;
			
			const tmpStr = `
					<div style="background-color: ${item.planColor}">
						<span class="goalContentDday">${item.planDday}</span> / 
						<span class="goalContentTitle">${item.planGoal}</span>
					<div>
				`;
			$("#totalplanRightGoalContent").append(tmpStr);
		});
	}
	
	console.log(closedList.length);
	if (closedList.length == 0) {
		const tmpStr = `<div>마감된 D-day 없음</div>`;
		$("#totalplanRightClosedContent").append(tmpStr);
		
	} else {
		closedList.forEach(function(item) {
			if (item.planGoal == null) {
				const planDday = new Date(item.planDday);
				const planClosed = item.planGoal;
				
				const tmpStr = `
						<div style="background-color: ${item.planColor}">
							<span class="closedContentDday"><input class="btnGoal" name="${item.planNo}_-_${item.planName}" type="button" value="달성 입력"></span>
							<span class="closedContentTitle">${item.planName}</span>
						<div>
					`;
				$("#totalplanRightClosedContent").append(tmpStr);
			}
			
		});
	}
	
	$(".btnGoal").click(function() {
		
		let arrayClass = $(this).attr("name").split("_-_");
		console.log(arrayClass);

		$('#updateGoal').css("display", "inline-block");
	      
	    $('#updateGoal').draggable({
	        hadle: "#updateGoal"
	    });
	    
	    $("input[name=planName]").val(arrayClass[1]);
	    $("input[name=planName]").attr("readonly", true);
	    
	    $("#goalPlanNo").val(arrayClass[0]);
	});
	$(".btnGoalCancel").on("click", function() {
		$('#updateGoal').css("display", "none");
	});
	
	$('.btnCategory').click(function() {
	      $('#addCategory').css("display", "inline-block");
	      
	      $('#addCategory').draggable({
	        hadle: "#addCategory"
	      });
	      
	      
	});
	
	$(".btnAddCategoryCancel").on("click", function() {
		$('#addCategory').css("display", "none");
	});
	
	$(".addCategoryLeft.select").on("change", function() {
		let selectValue = $(this).val();
		$("#addCategoryMain").val(selectValue);
		
	});
	
});