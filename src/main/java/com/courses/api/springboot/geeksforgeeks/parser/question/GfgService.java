package com.courses.api.springboot.geeksforgeeks.parser.question;

import com.courses.api.springboot.geeksforgeeks.parser.question.dto.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Service
@RestController
public class GfgService {
    @Autowired
    public GfgRepository gfgRepository;

    @CrossOrigin
    @GetMapping("/api/questions")
    public List<Question> getAllQuestionUrls() {
        List<Question> questions = new ArrayList<>();
        this.gfgRepository.findAll().forEach(question ->
                questions.add(question)
        );
        return questions;
    }

    @CrossOrigin
    @GetMapping("/api/question/{id}")
    public Question getQuestion(@PathVariable String id) {
        return this.gfgRepository.findById(id).get();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(method = RequestMethod.GET, value = "/api/question/{id}/status")
    public String getStatus(@PathVariable String id) {
        return this.gfgRepository.findById(id).get().getStatus();
    }

    @CrossOrigin
    @PostMapping("/api/question/{id}/status")
    public void getStatus(@RequestBody Question question, @PathVariable String id) {
        Question previousQuestion = this.gfgRepository.findById(id).get();
        previousQuestion.setStatus(question.getStatus());
        this.gfgRepository.save(previousQuestion);
    }


}
