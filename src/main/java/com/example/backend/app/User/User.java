package com.example.backend.app.User;

import com.example.backend.app.CheckIn.CheckIn;
import com.example.backend.app.Review.Review;
import com.example.backend.app.UserRelationship.UserRelationship;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Entity
@Getter
@Table(name="\"User\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firebaseUid;
    private String email;
    private String username;
    private String fullName;
    private String profilePictureUrl;
    private LocalDate joinDate;

    @OneToMany(mappedBy = "user")
    private List<CheckIn> checkIns;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;

    @OneToMany
    private List<UserRelationship> followers;

    @OneToMany
    private List<UserRelationship> following;

    public User(String email, String username, String fullName, String firebaseUid) {
        this.email = email;
        this.username = username;
        this.fullName = fullName;
        this.firebaseUid = firebaseUid;
        joinDate = LocalDate.now();
    }
}




