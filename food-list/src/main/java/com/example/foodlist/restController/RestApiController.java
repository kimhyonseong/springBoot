package com.example.foodlist.restController;

import com.example.foodlist.annotation.NoSpecial;
import com.example.foodlist.domain.Member;
import com.example.foodlist.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RestApiController {
    private final MemberService memberService;

    @PostMapping("/rest/register")
    public ResponseEntity<?> registerSuccess(
            @Valid @RequestBody Member member, Errors errors, BindingResult bindingResult
    ) {
        HttpHeaders headers = new HttpHeaders();
        StringBuilder sb = new StringBuilder();
        System.out.println(errors);

        Map<String, String> errorMap = new HashMap<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                FieldError fieldError = (FieldError) objectError;
                String message = objectError.getDefaultMessage();

                System.out.println("field : " + fieldError.getField());
                System.out.println("message : "+message);

                errorMap.put(fieldError.getField(),message);

                sb.append("field : ").append(fieldError.getField()).append("\n");
                sb.append("message : ").append(message).append("\n");
            });
            System.out.println(errorMap.get("memberId"));
            System.out.println(errorMap);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(sb.toString());
        }

        return ResponseEntity.ok().headers(headers).body(member);
    }
}
