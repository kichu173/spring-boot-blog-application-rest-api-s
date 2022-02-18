package com.sprinboot.blog.repository;

import com.sprinboot.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

// No need to annotate with @Repository because JpaRepository implementation class SimpleJpaRepository already annotated with it and @Transactional -> so no need to add it in service layer.
public interface PostRepository extends JpaRepository<Post, Long> {
}
