package com.brainworks.rest.service.impl;

import com.brainworks.rest.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //file name
        String name= file.getOriginalFilename ();
        //abc.png

        //random name generate file
        String randomId= UUID.randomUUID ().toString ();
        String filName=randomId.concat (name.substring (name.lastIndexOf (".")));

        //full path
        String filePath=path+ File.separator+filName;

        //create folder if not created
        File f =new File (path);
        if (!f.exists ()){
            f.mkdir ();
        }

        //file copy
        Files.copy (file.getInputStream (), Paths.get(filePath));

        return filName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullpath=path+File.separator+fileName;
        return new FileInputStream (fullpath);
    }
}
