$(function() {
	$('.btnMemo').click(function() {
	      $('.content-note').css("display", "block");
	      
	      $('.content-note').draggable({
	        hadle: ".content-note"
	      });
	});
	$('.close').click(function() {
	  $(".content-note").css("display", "none");
	});
	
	$(".stopwatch").click(function() {
  	  stopwatch();
 	})
 	
 	$(".calendar").click(function() {
	  calendar();
	 })
	 
	 date = new Date();
	 year = date.getFullYear();
	 month = date.getMonth() + 1;
	 day = date.getDate();
	 $("#modal_date").html(year + "-" + month + "-" + day);
	 
	 $(".save_wrapper").on("click", function(){
		 
		 if($("#noteContent").val()==null || $("#noteContent").val()=="") {
			 alert("아무것도 입력되지 않았습니다.");
		 } else {
			 $.ajax ({
			 url: '/planner/insertNote',
			 type: 'post',
			 data: {noteContent: $("#noteContent").val()},
			 success: function(obj) {
				 console.log(obj);			 
				 alert("저장되었습니다.");
				 $("#noteContent").val("");
			 }
		 	});
	 	};
	 });
	 
	 $(".chatbot").on("click", function() {
		 $("#gomgomeChatbot").css("display", "block");
	 })
	 $(".chatbot-close").on("click", function() {
		 $("#gomgomeChatbot").css("display", "none");
	 })
	 
});

function stopwatch() {
  window.open("http://localhost:8050/planner/stopwatch", "stopwatch", "width=405, height=273, top=150, left=200, resizable=no, scrollbars=no");
}

function calendar() {
  window.open("http://localhost:8050/planner/calendar", "calendar", "width=510px, height=523px, top=150, left=200");
}