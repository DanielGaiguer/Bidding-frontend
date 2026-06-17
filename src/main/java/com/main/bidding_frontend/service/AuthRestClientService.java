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
    
    public UserTokenDTO logar(UserRequestDTO user) {
        if (user.getEmail() == null || user.getEmail().trim().length() < 8) {
            throw new IllegalArgumentException("O e-mail deve ter no mínimo 8 caracteres.");
        }

        if (user.getSenha() == null || user.getSenha().trim().length() < 6) {
            throw new IllegalArgumentException("A senha deve ter no mínimo 6 caracteres.");
        }

        return restClient.post()
                .uri("/auth/login")
                .body(user)
                .retrieve()
                .body(UserTokenDTO.class);
    }
    
    public void registrar(UserDTO user){
        if (user.getEmail() == null || user.getEmail().trim().length() < 8) {
            throw new IllegalArgumentException("O e-mail deve ter no mínimo 8 caracteres.");
        }
        
        if (user.getEmail() == null || user.getNome().trim().length() < 6) {
            throw new IllegalArgumentException("O nome deve ter no mínimo 8 caracteres.");
        }

        if (user.getSenha() == null || user.getSenha().trim().length() < 6) {
            throw new IllegalArgumentException("A senha deve ter no mínimo 6 caracteres.");
        }
        user.setRole("FORNECEDOR");
        String response = restClient.post().uri("/auth/register").body(user).retrieve().body(String.class);
    }
}
