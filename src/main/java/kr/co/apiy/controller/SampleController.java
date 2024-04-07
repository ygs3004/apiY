package kr.co.apiy.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sample")
@Tag(name = "Hello", description = "Hello World API")
public class SampleController {

    @PostMapping("/")
    public ResponseEntity<String> helloWorld() {
        return new ResponseEntity<>("hello", HttpStatus.OK);
    }
}
