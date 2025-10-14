package com.panpan.nestexpense;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class ContentController {
    @RequestMapping("/content")
    public String content(Model model, Principal principal){
        if(principal != null){
            model.addAttribute("email", principal.getName());
        }
        return "overview.html";
    }

    @RequestMapping("/content/add")
    public String contentAdd(Model model, Principal principal){
        if(principal != null){
            model.addAttribute("email", principal.getName());
        }
        return "overview-add.html";
    }
}
