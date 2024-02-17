package com.exam.service.impl;

import com.exam.Model.exam.Questions;
import com.exam.Model.exam.Quiz;
import com.exam.Repo.QuestionRepository;
import com.exam.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Questions addQuestion(Questions question) {
        return this.questionRepository.save(question);
    }

    @Override
    public Questions updateQuestion(Questions question) {
        return this.questionRepository.save(question);
    }

    @Override
    public Set<Questions> getQuestions() {
        return new HashSet<>(this.questionRepository.findAll());
    }

    @Override
    public Questions getQuestion(Long questionId) {
        return this.questionRepository.findById(questionId).get();
    }

    @Override
    public Set<Questions> getQuestionsOfQuiz(Quiz quiz) {
        return this.questionRepository.findByQuiz(quiz);
    }

    @Override
    public void deleteQuestion(Long quesId) {
        Questions question = new Questions();
        question.setQuesId(quesId);
        this.questionRepository.delete(question);
    }

    @Override
    public Questions get(Long questionsId) {
        return this.questionRepository.getOne(questionsId);
    }


}
