

$(function() {
	
	console.log("************************");
	console.log(noteList);
	console.log("************************");

	for (let i = 0; i < noteList.length; i++) {
		let note = noteList[i];
		
		let noteNo = note.noteNo;
		let noteContent = note.noteContent;
		let noteReg = note.noteRegdate;
		
		let tmpStr = `
			<div class="eachNote">
				<img class="memoImg c-${noteNo}" src="/images/memo_beige.png" alt="메모 이미지 없음">
				<div class="eachContent c-${noteNo}">
					<input type="checkbox" style="zoom: 1.2;" value="${noteNo}">
					<span class="noteRegDate c-${noteNo}" style="float: right; margin-right: 6px;">
						${noteReg}
					</span>
					<hr style="clear: both;">
					<span class="noteContent c-${noteNo}">${noteContent}</span>
					<button class="btnDetail c-${noteNo}" type="button">+</button>
				</div>
			</div>
		`;
		$(".noteContainer").append(tmpStr);
	}
	
	$(".btnDetail").on("click", function() {
		
		
		let tmp = $(this).attr("class").split("-");
		let iden = tmp[1];
		
		
		$('.detailNote').css("display", "inline-block");
	      
	    $('.detailNote').draggable({
	        hadle: ".detailNote"
	    });

		$("#detailNoteNo").val(iden);
		$("#detailRegDate").html($(".noteRegDate.c-" + iden).text());
		$("#textArea").text("");
		$("#textArea").text($(".noteContent.c-" + iden).text());
	});

	$(".cancelDetail").on("click", function() {
		$('.detailNote').css("display", "none");
	});
	
	$(".btnDelete").on("click", function() {
		let iden = $("#detailNoteNo").val();
		
		$.ajax({
			url: '/mypage/deleteNoteAPI',
			type: 'delete',
			data: {noteNo: iden},
			success: function(obj) {
				console.log("success");
				location.reload();
			},
			error: function(e) {
				console.log(e);
			}
		});
	});
	
	$(".deleteNote").on("click", function() {
		let checkBox = $("input[type=checkbox]");
		let deleteList = new Array();
		for (let i = 0; i < checkBox.length; i++) {
			if (checkBox[i].checked == true) {
				console.log(checkBox[i].value);
				deleteList.push(checkBox[i].value);
			}
		}
		
		$.ajax({
			url: '/mypage/deleteNoteList',
			type: 'delete',
			data: {
				deleteList: deleteList
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
	
	$(".btnUpdate").on("click", function() {
		let iden = $("#detailNoteNo").val();
		let cont = $("#textArea").val();
		
		console.log(cont);
		
		let note = {
			noteNo: iden,
			noteContent: cont
		};
	
		$.ajax({
			url: '/mypage/updateNoteAPI',
			type: 'put',
			data: {note: JSON.stringify(note)},
			success: function(obj) {
				console.log("success");
				location.reload();
			},
			error: function(e) {
				console.log(e);
			}
		});

	});
	
});