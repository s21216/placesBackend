package com.example.backend.app.Business;

import com.example.backend.app.Review.Review;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firebaseUid;
    private String name;
    private String profilePictureUrl;
    private String email;
    private String phoneNumber;
    private String address;
    private String zipCode;
    private String city;
    private String district;
    private String cuisine;
    private Integer priceRange;
    private Double score;
    private Double longitude;
    private Double latitude;
    private LocalDate joinDate;

    @OneToMany(mappedBy = "business")
    private List<BusinessCategory> categories;

    @OneToMany(mappedBy = "business")
    private List<Review> reviews;

    public Business(String email, String name, String phoneNumber, String firebaseUid) {
        this.email = email;
        this.name = name;
        this.firebaseUid = firebaseUid;
        this.phoneNumber = phoneNumber;
        joinDate = LocalDate.now();
    }

}

