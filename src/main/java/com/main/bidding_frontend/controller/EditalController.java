/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main.bidding_frontend.controller;

import com.main.bidding_frontend.service.ApiService;
import org.springframework.stereotype.Controller;

@Controller
public class EditalController {
    
    private final ApiService apiService;
    
    public EditalController(ApiService apiService){
        this.apiService = apiService;
    }
}
