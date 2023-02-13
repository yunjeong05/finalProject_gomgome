package com.ezen.gomgome.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateGoalDTO {
	private int planNo;
	private String planName;
	private String planGoal;
	private int planStatus;
}
