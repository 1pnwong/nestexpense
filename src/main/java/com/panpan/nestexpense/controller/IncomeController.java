package com.panpan.nestexpense.controller;

import com.panpan.nestexpense.client.Client;
import com.panpan.nestexpense.client.ClientRepository;
import com.panpan.nestexpense.expense.Expense;
import com.panpan.nestexpense.expense.ExpenseRepository;
import com.panpan.nestexpense.income.Income;
import com.panpan.nestexpense.income.IncomeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class IncomeController {
    private final ClientRepository clientRepository;
    private final IncomeRepository incomeRepository;
    protected Long incomeID;

    public IncomeController(ClientRepository clientRepository, IncomeRepository incomeRepository) {
        this.clientRepository = clientRepository;
        this.incomeRepository = incomeRepository;
    }

    @RequestMapping("/income")
    public String content(Model model, Principal principal){
        if(principal != null){
            // Get UserID
            model.addAttribute("email", principal.getName());
            Client client = clientRepository.findByEmail(principal.getName());
            // Select all query that has userID
            List<Income> incomes = incomeRepository.findAllByUserIdSql(client.getUserID());
            model.addAttribute("incomes", incomes);
        }
        return "income/income.html";
    }

    @RequestMapping("/income/add")
    public String incomeAdd(Model model, Principal principal){
        model.addAttribute("income", new Income());
        return "income/income-add.html";
    }

    @PostMapping("/income/add")
    public String doAddIncome(@ModelAttribute("income") Income income, Principal principal){
        // Get and save user ID
        Client client = clientRepository.findByEmail(principal.getName());
        if(client != null){
            income.setUserID(client.getUserID());
        }
        incomeRepository.save(income);
        return "redirect:/income/add?success";
    }
}
