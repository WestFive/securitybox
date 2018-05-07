package com.imooc.controller;


import com.imooc.dto.FileInfo;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

@RestController
@RequestMapping("/file")
public class upLoadController {


    @PostMapping
    public FileInfo upload(MultipartFile file) throws IOException
    {
           System.out.println(file.getName());
           String  folder = "D://";
        File localFile = new File(folder,new Date().getTime()+".txt");
        file.transferTo(localFile);
        return  new FileInfo(localFile.getAbsolutePath());
    }


    @GetMapping("/{id}")
    public void download(@PathVariable String id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException
    {
        try(InputStream inputStream = new FileInputStream(new File("D://",id+".txt"));
            OutputStream outputStream = httpServletResponse.getOutputStream();){
            httpServletResponse.setContentType("application/x-download");
            httpServletResponse.addHeader("Content-Disposition","attachment;filename=test.txt");
            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();

        }
    }
}
