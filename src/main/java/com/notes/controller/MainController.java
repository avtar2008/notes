package com.notes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.notes.entity.Note;
import com.notes.entity.UserDetails;
import com.notes.utils.MongoUtils;
import lombok.SneakyThrows;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.ws.Response;
import java.util.Date;
import java.util.List;

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

        Note updateNote = new Note();
        updateNote.setId(new ObjectId(id));
        updateNote.setLast_updated(new Date());
        updateNote.setText(textBody.split(",")[0].trim());
        updateNote.setCreated(new Date(textBody.split(",")[1].trim()));
        updateNote.setName("user2");
        //todo : set user as well


        MongoCollection collection =  mongoDatabase.getCollection("notes");
        Bson filter = MongoUtils.updateFilterById(id);
        Document document = MongoUtils.mapEntityToDB(updateNote);
        collection.findOneAndReplace(filter, document);

    }

    @RequestMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity signUp(@RequestBody UserDetails userDetails){
        System.out.println("userDetails : " +  userDetails);

        MongoCollection<UserDetails> userCollection =  mongoDatabase.getCollection("users", UserDetails.class);
        String hashedPass = MongoUtils.createHash(userDetails.getPassword());
        userDetails.setPassword(hashedPass);
        userCollection.insertOne(userDetails);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/do_login", method = RequestMethod.POST)
    public ModelAndView performLogin(){
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        //System.out.println(userDetails);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        return modelAndView;
    }
}
