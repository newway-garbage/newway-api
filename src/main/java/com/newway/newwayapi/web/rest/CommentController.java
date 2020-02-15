package com.newway.newwayapi.web.rest;

import com.newway.newwayapi.entities.Comment;
import com.newway.newwayapi.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class CommentController {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentController(CommentRepository commentRepository){
        this.commentRepository = commentRepository;


    }

    @GetMapping("comments/{id}")
    public String displayCommentsByUser(@PathVariable String id, Model model){
        List<Comment> comments = commentRepository.findCommentByDeveloper_IdOrOrderByCreatedDate(Long.parseLong(id));
        model.addAttribute("comments", comments);
        return "comments";
    }
}
