package com.example.backend.User;

import com.example.backend.Review.Review;
import com.example.backend.UserRelationship.UserRelationship;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String firstName;
    private String lastName;
    private String profilePictureUrl;
    private LocalDate joinDate;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;

    @OneToMany
    private List<UserRelationship> followers;

    @OneToMany
    private List<UserRelationship> following;

}
