package com.exam.controller;

import com.exam.Model.exam.Questions;
import com.exam.Model.exam.Quiz;
import com.exam.service.QuestionService;
import com.exam.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService service;

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


    // eval quiz
    @PostMapping("/eval-quiz")
    public ResponseEntity<?> evalQuiz(@RequestBody List<Questions> questions)
    {

        System.out.println(questions);
        double marksGot=0;
        int correctAnswers=0;
        int attempted=0;

        for(Questions q : questions){

            //single questions
            Questions question = this.service.get(q.getQuesId());
            if(question.getAnswer().equals(q.getGivenAnswer()))
            {
                correctAnswers++;

                double marksSingle = Double.parseDouble(questions.get(0).getQuiz().getMaxMarks())/questions.size();
                marksGot += marksSingle;

            }
            if(q.getGivenAnswer() != null){
                attempted++;


            }
        };
        Map<Object,Object> map = Map.of("marksGot",marksGot,"correctAnswers",correctAnswers,"attempted",attempted);
        return ResponseEntity.ok(map);
    }
}
