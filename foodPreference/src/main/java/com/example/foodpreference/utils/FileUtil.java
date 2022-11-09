package com.example.foodpreference.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FileUtil {
    public String makeFileName(String originName) {
        String fileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        int expPos = originName.lastIndexOf(".");
        fileName += originName.substring(expPos,originName.length());

        return fileName;
    }
}
