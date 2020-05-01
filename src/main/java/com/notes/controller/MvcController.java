package com.notes.controller;

import com.notes.entity.UserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MvcController {

    @RequestMapping(value = "/notes/do_login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ModelAndView performLogin(@RequestBody UserDetails userDetails) {

        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        System.out.println(userDetails);
        //System.out.println(userDetails);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/secure/home.html");
        modelAndView.setStatus(HttpStatus.OK);

        return modelAndView;
    }
}
