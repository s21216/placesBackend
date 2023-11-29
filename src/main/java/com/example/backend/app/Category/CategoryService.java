package com.example.backend.app.Category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    List<Category> getCategories() {
        return categoryRepository.findAll().stream().sorted(Comparator.comparing(Category::getName)).toList();
    }
}
