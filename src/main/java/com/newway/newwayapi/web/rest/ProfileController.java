package com.newway.newwayapi.web.rest;


import com.newway.newwayapi.entities.Developer;
import com.newway.newwayapi.repository.CommentRepository;
import com.newway.newwayapi.repository.DeveloperRepository;
import com.newway.newwayapi.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


public class ProfileController {

    private final DeveloperRepository developerRepository;
    private final QuestionRepository questionRepository;
    private final CommentRepository commentRepository;


    @Autowired
    public ProfileController(DeveloperRepository developerRepository, QuestionRepository questionRepository, CommentRepository commentRepository){
        this.developerRepository = developerRepository;
        this.questionRepository = questionRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping("profile")
    public String displayMyProfile(Model model){
        Developer developer = new Developer();


        model.addAttribute("developer",developer);

        return "profile";
    }

    @GetMapping("profile/{id}")
    public String displayProfileById(@PathVariable Long id,Model model){
        Developer developer = developerRepository.getDeveloperById(id);

        model.addAttribute("developer",developer);


        return "profile";
    }



}

