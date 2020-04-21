package com.notes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import com.notes.constants.MongoConstants;
import com.notes.entity.Note;
import com.notes.utils.MongoUtils;
import lombok.SneakyThrows;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDatabaseUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@RestController
@RequestMapping(value = "/notes")
public class MainController {

    @Autowired
    MongoDatabase mongoDatabase;


    @SneakyThrows
    @RequestMapping(value = "/{user}")
    public ResponseEntity getNotesByUser(@PathVariable(value = "user") String userName) {

        Bson filter = MongoUtils.createUserFilter(userName);
        MongoCollection collection =  mongoDatabase.getCollection("notes");
        FindIterable<Note> result = collection.find(filter, Note.class);

        List<Note> notes = MongoUtils.mapIterableToList(result);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        System.out.println(new ObjectMapper().writeValueAsString(notes));
        return new ResponseEntity<List>(notes, headers, HttpStatus.OK);
    }

    @SneakyThrows
    @RequestMapping(value = "/save/{user}", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<String> saveNoteByUser(@PathVariable(value = "user") String userName, @RequestBody Note note) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        MongoCollection collection =  mongoDatabase.getCollection("notes");
        collection.insertOne(MongoUtils.mapEntityToDB(note));

        return new ResponseEntity<String>(note.toString(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/update/{id}", consumes = MediaType.TEXT_PLAIN_VALUE, method = RequestMethod.POST)
    public void updateNoteById(@PathVariable("id") String id, @RequestBody String textBody){
        System.out.println("id = " + id);
        System.out.println("textBody = " + textBody);

    }
}
