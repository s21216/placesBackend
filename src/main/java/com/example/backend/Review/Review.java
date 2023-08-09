package com.example.backend.Review;

import com.example.backend.Business.Business;
import com.example.backend.User.User;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double score;
    private String description;
    private LocalDate postDate;

    @ManyToOne
    private User user;

    @ManyToOne
    private Business business;

}
