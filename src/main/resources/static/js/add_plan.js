



$(function() {
	
	$("html").click(function() {
		/*
		console.log("id: " + $(e.target).id);
		if($(e.target).id !== "newScheduleByDrag") {
			$("#newScheduleByDrag").hide();
		}
		*/
		$("#addSchedule").hide();
		$("#focusSchedule").hide();
	});
	
	console.log("============");
	console.log(otherList);
	console.log(scheduleList);
	console.log("============");
	
	let cData = new Array();
	for (let l in otherList) {
		let cObj = new Object();
		cObj.id = "P" + otherList[l].planNo + "-S" + otherList[l].scheduleNo + "/" + "00" + "-T" + otherList[l].scheduleType;
		cObj.title = otherList[l].scheduleContent;
		cObj.start = otherList[l].scheduleStartTime;
		cObj.end = otherList[l].scheduleEndTime;
		cObj.color = "gray";
		cData.push(cObj);
	}
	for (let l in scheduleList) {
		let cObj = new Object();
		//cObj.id = scheduleList[l].planNo;
		cObj.id = "P" + scheduleList[l].planNo + "-S" + scheduleList[l].scheduleNo + "/" + "00" + "-T" + scheduleList[l].scheduleType;
		cObj.title = scheduleList[l].scheduleContent;
		cObj.start = scheduleList[l].scheduleStartTime;
		cObj.url = scheduleList[l].scheduleSource;
		cObj.end = scheduleList[l].scheduleEndTime;
		cObj.color = plan.planColor;//plan.planColor;
		cData.push(cObj);
		
		let tmpRightStr = `
			<tr name>
				<td>${scheduleList[l].scheduleType}</td>
				<td>${scheduleList[l].scheduleContent}</td>
				<td>${scheduleList[l].scheduleSource}</td>
			</tr>
		`;
		$("#rightUrlTable").children("tbody").append(tmpRightStr);
	}
	
	let dateNow = new Date(focusDate);
	
	Date.prototype.addDays = function(days) {
	    var date = new Date(this.valueOf());
	    date.setDate(date.getDate() + days);
	    return date;
	}
	
	function leftPad(value) {
	    if (value >= 10) {
	        return value;
	    }
	
	    return `0${value}`;
	}
	
	function toStringByFormatting(source, delimiter = '-') {
	    const year = source.getFullYear();
	    const month = leftPad(source.getMonth() + 1);
	    const day = leftPad(source.getDate());
	
	    return [year, month, day].join(delimiter);
	}
	
	$("#inAddCalendar").val(toStringByFormatting(dateNow));
	
	$("#inAddCalendar").datepicker({dateFormat: "yy-mm-dd"});
	
	$("#inAddCalendar").on("change", function() {
		let tmpDate = $("#inAddCalendar").val();
		
		
		let inDate = new Date(tmpDate);
		console.log(inDate);                   // Date
		

		calendar.gotoDate(inDate);
		
		changeDay(inDate);
		
		window.location.href = "/mypage/getMyschedule?planNo=" + plan.planNo + "&focusDate=" + toStringByFormatting(inDate);
		
	});
	
	function submitDate() {
		let tmpDate = calendar.getDate();
		
		
		let inDate = new Date(tmpDate);
		console.log(inDate);                   // Date
		

		calendar.gotoDate(inDate);
		
		changeDay(inDate);

		window.location.href = "/mypage/getMyschedule?planNo=" + plan.planNo + "&focusDate=" + toStringByFormatting(inDate);
	}
	
	let cntNewSchedule = 0;
	
	$(".btnNewScheduleSubmit").on("click", function() {
					
		let cObj = new Object();
		
		let timS = $(".newScheduleRight.start").val();
		let timE = $(".newScheduleRight.end").val();
		//let tmpStartTime = focusDate + "T" + timS + ":00.000Z";
		//let tmpEndTime = focusDate + "T" + timE + ":00.000Z";
		let tmpStartTime = focusDate + "T" + timS + ":00.00";
		let tmpEndTime = focusDate + "T" + timE + ":00.00";
		cObj.title = $(".newScheduleRight.content").val();
		cObj.url = $(".newScheduleRight.url").val();
		cObj.id = "P" + plan.planNo + "-S0" + "/" + cntNewSchedule + "-T" + $(".newScheduleRight.select").val();;
		cObj.start = tmpStartTime;
		cObj.end = tmpEndTime;
		cObj.color = "blue";
		
		cntNewSchedule += 1;
		
		console.log(cObj);
		calendar.addEvent(cObj);
		
		$(".newScheduleTr > input").val("");
		$("#newSchedule").hide();
	});
	
	let startTimeDrag = null;
	let endTimeDrag = null;
	let startTimeUpdate = null;
	let endTimeUpdate = null;
	let typeUpdate = null;
	let contentUpdate = null;
	let urlUpdate = null;
	let updateId = null;
	let moveUrl = null;
	
	var calendarEl = document.getElementById('addCalendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
		timeZone: 'Asia/Seoul',
		customButtons: {
			today: {
				text: 'today',
				click: function() {
					calendar.today();
					submitDate();
				}
			},
			prev: {
				text: '<',
				click: function() {
					calendar.incrementDate({days: -1});
					submitDate();
				}
			},
			next: {
				text: '<',
				click: function() {
					calendar.incrementDate({days: +1});
					submitDate();
				}
			},
			btnMonth: {
				text: initMonth(),
				click: function() {
					$("#inAddCalendar").focus();
				}
			},
			btn00: {
				text: dateNow.getDate(),
				click: function() {
					
				}
			},
			btnm1: {
				text: initDay("m1"),
				click: function() {
					calendar.incrementDate({days: -1});
					submitDate();
				}
			},
			btnp1: {
				text: initDay("p1"),
				click: function() {
					calendar.incrementDate({days: 1});
					submitDate();
				}
			},
			add: {
				text: '일정추가',
				click: function() {
					
					if ($("#newSchedule").css("display") != "none") {
						return;
					}
					
					$("#newSchedule").css("display", "inline-block");
					
					$('#newSchedule').draggable({
				        hadle: "#addCategory"
				    });
				    
				    $(".newScheduleRight.planTitle").text(plan.planName);
				    
				    
				}
			}
		},
        headerToolbar: {
			left: 'btnMonth today add',
			right: 'prev btnm1 btn00 btnp1 next'
		},
		initialView: 'timeGridDay',
		views: {
	      timeGridDay: {
			allDaySlot: false,
	        buttonText: ':15 slots',
	        slotDuration: '00:15'
	        
	      }
	    },
	    editable: true,
	    selectable: true,
	    select: function(info) {
	      
	    	console.log('selected ' + info.startStr + ' to ' + info.endStr);
/*	      
	      $("#testBtn").on("click", function() {
			  $("#testBtn").css("background-color", "blue");
			  console.log("testBtn click" + info.jsEvent.clientX);
		  });
*/
			let mouseLeft = info.jsEvent.pageX;
			let mouseTop = info.jsEvent.pageY;
			
			

			$("#addSchedule").hide();
			
			setTimeout(function() {
				if($("#addSchedule").css("display") == "none") {
					//console.log("drag was none");
					$("#addSchedule").show();
					$("#addSchedule").css("left", mouseLeft+"px");
					$("#addSchedule").css("top", mouseTop+"px");
					
					startTimeDrag = info.startStr.substr(-8, 5);
					endTimeDrag = info.endStr.substr(-8, 5);
					//console.log(startTimeDrag + " |||||| " + endTimeDrag);
				}
			}, 100);
	    },
	    eventClick: function(info) {
			
			info.jsEvent.preventDefault(); // don't let the browser navigate

			moveUrl = info.event.url;
		   
			
			updateId = info.event.id;
			let splitId = updateId.split("-");
			let planId = splitId[0].substr(1);
			let scheduleId = splitId[1].substr(1);
			let typeId = splitId[2].substr(1);
			if (planId != plan.planNo) {
				console.log("failed");
				return;
			}
			console.log("%%%%%%%%%%");
			//console.log(info.event.sourceId);
			//console.log(calendar.getEvents());
			console.log(info.jsEvent);
			console.log("%%%%%%%%%%");
			//console.log("event: " + info.event.start + " ~~ " + info.event.end);
			
			let mouseLeft = info.jsEvent.pageX;
			let mouseTop = info.jsEvent.pageY;
			
			
			$("#focusSchedule").hide();
			
			setTimeout(function() {
				if($("#focusSchedule").css("display") == "none") {
					console.log("updateBtn was none");
					$("#focusSchedule").show();
					$("#focusSchedule").css("left", mouseLeft+"px");
					$("#focusSchedule").css("top", mouseTop+"px");
					
					
					//console.log(info.event.startStr);
					let tmpTim = info.event.startStr;
					let tmpTime = tmpTim.substr(tmpTim.indexOf("T") + 1).split(":");
					startTimeUpdate = tmpTime[0] + ":" + tmpTime[1];
					
					tmpTim = info.event.endStr;
					tmpTime = tmpTim.substr(tmpTim.indexOf("T") + 1).split(":");
					endTimeUpdate = tmpTime[0] + ":" + tmpTime[1];
					
					//console.log(startTimeUpdate);
					
					typeUpdate = typeId;
					contentUpdate = info.event.title;
					urlUpdate = info.event.url;
					
					//console.log(startTimeDrag + " |||||| " + endTimeDrag);
				}
			}, 100);
		},
		nowIndicator: true,
	    events: cData
    });
    calendar.render();
    
    function initMonth() {
		return (dateNow.getMonth() + 1) + "월";
	}
    
    function initDay(s) {
		let tmpNo = 0;
		
		switch(s) {
			case 'm1': tmpNo = -1; break;
			case 'p1': tmpNo = 1; break;
		}
		
		console.log("tmpNo: " + tmpNo);
		
		let tmpChangeDate = dateNow.addDays(tmpNo);
		let returnNo = tmpChangeDate.getDate();
		
		return returnNo;
	}
	
	function changeDay(today) {
		
		$(".fc-btn00-button").text("");
		$(".fc-btnm1-button").text("");
		$(".fc-btnp1-button").text("");
		
		//console.log(today.addDays(-5));
		let m1 = today.addDays(-1).getDate();
		let p1 = today.addDays(1).getDate();
		
		// change MONTH
		$(".fc-btnMonth-button").text((today.getMonth()+1) + "월");
		//$(".fc-btnMonth-button").text("00");
		// change Day
		$(".fc-btn00-button").text(today.getDate());
		$(".fc-btnm1-button").text(m1);
		$(".fc-btnp1-button").text(p1);
		
		$("#inTodoCalendar").val(toStringByFormatting(today));
		
		//$("#inTodoCalendar").change();
	}
	
	calendar.gotoDate(dateNow);
	
	
	
	console.log("%%%%%%%%%%");
	console.log(calendar.getEvents());
	//console.log(plan.planNo);
	//console.log(calendar.getEventById(plan.planNo));
	console.log("%%%%%%%%%%");
	
	$("#moveScheduleEvent").on("click", function() {
		 if (moveUrl) {
		     window.open(moveUrl);
		 } else {
			 alert("등록된 url이 없습니다.");
		 }
	});
	
	/* ** UPDATE ** */
	$("#updateScheduleEvent").on("click", function() {
		console.log("click update");
		
		$("#updateSchedule").css("display", "inline-block");
					
		$('#updateSchedule').draggable({
	        hadle: "#addCategory"
	    });
	    
	    $(".updateScheduleRight.planTitle").text(plan.planName);
	    
	    $(".updateScheduleRight.select").text(typeUpdate);
	    $(".updateScheduleRight.content").val(contentUpdate);
	    $(".updateScheduleRight.url").val(urlUpdate);
	    $(".updateScheduleRight.start").val(startTimeUpdate);
	    $(".updateScheduleRight.end").val(endTimeUpdate);
	    
	    
	});
	$(".closeUpdatePop, .btnUpdateScheduleCancel").on("click", function() {
		$(".updateScheduleTr > input").val("");
		$("#updateSchedule").hide();
	});
	$(".btnUpdateScheduleSubmit").on("click", function() {
		//console.log("click udpate submit");
		
		let timS = $(".updateScheduleRight.start").val();
		let timE = $(".updateScheduleRight.end").val();
		//let tmpStartTime = focusDate + "T" + timS + ":00.000Z";
		//let tmpEndTime = focusDate + "T" + timE + ":00.000Z";
		let tmpStartTime = focusDate + "T" + timS + ":00.00";
		let tmpEndTime = focusDate + "T" + timE + ":00.00";
		
		
		//console.log("time");
		//console.log(tmpStartTime);
		let updateEvent = calendar.getEventById(updateId);
		
		//tmpEvent.setProp("title", "setProp 테스트 하고 있음");
		updateEvent.setProp("title", $(".updateScheduleRight.content").val());
		updateEvent.setProp("url", $(".updateScheduleRight.url").val());
		updateEvent.setStart(tmpStartTime);
		updateEvent.setEnd(tmpEndTime);
		
		$(".updateScheduleTr > input").val("");
		$("#updateSchedule").hide();
		
		//console.log(calendar.getEventById(updateId));
	});
	/* ** DELETE ** */
	$("#deleteScheduleEvent").on("click", function() {
		let deleteEvent = calendar.getEventById(updateId);
		deleteEvent.remove();
		console.log("********************");
		console.log(calendar.getEvents());
	});
	
	/* ** NEW ** */
	$("#newScheduleByDrag").on("click", function() {
		console.log("click drag");
		
		$("#newSchedule").css("display", "inline-block");
					
		$('#newSchedule').draggable({
	        hadle: "#addCategory"
	    });
	    
	    $(".newScheduleRight.planTitle").text(plan.planName);
	    
	    $(".newScheduleRight.start").val(startTimeDrag);
	    $(".newScheduleRight.end").val(endTimeDrag);
	});
	
	$("#copyScheduleByDrag").on("click", function() {
		
		$("#copySchedule").css("display", "inline-block");
		
		$('#copySchedule').draggable({
	        hadle: "#copySchedule"
	    });
	    
	    let storageArray = new Array();
	    
	    if (localStorage.getItem("gomgome_copy_schedule") != null) {
			storageArray = JSON.parse(localStorage.getItem("gomgome_copy_schedule"));				
		} else {
			/* 복사한 일정이 없음 */
			$("#copySchedule").hide();
			alert("복사한 일정이 없습니다.");
			
		}
		
		
	    for (let i = 0; i < storageArray.length; i++) {
			console.log(typeof(storageArray[i].content));
			let typ = storageArray[i].type;
			let con = storageArray[i].content;
			let url = storageArray[i].url;
			
			let tmpSt = `
				<tr>
					<td>${typ}</td>
					<td>${con}</td>
					<td><button type="button" class="btnStorage" name="${typ}%^&&^%${con}%^&&^%${url}">선택</button></td>
				</tr>
			`;
			
			/*
			let tmpSt = `
				<option value="${typ}&^%&^%&^%${con}">${typ} : ${con}</option>
			`;
			*/
			$("#tableBody > tbody").append(tmpSt);
			
		}
		
		$(".btnStorage").on("click", function() {
			//console.log($(this).attr("name"));
			let tmpName = $(this).attr("name").split("%^&&^%");
			
			$("#copySchedule").hide();
			$("#newSchedule").css("display", "inline-block");
					
			$('#newSchedule').draggable({
		        hadle: "#addCategory"
		    });
		    
		    $(".newScheduleRight.planTitle").text(plan.planName);
		    
		    $(".newScheduleRight.select").val(tmpName[0]);
		    $(".newScheduleRight.content").val(tmpName[1]);
		    $(".newScheduleRight.url").val(tmpName[2]);
		    
		    $(".newScheduleRight.start").val(startTimeDrag);
	   		$(".newScheduleRight.end").val(endTimeDrag);
			
		});
	});
	
	
	
	
	
	$(".closeCopyTable").on("click", function() {
		$("#copySchedule").hide();
	});
	
	$(".closeNewPop, .btnNewScheduleCancel").on("click", function() {
		$(".newScheduleTr > input").val("");
		$("#newSchedule").hide();
	});
	
	$(".deletePlan").on("click", function() {
		window.location.href = "/mypage/deletePlan?planNo=" + plan.planNo
								+ "&focusDate=" + focusDate;
	});
	
	
	$(".updateSchedule").on("click", function() {
		let focusEvents = calendar.getEvents();
		
		console.log("^^^^^^^^^^^^^^^^^^");
		
		let updateArray = new Array();
		for (let i = 0; i < focusEvents.length; i++) {
			let splitId = focusEvents[i].id.split("-");
			let planId = splitId[0].substr(1);
			let tmpSch = splitId[1].substr(1).split("/");
			let scheduleId = tmpSch[0];
			//console.log("tmp, scheduleId");
			//console.log(tmpSch + ", " + scheduleId);
			let typeId = splitId[2].substr(1);
			
			if (planId == plan.planNo) {
				let updateObj = new Object();
				
				updateObj.planNo = planId;
				updateObj.scheduleNo = scheduleId;
				updateObj.scheduleType = typeId;
				updateObj.scheduleContent = focusEvents[i].title;
				updateObj.scheduleSource = focusEvents[i].url;
				updateObj.scheduleStartTime = focusEvents[i].startStr;
				updateObj.scheduleEndTime = focusEvents[i].endStr;
				
				console.log(updateObj);
				updateArray.push(updateObj);
			}
		}
		console.log(updateArray);
		console.log("^^^^^^^^^^^^^^^^^^");
		
		$.ajax({
			url: '/mypage/saveSchedule',
			type: 'post',
			data: {
				param: JSON.stringify(updateArray),
				focusDate: focusDate
			},
			success: function(obj) {
				console.log("success");
				location.reload();
			},
			error: function(e) {
				console.log("error: " + e);
			}
		});
		
	});
	
	
	
	
	
	
	
});