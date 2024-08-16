package com.prakash.question_service.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class QuizAns {
	private Integer id;
	private String response; 
}
