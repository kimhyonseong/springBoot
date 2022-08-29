package com.example.foodlist.restController;

import com.example.foodlist.support.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class ImgRestApiController {
    private final ServletContext servletContext;

    @PostMapping("rest/foodImgUpload")
    public ResponseEntity<?> imgUpload(
            @RequestPart(value = "file",required=false) MultipartFile file
    ) throws Exception{
        FileUtils fileUtils = new FileUtils();
        boolean isImg = fileUtils.isImgMimeType(file);

        long fileSize = file.getSize();
        final String UPLOAD_PATH = "C:\\Users\\khs13\\Desktop\\foodListSpringBoot\\food-list\\src\\main\\resources\\static\\image\\tmp";
        final String webRoot = servletContext.getRealPath("/");  // webapp으로 설정되어있어 사용하면 안됨
        System.out.println("file origin name : "+file.getOriginalFilename());
        System.out.println("isImg : "+isImg);
        System.out.println("file size : "+fileSize);
        System.out.println("webRoot : "+webRoot);

        if(isImg) {
            try {
                File newFile = new File(UPLOAD_PATH, Objects.requireNonNull(file.getOriginalFilename()));
                file.transferTo(newFile);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(file.getOriginalFilename());
    }
}
