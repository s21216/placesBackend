package com.example.backend.Business;

import com.example.backend.Review.Review;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Entity
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String profilePictureUrl;
    private String address;
    private String city;
    private String district;
    private String category;
    private String cuisine;
    private Integer priceRange;
    private Double score;
    private Double longitude;
    private Double latitude;

    @OneToMany(mappedBy = "business")
    private List<Review> reviews;


}

