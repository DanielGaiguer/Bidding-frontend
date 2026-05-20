/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main.bidding_frontend.controller;

import com.main.bidding_frontend.model.UserDTO;
import com.main.bidding_frontend.model.UserRequestDTO;
import com.main.bidding_frontend.service.ApiService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    
    private final ApiService apiService;
    
    public AuthController(ApiService apiService){
        this.apiService = apiService;
    }
    
        
    @PostMapping("/login")
    public String logar(UserRequestDTO dto, HttpSession session){
        try{
            String token = apiService.logar(dto);
            session.setAttribute("token", token);
            return "redirect:/editais";
        }catch(Exception e ){
            return "redirect:/login?error=true";
        }
    }
    
    @PostMapping("/registrar")
    public String registrar(UserDTO dto){
        try{
            apiService.registrarUsuario(dto);
            return "redirect:/login";
        }catch(Exception e){
            return "redirect:/login?error=true";
        }
    }
}
