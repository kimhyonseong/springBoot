package com.example.foodpreference.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class FileUtil {
    public String makeFileName(String originName) {
        String fileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        int expPos = originName.lastIndexOf(".");
        fileName += originName.substring(expPos,originName.length());

        return fileName;
    }

    public Map<String,Object> uploadFileInfo(MultipartFile file) {
        Map<String, Object> fileInfo = new HashMap<>();

        try {
            // 정보들 map에 저장
            System.out.println(file.getOriginalFilename());
            System.out.println(Arrays.toString(file.getBytes()));
            System.out.println(file.getOriginalFilename());
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("업로드 불가능 파일 " + file.getName());
            fileInfo.put("status","error");
        }

        return fileInfo;
    }
}
