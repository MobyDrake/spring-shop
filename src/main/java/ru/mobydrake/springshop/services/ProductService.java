package ru.mobydrake.springshop.services;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mobydrake.springshop.exception.ProductNotFoundException;
import ru.mobydrake.springshop.persistence.entities.Image;
import ru.mobydrake.springshop.persistence.entities.Product;
import ru.mobydrake.springshop.persistence.entities.enums.ProductCategory;
import ru.mobydrake.springshop.persistence.entities.pojo.ProductPojo;
import ru.mobydrake.springshop.persistence.repositories.ProductRepository;


import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product findOneById(UUID uuid) throws ProductNotFoundException {
        return productRepository.findById(uuid).orElseThrow(
            () -> new ProductNotFoundException("Oops! Product wasn't found!")
        );
    }

    public List<Product> findAll(Integer category) {
        return category == null ? productRepository.findAll() : productRepository.findAllByCategory(ProductCategory.values()[category]);
    }

    @Transactional
    public String save(ProductPojo productPogo, Image image) {

        Product product = Product.builder()
                .added(new Date())
                .title(productPogo.getTitle())
                .description(productPogo.getDescription())
                .price(productPogo.getPrice())
                .available(productPogo.isAvailable())
                .category(productPogo.getCategory())
                .image(image)
                .build();

        productRepository.save(product);
        log.info("New Product has been successfully added! {}", product);
        return "redirect:/";
    }

}