package com.example.foodlist.restController;

import com.example.foodlist.support.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.validation.Valid;
import java.io.InputStream;

@RestController
public class ImgRestApiController {
//    @PostMapping("rest/foodImgUpload")
//    public ResponseEntity<?> imgUpload(
//            //@Valid @RequestParam("file") MultipartFile file
//            MultipartHttpServletRequest multipartHttpServletRequest
//            ) throws Exception{
//        MultipartFile file = multipartHttpServletRequest.getFile("file");
//        System.out.println("------------------------------");
//        System.out.println(multipartHttpServletRequest);
//        System.out.println(multipartHttpServletRequest.getFile("file"));
//        assert file != null;
//        String result = "img_url : "+file.getOriginalFilename();
//
//        return ResponseEntity.status(HttpStatus.OK).body(result);
//    }

    @PostMapping("rest/foodImgUpload")
    public ResponseEntity<?> imgUpload(
            @RequestPart(value = "file",required=false) MultipartFile file
    ) throws Exception{
        FileUtils fileUtils = new FileUtils();
        boolean isImg = fileUtils.isImgMimeType(file);

        long fileSize = file.getSize();
        System.out.println("file origin name : "+file.getOriginalFilename());
        System.out.println("isImg : "+isImg);
        System.out.println("file size : "+fileSize);

        return ResponseEntity.status(HttpStatus.OK).body(file.getOriginalFilename());
    }
}
