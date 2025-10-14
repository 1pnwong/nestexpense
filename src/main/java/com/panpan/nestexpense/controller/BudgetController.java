package com.panpan.nestexpense.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class BudgetController {
    @RequestMapping("/budget")
    public String content(Model model, Principal principal){
        if(principal != null){
            model.addAttribute("email", principal.getName());
        }
        return "budget.html";
    }

    @RequestMapping("/budget/add")
    public String overviewAdd(Model model, Principal principal){
        if(principal != null){
            model.addAttribute("email", principal.getName());
        }
        return "budget-add.html";
    }
}
