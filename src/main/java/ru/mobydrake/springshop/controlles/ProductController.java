package ru.mobydrake.springshop.controlles;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.mobydrake.springshop.exception.ProductNotFoundException;
import ru.mobydrake.springshop.exception.UnsupportedMediaTypeException;
import ru.mobydrake.springshop.persistence.entities.Image;
import ru.mobydrake.springshop.persistence.entities.pojo.ProductPojo;
import ru.mobydrake.springshop.services.ImageService;
import ru.mobydrake.springshop.services.ProductService;


import javax.imageio.ImageIO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ImageService imageService;
    private final ProductService productService;

    @GetMapping("/{id}")
    public String getOneProduct(Model model, @PathVariable String id) throws ProductNotFoundException {

        // TODO ДЗ - утилита, которая будет проверять UUID

        model.addAttribute("product", productService.findOneById(UUID.fromString(id)));
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

}