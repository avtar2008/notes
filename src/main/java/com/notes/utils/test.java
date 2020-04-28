package com.notes.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class test {

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ssz");
        System.out.println(sdf.format(new Date()));
    }
}
