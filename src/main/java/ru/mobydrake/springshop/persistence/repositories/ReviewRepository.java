package ru.mobydrake.springshop.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mobydrake.springshop.persistence.entities.Product;
import ru.mobydrake.springshop.persistence.entities.Review;
import ru.mobydrake.springshop.persistence.entities.Shopuser;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

    Optional<List<Review>> findByProduct(Product product);
    Optional<List<Review>> findByShopuser(Shopuser shopuser);
}
