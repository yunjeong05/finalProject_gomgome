package com.ezen.gomgome.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsertPlanDTO {
	private int planMaincategoryNo;
	private String planName;
/*
	private String planStartYear;
	private String planStartMonth;
	private String planStartDate;
	private String planEndYear;
	private String planEndMonth;
	private String planEndDate;
	private String planDdayYear;
	private String planDdayMonth;
	private String planDdayDate;
*/
	private String planColor;
	private String planStart;
	private String planEnd;
	private String planDday;
}
