package com.example.foodlist.restController;

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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class ImgRestApiController {
    private final ServletContext servletContext;

    @PostMapping("rest/foodImgUpload")
    public ResponseEntity<?> imgUpload(
            @RequestPart(value = "file",required=false) MultipartFile file,
            HttpServletRequest request
    ) throws Exception {
        FileUtils fileUtils = new FileUtils();
        Map<String, Object> map = fileUtils.isImgMimeType(file);
        boolean isImg = (boolean) map.get("allow");
        String mimeType = (String) map.get("type");

        long fileSize = file.getSize();
        String UPLOAD_PATH = servletContext.getRealPath("/")+"../resources/static/image/tmp";
        String URL_PATH = "http://localhost:8080/image/tmp/";
        String originFileName = file.getOriginalFilename();

        if(isImg && originFileName != null) {
            try {
                Map<String, String> body = new HashMap<>();
                body.put("file_path", URL_PATH);
                body.put("file_type",mimeType);
                body.put("file_size", String.valueOf(fileSize));

                String reName = fileUtils.fileReName(originFileName);
                File uploadFile = new File(UPLOAD_PATH, Objects.requireNonNull(originFileName));
                File renameFile = new File(UPLOAD_PATH,reName);

                boolean rename = renameFile.renameTo(uploadFile);

                file.transferTo(renameFile);
                body.put("file",reName);

                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(body);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }
}
