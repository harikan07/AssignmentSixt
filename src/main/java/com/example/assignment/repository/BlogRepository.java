package com.example.assignment.repository;

import com.example.assignment.model.Blog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface BlogRepository extends JpaRepository<Blog,Long> {
    List<Blog> findBlogsByUserId(UUID userId, Pageable pageable);
}
