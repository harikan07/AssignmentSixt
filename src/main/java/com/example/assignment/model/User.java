package com.example.assignment.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private String email;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    //    @JoinColumn(name="userid")
    @JsonManagedReference
    private Set<Blog> blogs = new HashSet<>();

    public User(){}

    public User(String username,String email){
        this.name=username;
        this.email=email;
    }

    public UUID getId(){
        return id;
    }

    public void setId(UUID id){
        this.id=id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name= name;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email= email;
    }

    public Set<Blog> getBlogs(){
        return blogs;
    }

    public void setBlogs(Set<Blog> blogs){
        this.blogs=blogs;
    }

    public void addBlog(Blog blog){
        blogs.add(blog);
    }
}
