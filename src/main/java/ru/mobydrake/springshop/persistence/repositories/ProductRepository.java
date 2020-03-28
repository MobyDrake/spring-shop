package ru.mobydrake.springshop.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mobydrake.springshop.persistence.entities.Product;
import ru.mobydrake.springshop.persistence.entities.enums.ProductCategory;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findAll();
    List<Product> findAllByCategory(ProductCategory category);
}