package com.bitirmeproje.service.pythonservice;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class PythonApiService {

    public List<Integer> getAnketOnerileri(int kullaniciId) {
        // 1. Python API URL'si
        String pythonApiUrl = "http://localhost:5001/oner";

        // 2. Request Body (JSON)
        Map<String, Integer> requestBody = new HashMap<>();
        requestBody.put("kullanici_id", kullaniciId);

        // 3. Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 4. Request Oluşturma
        HttpEntity<Map<String, Integer>> requestEntity =
                new HttpEntity<>(requestBody, headers);

        // 5. RestTemplate ile POST isteği
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.exchange(
                pythonApiUrl,
                HttpMethod.POST,
                requestEntity,
                Map.class
        );

        // 6. Response'dan önerileri al
        List<Map<String, Object>> oneriler = (List<Map<String, Object>>) response.getBody().get("oneriler");
        return oneriler.stream()
                .map(oner -> (Integer) oner.get("anket_id"))
                .collect(Collectors.toList());
    }
}
