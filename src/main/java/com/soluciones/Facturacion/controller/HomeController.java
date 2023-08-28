package com.soluciones.Facturacion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String goHome(Model model) {
        model.addAttribute("titulo", "Ejemplo Programaciòn");
        return "inicio";
    }

    @GetMapping(value = "/page")
    public String mostrarVentas() {
        return "page/page1";
    }

}
