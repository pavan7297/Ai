package com.google.gemini_api.Services.impl;

import com.google.gemini_api.Services.GoogleServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service("GoogleAiServices")
public class GoogleServicesImpl implements GoogleServices {

	// Access to APIKey and URL {Gemini}

	@Value("${gemini.api.url}")
	private String geminiUrl;

	@Value("${gemini.api.key}")
	private String geminiKey;

	private final WebClient webClient;

	public GoogleServicesImpl(WebClient.Builder webClient) {
		this.webClient = webClient.build();
	}

	@Override
	public String googleAi(String dataPayload) {
		
        //Construct the request payload with this

//      {
//          "contents": [{
//          "parts":[{"text": "Explain how AI works"}]
//      }]
//      }

		Map<String, Object> requestBody = Map.of("contents",
				new Object[] { Map.of("parts", new Object[] { Map.of("text", dataPayload) }) });



        // Make Api Call
		String responce = webClient.post().uri(geminiUrl + geminiKey).header("Content-Type", "application/json")
				.bodyValue(requestBody).retrieve().bodyToMono(String.class).block();

		return responce;
	}

	@Override
	public String cricketAi(String question) {
		if (!question.toLowerCase().contains("cricket")) {
			return "This question doesn't seem to be about cricket. But here's a cricket joke instead:\n\n" +
					"Why did the cricket team bring string to the game?\nBecause they wanted to tie the match!";
		}

		String prompt = "You are a cricket expert. Answer the following question: " + question;
		return sendToGemini(prompt);
	}

	@Override
	public String codingAi(String question) {
		String prompt = "You are a senior software engineer. Explain the following with examples if needed: " + question;
		return sendToGemini(prompt);
	}

	private String sendToGemini(String dataPayload) {
		Map<String, Object> requestBody = Map.of(
				"contents", new Object[]{
						Map.of("parts", new Object[]{
								Map.of("text", dataPayload)
						})
				}
		);


		return webClient.post()
				.uri(geminiUrl + geminiKey)
				.header("Content-Type", "application/json")
				.bodyValue(requestBody)
				.retrieve()
				.bodyToMono(String.class)
				.block();
	}
}
