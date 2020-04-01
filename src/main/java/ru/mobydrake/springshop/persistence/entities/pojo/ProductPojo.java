package ru.mobydrake.springshop.persistence.entities.pojo;

import lombok.Data;
import ru.mobydrake.springshop.persistence.entities.enums.ProductCategory;

@Data
public class ProductPojo {
    private String title;
    private String description;
    private Double price;
    private boolean available;
    private ProductCategory category;
}
