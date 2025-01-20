package com.example.assignment;

import com.example.assignment.model.Blog;
import com.example.assignment.model.User;
import com.example.assignment.repository.BlogRepository;
import com.example.assignment.repository.UserRepository;
import com.example.assignment.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTests {

    @Mock
    UserRepository userRepository;

    @Mock
    BlogRepository blogRepository;

    @InjectMocks
    UserService userService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    //adding a blog
    @Test
    public void testAddBlog(){
        UUID userId = UUID.randomUUID();
        User user=new User();
        user.setId(userId);

        Blog blog=new Blog();
        blog.setId(1L);
        blog.setContent("Sample content");

        //Mock repository methods
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        //call method under test
        User result=userService.addBlog(userId,blog);

        //Assert
        assertNotNull(result);
        assertTrue(result.getBlogs().contains(blog));
        assertEquals(userId,blog.getUser().getId());
        verify(userRepository).save(user);
    }

    //retrieve all blogs of a specific user
    @Test
    public void testGetAllBlogsOfUser(){
        UUID userId= UUID.randomUUID();
        User user=new User();
        user.setId(userId);

        Blog blog1=new Blog();
        blog1.setId(1L);
        blog1.setContent("Sample content 1");

        Blog blog2=new Blog();
        blog2.setId(2L);
        blog2.setContent("Sample content 2");

        user.addBlog(blog1);
        user.addBlog(blog2);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        List<Blog> result = userService.getALLBlogsOfUser(userId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(blog1));
        assertTrue(result.contains(blog2));
        verify(userRepository).findById(userId);
    }

    //edge case when the user is not found to retrieve blogs
    @Test
    public void testGetAllBlogsOfUser_UserNotFound(){
        UUID userId= UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        List<Blog> result = userService.getALLBlogsOfUser(userId);

        assertNull(result);
    }

    //updating the blog of particular user
    @Test
    public void testUpdateBlog(){
        UUID userId= UUID.randomUUID();
        User user=new User();
        user.setId(userId);

        Blog currentBlog= new Blog();
        currentBlog.setId(1L);
        currentBlog.setContent("Sample content");
        currentBlog.setUser(user);

        user.addBlog(currentBlog);

        Blog updatedBlog = new Blog();
        updatedBlog.setContent("Updated content");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(blogRepository.findById(1L)).thenReturn(Optional.of(currentBlog));
        when(blogRepository.save(currentBlog)).thenReturn(currentBlog);
        when(userRepository.save(user)).thenReturn(user);

        User result= userService.updateBlog(userId,updatedBlog,1L);

        assertNotNull(result);
        assertEquals("Updated content", currentBlog.getContent());
        assertEquals(userId, currentBlog.getUser().getId());
        verify(blogRepository).save(currentBlog);
        verify(userRepository).save(user);

    }

    //edge case when blog doesn't exist while updating
    @Test
    public void testUpdateBlog_BlogNotFound(){
        UUID userid=UUID.randomUUID();
        User user=new User();
        user.setId(userid);

        Blog updatedBlog=new Blog();
        updatedBlog.setContent("Sample content");

        when(userRepository.findById(userid)).thenReturn(Optional.of(user));
        when(blogRepository.findById(1L)).thenReturn(Optional.empty());

        User result= userService.updateBlog(userid,updatedBlog,1L);

        assertNull(result);
        verify(blogRepository, never()).save(any());

    }

    //deleting blog of particular user
    @Test
    public void testDeleteBlogOfUser(){
        UUID userId=UUID.randomUUID();
        User user=new User();
        user.setId(userId);

        Blog blog =new Blog();
        blog.setId(1L);
        blog.setContent("Sample content");
        blog.setUser(user);

        user.addBlog(blog);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(blogRepository.findById(1L)).thenReturn(Optional.of(blog));
        doNothing().when(blogRepository).deleteById(1L);

        userService.deleteBlogOfUser(userId,blog,1L);

        verify(blogRepository).deleteById(1L);
    }
}
