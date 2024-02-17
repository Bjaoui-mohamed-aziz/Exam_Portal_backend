package com.exam.Repo;

import com.exam.Model.exam.Questions;
import com.exam.Model.exam.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface QuestionRepository extends JpaRepository<Questions,Long> {
    Set<Questions> findByQuiz(Quiz quiz);
}
