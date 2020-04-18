package com.notes.init;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.notes.entity.MongoConnParams;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Configuration
public class ConfigInitializer {

    @Autowired
    MongoClient mongoClient;

    @Bean
    public MongoDatabase mongoDatabase() {
        final CodecProvider codecProvider = PojoCodecProvider.builder().automatic(true).build();
        final CodecRegistry codecRegistry = fromRegistries(mongoClient.getDefaultCodecRegistry(), fromProviders(codecProvider));
        return mongoClient
                .getDatabase("notes")
                .withCodecRegistry(codecRegistry);
    }
}
