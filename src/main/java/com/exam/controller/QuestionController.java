package com.exam.controller;

import com.exam.Model.User;
import com.exam.Model.exam.Questions;
import com.exam.Model.exam.Quiz;
import com.exam.Model.exam.TestResult;
import com.exam.service.QuestionService;
import com.exam.service.QuizService;
import com.exam.service.UserService;
import com.exam.service.impl.TestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/question")
public class QuestionController {


    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService service;

    @Autowired
    private TestResultService testResultService;

    @Autowired
    private QuizService quizService;
    //add question
    @PostMapping("/")
    public ResponseEntity<Questions> add(@RequestBody Questions question){
        return ResponseEntity.ok(this.service.addQuestion(question));
    }

    //update the question
    @PutMapping("/")
    public ResponseEntity<Questions> update(@RequestBody Questions question)
    {
        return ResponseEntity.ok(this.service.updateQuestion(question));
    }
    //get all questions
    @GetMapping("/quiz/{qid}")
    public ResponseEntity<?> geQuestionsOfQuiz(@PathVariable("qid") Long qid)
    {
     /*   Quiz quiz = new Quiz();
        quiz.setqID(qid);
        Set<Questions> questionsOfQuiz = this.service.getQuestionsOfQuiz(quiz);
        return ResponseEntity.ok(questionsOfQuiz);*/

        Quiz quiz = this.quizService.getQuiz(qid);
        Set<Questions> questions = quiz.getQuestions();
        List<Questions>list = new ArrayList(questions);
        if(list.size()>Integer.parseInt(quiz.getNumberOfQuestions()))

        {
            list=list.subList(0,Integer.parseInt(quiz.getNumberOfQuestions() + 1));
        }

        list.forEach((q) -> {
            q.setAnswer("");
        });
        Collections.shuffle(list);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/quiz/all/{qid}")
    public ResponseEntity<?> geQuestionsOfQuizAdmin(@PathVariable("qid") Long qid)
    {
        Quiz quiz = new Quiz();
        quiz.setqID(qid);
        Set<Questions> questionsOfQuiz = this.service.getQuestionsOfQuiz(quiz);
        return ResponseEntity.ok(questionsOfQuiz);



        //  return ResponseEntity.ok(list);
    }

    //get single question
    @GetMapping("/{quesId}")
    public Questions get(@PathVariable("quesId") Long quesId)
    {
        return this.service.getQuestion(quesId);
    }

    //delete single question
    @DeleteMapping("/{quesId}")
    public void delete(@PathVariable("quesId") Long quesId)
    {
        this.service.deleteQuestion(quesId);
    }


    @PostMapping("/eval-quiz")
    public ResponseEntity<?> evalQuiz(@RequestBody List<Questions> questions, @RequestParam("userId") Long userId) {
        try {
            double marksGot = 0;
            int correctAnswers = 0;
            int attempted = 0;

            // Check if questions are provided
            if (questions.isEmpty()) {
                return ResponseEntity.badRequest().body("No questions provided.");
            }

            Quiz quiz = questions.get(0).getQuiz(); // Get the quiz from the first question

            // Validate quiz and its category
            if (quiz == null || quiz.getqID() == null) {
                return ResponseEntity.badRequest().body("Quiz or Quiz ID is missing.");
            }

            if (quiz.getCategory() == null) {
                return ResponseEntity.badRequest().body("Category is missing for the quiz.");
            }

            // Fetch user details
            User user = userService.getUserId(userId);
            if (user == null) {
                return ResponseEntity.badRequest().body("Invalid user ID.");
            }

            // Evaluate quiz
            for (Questions q : questions) {
                Questions question = this.service.get(q.getQuesId());
                if (question.getAnswer().equals(q.getGivenAnswer())) {
                    correctAnswers++;
                    double marksSingle = Double.parseDouble(quiz.getMaxMarks()) / questions.size();
                    marksGot += marksSingle;
                }
                if (q.getGivenAnswer() != null) {
                    attempted++;
                }
            }

            // Save test result
            TestResult testResult = testResultService.saveTestResult(user, quiz, marksGot, correctAnswers, attempted);

            // Prepare the response
            Map<Object, Object> resultMap = Map.of(
                    "marksGot", marksGot,
                    "correctAnswers", correctAnswers,
                    "attempted", attempted,
                    "testResultId", testResult.getId(),
                    "username", user.getUsername() // Include username in the response
            );

            return ResponseEntity.ok(resultMap);

        } catch (Exception e) {
            e.printStackTrace(); // Log the error details
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while evaluating the quiz.");
        }
    }


    @GetMapping("/results")
    public ResponseEntity<?> getAllTestResultsWithUsernames() {
        try {
            // Fetch all test results
            List<TestResult> testResults = testResultService.getAllTestResults();

            // Check if there are no results
            if (testResults.isEmpty()) {
                return ResponseEntity.badRequest().body("No test results found.");
            }

            // Prepare a list to store result maps
            List<Map<Object, Object>> resultList = new ArrayList<>();

            // Loop through each test result and add the required details to the list
            for (TestResult testResult : testResults) {
                User user = testResult.getUser();

                Map<Object, Object> resultMap = Map.of(
                        "marksGot", testResult.getMarksGot(),
                        "correctAnswers", testResult.getCorrectAnswers(),
                        "attempted", testResult.getAttempted(),
                        "testResultId", testResult.getId(),
                        "username", user.getUsername(),
                            "lastName", user.getLastName()
                );

                resultList.add(resultMap);
            }

            return ResponseEntity.ok(resultList);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching the results.");
        }
    }

    @DeleteMapping("result/{resultId}")
    public void deleteResult(@PathVariable("resultId") Long resultId)
    {
        this.testResultService.deletebyId(resultId);
    }
}
