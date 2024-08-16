package com.prakash.question_service.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prakash.question_service.entity.Question;
import com.prakash.question_service.entity.QuestionWrapper;
import com.prakash.question_service.model.QuizAns;
import com.prakash.question_service.service.QuestionService;


@RestController
@RequestMapping("question")
public class QuestionController {
	private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
	
	@Autowired QuestionService questionService; 
	
	@GetMapping("/allQuestions")
	public ResponseEntity<List<Question>> getAllQuestions() {
		return questionService.getAllQuestions();
	}
	
	@GetMapping("/category/{category}")
	public ResponseEntity<List<Question>> getQuestionsBasedOnCategory(@PathVariable String category){
		return questionService.getQuestionsBasedOnCategory(category);
	}
	
	@PostMapping("/addQuestion")
	public ResponseEntity<String> addQuestion(@RequestBody Question question){
		return questionService.addQuestion(question);
	}
	
	@GetMapping("/generate")
	public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category,
			@RequestParam int noOfQuestions){
		logger.info("category>> " + category);
		logger.info("noOfQuestions>> " + noOfQuestions);
		return questionService.getQuestionsForQuiz(category,noOfQuestions);
	}
	
	@PostMapping("/getQuestionsById")
	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds){
		logger.info("questionIds>> " + questionIds);
		return questionService.getQuestionsById(questionIds);
	}
	
	@PostMapping("/getScore")
	public ResponseEntity<String> getScore(@RequestBody List<QuizAns> quizAnswers){
		return questionService.calculateCorrectAns(quizAnswers);
	}
	
}
