$(function(){
	
	var planNo = document.getElementsByClassName("no");
	var startDay = document.getElementsByClassName("startDay");
	var endDay = document.getElementsByClassName("endDay");
	var planColor = document.getElementsByClassName("color");
	var planDates=[];
	var finalArray = [];
	
	for(i=0; i<planNo.length ;i++) {
		//onePlan=[];
		/*for(j=0; j<1; j++) {
			onePlan.push(planNo.item(i).textContent);
			onePlan.push(startDay.item(i).textContent);
			onePlan.push(endDay.item(i).textContent);
			onePlan.push(planColor.item(i).textContent);
		}*/
		
		const planObj = {
			planNo: planNo.item(i).textContent,
			startDay: startDay.item(i).textContent,
			endDay: endDay.item(i).textContent,
			planColor: planColor.item(i).textContent
		};
		
		planDates.push(planObj);
	}
	
	
	//console.log(new Date(planDates[0][1]));
	
	Date.prototype.addDays = function(days) {
	   var date = new Date(this.valueOf())
	   date.setDate(date.getDate() + days);
	   return date;
	}
	
	function getDates(startDate, stopDate) {
	  var dateArray = new Array();
	  var currentDate = startDate;
	  while (currentDate <= stopDate) {
	    dateArray.push(currentDate)
	    currentDate = currentDate.addDays(1);
	  }
	  return dateArray;
	}
	
	function isodate(date) {
	    var fulliso = date.toISOString();
	    return fulliso.split("T")[0]
	}
	
	for(i=0; i<planDates.length; i++) {
		var dateArray = getDates(new Date(planDates[i].startDay), new Date(planDates[i].endDay));
		var convertDateArray = [];
		const planObj = planDates[i];
		
	
		console.log("===========플랜번호 : " + planDates[i][0])
		for(k=0; k<dateArray.length; k++) {
			var currDate = dateArray[k];	
    		var cdateISO = isodate(currDate)
    		console.log(currDate);
    		convertDateArray.push(cdateISO);
		}
						
		planObj.dateArray = convertDateArray; 
		finalArray.push(planObj);
	}
	
	//for(i=0; i<planDates.length; i++) {
	//	console.log(planDates[i].length)
		
	//	for(j=0; j<planDates[i].length; j++) {
	//		if(j=1) {
	//			console.log(planDates[i][j]);
	//		}
	//	}
	//}
	
    var today = new Date();
    var date = new Date();        

    $("input[name=preMon]").click(function() { // 이전달
        $("#calendar > tbody > td").remove();
        $("#calendar > tbody > tr").remove();
        today = new Date ( today.getFullYear(), today.getMonth()-1, today.getDate());
        buildCalendar();
    })
    
    $("input[name=nextMon]").click(function(){ //다음달
        $("#calendar > tbody > td").remove();
        $("#calendar > tbody > tr").remove();
        today = new Date ( today.getFullYear(), today.getMonth()+1, today.getDate());
        buildCalendar();
    })


    function buildCalendar() {
        
        nowYear = today.getFullYear();
        nowMonth = today.getMonth();
        firstDate = new Date(nowYear,nowMonth,1).getDate();
        firstDay = new Date(nowYear,nowMonth,1).getDay(); //1st의 요일
        lastDate = new Date(nowYear,nowMonth+1,0).getDate();

        if((nowYear%4===0 && nowYear % 100 !==0) || nowYear%400===0) { //윤년 적용
            lastDate[1]=29;
        }

        $(".year_mon").text(nowYear+"년 "+(nowMonth+1)+"월");

        for (i=0; i<firstDay; i++) { //첫번째 줄 빈칸
            $("#calendar tbody:last").append("<td></td>");
        }
        for (i=1; i <=lastDate; i++){ // 날짜 채우기
            plusDate = new Date(nowYear,nowMonth,i).getDay();
            if (plusDate==0) {
                $("#calendar tbody:last").append("<tr></tr>");
            }
            $("#calendar tbody:last").append("<td class='date'>"+ i +"</td>");
        }
        if($("#calendar > tbody > td").length%7!=0) { //마지막 줄 빈칸
            for(i=1; i<= $("#calendar > tbody > td").length%7; i++) {
                $("#calendar tbody:last").append("<td></td>");
            }
        }
        
        $(".date").each(function(index){ // 오늘 날짜 표시
            for(let i = 0; i < finalArray.length; i++) {
				for(let j = 0; j < finalArray[i].dateArray.length; j++) {
				console.log(nowYear == finalArray[i].dateArray[j].substr(0, 4));
				console.log(nowMonth);
				console.log(finalArray[i].dateArray[j].substr(5, 2));
				console.log(nowMonth < 10 ? '0' + nowMonth : nowMonth  == finalArray[i].dateArray[j].substr(5, 2));
					if(nowYear == finalArray[i].dateArray[j].substr(0, 4) && nowMonth < 10 ? '0' + nowMonth : nowMonth  == finalArray[i].dateArray[j].substr(5, 2)) {
						if($(this).text() < 10 ? '0' + $(this).text() : $(this).text() == finalArray[i].dateArray[j].substr(8, 2)) {
							var str = "";
							str += `
								<div style='height: 30%; background: ${finalArray[i].planColor};'>
								</div>
							`;
							$(".date").eq(index).append(str);
						}
					}
				}
			}
			
            if(nowYear==date.getFullYear() && nowMonth==date.getMonth() && $(".date").eq(index).text()==date.getDate()) {
                $(".date").eq(index).addClass('colToday');
            }
            
        }) 
        
    }
    buildCalendar();
	//console.log($(".date"))
})