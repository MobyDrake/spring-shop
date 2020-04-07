package ru.mobydrake.springshop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mobydrake.springshop.persistence.entities.Product;
import ru.mobydrake.springshop.persistence.entities.Review;
import ru.mobydrake.springshop.persistence.entities.Shopuser;
import ru.mobydrake.springshop.persistence.repositories.ReviewRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Optional<List<Review>> getReviewsByProduct(Product product) {
        return reviewRepository.findByProduct(product);
    }

    public Optional<List<Review>> getReviewsByShopuser(Shopuser shopuser) {
        return reviewRepository.findByShopuser(shopuser);
    }

    public void save(Review review) {
        reviewRepository.save(review);
    }

    public UUID moderate(UUID id, String option) throws EntityNotFoundException {
        Review review = reviewRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Oops! Review " + id + " wasn't found!")
        );
        review.setApproved(option.equals("approve"));
        save(review);
        return review.getProduct().getId();
    }

}