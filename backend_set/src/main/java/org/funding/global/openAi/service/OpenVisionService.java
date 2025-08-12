package org.funding.global.openAi.service;

import lombok.RequiredArgsConstructor;
import org.funding.global.openAi.client.OpenAIVisionClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenVisionService {

    private final OpenAIVisionClient openAIVisionClient;

    public String analyzeImage(String imageUrl, String prompt) {
        return openAIVisionClient.analyzeImageWithPrompt(imageUrl, prompt);
    }
}
