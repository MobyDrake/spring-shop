package ru.mobydrake.springshop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mobydrake.springshop.persistence.entities.Product;
import ru.mobydrake.springshop.persistence.entities.Review;
import ru.mobydrake.springshop.persistence.entities.Shopuser;
import ru.mobydrake.springshop.persistence.repositories.ReviewRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Optional<List<Review>> getReviewsByProduct(Product product) {
        return reviewRepository.findByProduct(product);
    }

    public Optional<List<Review>> getReviewsByShopuser(Shopuser shopuser) {
        return reviewRepository.findByShopuser(shopuser);
    }

    @Transactional
    public void save(Review review) {
        reviewRepository.save(review);
    }

}