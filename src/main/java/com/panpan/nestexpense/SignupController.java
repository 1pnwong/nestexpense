package com.panpan.nestexpense;

import com.panpan.nestexpense.client.Client;
import com.panpan.nestexpense.client.ClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class SignupController {
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupController(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("SignupDto", new SignupDto());
        return "signup.html";
    }

    @PostMapping("/signup")
    public String processSignup(@ModelAttribute("SignupDto")
        SignupDto signupDto) {
        //check if password match
        if(!signupDto.getPassword().equals(signupDto.getConfirmPassword())) {
            return "redirect:/signup?passwordError";
        }

        //Check for existing username
        if(clientRepository.findByEmail(signupDto.getEmail()) != null) {
            return "redirect:/signup?emailError";
        }

        //Create and save a new user
        Client client = new Client();
        client.setEmail(signupDto.getEmail());
        client.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        clientRepository.save(client);
        return "redirect:/login?signupSuccess";
    }
}
