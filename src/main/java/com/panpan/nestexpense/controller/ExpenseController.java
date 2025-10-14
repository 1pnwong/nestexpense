package com.panpan.nestexpense.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class ExpenseController {
    @RequestMapping("/expense")
    public String content(Model model, Principal principal){
        if(principal != null){
            model.addAttribute("email", principal.getName());
        }
        return "expense.html";
    }

    @RequestMapping("/expense/add")
    public String overviewAdd(Model model, Principal principal){
        if(principal != null){
            model.addAttribute("email", principal.getName());
        }
        return "expense-add.html";
    }
}
