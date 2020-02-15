package com.newway.newwayapi.entities;

import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "question")
public class Question {

    @javax.persistence.Id
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 1024)
    private String content;

    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "id_developer")
    private Developer developer;

    @OneToMany(mappedBy = "question")
    private List<Comment> comments;



    @Column(nullable = false, length = 32)
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
