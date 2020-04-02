package ru.mobydrake.springshop.controlles;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.mobydrake.springshop.exception.ProductNotFoundException;
import ru.mobydrake.springshop.exception.UnsupportedMediaTypeException;
import ru.mobydrake.springshop.exception.WrongCaptchaCodeException;
import ru.mobydrake.springshop.persistence.entities.Image;
import ru.mobydrake.springshop.persistence.entities.Product;
import ru.mobydrake.springshop.persistence.entities.Review;
import ru.mobydrake.springshop.persistence.entities.Shopuser;
import ru.mobydrake.springshop.persistence.entities.pojo.ProductPojo;
import ru.mobydrake.springshop.persistence.entities.pojo.ReviewPojo;
import ru.mobydrake.springshop.services.ImageService;
import ru.mobydrake.springshop.services.ProductService;
import ru.mobydrake.springshop.services.ReviewService;
import ru.mobydrake.springshop.services.ShopuserService;


import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ImageService imageService;
    private final ProductService productService;
    private final ShopuserService shopuserService;
    private final ReviewService reviewService;

    @GetMapping("/{id}")
    public String getOneProduct(Model model, @PathVariable String id) throws ProductNotFoundException {

        // TODO ДЗ - утилита, которая будет проверять UUID
        Product product = productService.findOneById(UUID.fromString(id));
        List<Review> reviews = reviewService.getReviewsByProduct(product).orElse(new ArrayList<>());
        model.addAttribute("product", product);
        model.addAttribute("reviews", reviews);
        return "product";
    }

    @GetMapping(value = "/images/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getImage(@PathVariable String id) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(imageService.loadFileAsResource(id), "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @PostMapping
    public String addOne(@RequestParam("image") MultipartFile image, ProductPojo productPojo) throws IOException, UnsupportedMediaTypeException {
        Image img = imageService.uploadImage(image, productPojo.getTitle());
        return productService.save(productPojo, img);
    }

    @PostMapping("/reviews")
    public String addReview(ReviewPojo reviewPojo, HttpSession session, Principal principal) throws ProductNotFoundException, WrongCaptchaCodeException {

        if (reviewPojo.getCaptchaCode().equals(session.getAttribute("captchaCode"))) {
            Product product = productService.findOneById(reviewPojo.getProductId());
            Shopuser shopuser = shopuserService.findByPhone(principal.getName());

            Review review = Review.builder()
                    .commentary(reviewPojo.getCommentary())
                    .product(product)
                    .shopuser(shopuser)
                    .build();

            reviewService.save(review);
            return "redirect:/products/" + product.getId();
        } else {
            throw new WrongCaptchaCodeException("Wrong captcha");
        }
    }

}