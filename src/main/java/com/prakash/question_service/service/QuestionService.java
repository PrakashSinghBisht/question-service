package com.prakash.question_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.prakash.question_service.entity.Question;
import com.prakash.question_service.entity.QuestionWrapper;
import com.prakash.question_service.model.QuizAns;
import com.prakash.question_service.repository.QuestionRepository;

@Service
public class QuestionService {

	@Autowired
	QuestionRepository questionRepository;

	public ResponseEntity<List<Question>> getAllQuestions() {
		try {
			return new ResponseEntity<>(questionRepository.findAll(), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList(), HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<List<Question>> getQuestionsBasedOnCategory(String category) {
		try {
			return new ResponseEntity(questionRepository.findByCategory(category),HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList(), HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<String> addQuestion(Question question) {
		try {
			return new ResponseEntity(questionRepository.save(question),HttpStatus.CREATED);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, int noOfQuestions) {
		List<Integer> questions = questionRepository.findRandomQuestionsByCategory(category,noOfQuestions);
		return new ResponseEntity<List<Integer>>(questions, HttpStatus.OK);
	}

	public ResponseEntity<List<QuestionWrapper>> getQuestionsById(List<Integer> questionIds) {
		List<QuestionWrapper> wrappers = new ArrayList();
		List<Question> questions = new ArrayList();
		for(Integer id : questionIds) {
			questions.add(questionRepository.findById(id).get());
		}
		
		for(Question question : questions) {
			QuestionWrapper wrapper = new QuestionWrapper();
			wrapper.setId(question.getId());
			wrapper.setQuestionTitle(question.getQuestionTitle());
			wrapper.setOption1(question.getOption1());
			wrapper.setOption2(question.getOption2());
			wrapper.setOption3(question.getOption3());
			wrapper.setOption4(question.getOption4());
			wrappers.add(wrapper);
		}
		return new ResponseEntity<>(wrappers, HttpStatus.OK);
	}

	public ResponseEntity<String> calculateCorrectAns(List<QuizAns> quizAnswers) {
		int correctAns = 0;
		for(QuizAns quizAns : quizAnswers) {
			Question question = questionRepository.findById(quizAns.getId()).get();
			if(quizAns.getResponse().equals(question.getRightAnswer())) {
				correctAns++;
			}
		}
		return new ResponseEntity<String>("You got "+correctAns+" marks out of "+quizAnswers.size()+"",HttpStatus.OK);
	
	}

}
