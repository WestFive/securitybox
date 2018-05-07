package com.imooc.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class upLoadControllerTest {


    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;


    @Before
    public void Setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void upload() throws Exception {

        String str = mockMvc.perform(MockMvcRequestBuilders.fileUpload("/file").file(new MockMultipartFile("file","test.txt","multipart/form-data","hello upload".getBytes("UTF-8"))
        )).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        System.out.println(str);
    }



}