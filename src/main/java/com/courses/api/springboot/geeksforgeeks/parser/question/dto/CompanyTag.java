package com.courses.api.springboot.geeksforgeeks.parser.question.dto;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "CompanyTagTable")
public class CompanyTag {
    @Id
    @Column(name = "company_id")
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Column(unique = true)
    private String name;

    public CompanyTag() {}

    public CompanyTag(String name) {
        this.name = name;
    }
    public CompanyTag(String id, String name) {
        this.name = name; this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }
}
