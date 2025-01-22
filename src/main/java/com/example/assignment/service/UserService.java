package com.example.assignment.service;

import com.example.assignment.model.Blog;
import com.example.assignment.model.User;
import com.example.assignment.repository.BlogRepository;
import com.example.assignment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserService {

    @Autowired
    private UserRepository  userRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private BlogService blogService;

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(UUID id){
        return userRepository.findById(id);
    }

    public void deleteUserById(UUID id){
        userRepository.deleteById(id);
    }


//    public User addBlog(UUID userid, Blog blog){
//        Optional<User> user =userRepository.findById(userid);
//        if(user.isPresent()){
//            user.get().addBlog(blog); //get actual user object;
//            return userRepository.save(user.get());
//
//        }
//        return null;
//    }

    public User addBlog(UUID userid, Blog blog){
        Optional<User> optionalUser = userRepository.findById(userid);
        if(optionalUser.isPresent()){
            User user=optionalUser.get();//get actual user object;
            blog.setUser(user);
            user.addBlog(blog);
            return userRepository.save(user);
        }
        return null;
    }

    public List<Blog> getALLBlogsOfUser(UUID userid,int page){
        Optional<User> optionalUser = userRepository.findById(userid);
        if(optionalUser.isPresent()){
            return blogService.getBlogsForUser(userid,page);
        }
        return null;
    }

    public User updateBlog(UUID userid,Blog blog,Long blogid){
        Optional<User> optionalUser = userRepository.findById(userid);
        if(optionalUser.isPresent()){
            User user=optionalUser.get();
            Optional<Blog> optionalBlog = blogRepository.findById(blogid);

            if(optionalBlog.isPresent()){
                Blog currentblog= optionalBlog.get();

                if(currentblog.getUser().getId().equals(userid)){
                    currentblog.setContent(blog.getContent());
                    blogRepository.save(currentblog);
                    return userRepository.save(user);
                }
            }
        }
        return null;
    }

    public void deleteBlogOfUser(UUID userid,Blog blog,Long blogid){
        Optional<User> optionalUser = userRepository.findById(userid);

        if(optionalUser.isPresent()){
            User user=optionalUser.get();
            Optional<Blog> optionalBlog = blogRepository.findById(blogid);

            if(optionalBlog.isPresent()){
                Blog currentblog= optionalBlog.get();

                if(currentblog.getUser().getId().equals(userid))
                    blogRepository.deleteById(blogid);
            }
        }
    }

}
