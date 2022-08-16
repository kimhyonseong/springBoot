package com.example.foodlist.clientConroller;

import com.example.foodlist.domain.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RegisterController {
    @PostMapping("/register")
    public ResponseEntity<List<Member>> register() {
        ResponseEntity<List<Member>> response = null;

        return response;
    }
}
