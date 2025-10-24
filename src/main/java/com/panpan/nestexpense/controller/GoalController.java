package com.panpan.nestexpense.controller;

import com.panpan.nestexpense.client.Client;
import com.panpan.nestexpense.client.ClientRepository;
import com.panpan.nestexpense.expense.Expense;
import com.panpan.nestexpense.expense.ExpenseRepository;
import com.panpan.nestexpense.goal.Goal;
import com.panpan.nestexpense.goal.GoalRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class GoalController {
    private final ClientRepository clientRepository;
    private final GoalRepository goalRepository;
    protected Long goalID;

    public GoalController(ClientRepository clientRepository, GoalRepository goalRepository) {
        this.clientRepository = clientRepository;
        this.goalRepository = goalRepository;
    }

    @RequestMapping("/goal")
    public String content(Model model, Principal principal){
        if(principal != null){
            // Get UserID
            model.addAttribute("email", principal.getName());
            Client client = clientRepository.findByEmail(principal.getName());
            // Select all query that has userID
            List<Goal> goals = goalRepository.findAllByUserIdSql(client.getUserID());
            model.addAttribute("goals", goals);
        }
        return "goal/goal.html";
    }

    @RequestMapping("/goal/add")
    public String goalAdd(Model model, Principal principal){
        model.addAttribute("goal", new Goal());
        return "goal/goal-add.html";
    }

    @PostMapping("/goal/add")
    public String doAddGoal(@ModelAttribute("goal") Goal goal, Principal principal){
        // Get and save user ID
        Client client = clientRepository.findByEmail(principal.getName());
        if(client != null){
            goal.setUserID(client.getUserID());
        }
        goalRepository.save(goal);
        return "redirect:/goal/add?success";
    }
}
