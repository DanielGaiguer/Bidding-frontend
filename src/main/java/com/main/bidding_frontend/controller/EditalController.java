package com.main.bidding_frontend.controller;

import com.main.bidding_frontend.model.EditalDTO;
import com.main.bidding_frontend.service.EditalService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EditalController {
    @Autowired
    private EditalService service;

    @GetMapping("/")
    public String homePage(HttpSession session, Model model) {
        model.addAttribute("role", session.getAttribute("role"));
        model.addAttribute("userNome", session.getAttribute("userNome"));
        return "home";
    }

    @GetMapping("/editais")
    public String listarEditais(
            @RequestParam(required = false) String status,
            HttpSession session,
            Model model) {

        String token = (String) session.getAttribute("token");

        List<EditalDTO> editais = service.listarEditais(token);

        if (status != null && !status.isEmpty()) {
            editais = editais.stream()
                    .filter(e -> e.getStatus().equals(status))
                    .toList();
        }

        model.addAttribute("editais", editais);
        model.addAttribute("status", status);

        return "editais";
    }

    @GetMapping("/editais/criar")
    public String criarEditalPage(HttpSession session, Model model) {

        model.addAttribute("role", session.getAttribute("role"));

        return "criar-edital";
    }

    @GetMapping("/editais/{id}")
    public String detalhesEdital(
            @PathVariable Long id,
            HttpSession session,
            Model model) {

        String token = (String) session.getAttribute("token");

        List<EditalDTO> editais = service.listarEditais(token);

        EditalDTO edital = editais.stream()
            .filter(e -> e.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException("Edital não encontrado com o ID: " + id));

        model.addAttribute("edital", edital);

        return "edital-detalhe";
    }
    
    @PostMapping("/editais/criar")
    public String criarEdital(@ModelAttribute EditalDTO edital, HttpSession session){
        service.criarEdital(edital, session.getAttribute("token").toString());
        return "redirect:/editais";
    }
}