package com.panpan.nestexpense;

import com.panpan.nestexpense.client.Client;
import com.panpan.nestexpense.client.ClientRepository;
import com.panpan.nestexpense.expense.Expense;
import com.panpan.nestexpense.expense.ExpenseRepository;
import com.panpan.nestexpense.income.Income;
import com.panpan.nestexpense.income.IncomeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
    private final ClientRepository clientRepository;
    private final ExpenseRepository expenseRepository;
    private final IncomeRepository incomeRepository;

    public HomeController(ClientRepository clientRepository, ExpenseRepository expenseRepository, IncomeRepository incomeRepository) {
        this.clientRepository = clientRepository;
        this.expenseRepository = expenseRepository;
        this.incomeRepository = incomeRepository;
    }

    @RequestMapping("/")
    public String index(Model model, Principal principal){
        if(principal != null){
            // Get UserID
            model.addAttribute("email", principal.getName());
            Client client = clientRepository.findByEmail(principal.getName());
            // Select all query that has userID
            List<Expense> expenses = expenseRepository.findAllByUserIdSql(client.getUserID());
            model.addAttribute("expenses", expenses);
            // Select all query that has userID
            List<Income> incomes = incomeRepository.findAllByUserIdSql(client.getUserID());
            model.addAttribute("incomes", incomes);
        }
        return "home.html";
    }
}


