package com.example.foodpreference.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Component
public class FileUtil {
    public String makeFileName(String originName) {
        String fileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        int expPos = originName.lastIndexOf(".");
        fileName += originName.substring(expPos,originName.length());

        return fileName;
    }

    public Map<String,Object> fileInfo(MultipartFile file) {
        Map<String, Object> fileInfo = new HashMap<>();

        fileInfo.put("contentType",file.getContentType());
        fileInfo.put("originName",file.getOriginalFilename());
        fileInfo.put("size",file.getSize());

        return fileInfo;
    }
}
