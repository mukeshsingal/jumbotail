package com.courses.api.springboot.geeksforgeeks.parser.parser.impl;

import com.courses.api.springboot.geeksforgeeks.parser.question.GfgRepository;
import com.courses.api.springboot.geeksforgeeks.parser.question.dto.*;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PracticeQuestionParser implements BaseParser {

    @Autowired
    public TopicsRepository topicsRepository;
    @Autowired
    public CompanyTagsRepository companyTagsRepository;
    @Autowired
    public GfgRepository gfgRepository;

    public String getQuestionTitle(Element document) {
        Elements entryContent = document.getElementsByClass("problemTitle");
        Element elements = entryContent.first();
        return elements.text();
    }

    public Set<CompanyTag> getCompanyTags(Element document) {

        Set<CompanyTag> tagList = new HashSet<>();

        Element fullPageDiv = document.getElementsByClass("showTag").first();
        Elements children = fullPageDiv.parent().parent().child(1).children();

        for (Element e: children) {
            if(!e.text().equals("Company Tags")) {
                CompanyTag tag = this.companyTagsRepository.findByName(getText(e.text()));
                if(tag == null) {
                    System.out.println("tag is null");
                    CompanyTag newTag = new CompanyTag(getText(e.text()));
                    tagList.add(newTag);
                    this.companyTagsRepository.save(newTag);
                }
                else {
                    tagList.add(tag);
                }
            }
        }
        return tagList;
    }

    public Set<TopicTag> getQuestionTags(Element document) {
        Set<TopicTag> tagList = new HashSet<>();

        Element fullPageDiv = document.getElementsByClass("showTag").first();
        Elements children = fullPageDiv.parent().children();

        for (Element e: children) {
            if(!e.text().equals("Show Topic Tags") && !e.text().equals("Hide Topic Tags")) {

                TopicTag tag = this.topicsRepository.findByName(getText(e.text()));
                if(tag == null) {
                    System.out.println("tag is null");
                    TopicTag newTag = new TopicTag(getText(e.text()));
                    tagList.add(newTag);
                    this.topicsRepository.save(newTag);
                }
                else {
                    tagList.add(tag);
                }
            }
        }
        return tagList;
    }

    public HashMap<String, String> getDifficultyLevel(Element document) {
        Element fullPageDiv = document.getElementsByClass("fullPageDiv").first();
        Element secondDiv = fullPageDiv.child(1);
        Element contentRows = secondDiv.child(0).child(0).child(0);
        Element difficultyTag = contentRows.child(0).child(1);
        Element e = difficultyTag.children().first();

        String level = e.text();

        HashMap<String, String> map = new HashMap();
        if (level.contains(DifficultyLevel.Basic.toString())) {
            map.put("Difficulty", DifficultyLevel.Basic.toString());
        } else if (level.contains(DifficultyLevel.Easy.toString())) {
            map.put("Difficulty", DifficultyLevel.Easy.toString());
        } else if (level.contains(DifficultyLevel.Medium.toString())) {
            map.put("Difficulty", DifficultyLevel.Medium.toString());
        } else if (level.contains(DifficultyLevel.Hard.toString())) {
            map.put("Difficulty", DifficultyLevel.Hard.toString());
        } else {
            map.put("Difficulty", DifficultyLevel.Expert.toString());
        }

        String questionRating = level.substring(level.indexOf("Marks: ")).split(" ")[1];
        map.put("questionRating", questionRating);
        return map;
    }
}
