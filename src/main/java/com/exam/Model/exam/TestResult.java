package com.exam.Model.exam;


import com.exam.Model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    private double marksGot;
    private int correctAnswers;
    private int attempted;
    private LocalDateTime resultDate;


    public TestResult() {
    }

    public TestResult(User user, Quiz quiz, double marksGot, int correctAnswers, int attempted, LocalDateTime resultDate) {
        this.user = user;
        this.quiz = quiz;
        this.marksGot = marksGot;
        this.correctAnswers = correctAnswers;
        this.attempted = attempted;
        this.resultDate = resultDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public double getMarksGot() {
        return marksGot;
    }

    public void setMarksGot(double marksGot) {
        this.marksGot = marksGot;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getAttempted() {
        return attempted;
    }

    public void setAttempted(int attempted) {
        this.attempted = attempted;
    }

    public LocalDateTime getResultDate() {
        return resultDate;
    }

    public void setResultDate(LocalDateTime resultDate) {
        this.resultDate = resultDate;
    }

}