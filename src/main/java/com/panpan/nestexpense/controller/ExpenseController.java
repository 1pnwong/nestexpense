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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class ExpenseController {
    private final ClientRepository clientRepository;
    private final ExpenseRepository expenseRepository;
    protected Long expenseID;

    //constructor
    public ExpenseController(ClientRepository clientRepository, ExpenseRepository expenseRepository) {
        this.clientRepository = clientRepository;
        this.expenseRepository = expenseRepository;
    }

    //get method when go to /expense
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
        // Get UserID
        model.addAttribute("email", principal.getName());
        model.addAttribute("expense", new Expense());
        return "expense/expense-add.html";
    }

    //post method after completing the form and submitting
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

    //add budget as flash attribute so it that it is hidden. this is to ensure security
    @PostMapping("/expense/edit/prepare")
    public String prepareEditExpense(@RequestParam("expenseID") Long expenseID, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("expenseID", expenseID);
        return "redirect:/expense/edit";
    }

    @RequestMapping("/expense/edit")
    public String showEditExpense(Model model, Principal principal){
        // Get UserID
        model.addAttribute("email", principal.getName());
        expenseID = (Long)model.getAttribute("expenseID");
        model.addAttribute("expense", new Expense());
        return "expense/expense-edit.html";
    }

    @PostMapping("expense/edit")
    public String doEditExpense(@ModelAttribute("expense") Expense expense,Model model){
        int affectedRows = expenseRepository.updateByExpenseIDSql(expenseID, expense.getSentToWhere(), expense.getAmount(), expense.getDateExpense(), expense.getCategory());
        return "redirect:/expense/edit?success";
    }

    @PostMapping("/expense/delete")
    public String doDeleteContent(@RequestParam("expenseID") Long expenseID, RedirectAttributes redirectAttributes){
        expenseRepository.deleteByExpenseIDSql(expenseID);
        return "redirect:/expense";
    }


}
