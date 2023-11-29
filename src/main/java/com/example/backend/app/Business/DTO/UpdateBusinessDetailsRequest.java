package com.example.backend.app.Business.DTO;

import com.example.backend.app.Attribute.Attribute;
import com.example.backend.app.Business.Cost;
import com.example.backend.app.Category.Category;

import java.util.Set;

public record UpdateBusinessDetailsRequest(
        String type,
        String description,
        String phoneNumber,
        Set<Category> categories,
        Cost cost,
        Set<Attribute> attributes
) {
}
