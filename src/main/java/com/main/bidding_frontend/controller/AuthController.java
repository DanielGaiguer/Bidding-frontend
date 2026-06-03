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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tools.jackson.databind.ObjectMapper;


@Controller
public class AuthController {
    @Autowired
    private AuthRestClientService service;

    @GetMapping("/login")
    public String login(
        Model model
    ) {
        model.addAttribute("user", new UserDTO());
        return "login";
    }
    
    @PostMapping("/login")
    public String logar(@ModelAttribute UserRequestDTO user, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        try {
            UserTokenDTO userLogged = service.logar(user);

            session.setAttribute("token", userLogged.getToken());
            session.setAttribute("role", userLogged.getRole());
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Cadastro realizado com sucesso");

            return "redirect:/";

        } catch (HttpStatusCodeException e) {
            String mensagemErroBackend = new ObjectMapper().readTree(e.getResponseBodyAsString()).get("message").asString();
            redirectAttributes.addFlashAttribute("mensagemErro", mensagemErroBackend);
            model.addAttribute("user", new UserRequestDTO());
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(
        HttpSession session
    ){
        session.invalidate();
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
            @ModelAttribute UserDTO user,
            @RequestParam String confirmarSenha,
            RedirectAttributes redirectAttributes,
            BindingResult result
    ) {

        if (result.hasErrors()) {
            return "register";
        }

        if (!user.getSenha().equals(confirmarSenha)) {
            result.rejectValue("senha", "error.user", "Senhas não conferem");
            return "register";
        }
        try{
            service.registrar(user);
            return "redirect:/login";
        }catch(HttpStatusCodeException e){
            String mensagemErroBackend = new ObjectMapper().readTree(e.getResponseBodyAsString()).get("message").asString();
            redirectAttributes.addFlashAttribute("mensagemErro", mensagemErroBackend);
            return "redirect:/register";
        }catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
            return "redirect:/login";
        }
    }
}
