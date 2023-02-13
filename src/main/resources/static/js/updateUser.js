$(function(){
	var pwValidation = false; 
	$("#pwValidation").hide();
	
	/*비밀번호 유효성 검사*/
	function validatePassword(character){
		return /^(?=.*[a-zA-Z])(?=.*[!@#$%^&*])(?=.*[0-9]).{6,}$/.test(character);
	}
	
	/* 비밀번호 입력 될 때 마다 유효성 검사*/
	$("#userPw").on("change", function(){
		// 비밀번호 유효성 처리 
		if(!validatePassword($("#userPw").val())){
			pwValidation = false;
			$("#pwValidation").show();
			//$("#userPw").focus();
		} else {
			pwValidation = true;
			$("#pwValidation").hide();
		}
		
		// 비밀번호 확인까지 입력 후 다시 비밀번호 재설정 
		if($("#userPw").val() == $("#userPwCheck").val()){
			pwCheck = true; 
			$("#pwCheckResult").css("color", "green");
			$("#pwCheckResult").text("비밀번호가 일치합니다.");
		} else {
			pwCheck = false; 
			$("#pwCheckResult").css("color", "red");
			$("#pwCheckResult").text("비밀번호가 일치하지 않습니다.");
		}
	});
	
	/* 비밀번호 확인란 입력할 때 일치여부 체크*/
	$("#userPwCheck").on("change", function(){
		$("#pwCheckResult").show();
		
		if($("#userPw").val() == $("#userPwCheck").val()){
			pwCheck = true;
			$("#pwCheckResult").css("color", "green");
			$("#pwCheckResult").text("비밀번호가 일치합니다.");
		} else {
			pwCheck = false;
			$("#pwCheckResult").css("color", "red");
			$("#pwCheckResult").text("비밀번호가 일치하지 않습니다.");
		}
	});
	
	/* '비밀번호' 내용 보여주기 (눈 이미지 클릭했을 때 글자보여주기) */
	$("#update_img_pw_wrap01 img").on("click", function(){
		$("#userPw").toggleClass("active");

		console.log(document.getElementById("userPw").classList);
		
		if($("#userPw").hasClass("active")){
			$(this).parent().prev().attr("type", "text");
			$("#update_img01_eye02").hide();
			$("#update_img01_eye01").show();
		} else {
			$(this).parent().prev().attr("type", "password");
			$("#update_img01_eye01").hide();
			$("#update_img01_eye02").show();
		}
		
	});
	
	/* '비밀번호확인' 내용 보여주기 (눈 이미지 클릭했을 때 글자보여주기) */
	$("#update_img_pw_wrap02 img").on("click", function(){
		$("#userPwCheck").toggleClass("active");

		if($("#userPwCheck").hasClass("active")){
			$(this).parent().prev().attr("type", "text");
			$("#update_img02_eye02").hide();
			$("#update_img02_eye01").show();
		} else {
			$(this).parent().prev().attr("type", "password");
			$("#update_img02_eye01").hide();
			$("#update_img02_eye02").show();
		}
		
	});
	
	var now = new Date();
	var year = now.getFullYear();
	var mon = (now.getMonth()+1) > 9 ? "+(now.getMonth()+ 1" : '0'+(now.getMonth()+1);
	var day = (now.getDate()) > 9 ?  (now.getDate()) : '0'+(now.getDate());
	
	for(var i = 1900; i <= year ; i++ ){
		$("#year").append('<option value="' + i+ '">' + i + '</option>');
	}	
	
	for(var i = 1; i<=12; i++){
		var mm = i > 9 ? i : "0"+i;
		$("#month").append('<option value="' + mm + '">' + mm + '</option>');
	}
	
	for(var i= 1; i <= 31; i++){
		var dd 	= i > 9 ? i : "0"+i;
		$("#day").append('<option value="' + dd + '">' + dd + '</option>');
	}
	
	var birth = $("#userBirth").val();
	var birthArr = [];
	birthArr = birth.split('-');
	console.log(birthArr)
	
	/*
	var birthYear = birth.split(0,4);
    var birthMonth = birth.substr(5,7);
    console.log(birthYear);
    var birthDay = birth.substr(8,10);
	*/
	if($("#year").val() != null) {
		$("#year > option[value='"+birthArr[0]+"']").attr("selected", "true");	
	} else {
		$("#year > option[value='"+year+"']").attr("selected", "true");		
	}
	
	if($("#month").val() != null) {
		$("#month > option[value='"+birthArr[1]+"']").attr("selected", "true");
	} else {
		$("#month > option[value='"+mon+"']").attr("selected", "true");
	}
	
	if($("#day").val() != null){
		$("#day > option[value='"+birthArr[2]+"']").attr("selected", "true");	
	} else {
		$("#day > option[value='"+day+"']").attr("selected", "true");	
	}
	
	/* 전화번호 유효성 검사 - 숫자만 가능(- 없이, 최대 12글자, keyup) */
	// 맞지 않는 조건 식일 때 input 값 지우기 
	$("#userTel").on("change", function(){
		//$(this).val( $(this).val().replace(/[^0-9]/g, "").replace(/(^02|^0505|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})$/,"$1-$2-$3").replace("--", "-") );
		$(this).val( $(this).val().replace(/[^0-9]/g, "").replace(/(^02|^0504|^0505|^0\d{2})(\d+)?(\d{4})$/, '$1-$2-$3').replace("--", "-") );
		
	});
	
	/* 이메일 계정 유효성 검사 - 정규식 */
	function validateEmail01(character){
		return /^[\w!#$%&'*+/=?^_{|}~-]+(?:\.[\w!#$%&'*+/=?^_{|}~-]+)*$/g.test(character);
	}
			
	/* 이메일 계정 유효성 검사 */
	$("#str_email01").on("keyup", function(){ 
		if(!validateEmail01($("#str_email01").val())){
			checkEmail = false;
			$("#str_email01").val("");
		} else {
			checkEmail = true;
			$("#str_email01").val();
		}
		
	});

	/* 이메일 선택 readonly */
	if($("#selectEmail option:selected").val() == "1"){
		$("#str_email03").attr("readonly", true);
	}
	
	/* 이메일 주소 선택 */
	$("select[name=selectEmail]").on("change", function() {
		$("#str_email03").val("");
		if($("#selectEmail option:selected").val() == "2") {
			$("#str_email03").attr("readonly", false);						
				$("#str_email03").on("change", function() {
					$("#str_email03").val($("#str_email03").val().toLowerCase());						
					if($("#str_email03").val() == 'gmail.com' || $("#str_email03").val() == 'naver.com'|| $("#str_email03").val() == 'daum.net' 
					|| $("#str_email03").val() == 'nate.com' || $("#str_email03").val() == 'kakao.com') {
						$("#emailCheckResult").hide();
					} else {
						$("#str_email03").val("");
						$("#emailCheckResult").show();
					}
				});
		} else if($("#selectEmail option:selected").val() != "1" && $("#selectEmail option:selected").val() != "2") {
			$("#str_email03").val($("#selectEmail option:selected").val());
			$("#emailCheckResult").hide();						
		}
	});
	
	
	//체크박스 클릭 이벤트(상태값 담아주기)
	/*$("#input[name='maincategoryNo']").on("click", function() {
		if($(this).is(":checked")) {
			const cate = {
				cateStatus: "I",
				maincategoryNo: $(this).val()
			};
			
			cateArray.push(cate);
		} else {
			const cate = {
				cateStatus: "D",
				maincategoryNo: $(this).val()
			};
			
			cateArray.push(cate);
		}
	});*/
	
	/* 수정기능 */
	$("#btnUpdate").on("click", function(){
		/* 생년월일 정보 담기 */					
		$("#userBirthday").val($("#year").val() + '-' + $("#month").val() + '-' + $("#day").val());
		
		/* 관심사 정보 담기*/
		let cateArray = [];
		
		$("input:checkbox[name='maincategoryNo']").each(function() {
			if($(this).is(":checked"))
				cateArray.push($(this).val());
		});
		
		$("#cateArray").val(JSON.stringify(cateArray));
		
		/*이메일 정보 담기 */
		$("#emailArray").val($("#str_email01").val() + '@' + $("#str_email03").val());
		
		$.ajax({
			url:'/user/updateUserInfo',
			type:'post',
			data: $("#updateForm").serialize(),
			success: function(obj){
				console.log(obj);
				alert("수정되었습니다.");
				//$(".updateInfoModal").modal('show');
				//$(".modal-backdrop").removeClass('show');
			},
			error: function(e){
				console.log(e);
			}	
		});		
	}); 
	
	
	/* 탈퇴 기능 */
	$("#btnOut").on("click", function(){
		$.ajax({
			url:'/user/deleteInfo',
			type: 'post', 
			data: {
				userId: $("#userId").val(),
				userDelete: $("#userDelete").val()
			},	
			success: function(obj){
				console.log(obj);
				if(obj.item.deleteMsg == "1" ){
					alert("정말탈퇴하시겠습니까?");
					location.href="/user/loginView";
					
				} else {
					alert("탈퇴에 실패하몄습니다. 관리자에게 문의하세요.");
					return; // 함수를 끝 종료 시킴
				}
			},
			error: function(e){
				console.log(e);
			}
	
		});							
							
	});
	
	
});		