package com.danyue.reactspringbootblogbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "blog_image")
public class BlogImage {

    public BlogImage(String name, String filepath) {
        this.name = name;
        this.filepath = filepath;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String filepath;
}
