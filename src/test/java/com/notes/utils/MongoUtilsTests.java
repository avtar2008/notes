package com.notes.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MongoUtilsTests {

    @Test
    public void createHashTest(){

        System.out.println("sadwasdw : " + MongoUtils.createHash( "user123"));
    }
}
