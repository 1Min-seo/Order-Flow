package com.project.orderflow.admin.ChatGPT4o;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OpenAiAPIController {
    private final AiCallService aiCallService;

    @PostMapping("/image")
    public String imageAnalysis(@RequestParam MultipartFile image, @RequestParam String requestText)
            throws IOException {
        ChatGPTResponse response = aiCallService.requestImageAnalysis(image, requestText);
        return response.getChoices().get(0).getMessage().getContent();
    }

    @PostMapping("/text")
    public String textAnalysis(@RequestParam String requestText) {
        ChatGPTResponse response = aiCallService.requestTextAnalysis(requestText);
        return response.getChoices().get(0).getMessage().getContent();
    }

    @GetMapping("/analysis")
    public String OrderAnalysis(@RequestParam Integer requestID) {
        return aiCallService.analyzeOrdersByTableId(requestID);
    }
    
}