package com.courses.api.springboot.geeksforgeeks.parser.question;

import com.courses.api.springboot.geeksforgeeks.parser.parser.GFGParser;
import com.courses.api.springboot.geeksforgeeks.parser.question.dto.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class GFGController {

    @Autowired
    public GfgRepository gfgRepository;

    String mainPage = "https://www.geeksforgeeks.org/must-coding-questions-company-wise/";
    String questionUrl = "https://www.geeksforgeeks.org/convert-a-given-binary-tree-to-doubly-linked-list-set-4";
    String questionUrl2 = "https://practice.geeksforgeeks.org/problems/kadanes-algorithm/0";

    @CrossOrigin
    @GetMapping("/questions")
    public HashMap<String, List<String>> getAllQuestionUrls() {
        return GFGParser.getQuestionUrlsCompanyWise(mainPage);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/save/all")
    public void getAllQuestionJson(@RequestBody PostRequestBody postRequestBody) {
        System.out.println("\n[  ==== JumboTail Parser started  ==== ]");
        System.out.println("[  ==== URL : " + postRequestBody.getUrl() + "==== ]");
        List<Question> allQuestions = GFGParser.getAllQuestions(mainPage);
        for (Question q : allQuestions) {
            try {
                this.gfgRepository.save(q);
            } catch (Exception e) {
                System.out.println("[ ==== Exception occurred : " + e.getLocalizedMessage() + " === ]");
            }
        }
        System.out.println("[  ==== JumboTail Parser Finished ==== ]");
        System.out.println("[  ==== Saved " + allQuestions.size() + " rows successfully ==== ]");
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, value = "/api/save")
    public void getQuestion(@RequestBody PostRequestBody postRequestBody) {
        System.out.println("\n[  ==== JumboTail Parser started  ==== ]");
        System.out.println("[  ==== URL : " + postRequestBody.getUrl() + "==== ]");
        Question question = GFGParser.getQuestion(postRequestBody.getUrl());

        try {
            this.gfgRepository.save(question);
        } catch (Exception e) {
            System.out.println("[ ==== Exception occurred : " + e.getLocalizedMessage() + " === ]");
        }

        System.out.println("[  ==== JumboTail Parser Finished ==== ]");
        System.out.println("[  ==== Saved 1 rows successfully ==== ]");
    }
}
