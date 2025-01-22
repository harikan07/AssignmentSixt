package com.example.assignment.service;

import com.example.assignment.model.Blog;
import com.example.assignment.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Value("${blogs.limit}")
    private int blogsLimit;

    public Blog saveBlog(Blog blog){
        return blogRepository.save(blog);
    }

    public List<Blog> getAllBlogs(){
        return blogRepository.findAll();
    }

    public Optional<Blog> getBlogById(Long id){
        return blogRepository.findById(id);
    }

    public void deleteBlogById(Long id){
        blogRepository.deleteById(id);
    }

    public List<Blog> getBlogsForUser(UUID userid,int page){
        Pageable pageable = PageRequest.of(page, blogsLimit);
        return blogRepository.findBlogsByUserId(userid, pageable);
    }
}
