/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main.bidding_frontend.service;

import com.main.bidding_frontend.model.EditalDTO;
import com.main.bidding_frontend.model.UserDTO;
import com.main.bidding_frontend.model.UserRequestDTO;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {
    private final RestTemplate restTemplate;
    private final String BASE_URL = "http://localhost:9000";
    
    public ApiService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }
    
    public List<EditalDTO> listarEditais(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity entity = new HttpEntity(headers);
        
        return restTemplate.exchange(BASE_URL + "/api/editais", HttpMethod.GET, entity, new ParameterizedTypeReference<List<EditalDTO>>() {}).getBody();
    }
    
    public String logar(UserRequestDTO user){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(user, headers);
        return restTemplate.exchange(BASE_URL + "/api/auth/login", HttpMethod.POST, entity, String.class).getBody();
    }
    
    public String registrarUsuario(UserDTO user){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(user, headers);
        return restTemplate.exchange(BASE_URL + "/api/auth/login", HttpMethod.POST, entity, String.class).getBody();
    }
    
    public String criarEdital(EditalDTO edital, String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(edital, headers);
        return restTemplate.exchange(BASE_URL + "/api/editais", HttpMethod.POST, entity, String.class).getBody();
    }
}
