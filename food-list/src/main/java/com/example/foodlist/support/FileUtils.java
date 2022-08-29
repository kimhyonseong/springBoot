package com.example.foodlist.support;

import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

@Component
public class FileUtils {
    public boolean isImgMimeType(MultipartFile multipartFile){
        final List<String> ALLOW_EXT = Arrays.asList("image/jpeg","image/pjpeg","image/png");
        try {
            InputStream inputStream = multipartFile.getInputStream();
            String mimeType = new Tika().detect(inputStream);

            System.out.println(mimeType);

            return ALLOW_EXT.stream().anyMatch(matchType -> matchType.equalsIgnoreCase(mimeType));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
