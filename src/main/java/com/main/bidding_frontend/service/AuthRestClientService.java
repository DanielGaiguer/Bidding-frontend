package com.main.bidding_frontend.service;

import com.main.bidding_frontend.model.UserDTO;
import com.main.bidding_frontend.model.UserRequestDTO;
import com.main.bidding_frontend.model.UserTokenDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;


@Service
public class AuthRestClientService {
    private final RestClient restClient;
    
    public AuthRestClientService() {
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:9000/api")
                .build();
    }
    
    public UserTokenDTO logar(UserRequestDTO user){
        return restClient.post()
                .uri("/auth/login")
                .body(user)
                .retrieve()
                .body(UserTokenDTO.class);
    }
    
    public void registrar(UserDTO user){
        user.setRole("FORNECEDOR");
        String response = restClient.post().uri("/auth/register").body(user).retrieve().body(String.class);
    }
}
