package kr.co.apiy.global.service;

import kr.co.apiy.global.sample.SampleDto;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WebRequestServiceTest {

    @Autowired
    WebRequestService webRequestService;

    @DisplayName("Post 요청 테스트")
    @Test
    public void postTest() {
        String baseUri = "http://localhost:8080";
        String subUri = "/sample/";
        SampleDto.Request request = SampleDto.Request.builder().parameter1("param1").parameter2("param2").build();
//        SampleDto.Request request = SampleDto.Request.builder().parameter1("param1").build();

        JSONObject result = webRequestService.post(baseUri, subUri, request);

        System.out.println(result);
    }

}
