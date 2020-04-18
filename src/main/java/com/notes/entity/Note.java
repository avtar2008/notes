package com.notes.entity;

import com.notes.constants.MongoConstants;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.Date;

@Data
public class Note {

    @BsonId
    ObjectId id;

    @BsonProperty(value = MongoConstants.NAME)
    String name;

    @BsonProperty(value = MongoConstants.GENDER)
    String gender;

    @BsonProperty(value = MongoConstants.TEXT)
    String text;

    @BsonProperty(value = MongoConstants.CREATED)
    Date created;
}
