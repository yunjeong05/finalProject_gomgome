let colorFocusOn = "#B5A27F";
let colorUnFocus = "#F5F5F5";
				
$(function() {
	
	$("#btnStatistic").on("click", function(e) {
		//e.preventDefault();
		//location.reload("statistic_calendar");
		//$('statisticSub').toggleClass("displayStatistic");
		if($(".statisticSub").css("display") == "none") {
			$(".statisticSub").show();
			$("#btnStatistic").css("color", "black");
			$("#btnStatistic").css("backgroundColor", "#D8C6A6");
			//$("#containerMypage").load("/mypage/statistic_calendar.html");
			//$("#containerMypage").load("@{/mypage/statistic_calendar}");
			$("#containerContent").load("test.html");
			
		} else {
			$(".statisticSub").hide();
			$("#btnStatistic").css("color", "#e9e9e9");
			$("#btnStatistic").css("backgroundColor", "#B5A27F");
			

		}
	});
});