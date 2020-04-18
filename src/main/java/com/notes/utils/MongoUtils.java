package com.notes.utils;

import com.google.common.collect.Lists;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.notes.constants.MongoConstants;
import com.notes.entity.Note;
import org.bson.conversions.Bson;

import java.util.List;

public class MongoUtils {

    public static List<Note> mapIterableToList(FindIterable<Note> mongoNotes){
        final List<Note> notes = Lists.newArrayList();

        if(mongoNotes == null){
            return notes;
        }

        for(Note note : mongoNotes){
            if(note == null){
                continue;
            }

            notes.add(note);
        }

        return notes;
    }

    public static Bson createUserFilter(String userName) {
        return Filters.eq(MongoConstants.NAME, userName);
    }
}
