package com.courses.api.springboot.geeksforgeeks.parser.question;

import com.courses.api.springboot.geeksforgeeks.parser.parser.GFGParser;
import com.courses.api.springboot.geeksforgeeks.parser.question.dto.Question;
import com.courses.api.springboot.geeksforgeeks.parser.question.dto.TopicsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class GFGController {

    @Autowired
    public GfgRepository gfgRepository;

    @Autowired
    public TopicsRepository topicsRepository;

    @Autowired GFGParser parser;

    String mainPage = "https://www.geeksforgeeks.org/must-coding-questions-company-wise/";
    String questionUrl = "https://www.geeksforgeeks.org/convert-a-given-binary-tree-to-doubly-linked-list-set-4";
    String questionUrl2 = "https://practice.geeksforgeeks.org/problems/kadanes-algorithm/0";

    @CrossOrigin
    @GetMapping("/questions")
    public HashMap<String, List<String>> getAllQuestionUrls() {
        return parser.getQuestionUrlsCompanyWise(mainPage);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/save/all")
    public void getAllQuestionJson(@RequestBody PostRequestBody postRequestBody) {
        System.out.println("\n[  ==== JumboTail Parser started  ==== ]");
        System.out.println("[  ==== URL : " + postRequestBody.getUrl() + "==== ]");
        List<Question> allQuestions = parser.getAllQuestions(mainPage);
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
        Question question = parser.getQuestion(postRequestBody.getUrl());

        try {
            this.gfgRepository.save(question);
        } catch (Exception e) {
            System.out.println("[ ==== Exception occurred : " + e.getLocalizedMessage() + " === ]");
        }

        System.out.println("[  ==== JumboTail Parser Finished ==== ]");
        System.out.println("[  ==== Saved 1 rows successfully ==== ]");
    }


    @RequestMapping(method = RequestMethod.POST, value = "/api/save/company")
    public void getAllCompanyQuestions(@RequestBody CompanyQuestionRequestBody postRequestBody) {
        System.out.println("\n[  ==== JumboTail Parser started  ==== ]");
        System.out.println("[  ==== URL : " + postRequestBody.getName() + "==== ]");
        List<Question> allQuestions = parser.getQuestionsByCompanyName(postRequestBody.getName());
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
}
