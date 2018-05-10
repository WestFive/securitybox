package com.westf.securityd.controller;

import com.westf.securityd.bean.SimpleResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class tempController {

    @GetMapping("/temp")
    private SimpleResponse get()
    {
        return  new SimpleResponse("OK");
    }


}
