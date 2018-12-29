package com.courses.api.springboot.geeksforgeeks.parser.parser;

import com.courses.api.springboot.geeksforgeeks.parser.question.dto.Question;
import com.courses.api.springboot.geeksforgeeks.parser.parser.impl.BaseParser;
import com.courses.api.springboot.geeksforgeeks.parser.parser.impl.ParserFactory;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GFGParser {

    public static List<Question> getAllQuestions(String mainPageUrl) {
        List<Question> questions = new ArrayList<>();
        HashMap<String, List<String>> questionsUrlsByCompany = getQuestionUrlsCompanyWise(mainPageUrl);
        questionsUrlsByCompany.forEach((companyName, questionUrls) -> {
            questionUrls.forEach(url -> {
                System.out.println("[ === Processing " + companyName + " : " + url + " === ]");
                questions.add(ParserFactory.getBaseQuestionParser(url).getQuestion(url));
            });
        });

        return questions;
    }

    public static HashMap<String, List<String>> getQuestionUrlsCompanyWise(String mainPageUrl) {

        HashMap<String, List<String>> questionsByCompany = new HashMap<>();
        try {
            Document document = Jsoup.connect(mainPageUrl).get();
            Element element = document.getElementsByClass("entry-content").first();
            Elements elements = element.children();

            for (int i = 3; i < elements.size(); i++) {
                List<String> companyList = new ArrayList<>();

                Element e = elements.get(i);
                String companyName = nextCompanyNameExist(elements, i);

                if (companyName == null)
                    break;

                companyName = companyName.replace(":", "").trim();

                while (e.nodeName() != "ol" && i < elements.size()) {
                    e = elements.get(i);
                    i++;
                }

                if (i < elements.size()) {
                    Elements links = e.children();
                    links.forEach(l -> {
                        companyList.add(l.child(0).attr("href"));
                    });
                }

                i++;

                questionsByCompany.put(companyName, companyList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return questionsByCompany;
    }

    private static String nextCompanyNameExist(Elements elements, int i) {
        for (int j = i; j < elements.size(); j++) {
            Element e = elements.get(i);
            if (e.nodeName().equals("p") && e.children().size() > 0) {
                return e.text();
            }
        }
        return null;
    }


    public static Question getQuestion(String url) {
        BaseParser baseQuestion = ParserFactory.getBaseQuestionParser(url);
        return baseQuestion.getQuestion(url);
    }
}
