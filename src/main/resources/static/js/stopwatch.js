$(function() {
	
	var time = 0;
	
  	$.ajax({
		  url: '/planner/getTime',  
		  type: 'get',
		  success: function(obj) {
			  time = obj.item;
			  init();
		}
  	});
  
  	var starFlag = true;
  
 	buttonEvt();
  

 	function init(){ 
		 var hour = 0;
		 var min = 0;
		 var sec = 0;
		 
		 min = Math.floor(time/60);
		 hour = Math.floor(min/60);
		 sec = time%60;
		 min = min%60;
		 
		 console.log(time);
		 console.log(sec);
		 
		 var th = hour;
		 var tm = min;
		 var ts = sec;
		  
		 if(th<10) {
			 th = "0" + hour;
		 }
  		 if(tm < 10){
			 tm = "0" + min;
   		 }
  		 if(ts < 10){
			 ts = "0" + sec;
		 }
		 
		 document.getElementById("time").innerHTML = "&emsp;" + th + ":" + tm +":" + ts;
  	}

  	function buttonEvt(){
		  var hour = 0;
		  var min = 0;
		  var sec = 0;
		  var timer;
		  
		  $(".play").click(function(){

			  if(starFlag){
				  $(".btn").css("color","#FAED7D")
				  this.style.color = "#4C4C4C";
				  starFlag = false;

				  timer = setInterval(function(){
					  time++;
					  min = Math.floor(time/60);
					  hour = Math.floor(min/60);
					  sec = time%60;
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
          			  
          		  	  document.getElementById("time").innerHTML = "&emsp;"+ th + ":" + tm + ":" + ts;
          		  }, 1000);
      			}

      	});

      	// pause btn
      	$(".pause").click(function(){
			  console.log("스탑워치"+$("#time").text());
			  
			  if(time != 0){
				  $(".btn").css("color","#FAED7D")
				  $("#playImg").filter("opacity","0.5")
				  $("#playImg").filter("drop-shadow","0 0 0 green")
				  this.style.color = "#4C4C4C";
				  
				  $.ajax({
					  url: '/planner/pause',
					  type: 'post',
					  data: {time: time},
					  success: function(obj){
						  console.log(obj);
					  },
		  		  });
		  		  
		  		  clearInterval(timer);
          		  starFlag = true;
      		  }
      	});

      	// stop btn
      	$(".stop").click(function(){
			  if(time != 0){
				  $(".btn").css("color","#FAED7D")
				  this.style.color = "#4C4C4C";
				  clearInterval(timer);
				  starFlag = true;
				  time = 0;
				  init();
				  
				  $.ajax({
					  url: '/planner/pause',
					  type: 'post',
					  data: {time: time},
					  success: function(obj){
						  console.log(obj);
					  },
		  		  });
      		  };
    	});
};
  
  
 });