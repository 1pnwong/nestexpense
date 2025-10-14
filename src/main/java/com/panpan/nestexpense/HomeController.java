package com.panpan.nestexpense;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String index(Model model, Principal principal){
        if(principal != null){
            model.addAttribute("email", principal.getName());
        }
        return "index.html";
    }
}


