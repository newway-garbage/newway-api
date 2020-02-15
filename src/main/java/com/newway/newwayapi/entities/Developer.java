package com.newway.newwayapi.entities;




import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "developer")
public class Developer{



    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false , length = 10)
    private String username;

    @Column(nullable = false , length = 10)
    private String password;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "developer")
    private List<Question> questions;

    @OneToMany(mappedBy = "developer")
    private List<Comment> comments;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString(){
        return "[username=" + username + ", email=" + email + ", password=" + password
                + "" +  "]";
    }
}