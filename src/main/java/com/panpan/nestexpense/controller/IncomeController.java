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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class IncomeController {
    private final ClientRepository clientRepository;
    private final IncomeRepository incomeRepository;
    protected Long incomeID;

    //constructor
    public IncomeController(ClientRepository clientRepository, IncomeRepository incomeRepository) {
        this.clientRepository = clientRepository;
        this.incomeRepository = incomeRepository;
    }

    //get method when go to /income
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
        // Get UserID
        model.addAttribute("email", principal.getName());
        model.addAttribute("income", new Income());
        return "income/income-add.html";
    }

    //post method after completing the form and submitting
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

    //add budget as flash attribute so it that it is hidden. this is to ensure security
    @PostMapping("/income/edit/prepare")
    public String prepareEditIncome(@RequestParam("incomeID") Long incomeID, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("incomeID", incomeID);
        return "redirect:/income/edit";
    }

    @RequestMapping("/income/edit")
    public String showEditIncome(Model model, Principal principal){
        // get UserID
        model.addAttribute("email", principal.getName());
        incomeID = (Long)model.getAttribute("incomeID");
        model.addAttribute("income", new Income());
        return "income/income-edit.html";
    }

    @PostMapping("income/edit")
    public String doEditIncome(@ModelAttribute("income") Income income,Model model){
        int affectedRows = incomeRepository.updateByIncomeIDSql(incomeID, income.getSource(), income.getAmount(), income.getDateIncome());
        return "redirect:/income/edit?success";
    }

    @PostMapping("/income/delete")
    public String doDeleteContent(@RequestParam("incomeID") Long incomeID, RedirectAttributes redirectAttributes){
        incomeRepository.deleteByIncomeIDSql(incomeID);
        return "redirect:/income";
    }
}
