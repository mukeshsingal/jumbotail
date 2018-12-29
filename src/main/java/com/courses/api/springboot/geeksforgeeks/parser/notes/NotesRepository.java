package com.courses.api.springboot.geeksforgeeks.parser.notes;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotesRepository extends CrudRepository<Notes, String> {
    Notes findByQuestionId(String questionId);
}
