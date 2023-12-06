package com.example.springmvc.controllers;

import com.example.springmvc.dtos.PasswordDTO;
import com.example.springmvc.dtos.UsersDTO;
import com.example.springmvc.models.Users;
import com.example.springmvc.serviceImpl.UsersServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
    private UsersServiceImpl usersService;

    public UserController(UsersServiceImpl userService) {this.usersService = userService;}

    @GetMapping("/register")
    public String indexPage(Model model){
        model.addAttribute("user", new UsersDTO());
        return "sign-up";
    }

    @GetMapping("/login")
    public ModelAndView loginPage(){
        return new ModelAndView("login")
                .addObject("user", new UsersDTO());
    }

    @PostMapping("/sign-up")
    public String signUp(@ModelAttribute UsersDTO usersDTO){
        Users user = usersService.saveUser.apply(new Users(usersDTO));
        log.info("User details ---> {}", user);
        return "successful-register";
    }

    @GetMapping("/sign-up")
    public String signUpPage(Model model){
        model.addAttribute("user", new Users());
       return "index";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute UsersDTO usersDTO, HttpServletRequest request, Model model){
        Users user = usersService.findUserByUsername.apply(usersDTO.getUsername());
        log.info("User details ---> {}", user);
        if (usersService.verifyUserPassword
                .apply(PasswordDTO.builder()
                        .password(usersDTO.getPassword())
                        .hashPassword(user.getPassword())
                        .build())){
            HttpSession session = request.getSession();
            session.setAttribute("userID", user.getId());
            model.addAttribute("userID", session.getAttribute( "userID"));
            return "dashboard";
        }
        return "redirect:/user/login";
    }

}
