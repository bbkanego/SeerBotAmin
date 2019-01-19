package com.seerlogics.botadmin.service;

import com.seerlogics.botadmin.model.Category;
import com.seerlogics.botadmin.repository.CategoryRepository;
import com.lingoace.spring.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by bkane on 11/1/18.
 */
@Service
public class CategoryService extends BaseServiceImpl<Category> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Collection<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getSingle(Long id) {
        return categoryRepository.getOne(id);
    }

    @Override
    public Category save(Category object) {
        return categoryRepository.save(object);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    public Category getCategoryByCode(String code) {
        return categoryRepository.findByCode(code);
    }
}
