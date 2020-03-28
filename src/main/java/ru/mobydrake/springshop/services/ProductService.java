package ru.mobydrke.springshop.services;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import ru.mobydrke.springshop.exception.ProductNotFoundException;
import ru.mobydrke.springshop.persistence.entities.Product;
import ru.mobydrke.springshop.persistence.entities.enums.ProductCategory;
import ru.mobydrke.springshop.persistence.repositories.ProductRepository;


import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product findOneById(UUID uuid) throws ProductNotFoundException {
        return productRepository.findById(uuid).orElseThrow(
            () -> new ProductNotFoundException("Oops! Product " + uuid + " wasn't found!")
        );
    }

    public List<Product> findAll(Integer category) {
        return category == null ? productRepository.findAll() : productRepository.findAllByCategory(ProductCategory.values()[category]);
    }

}