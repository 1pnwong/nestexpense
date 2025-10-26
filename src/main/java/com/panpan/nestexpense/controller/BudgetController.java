package com.panpan.nestexpense.controller;

import com.panpan.nestexpense.budget.Budget;
import com.panpan.nestexpense.budget.BudgetRepository;
import com.panpan.nestexpense.client.Client;
import com.panpan.nestexpense.client.ClientRepository;
import com.panpan.nestexpense.expense.Expense;
import com.panpan.nestexpense.expense.ExpenseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class BudgetController {
    private final ClientRepository clientRepository;
    private final BudgetRepository budgetRepository;
    protected Long budgetID;

    public BudgetController(ClientRepository clientRepository, BudgetRepository budgetRepository) {
        this.clientRepository = clientRepository;
        this.budgetRepository = budgetRepository;
    }

    @RequestMapping("/budget")
    public String content(Model model, Principal principal){
        if(principal != null){
            // Get UserID
            model.addAttribute("email", principal.getName());
            Client client = clientRepository.findByEmail(principal.getName());
            // Select all query that has userID
            List<Budget> budgets = budgetRepository.findAllByUserIdSql(client.getUserID());
            model.addAttribute("budgets", budgets);
        }
        return "budget/budget.html";
    }

    @RequestMapping("/budget/add")
    public String budgetAdd(Model model, Principal principal){
        model.addAttribute("budget", new Budget());
        return "budget/budget-add.html";
    }

    @PostMapping("/budget/add")
    public String doAddBudget(@ModelAttribute("budget") Budget budget, Principal principal){
        // Get and save user ID
        Client client = clientRepository.findByEmail(principal.getName());
        if(client != null){
            budget.setUserID(client.getUserID());
        }
        budgetRepository.save(budget);
        return "redirect:/budget/add?success";
    }

    @PostMapping("/budget/edit/prepare")
    public String prepareEditBudget(@RequestParam("budgetID") Long budgetID, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("budgetID", budgetID);
        return "redirect:/budget/edit";
    }

    @RequestMapping("/budget/edit")
    public String showEditBudget(Model model){
        budgetID = (Long)model.getAttribute("budgetID");
        model.addAttribute("budget", new Budget());
        return "budget/budget-edit.html";
    }

    @PostMapping("budget/edit")
    public String doEditBudget(@ModelAttribute("budget") Budget budget,Model model){
        int affectedRows = budgetRepository.updateByBudgetIDSql(budgetID, budget.getAmountSpent(), budget.getCategory());
        return "redirect:/budget";
    }

    @PostMapping("/budget/delete")
    public String doDeleteContent(@RequestParam("budgetID") Long budgetID, RedirectAttributes redirectAttributes){
        budgetRepository.deleteByBudgetIDSql(budgetID);
        return "redirect:/budget";
    }
}
