package com.example.backend.app.Review;

import com.example.backend.app.Business.Business;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class ReviewReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private Instant createdAt;

    @JsonIgnore
    @OneToOne
    private Review review;

    @JsonIgnore
    @ManyToOne
    private Business business;
}
