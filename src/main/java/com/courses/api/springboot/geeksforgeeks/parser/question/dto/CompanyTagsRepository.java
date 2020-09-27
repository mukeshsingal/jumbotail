package com.courses.api.springboot.geeksforgeeks.parser.question.dto;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyTagsRepository extends CrudRepository<CompanyTag, String> {

    @Query("SELECT new com.courses.api.springboot.geeksforgeeks.parser.question.dto.CompanyTag(u.id, u.name) FROM CompanyTag u WHERE u.name = :name")
    CompanyTag findByName(@Param("name") String name);
}
