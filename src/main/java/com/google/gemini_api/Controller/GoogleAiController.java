package com.google.gemini_api.Controller;

import com.google.gemini_api.Services.GoogleServices;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class GoogleAiController {

    @Autowired
    @Qualifier("GoogleAiServices")
    private GoogleServices googleServices;

    @PostMapping("/ai")
    public ResponseEntity<String> GeminiFlash(@RequestBody Map<String, String> payload){
        String question = payload.get("payload");
        String answer = googleServices.googleAi(question);
        return ResponseEntity.ok(answer);
    }

    @PostMapping("/ai/cricket")
    public ResponseEntity<String> cricketAi(@RequestBody Map<String, String> payload) {
        String question = payload.get("payload");
        String answer = googleServices.cricketAi(question);
        return ResponseEntity.ok(answer);
    }

    @PostMapping("/ai/coding")
    public ResponseEntity<String> codingAi(@RequestBody Map<String, String> payload) {
        String question = payload.get("payload");
        String answer = googleServices.codingAi(question);
        return ResponseEntity.ok(answer);
    }
}
