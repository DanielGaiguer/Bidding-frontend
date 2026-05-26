/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main.bidding_frontend.service;

import com.main.bidding_frontend.model.LancePostDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class LanceService {
    private final RestClient restClient;
    
    public LanceService(){
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:9000/api")
                .build();
    }
    
    public String criarLance(LancePostDTO lance, String token){
        Long id = lance.getIdEdital();
        String response = restClient.post().uri("editais/{id}/lances", id).header("Authorization", "Bearer " + token).body(lance).retrieve().body(String.class);
        return response;
    }
}
