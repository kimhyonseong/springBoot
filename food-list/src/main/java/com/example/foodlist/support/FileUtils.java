package com.example.foodlist.support;

import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class FileUtils {
    public Map<String,Object> fileInfo(MultipartFile multipartFile){
        final List<String> ALLOW_EXT = Arrays.asList("image/jpeg","image/pjpeg","image/png");
        Map<String,Object> map = new HashMap<>();

        try {
            InputStream inputStream = multipartFile.getInputStream();
            String mimeType = new Tika().detect(inputStream);

            System.out.println(mimeType);
            map.put("type",mimeType);
            map.put("size",multipartFile.getSize());
            map.put("allow",ALLOW_EXT.stream().anyMatch(matchType -> matchType.equalsIgnoreCase(mimeType)));

            return map;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String fileReName(String originFileName) {
        String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        int expPos = originFileName.lastIndexOf(".");
        String exp = originFileName.substring(expPos,originFileName.length());

        return newFileName+exp;
    }
}
