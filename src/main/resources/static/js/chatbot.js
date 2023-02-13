$(function(){
	var stompClient = null;

	function setConnected(connected) {
	    $("#connect").prop("disabled", connected);
	    $("#disconnect").prop("disabled", !connected);
	    $("#send").prop("disabled", !connected);
	    if (connected) {
	        $("#conversation").show();
	    }
	    else {
	        $("#conversation").hide();
	    }
	    $("#msg").html("");
	}
	
	function connect() {
	    var socket = new SockJS('/ws');
	    stompClient = Stomp.over(socket);
	    stompClient.connect({}, function (frame) {
	        setConnected(true);
	        console.log('Connected: ' + frame);
	        stompClient.subscribe('/topic/public', function (message) {
				console.log(message);
	            showMessage(message.body); //서버에 메시지 전달 후 리턴받는 메시지
	        });
	        
	        //stompClient.send("/app/sendMessage", {}, JSON.stringify("welcome"));
	    });
	}
	
	function disconnect() {
	    if (stompClient !== null) {
	        stompClient.disconnect();
	    }
	    setConnected(false);
	    console.log("Disconnected");
	}
	
	function sendMessage() {
	    let message = $("#msg").val()
	    showMessage(message);
	
	    stompClient.send("/app/sendMessage", {}, JSON.stringify(message)); //서버에 보낼 메시지
	}
	
	function showMessage(message) {
		if($("#msg").val()){
		    $("#communicate").append("<div align='right' id='sendMsg'>" + message + "<div>");
		} else {
		    $("#communicate").append("<div align='left' id='recieveMsg'>" + message + "<div>");
		}
	}
	
	$(function () {
	    $(".form-inline").on('submit', function (e) {
	        e.preventDefault();
		    $("#msg").val("");
	    });
	    $( ".chatbot" ).click(function() { connect(); });
	    $( ".chatbot-close" ).click(function() { disconnect(); });
	    $( "#send" ).click(function() { sendMessage(); });
	});
});