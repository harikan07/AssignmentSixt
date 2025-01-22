package com.example.assignment.controller;

import com.example.assignment.model.Blog;
import com.example.assignment.model.User;
import com.example.assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    //endpoint to add user
    @PostMapping
    public User createUser(@Validated @RequestBody User user){
        return userService.saveUser(user);
    }

    //endpoint to get all users
    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    //endpoint to get user by id
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable UUID id){
        return userService.getUserById(id);
    }

    //endpoint to remove user
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id){
        userService.deleteUserById(id);
    }

    //endpoint to create blog for every user
    @PostMapping("/blogs")
    public User addBlog(@RequestParam UUID userid,@RequestBody Blog blog){
        return userService.addBlog(userid,blog);
    }

    //endpoint to retrieve blogs of each user
    @GetMapping("/{userid}/blogs")
    public List<Blog> getAllBlogsOfUser(@PathVariable UUID userid, @RequestParam(defaultValue = "0") int page){
        return userService.getALLBlogsOfUser(userid,page);
    }

    //endpoint to update blog of each user
    @PutMapping("/{userid}/blogs/{blogid}")
    public ResponseEntity<String> updateBlog(@PathVariable UUID userid,@RequestBody Blog blog,@PathVariable Long blogid){
        User updatedUser = userService.updateBlog(userid,blog,blogid);
        if(updatedUser!=null){
            return ResponseEntity.ok("OK,Updated!!");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error updating the contents");
        }
    }

    //endpoint to delete blog of specific user
    @DeleteMapping("/{userid}/blogs/{blogid}")
    public void deleteBlogOfUser(@PathVariable UUID userid,@RequestBody Blog blog,@PathVariable Long blogid){
        userService.deleteBlogOfUser(userid,blog,blogid);
    }

}
