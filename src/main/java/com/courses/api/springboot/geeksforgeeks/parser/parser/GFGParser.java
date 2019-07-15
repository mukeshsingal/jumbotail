package com.courses.api.springboot.geeksforgeeks.parser.parser;

import com.courses.api.springboot.geeksforgeeks.parser.question.GfgRepository;
import com.courses.api.springboot.geeksforgeeks.parser.question.dto.Question;
import com.courses.api.springboot.geeksforgeeks.parser.parser.impl.BaseParser;
import com.courses.api.springboot.geeksforgeeks.parser.parser.impl.ParserFactory;

import com.courses.api.springboot.geeksforgeeks.parser.question.dto.TopicsRepository;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class GFGParser {

    @Autowired
    public ParserFactory factory;

    public List<Question> getAllQuestions(String mainPageUrl) {
        List<Question> questions = new ArrayList<>();
        HashMap<String, List<String>> questionsUrlsByCompany = getQuestionUrlsCompanyWise(mainPageUrl);
        questionsUrlsByCompany.forEach((companyName, questionUrls) -> {
            questionUrls.forEach(url -> {
                System.out.println("[ === Processing " + companyName + " : " + url + " === ]");
                questions.add(factory.getBaseQuestionParser(url).getQuestion(url));
            });
        });

        return questions;
    }

    public HashMap<String, List<String>> getQuestionUrlsCompanyWise(String mainPageUrl) {

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

    private String nextCompanyNameExist(Elements elements, int i) {
        for (int j = i; j < elements.size(); j++) {
            Element e = elements.get(i);
            if (e.nodeName().equals("p") && e.children().size() > 0) {
                return e.text();
            }
        }
        return null;
    }


    public Question getQuestion(String url) {
        BaseParser baseQuestion = factory.getBaseQuestionParser(url);
        return baseQuestion.getQuestion(url);
    }

    public List<Question> getQuestionsByCompanyName(String name) {

        ArrayList<Question> questions = new ArrayList<>();
        try {
            int pageNumber = 1;
            HttpPost request = getCompanyQuestionRequest(name, pageNumber);
            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(request);
            String responseString = new BasicResponseHandler().handleResponse(response);
            Document document = Jsoup.parse(responseString);
            Elements elements = document.getElementsByClass("problem-block");
            for (Element e: elements) {
                questions.add(getQuestion(e.child(0).attr("href")));
                System.out.println("Saved " + e.child(0).attr("href"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //BaseParser baseQuestion = ParserFactory.getBaseQuestionParser(url);
        //return
        // baseQuestion.getQuestion(url);
        return questions;
    }


    private static HttpPost getCompanyQuestionRequest (String companyName, int pageNumber) throws UnsupportedEncodingException {
        //Define a postRequest request
        HttpPost postRequest = new HttpPost("https://practice.geeksforgeeks.org/ajax/practicePageAjax.php");

        //Set the API media type in http content-type header
        //postRequest.addHeader("content-type", "application/xml");

        //Set the request post body
        StringEntity userEntity = new StringEntity("company%5B%5D=" + companyName + "&page=" + pageNumber);
        postRequest.setEntity(userEntity);

        return postRequest;
    }
}
