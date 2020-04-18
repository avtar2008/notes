package com.notes.controller;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.notes.entity.Note;
import com.notes.utils.MongoUtils;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@RestController
@RequestMapping(value = "/notes")
public class MainController {

    @Autowired
    MongoDatabase mongoDatabase;

    @RequestMapping(value = "/{user}")
    public ResponseEntity getNotesByUser(@PathVariable(value = "user") String userName) {

        Bson filter = MongoUtils.createUserFilter(userName);
        MongoCollection collection =  mongoDatabase.getCollection("notes");

        FindIterable<Note> result = collection.find(filter, Note.class);

        List<Note> notes = MongoUtils.mapIterableToList(result);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");

        return new ResponseEntity<List>(notes, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/save/{user}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveNoteByUser(@PathVariable(value = "user") String userName, @RequestBody Note note) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");

        return new ResponseEntity<String>(note.toString(), headers, HttpStatus.OK);
    }
}
