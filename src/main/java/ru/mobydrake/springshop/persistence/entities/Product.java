package ru.mobydrake.springshop.persistence.entities;

import lombok.*;
import ru.mobydrake.springshop.persistence.entities.enums.ProductCategory;
import ru.mobydrake.springshop.persistence.entities.utils.PersistableEntity;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Product extends PersistableEntity {

    private String title;
    private String description;
    private Double price;
    private Date added;
    private boolean available;

    @Enumerated(EnumType.ORDINAL)
    private ProductCategory category;

    @OneToOne
    @JoinColumn(name = "image")
    private Image image;
}
