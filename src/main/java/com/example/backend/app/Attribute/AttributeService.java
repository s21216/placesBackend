package com.example.backend.app.Attribute;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttributeService {
    private final AttributeRepository attributeRepository;

    List<Attribute> getAttributes() {
        return attributeRepository.findAll().stream().sorted(Comparator.comparing(Attribute::getName)).toList();
    }
}