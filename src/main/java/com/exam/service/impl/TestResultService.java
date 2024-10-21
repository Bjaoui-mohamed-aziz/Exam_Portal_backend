package com.exam.service.impl;

import com.exam.Model.User;
import com.exam.Model.exam.Quiz;
import com.exam.Model.exam.TestResult;
import com.exam.Repo.TestResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class TestResultService {

    @Autowired
    private TestResultRepository testResultRepository;

    public TestResult saveTestResult(User user, Quiz quiz, double marksGot, int correctAnswers, int attempted) {
        TestResult testResult = new TestResult();
        testResult.setUser(user);
        testResult.setQuiz(quiz);
        testResult.setMarksGot(marksGot);
        testResult.setCorrectAnswers(correctAnswers);
        testResult.setAttempted(attempted);
        testResult.setResultDate(LocalDateTime.now());

        return testResultRepository.save(testResult);
    }

    public TestResult getById(Long id) {
        Optional<TestResult> testResult = testResultRepository.findById(id);
        if (testResult.isPresent()) {
            return testResult.get();
        } else {
            throw new RuntimeException("Test result not found for ID: " + id);
        }
    }
}