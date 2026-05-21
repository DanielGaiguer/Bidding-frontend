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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    
    private ApiService apiService;
        
    @PostMapping("/api/login")
    public String logar(@ModelAttribute UserRequestDTO dto, HttpSession session){
        try{
            String token = apiService.logar(dto);
            session.setAttribute("email", dto.getEmail());
            session
            session.setAttribute("token", token);
            return "redirect:/editais";
        }catch(Exception e ){
            return "redirect:/login?error=true";
        }
    }
    
    @PostMapping("/api/registrar")
    public String registrar(UserDTO dto){
        try{
            apiService.registrarUsuario(dto);
            return "redirect:/login";
        }catch(Exception e){
            return "redirect:/login?error=true";
        }
    }
}
