package com.rich.bryan.controller;

import com.rich.bryan.dto.RegisterForm;
import com.rich.bryan.services.NewUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @Autowired
    private NewUserService newUserService;

    @GetMapping("/login")
    public String login(){
        return "Login";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("register", new RegisterForm());
        return "Register";
    }

    @PostMapping("/register")
    public String postRegister(@Validated @ModelAttribute("register") RegisterForm rf, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            System.out.println(bindingResult);
            return "Register";
        } else if(!newUserService.newUser(rf)){
            bindingResult.rejectValue("unique","uniqueEmail", "This Email is already in use");
            return "Register";
        }

        redirectAttributes.addAttribute("r", new String("registered"));
        return "redirect:/login";
    }
}
