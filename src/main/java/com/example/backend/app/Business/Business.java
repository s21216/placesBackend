package com.example.backend.app.Business;

import com.example.backend.app.Attribute.Attribute;
import com.example.backend.app.Category.Category;
import com.example.backend.app.CheckIn.CheckIn;
import com.example.backend.app.Review.Review;
import com.example.backend.app.Review.ReviewReply;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.search.mapper.pojo.bridge.builtin.annotation.GeoPointBinding;
import org.hibernate.search.mapper.pojo.bridge.builtin.annotation.Latitude;
import org.hibernate.search.mapper.pojo.bridge.builtin.annotation.Longitude;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Indexed
@GeoPointBinding(fieldName = "location")
@NoArgsConstructor
@Getter
@Setter
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @FullTextField
    private String firebaseUid;

    @FullTextField
    private String name;

    private String profilePictureUrl;

    private String email;

    private String phoneNumber;

    private String address;

    private String zipCode;

    private String district;

    private String city;

    private String country;

    @FullTextField
    private String description;

    private String type;


    @Enumerated(EnumType.STRING)
    @GenericField
    private Cost cost;

    private Double score;

    @Latitude
    private Double locationLatitude;

    @Longitude
    private Double locationLongitude;

    private LocalDate joinDate;

    @ManyToMany
    @JoinTable(
            name = "business_category",
            joinColumns = @JoinColumn(name = "business_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @IndexedEmbedded
    private Set<Category> categories = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "business_attribute",
            joinColumns = @JoinColumn(name = "business_id"),
            inverseJoinColumns = @JoinColumn(name = "attribute_id")
    )
    @IndexedEmbedded
    private Set<Attribute> attributes = new HashSet<>();

    @OneToMany(mappedBy = "business")
    private Set<Review> reviews = new HashSet<>();

    @OneToMany(mappedBy = "business")
    private Set<CheckIn> checkIns = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "business")
    private List<ReviewReply> reviewReplies = new ArrayList<>();

    public Business(String email, String name, String phoneNumber, String firebaseUid) {
        this.email = email;
        this.name = name;
        this.firebaseUid = firebaseUid;
        this.phoneNumber = phoneNumber;
        joinDate = LocalDate.now();
    }

    public void setDetails(String type, String description, String phoneNumber, Set<Category> categories, Cost cost, Set<Attribute> attributes) {
        setType(type);
        setDescription(description);
        setPhoneNumber(phoneNumber);
        setCategories(categories);
        setCost(cost);
        setAttributes(attributes);
    }

}

