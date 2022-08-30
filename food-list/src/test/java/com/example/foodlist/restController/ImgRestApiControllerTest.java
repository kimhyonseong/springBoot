package com.example.foodlist.restController;

import com.example.foodlist.support.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.ServletContext;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ImgRestApiControllerTest {
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private FileUtils fileUtils;

    @Test
    void pathTest() {
        String path = servletContext.getContextPath();
        String path1 = servletContext.getRealPath("../");
        String path2 = servletContext.getRealPath("/");
        String path3 = servletContext.getRealPath("../resources/static/image/tmp");

        System.out.println("path : "+path);
        System.out.println("path1 : "+path1);
        System.out.println("path2 : "+path2);
        System.out.println("path2 : "+path3);
    }

    @Test
    void fileNameTest() {
        String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        int expPos = "hotdog.hot.jpg".lastIndexOf(".");
        String exp = "hotdog.hot.jpg".substring(expPos,"hotdog.hot.jpg".length());

        System.out.println("newFileName : "+newFileName);
        System.out.println("expPos : "+expPos);
        System.out.println("exp : "+exp);
        System.out.println("full name : "+newFileName+exp);

        System.out.println("method - fileReName : "+fileUtils.fileReName("hotdog.hot.jpg"));
    }
}