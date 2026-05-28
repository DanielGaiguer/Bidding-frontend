/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main.bidding_frontend.controller;

import com.main.bidding_frontend.model.UserDTO;
import com.main.bidding_frontend.model.UserRequestDTO;
import com.main.bidding_frontend.model.UserTokenDTO;
import com.main.bidding_frontend.service.AuthRestClientService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AuthController {
    @Autowired
    private AuthRestClientService service;

    @GetMapping("/login")
    public String login(
        Model model
    ) {
        UserDTO user = new UserDTO();
        model.addAttribute("user", user);
        return "login";
    }
    
    @PostMapping("/login")
    public String logar(
            @ModelAttribute UserRequestDTO user,
            HttpSession session
    ) {
        UserTokenDTO userLogged = service.logar(user);
        session.setAttribute("token", userLogged.getToken());  
        session.setAttribute("role", userLogged.getRole());
        return "redirect:/";
    }
    
    @GetMapping("/logout")
    public String logout(
        HttpSession session
    ){
        session.setAttribute("token", "");
        session.setAttribute("role", "");
        return "redirect:/";
    }
    
    @GetMapping("/registrar")
    public String registrar(
            Model model
    ) {
        UserDTO newUser = new UserDTO();
        model.addAttribute("user", newUser);
        return "register";
    }
    
    @PostMapping("/registrar")
    public String mandarRegistro(
            @Validated @ModelAttribute("user") UserDTO user,
            BindingResult result
    ) {

        if (result.hasErrors()) {
            return "register";
        }

        service.registrar(user);
        return "redirect:/login";
    }
}
