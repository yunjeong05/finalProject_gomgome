package com.ezen.gomgome.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Data;

@Data
public class ResponseDTO<T> { 
	private List<T> items;
	
	private T item; // 겟보드 같은 객체는 item으로 만들어서 던져버리는..

	private String errorMessage;
	
	private int statusCode;

	private Page<T> pageItems; // 페이징 처리된 객체
}
