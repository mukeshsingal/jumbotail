package com.courses.api.springboot.geeksforgeeks.parser.question.dto;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.Constraint;


@Entity
@Table(name = "TopicTagTable")
public class TopicTag {

    public TopicTag(String name) {
        this.name = name;
    }
    public TopicTag(String id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Id
    @Column(name = "tag_id")
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Column(unique = true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public TopicTag() {
    }

}
