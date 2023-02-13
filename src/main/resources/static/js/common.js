
$(document).ready(function(){
	
	// console.log(pageNoticeList);
	$('.modalOpen').on('click', modalOpen);
	$('.modalClose').on('click', modalClose);
	$('.modal-container').on('click', function(e){
		if(!$('.modal-wrapper').has(e.target).length){
			$('html').removeClass('modalOn');
			$(this).fadeOut(0);
			$(this).delay(50).fadeOut(0);
			$(this).removeClass('show');
			$('html').css('overflow-y','auto');
		}
	});
	
	$("#insertForm").on("submit", function(e) {
		if($("#changeTest").val() == "") {
			e.preventDefault();
			alert("신고이유를 선택하세요.");
			return;
		}
	});
	
	$("#communityFile").on("change", function(e) {
		console.log(111111);
		
		const files = e.target.files;
	
		//변수로 받아온 파일들을 배열 형태로 변환
		const fileArr = Array.prototype.slice.call(files);
		
		//배열에 있는 파일들을 하나씩 꺼내서 처리
		for(f of fileArr) {
			let htmlStr = "";
			htmlStr += `<span>${f.name}</span>&nbsp;&nbsp;&nbsp;`;
			$("#fileNames").append(htmlStr);
		}
	});
});
function modalOpen(modalID){
	$('html').css('overflow-y','hidden');
	$('.modal-container').fadeOut(0);
	$('.modal-container').removeClass('show');
	$(this).data('modal') ? modalID = $(this).data('modal') : '';
	$('.modal-container.show').length > 0 ? $('#' + modalID).css({
		'z-index': $('.modal-container.show').css('z-index') + 1,
		'background': 'transparent'
	}) : '';
	$('#' + modalID).css('display', 'flex').focus();
	$('#' + modalID + ' .modal-wrapper').delay(100).fadeIn(100).focus();
	$('#' + modalID).addClass('show').focus();

	return false;
}
function modalClose(){
	$('html').css('overflow-y','auto');
	$(this).closest('.modal-container').fadeOut(0);
	$(this).closest('.modal-container').removeClass('show');
	
	
}


$(function() {
	$("#deleteBtn").on('click', function(e) {
		const commuType = $("#communityType").val();
		//e.preventDefault();
		console.log($("#communityNo").val());
		$.ajax({
			url: '/community/community',
			type: 'delete',
			data: {communityNo : $("#communityNo").val()},
			success: function() {
				alert("삭제되었습니다.");
				if (commuType == 1) {
					window.location.href="/community/prList";
				}
				else if(commuType == 2) {
					window.location.href="/community/howtoList";
				}
				else if(commuType == 3) {
					window.location.href="/community/bulletinList";
				}
			},
			error: function(e) {
				console.log(e);
			}
		});
	});
})

// 위제 삭제 부분 저렇게 하면 window.location.href="/community/bulletinList"; 삭제 후에 무조건 자유게시판 목록으로 가서 이거 해결해야할듯 ㅠㅠ

function closePop() {
 document.getElementById("modalPop").style.display = "none";
}
      
      
      