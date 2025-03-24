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

		Map<String, Object> requestBody = Map.of("contents",
				new Object[] { Map.of("parts", new Object[] { Map.of("text", dataPayload) }) });

		String responce = webClient.post().uri(geminiUrl + geminiKey).header("Content-Type", "application/json")
				.bodyValue(requestBody).retrieve().bodyToMono(String.class).block();

		return responce;
	}
}
