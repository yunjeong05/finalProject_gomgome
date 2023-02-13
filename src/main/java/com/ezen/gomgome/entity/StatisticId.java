package com.ezen.gomgome.entity;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Data;

@Data
public class StatisticId implements Serializable {
	private String userId;
	private LocalDate statisticDate;
}
