package com.example.foodlist.restController;

import com.example.foodlist.service.ImageService;
import com.example.foodlist.support.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class ImgRestApiController {
    private final ServletContext servletContext;
    private final ImageService imageService;

    @PostMapping("rest/foodImgUpload")
    public ResponseEntity<?> imgUpload(
            @RequestPart(value = "file",required=false) MultipartFile file,
            HttpServletRequest request
    ) {

        Map<String,Map<String, Object>> uploadResult = imageService.imgUpload(file,"food");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(uploadResult.get("body"));
    }
}
