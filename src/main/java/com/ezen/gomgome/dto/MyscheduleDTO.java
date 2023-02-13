package com.ezen.gomgome.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyscheduleDTO {
	private String scheduleYear;
	private String scheduleMonth;
	private String scheduleDate;
}
