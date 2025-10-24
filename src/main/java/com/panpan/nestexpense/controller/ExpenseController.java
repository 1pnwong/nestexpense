package com.panpan.nestexpense.controller;

import com.panpan.nestexpense.client.Client;
import com.panpan.nestexpense.client.ClientRepository;
import com.panpan.nestexpense.expense.Expense;
import com.panpan.nestexpense.expense.ExpenseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class ExpenseController {
    private final ClientRepository clientRepository;
    private final ExpenseRepository expenseRepository;

    public ExpenseController(ClientRepository clientRepository, ExpenseRepository expenseRepository) {
        this.clientRepository = clientRepository;
        this.expenseRepository = expenseRepository;
    }

    @RequestMapping("/expense")
    public String expense(Model model, Principal principal){
        if(principal != null){
            // Get UserID
            model.addAttribute("email", principal.getName());
            Client client = clientRepository.findByEmail(principal.getName());
            // Select all query that has userID
            List<Expense> expenses = expenseRepository.findAllByUserIdSql(client.getUserID());
            model.addAttribute("expenses", expenses);
        }
        return "expense/expense.html";
    }

    @RequestMapping("/expense/add")
    public String budgetAdd(Model model, Principal principal){
        model.addAttribute("expense", new Expense());
        return "expense/expense-add.html";
    }

    @PostMapping("/expense/add")
    public String doAddExpense(@ModelAttribute("expense") Expense expense, Principal principal){
        // Get and save user ID
        Client client = clientRepository.findByEmail(principal.getName());
        if(client != null){
            expense.setUserID(client.getUserID());
        }
        expenseRepository.save(expense);
        return "redirect:/expense/add?success";
    }
}
