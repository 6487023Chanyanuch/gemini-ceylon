package com.example.demo.controller;

import com.example.demo.model.Astronomer;
import com.example.demo.model.ScienceObserver;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/astro")
    public String showAstronomerPage(Model model) {
        return "astro";
    }

    @GetMapping("/sci")
    public String showScienceObserverPage(Model model) {
        return "sci";
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam("email") String email,
                              @RequestParam("password") String password,
                              HttpSession session) {

        User user = userRepository.findByEmailAndPassword(email, password);
        if (user != null) {
            session.setAttribute("userId", user.getID());
            session.setAttribute("userName", user.getFirstname());
            System.out.println(user.getID());
            System.out.println(user.getFirstname());

            if (user instanceof Astronomer) {
                return new ModelAndView("redirect:/astro");
            } else if (user instanceof ScienceObserver) {
                return new ModelAndView("redirect:/sci");
            }
        }
        // Redirect back to login page in case of failure
        return new ModelAndView("redirect:/login");
    }
}