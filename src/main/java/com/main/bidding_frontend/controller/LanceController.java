/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main.bidding_frontend.controller;

import com.main.bidding_frontend.model.LancePostDTO;
import com.main.bidding_frontend.service.ApiService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LanceController {
    private final ApiService apiService;
    
    public LanceController(ApiService apiService){
        this.apiService = apiService;
    }
    
    @PostMapping("/api/lances")
    public String criarLance(HttpSession session, LancePostDTO lance){
        String token = (String) session.getAttribute("token");
        return apiService.criarLance(lance, token);
    }
   
}
