package com.courses.api.springboot.geeksforgeeks.parser.question.dto;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicsRepository extends CrudRepository<TopicTag, String> {

    @Query("SELECT new com.courses.api.springboot.geeksforgeeks.parser.question.dto.TopicTag(u.id, u.name) FROM TopicTag u WHERE u.name = :name")
    TopicTag findByName(@Param("name") String name);
}
