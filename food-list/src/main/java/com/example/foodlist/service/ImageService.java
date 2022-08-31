package com.example.foodlist.service;

import com.example.foodlist.domain.FoodImg;
import com.example.foodlist.support.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final FileUtils fileUtils;

    public Map<String,Map<String,Object>> imgUpload(MultipartFile file) {
        return this.imgUpload(file,"");
    }

    public Map<String,Map<String,Object>> imgUpload(MultipartFile file, String location) {
        Map<String, Map<String,Object>> result = new HashMap<>();
        Map<String, Object> body = new HashMap<>();
        Map<String, Object> fileInfo = fileUtils.fileInfo(file);

        if (!Objects.equals(location, "")) {
            location += "/";
        }

        String UPLOAD_PATH = Paths.get("./images/"+location).toAbsolutePath().normalize().toString();
        String URL_PATH = "/images/"+location;
        String originFileName = file.getOriginalFilename();

        result.put("fileInfo",fileInfo);

        if((boolean) fileInfo.get("allow") && originFileName != null) {
            try {
                body.put("error","none");
                body.put("file_path", URL_PATH);
                body.put("file_type", fileInfo.get("type"));
                body.put("file_size", String.valueOf(fileInfo.get("size")));

                String reName = fileUtils.fileReName(originFileName);
                File uploadFile = new File(UPLOAD_PATH, Objects.requireNonNull(originFileName));
                File renameFile = new File(UPLOAD_PATH, reName);

                boolean rename = renameFile.renameTo(uploadFile);

                file.transferTo(renameFile);
                body.put("file", reName);

                result.put("body",body);
            } catch (IOException e) {
                e.printStackTrace();
                body.put("error","IOException");
                result.put("body",body);
            }
        } else if ((boolean) fileInfo.get("allow")){
            body.put("error","Not allow File Type");
            result.put("body",body);
        } else {
            body.put("error","None File");
            result.put("body",body);
        }

        return result;
    }
}
