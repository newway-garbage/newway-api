package com.newway.newwayapi.web.rest;

import com.newway.newwayapi.entities.Developer;
import com.newway.newwayapi.repository.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;


@Controller
public class RegisterController {

    private final DeveloperRepository developerRepository;

    @Autowired
    public RegisterController(DeveloperRepository developerRepository){
        this.developerRepository = developerRepository;

    }

    @GetMapping("register")
    public String displayRegister(Model model){
        return "register";
    }


    @PostMapping("register")
    public RedirectView registerDeveloper(@RequestParam("username") String username, @RequestParam("password")String password,
                                          @RequestParam("introduction") String introduction, HttpServletRequest request) {
        String contextPath = request.getContextPath();
        Developer developer = new Developer();
        if (developerRepository.getDeveloperByUsername(username) == null){
            developer.setUsername(username);
            developer.setPassword(password);
            developerRepository.save(developer);
            return new RedirectView(contextPath + "/login");
        }
        else
            return new RedirectView(contextPath+ "/register");
    }
}
