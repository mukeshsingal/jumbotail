package com.courses.api.springboot.geeksforgeeks.parser.notes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class NotesController {

    @Autowired
    public NotesRepository notesRepository;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/api/note/{id}")
    public Notes getQuestion(@PathVariable String id) {
        return this.notesRepository.findByQuestionId(id);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, value = "/api/notes")
    public void getQuestion(@RequestBody Notes note) {
        System.out.println(note.getNotes());
        System.out.println(note.getQuestionId());
        try {
            this.notesRepository.save(note);
        } catch (Exception e) {
            System.out.println("[ ==== Exception occurred : " + e.getLocalizedMessage() + " === ]");
        }
        System.out.println("[  ==== Saved 1 note " + note.getNotes() + " ==== ]");
    }
}
