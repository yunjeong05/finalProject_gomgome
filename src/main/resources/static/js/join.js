$(function(){
	/*유효성 검사(아이디, 비밀번호, 닉네임, 이메일, 전화번호)*/
	var checkId = false; 
	var pwValidation = false; 
	var pwCheck = false; 
	var checkNickname = false;
	var checkEmail = false;
	var blankId = false;	
	var blankNickname = false;
	
	$("#pwValidation").hide();
	$("#pwCheckResult").hide();
	$("#nicknameValidation").hide();
	$("#telValidation").hide();
	$("#idValidation").hide();
	
	/* 아이디 유효성 검사 - 정규식 */
	function validateId(character){
		return /^(?=.*[a-zA-Z])(?=.*[0-9])|(?=.*[@,.]).{6,}$/.test(character);
	}
	
	/* 아이디 유효성 검사*/
	$("#userId").on("keyup", function(){
		if(!validateId($("#userId").val())){
			blankId = false;
			$("#idValidation").show();
			$("#userId").focus();
			$("#btnIdCheck").attr("disabled", true);
		} else {
			blankId = true;
			$("#idValidation").hide();
			$("#btnIdCheck").attr("disabled", false);
		} 
	
	});
	
	/* id 중복체크 */
	$("#btnIdCheck").on("click", function(){
		console.log($("#userId").val());
		console.log(validateId($("#userId").val()));
		if($("#userId").val() != "" && validateId($("#userId").val())){
			$("#idValidation").hide();
			$("#btnIdCheck").attr("disabled", false);
			$.ajax({
				url:"/user/idCheck",
				type: "post",
				data: $("#joinForm").serialize(),
				success: function(obj){
					console.log(obj);
					
					if(obj.item.msg == 'duplicatedId'){
						checkId=false;
						alert("중복된 아이디 입니다.");
						$("#userId").focus();
					} else {
						if(confirm("사용가능한 아이디 입니다." + $("#userId").val()+
						"를(을) 사용하시겠습니까?")){
							checkId = true;
							$("#btnIdCheck").attr("disabled", true);
						}
					}
				},
				error: function(e){
					console.log(e);
				}	
			})
			
		} else{
			alert("아이디는 형식에 맞게 작성해주세요");
			$("#userId").focus();
			$("#idValidation").show();
			$("#btnIdCheck").attr("disabled", true);
		}
		
	});
	
	
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
	$("#join_img_pw_wrap01 img").on("click", function(){
		$("#userPw").toggleClass("active");

		console.log(document.getElementById("userPw").classList);
		
		if($("#userPw").hasClass("active")){
			$(this).parent().prev().attr("type", "text");
			$("#join_img01_eye02").hide();
			$("#join_img01_eye01").show();
		} else {
			$(this).parent().prev().attr("type", "password");
			$("#join_img01_eye01").hide();
			$("#join_img01_eye02").show();
		}
		
	});
	
	/* '비밀번호확인' 내용 보여주기 (눈 이미지 클릭했을 때 글자보여주기) */
	$("#join_img_pw_wrap02 img").on("click", function(){
		$("#userPwCheck").toggleClass("active");

		if($("#userPwCheck").hasClass("active")){
			$(this).parent().prev().attr("type", "text");
			$("#join_img02_eye02").hide();
			$("#join_img02_eye01").show();
		} else {
			$(this).parent().prev().attr("type", "password");
			$("#join_img02_eye01").hide();
			$("#join_img02_eye02").show();
		}
		
	});

	/* 닉네임 유효성 검사 - 정규식 */
	function validateNickname(character){
		return /^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9|]{3,}$/.test(character);
	}
	
	/* 닉네임 유효성 검사*/
	$("#userNickname").on("keyup", function(){
		if($(this).val() != ""){
			if(!validateNickname($("#userNickname").val())){
				blankNickname = false;
				$("#nicknameValidation").show();
				$("#userNickname").focus();
				$("#btnNicknameCheck").attr("disabled", true);
			} else {
				blankNickname = true;
				$("#nicknameValidation").hide();
				$("#btnNicknameCheck").attr("disabled", false);
			}
		} else {
			checkNickname = false;
		}
		
	});
	
    /* 닉네임 중복체크 */
    $("#btnNicknameCheck").on("click", function(){
		if(blankNickname){
			$.ajax({
			url:"/user/nicknameCheck",
				type:"post",
				data: $("#joinForm").serialize(),
				success: function(obj){
					console.log(obj);
					if(obj.item.msg == 'dulicatedNickName'){
						checkNickname = false;										
						alert("중복된 닉네임입니다.");
						$("#userNickname").focus();
					} else {
						if(confirm("사용가능한 닉네임입니다." + $("#userNickname").val() + "를(을) 사용하시겠습니까?")){
							checkNickname = true;
							$("#btnNicknameCheck").attr("disabled", true);
						}
					}
				}, 
				error: function(e){
					console.log(e);
				}
			});
		} else {
			alert("닉네임은 형식에 맞게 작성해주세요");
			$("#userNickname").focus();
		}
		
	});
	
	/* 생년월일 */
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
	
	$("#year > option[value='"+year+"']").attr("selected", "true");
	$("#month > option[value='"+mon+"']").attr("selected", "true");
	$("#day > option[value='"+day+"']").attr("selected", "true");
	
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
    
	/* 회원가입 관심사 페이지 이동 */
	$("#BtnjoinNext").on("click", function(){
        $("#join_box").hide(0,function(){
          $("#total_join_interest").show(0);
          $("#btns_join").show(0);
        });
    });

	/* 관심사 hover 효과 */
    $("#join_interest label").hover(
      function(){
        console.log($(this).text());
        if($(this).text() == "시험" ){
          $("#join_span_box01").css('opacity','1');
          $(this).parent().css('opacity','0');              
        } else if($(this).text() == "자기개발" ){
          $("#join_span_box02").css('opacity','1');
          $(this).parent().css('opacity','0');   
        } else if($(this).text() == "취미" ){
          $("#join_span_box03").css('opacity','1');
          $(this).parent().css('opacity','0');   
        } else if($(this).text() == "운동" ){
          $("#join_span_box04").css('opacity','1');
          $(this).parent().css('opacity','0');   
        }
      },
      function() {
        if($(this).text() == "시험" ){
          $("#join_span_box01").css('opacity','0');
          $(this).parent().css('opacity','1');   
        } else if($(this).text() == "자기개발" ){
          $("#join_span_box02").css('opacity','0');
          $(this).parent().css('opacity','1');
        } else if($(this).text() == "취미" ){
          $("#join_span_box03").css('opacity','0');
          $(this).parent().css('opacity','1');
        } else if($(this).text() == "운동" ){
          $("#join_span_box04").css('opacity','0');
          $(this).parent().css('opacity','1');
        }
      }
    );
	
	/* 관심사 체크박스 효과 */
    $("input:checkbox[name='maincategoryNo']").on("change", function() {
      if($(this).is(":checked")) {
        console.log($(this));
        $(this).parent().css('opacity','1');
        $(this).parent().next().css('opacity','0');
        $(this).next().css("background", "#D8C6A6").css("color","#FFF");
      } else {
        $(this).parent().css('opactiy','0');
        $(this).parent().next().css('opacity','1');
        $(this).next().css("background", "#FFF").css("color", "#333333");
      }
    });
    
    
    /*'이전으로 버튼 클릭 시 화면 전환*/
    $("#BtnJoinBofore").on("click", function(){
		 $("#total_join_interest").hide(0, function(){
			$("#join_box").show(0);
		 });
	});
	
	function viewChange(){
		$("#join_box").show(0);
		$("#total_join_interest").hide(0);
	}
	
	$("#joinForm").on("submit", function(e) {	
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

		if(!checkId){
			e.preventDefault();
			alert("아이디 중복체크를 진행하세요.");
			viewChange();
			$("#userId").focus(); 
			return; 
		}
		
		if(!pwValidation) {
			e.preventDefault();
			alert("비밀번호가 형식에 맞지 않습니다.");
			viewChange();
			$("#userPw").focus();
			return; 
		} else {
			if($("#userPw").val() == "" || $("#userPwCheck").val() == "") {
				e.preventDefault();
				alert("비밀번호를 입력해주세요.");
				viewChange();
				$("#userPw").focus();
				return; 
			}
		}
		
		if(!pwCheck){
			e.preventDefault();
			alert("비밀번호가 일치하지 않습니다.");
			viewChange();
			$("#userPwCheck").focus();
			return; 
		}
		
		
		if($("#userName").val() == ""){
			e.preventDefault();
			alert("이름을 작성해주세요.");
			viewChange();
			$("#userName").focus();
			return;
		}
		
		if(!checkNickname){
			e.preventDefault();
			alert("닉네임 중복체크를 진행하세요.");
			viewChange();
			$("#userNickname").focus();
			return;
		}
		
		if($("#str_email01").val() == ""){
			e.preventDefault();
			alert("이메일 계정을 입력해주세요.");
			viewChange();
			$("#str_email01").focus();
			return;
		}
		
		if($("#str_email03").val() == ""){
			e.preventDefault();
			alert("도메인을 작성해주세요.");
			viewChange();
			$("#str_email03").focus();
			return;
		}
	
		if($("input[type=checkbox][name='maincategoryNo']:checked").length == 0){
			e.preventDefault();
			alert("관심사 1개이상 선택해주세요.");
			return;
		}
		
		$("#joinText").text(`가입을 축하드립니다.`);
        $(".joinSuccessModal").modal("show");
        $(".modal-backdrop").removeClass("show");

	});		
});