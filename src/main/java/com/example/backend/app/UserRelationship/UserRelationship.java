package com.example.backend.app.UserRelationship;

import com.example.backend.app.User.User;
import jakarta.persistence.*;

@Entity
public class UserRelationship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User followedUser;

    @ManyToOne
    private User follower;
}
