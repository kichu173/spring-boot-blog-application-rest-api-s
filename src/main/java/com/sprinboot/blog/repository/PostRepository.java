package com.sprinboot.blog.repository;

import com.sprinboot.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

// No need to annotate with @Repository because JpaRepository implementation class SimpleJpaRepository already annotated with it and @Transactional
public interface PostRepository extends JpaRepository<Post, Long> {
}
