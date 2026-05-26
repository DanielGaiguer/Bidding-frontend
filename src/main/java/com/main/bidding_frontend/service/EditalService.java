/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main.bidding_frontend.service;

import com.main.bidding_frontend.model.EditalDTO;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class EditalService {
    private final RestClient restClient;

    public EditalService() {
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:9000/api")
                .build();
    }
    
    public List<EditalDTO> listarEditais(String token) {
        EditalDTO[] editais = restClient.get()
                .uri("/editais")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(EditalDTO[].class);
       
        return Arrays.asList(editais);
    }
     
    public String criarEdital(EditalDTO edital, String token){
        String response = restClient.get()
                .uri("/editais")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(String.class);
        return response;
    }

}
