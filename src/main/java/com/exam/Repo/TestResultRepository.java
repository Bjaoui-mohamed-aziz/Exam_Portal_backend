package com.exam.Repo;

import com.exam.Model.exam.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestResultRepository extends JpaRepository<TestResult, Long> {


}



