package com.courses.api.springboot.geeksforgeeks.parser.parser.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParserFactory {
    @Autowired
    private SimpleQuestionParser simpleParser;
    @Autowired
    private PracticeQuestionParser practiceParser;

    public BaseParser getBaseQuestionParser(String url) {
        if (url.startsWith("https://practice.geeksforgeeks.org/problems/")) {
            return practiceParser;
        }
        else {
            return simpleParser;
        }

    }
}
