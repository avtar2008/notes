package com.notes.utils;

import com.google.common.collect.Lists;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.notes.constants.MongoConstants;
import com.notes.entity.Note;
import com.notes.entity.UserDetails;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class MongoUtils {

    public static List<Note> mapNoteIterableToList(FindIterable<Note> mongoNotes) {
        final List<Note> notes = Lists.newArrayList();

        if (mongoNotes == null) {
            return notes;
        }

        for (Note note : mongoNotes) {
            if (note == null) {
                continue;
            }
            System.out.println("id = " + note.getId());
            notes.add(note);
        }

        return notes;
    }

    public static List<UserDetails> mapUserIterableToList(FindIterable<UserDetails> mongoUser) {
        final List<UserDetails> users = Lists.newArrayList();

        if (mongoUser == null) {
            return users;
        }

        for (UserDetails user : mongoUser) {
            if (user == null) {
                continue;
            }
//            System.out.println("id = " + user.getId());
            users.add(user);
        }

        return users;
    }

    public static Bson createUserFilter(String userName) {
        return Filters.eq(MongoConstants.NAME, userName);
    }

    public static Document mapEntityToDB(Note note) {
        Document document = new Document();

        document.put(MongoConstants.NAME, note.getName());
        document.put(MongoConstants.CREATED, note.getCreated());
        //document.put(MongoConstants.GENDER, note.getGender());
        document.put(MongoConstants.TEXT, note.getText());
        document.put(MongoConstants.LAST_UPDATED, note.getLast_updated());
        if (note.getId() == null) {
            note.setId(new ObjectId());
        }
        document.put(MongoConstants.ID, note.getId());
        return document;
    }

    public static Bson updateFilterById(String id) {
        return Filters.eq(MongoConstants.ID, new ObjectId(id));
    }

    public static String createHash(String password) {
        String hash = null;

        try {

            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            hash = no.toString(16);
            while (hash.length() < 32) {
                hash = "0" + hash;
            }
            System.out.println("hash : " + hash);
            return hash;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getApplicationRole(String userName, String password) {
        System.out.println("sdasdwwd");
        return "USER";
    }

    public static Bson createUserFilter(String username, String password) {
        return Filters.and(
                Filters.eq(MongoConstants.USERNAME, username),
                Filters.eq(MongoConstants.PASSWORD, password));


    }
}
