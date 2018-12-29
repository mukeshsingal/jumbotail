package com.courses.api.springboot.geeksforgeeks.parser.question;

import com.courses.api.springboot.geeksforgeeks.parser.question.dto.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GfgRepository extends CrudRepository<Question, String> {

}
