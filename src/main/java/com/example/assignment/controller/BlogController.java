package com.example.assignment.controller;

import com.example.assignment.model.Blog;
import com.example.assignment.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @PostMapping
    public Blog createBlog(@RequestBody Blog blog){
        return blogService.saveBlog(blog);
    }

    @GetMapping
    public List<Blog> getAllBlogs(){
        return blogService.getAllBlogs();
    }

    @GetMapping("/{id}")
    public Optional<Blog> getBlogById(@PathVariable Long id){
        return blogService.getBlogById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteBlogById(Long id){
        blogService.deleteBlogById(id);
    }
    
}
