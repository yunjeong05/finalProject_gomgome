
$(function() {
/*
	let tmpData = [
		{
			title: 'testTitle',
			start: '2023-01-15T16:00:00',
			end: '2023-01-15T17:00:00'
		},
		{
			title: 'testTitle',
			start: '2023-01-15T18:00:00',
			end: '2023-01-15T19:00:00'
		},
		{
			title: 'testTitle',
			start: '2023-01-15T20:00:00',
			end: '2023-01-15T21:00:00'
		}
	];
	
	
	let calendarArray = new Array();
	
	let cData = new Object();
	
	cData.qwe = "qwe";
	cData.asd = "asd";
	
	calendarArray.push(cData);
	console.log(calendarArray);
	
	cData.qwe = "ewq";
	cData.asd = "ewq";
	
	calendarArray.push(cData);
	console.log(calendarArray);
*/
	
	//console.log(scheduleMap[10]);
	//console.log(scheduleMap[11]);
	//console.log(scheduleMap[12]); 
//	console.log(typeof(tmpData) + ": " + tmpData);
//	console.log(typeof(planList) + ": " + planList);
	
	let dateNow = new Date(focusDate);
	
	let mapPlan = new Map();
	for (let i = 0; i < planList.length; i++) {
		mapPlan.set(planList[i].planNo, planList[i].planColor);
	}
	
	console.log("dateNow: " + dateNow);
	console.log(mapPlan);
	
	let cData = new Array();
	
	for (let l in scheduleMap) {
		for (let ll in scheduleMap[l]) {
			let cObj = new Object();
			cObj.title = scheduleMap[l][ll].scheduleType + ": " + scheduleMap[l][ll].scheduleContent;
			cObj.start = scheduleMap[l][ll].scheduleStartTime;
			cObj.end = scheduleMap[l][ll].scheduleEndTime;
			cObj.color = mapPlan.get(scheduleMap[l][ll].planNo);
			console.log(cObj);
			cData.push(cObj);
		}
	}
	console.log("-----------");
	console.log(cData);
	
	console.log("=============");
	let scheduleTotal = 0;
	let scheduleTotalHou = 0;
	let scheduleTotalMin = 0;
	planList.forEach(function(item) {
		let tmpPlanNo = item.planNo;          // planNo
		let tmpFor = scheduleMap[tmpPlanNo];  // schedules : planNo
		let tmpTotalminute = 0;                 // all time of schedules
		let startDate = item.planStartDay;
		let endDate = item.planEndDay;
		let ddayDate = item.planDday;
		let tmpPlanName = item.planName;
		
		//console.log(item);
		//console.log(tmpFor);
		
		let tmpScheduleStr = ``;
		let cntSche = 1;
		
		for (let o in tmpFor) {
			console.log(tmpFor[o]);  // 각 스케줄
			
			
			let startT = new Date(tmpFor[o].scheduleStartTime);
			if (toStringByFormatting(startT) === toStringByFormatting(dateNow)) {
				let endT = new Date(tmpFor[o].scheduleEndTime);
				let ti = (endT.getTime() - startT.getTime()) / (1000*60);
				//console.log(ti);
				tmpTotalminute += ti;	
				
				tmpScheduleStr += `
					<tr>
						<th>일정 ${cntSche}</th>
						<td>${tmpFor[o].scheduleType}: ${tmpFor[o].scheduleContent}</td>
					</tr>
				`;
				cntSche += 1;
			}
			
		}
		scheduleTotal += tmpTotalminute;
		let min = String(tmpTotalminute % 60);
		min = min.padStart(2, "0");
		let hou = String(parseInt(tmpTotalminute / 60));
		hou = hou.padStart(2, "0");
		//console.log(tmpTotalhour);
		let rightContentStr = `
		
			<button type="button" class="tododateSelectPlan" name="${tmpPlanNo}">
				<i class="fa fa-caret-right rotate">
				</i>
				<span class="displayTime">&emsp;${hou}:${min}</span>&emsp;${tmpPlanName}
			</button>
			<button type="button" class="move2Plan btn${tmpPlanNo}" name="${tmpPlanNo}" >>></button>
			<br>
			
			<table class="displayTable t${tmpPlanNo}">
				<tr>
					<th>시작</th>
					<td>${startDate}</td>
				</tr>
				<tr>
					<th>마감</th>
					<td>${endDate}</td>
				</tr>
				<tr>
					<th>D-day</th>
					<td>${ddayDate}</td>
				</tr>
		` + tmpScheduleStr + `
			</table>
		`;
		$("#todoRightContent").append(rightContentStr);
	});
	scheduleTotalHou = String(parseInt(scheduleTotal / 60));
	scheduleTotalMin = String(scheduleTotal % 60);
	let tmpScheduleStr = `
		${scheduleTotalHou}시간 ${scheduleTotalMin}분
	`;
	$("#tododateTotalstudyContent").text(tmpScheduleStr);
	let tmpPlanStr =`
		${planList.length}가지
	`;
	$("#tododatePlanContent").text(tmpPlanStr);
	
	console.log("=============");
	
	$(".tododateSelectPlan").on("click", function() {
		let className = $(this).attr("name");
		let thisClass = $(this);
		
		if($(".displayTable.t" + className).css("display") == "none") {
			$(".displayTable.t" + className).show();
			thisClass.children(".fa").toggleClass("down");
		} else {
			$(".displayTable.t" + className).hide();
			thisClass.children(".fa").toggleClass("down");
		}
	});

	$(".move2Plan").on("click", function() {
		let planNo4move = $(this).attr("name");
		console.log("click");
		
		window.location.href = "/mypage/getMyschedule?planNo=" + planNo4move + "&focusDate=" + toStringByFormatting(dateNow);
		
	});

	let calendarDate = "";
	let calendarYear = "";
	let calendarMonth = "";
	let calendarDay = "";
	
	let tmpStr = 't';
	
	console.log("text: " + $(".tmp.Text.a01").text());
	console.log("focusDate: " + focusDate);
	
	
	
	Date.prototype.addDays = function(days) {
	    var date = new Date(this.valueOf());
	    date.setDate(date.getDate() + days);
	    return date;
	}
	
	//console.log("dateNow: " + dateNow.addDays(-30));
	
	$("#inTodoCalendar").val(toStringByFormatting(dateNow));

/*	
	let tmpDate = $("#inTodoCalendar").val();
	let splitDate = tmpDate.split("-");
	
	calendarDate = tmpDate;
	calendarYear = splitDate[0];
	calendarMonth = parseInt(splitDate[1]);
	calendarDay = splitDate[2];
*/	

	$("#inTodoCalendar").datepicker({dateFormat: "yy-mm-dd"});
	
	$("#inTodoCalendar").on("change", function() {
		let tmpDate = $("#inTodoCalendar").val();
		//let tmpDate = calendar.getDate();
//		splitDate = tmpDate.split("-");
		
		//calendarYear = splitDate[0];
		//calendarMonth = parseInt(splitDate[1]);
		//calendarDay = splitDate[2];
		
		console.log("change: " + calendarYear);
		console.log(typeof(tmpDate));          // string
		console.log("tmpDate: " + tmpDate);
		
		let inDate = new Date(tmpDate);
		console.log(inDate);                   // Date
		

		calendar.gotoDate(inDate);
		
		changeDay(inDate);
/*		
		$("#scheduleYearto").val(inDate.getYear() + 1900);
		$("#scheduleMonthto").val(String(inDate.getMonth() + 1).padStart(2, "0"));
		$("#scheduleDateto").val(String(inDate.getDate()).padStart(2, "0"));
*/
		$("#scheduleYearto").val(String(inDate.getYear() + 1900));
		$("#scheduleMonthto").val(String(inDate.getMonth() + 1));
		$("#scheduleDateto").val(String(inDate.getDate()));

		$("#formTodoDate").submit();
		
		
		
		// change MONTH
		//$(".fc-btnMonth-button").text(calendarMonth + "월");
		// change Day
		//$(".fc-btn00-button").text(calendarDay);
	});
	
	function submitDate() {
		let tmpDate = calendar.getDate();

		
		console.log("change: " + calendarYear);
		console.log(typeof(tmpDate));          // string
		console.log("tmpDate: " + tmpDate);
		
		let inDate = new Date(tmpDate);
		console.log(inDate);                   // Date
		

		calendar.gotoDate(inDate);
		
		changeDay(inDate);

		$("#scheduleYearto").val(String(inDate.getYear() + 1900));
		$("#scheduleMonthto").val(String(inDate.getMonth() + 1));
		$("#scheduleDateto").val(String(inDate.getDate()));

		$("#formTodoDate").submit();
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
	
	var calendarEl = document.getElementById('todoCalendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
		customButtons: {
			today: {
				text: 'today',
				click: function() {
					calendar.today();
	//				changeDay(calendar.getDate());
					//console.log("today: " + calendar.getDate());
					//$("#inTodoCalendar").change();
					submitDate();
				}
			},
			prev: {
				text: '<',
				click: function() {
					calendar.incrementDate({days: -1});
	//				changeDay(calendar.getDate());
					//console.log("today: " + calendar.getDate());
					//$("#inTodoCalendar").change();
					submitDate();
				}
			},
			next: {
				text: '<',
				click: function() {
					calendar.incrementDate({days: +1});
	//				changeDay(calendar.getDate());
					//console.log("today: " + calendar.getDate());
					//$("#inTodoCalendar").change();
					submitDate();
				}
			},
			btnMonth: {
				text: initMonth(),
				click: function() {
					$("#inTodoCalendar").focus();
				}
			},
			btn00: {
				text: dateNow.getDate(),
				click: function() {
					//event.target.innerHTML = 'q';
					console.log(event.target.parentElement.children); 
					changeBtnText(event);
					//$("#inTodoCalendar").change();
					//submitDate();
				}
			},
			btnm2: {
				text: initDay("m2", calendarDate),
				click: function() {
					//event.target.innerHTML = 'q';
					console.log(event.target.parentElement.children);
					console.log("click value: " + $(".fc-btnm2-button").attr("title")); 
					calendar.incrementDate({days: -2});
	//				changeDay(calendar.getDate());
					//$("#inTodoCalendar").change();
					submitDate();
				}
			},
			btnm1: {
				text: initDay("m1", calendarDate),
				click: function() {
					//event.target.innerHTML = 'q';
					console.log(event.target.parentElement.children); 
					calendar.incrementDate({days: -1});
	//				changeDay(calendar.getDate());
					//$("#inTodoCalendar").change();
					submitDate();
				}
			},
			btnp1: {
				text: initDay("p1", calendarDate),
				click: function() {
					//event.target.innerHTML = 'q';
					console.log(event.target.parentElement.children); 
					calendar.incrementDate({days: 1});
	//				changeDay(calendar.getDate());
					//$("#inTodoCalendar").change();
					submitDate();
				}
			},
			btnp2: {
				text: initDay("p2", calendarDate),
				click: function() {
					//event.target.innerHTML = 'q';
					console.log(event.target.parentElement.children); 
					calendar.incrementDate({days: 2});
	//				changeDay(calendar.getDate());
					//$("#inTodoCalendar").change();
					submitDate();
				}
			},
		},
        headerToolbar: {
			left: 'btnMonth today',
			right: 'prev btnm2 btnm1 btn00 btnp1 btnp2 next'
		},
		initialView: 'timeGridDay',
		views: {
	      timeGridDay: {
			allDaySlot: false,
	        buttonText: ':15 slots',
	        slotDuration: '00:30'
	        
	      }
	    },
	    nowIndicator: true,
	    events: cData
    });
    calendar.render();
    
    function initMonth() {
		return (dateNow.getMonth() + 1) + "월";
	}

	function initDay(s, today) {
		//let prevStr = ".fc-btn";
		//let nextStr = "-button";
		//let str = prevStr + s + nextStr;
		
		
		let tmpNo = 0;
		
		switch(s) {
			case 'm2': tmpNo = -2; break;
			case 'm1': tmpNo = -1; break;
			case 'p1': tmpNo = 1; break;
			case 'p2': tmpNo = 2; break;
		}
		
		console.log("tmpNo: " + tmpNo);
		
		let tmpChangeDate = dateNow.addDays(tmpNo);
		let returnNo = tmpChangeDate.getDate();
		
		return returnNo;
	}
	
	function changeDay(today) {
		
		$(".fc-btn00-button").text("");
		$(".fc-btnm2-button").text("");
		$(".fc-btnm1-button").text("");
		$(".fc-btnp1-button").text("");
		$(".fc-btnp2-button").text("");
		
		//console.log(today.addDays(-5));
		let m2 = today.addDays(-2).getDate();
		let m1 = today.addDays(-1).getDate();
		let p1 = today.addDays(1).getDate();
		let p2 = today.addDays(2).getDate();
		
		// change MONTH
		$(".fc-btnMonth-button").text((today.getMonth()+1) + "월");
		//$(".fc-btnMonth-button").text("00");
		// change Day
		$(".fc-btn00-button").text(today.getDate());
		$(".fc-btnm2-button").text(m2);
		$(".fc-btnm1-button").text(m1);
		$(".fc-btnp1-button").text(p1);
		$(".fc-btnp2-button").text(p2);
		
		$("#inTodoCalendar").val(toStringByFormatting(today));
		
		//$("#inTodoCalendar").change();
	}
	
	calendar.gotoDate(dateNow);
	
	
	/* * tododateRight * */
	
	
	
	
	/* ******************************************************************************************** */
/*	
	console.log("value: " + $("#tmpText").val());
	console.log("value: " + $("#tmpText").text());
	
	console.log(userStudyhourTotal);
	console.log(planUsersList);
	console.log(scheduleList);
	
	console.log("------------");
	
	let tod = new Date();
	tod.setHours(0, 0, 0, 0);
	console.log(tod);
	
	let addHour = 1;
	console.log(tod.getHours());
	console.log(tod.getHours() + addHour);
	
	// planNo : color 할당
//	for(let i = 0; i < planUsersList; i++) {
		
//	}
	
	console.log("scheduleList.length: " + scheduleList.length);
	let tableStr = `
			<tr>
				<th>시</th>
				<td>0-10</td>
				<td>10-20</td>
				<td>20-30</td>
				<td>30-40</td>
				<td>40-50</td>
				<td>50-00</td>
				<td>00-10</td>
				<td>10-20</td>
				<td>20-30</td>
				<td>30-40</td>
				<td>40-50</td>
				<td>50-00</td>
			</tr>
		`;
	$(".calendarTableThead").append(tableStr);
	for(let i = 0; i < 24; i+=2) {
		let tableStr = `
			<tr>
				<td>${i}-${i+2}</td>
				<td> </td>
				<td> </td>
				<td> </td>
				<td> </td>
				<td> </td>
				<td> </td>
				<td> </td>
				<td> </td>
				<td> </td>
				<td> </td>
				<td> </td>
				<td> </td>
			</tr>
		`;
		$(".calendarTableTbody").append(tableStr);
	}
*/	
});