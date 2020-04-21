package com.notes.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.notes.constants.MongoConstants;
import lombok.Data;
import lombok.ToString;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Data

public class Note {

    @BsonId
    @JsonSerialize(using = ToStringSerializer.class)
    ObjectId id;

    @BsonProperty(value = MongoConstants.NAME)
    String name;

    @BsonProperty(value = MongoConstants.GENDER)
    String gender;

    @BsonProperty(value = MongoConstants.TEXT)
    String text;

    @BsonProperty(value = MongoConstants.CREATED)
    Date created;

    @BsonProperty(value = MongoConstants.LAST_UPDATED)
    Date last_updated;
}
