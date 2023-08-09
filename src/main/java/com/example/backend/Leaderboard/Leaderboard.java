package com.example.backend.Leaderboard;

import com.example.backend.User.User;
import jakarta.persistence.*;

@Entity
public class Leaderboard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    private Integer scoredCount;
}
