package com.sprinboot.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "content", nullable = false)
    private String content;

    // one post can have multiple comments
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)// mapped by to say owning relationship, where here is 'post'
    private Set<Comment> comments = new HashSet<>();// Using Set instead of List as it includes duplicates, where Set will not.
}
