package com.exam.service;

import com.exam.Model.exam.Questions;
import com.exam.Model.exam.Quiz;
import org.aspectj.weaver.patterns.TypePatternQuestions;

import java.util.Set;

public interface QuestionService {

    public Questions addQuestion(Questions question);

    public Questions updateQuestion(Questions question);

    public Set<Questions> getQuestions();

    public Questions getQuestion(Long questionId);

    public Set<Questions> getQuestionsOfQuiz(Quiz quiz);

    public void deleteQuestion(Long quesId);

    public Questions get(Long questionsId);
}
