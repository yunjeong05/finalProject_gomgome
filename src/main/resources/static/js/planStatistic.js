$(function(){
	
	// statistic_day 초값 시간으로 변환
	var allSeconds = [];
	
	$("#statistic_day .userInfo").each(function(index,item) {
	
		allSeconds.push($(this).find('.userRecord').text())
		seconds = $(this).find('.userRecord').text()
		console.log(seconds)
		
		min = Math.floor(seconds/60);
		hour = Math.floor(min/60);
		sec = seconds%60;
		min = min%60;
		  
		var th = hour;
		var tm = min;
		var ts = sec;
		  
		if(th<10){
		  th = "0" + hour;
		}
		if(tm < 10){
		  tm = "0" + min;
		}
		if(ts < 10){
		  ts = "0" + sec;
		}
		$(this).find('.userRecord').text( th + ":" + tm + ":" + ts);
	})
	
	// statistic_total 초값 시간으로 변환

	$("#statistic_total .userInfo").each(function(index,item) {
	
		seconds = $(this).find('.userRecord').text()
		console.log(seconds)
		
		min = Math.floor(seconds/60);
		hour = Math.floor(min/60);
		sec = seconds%60;
		min = min%60;
		  
		var th = hour;
		var tm = min;
		var ts = sec;
		  
		if(th<10){
		  th = "0" + hour;
		}
		if(tm < 10){
		  tm = "0" + min;
		}
		if(ts < 10){
		  ts = "0" + sec;
		}
		$(this).find('.userRecord').text( th + ":" + tm + ":" + ts);
	})
	
	
})