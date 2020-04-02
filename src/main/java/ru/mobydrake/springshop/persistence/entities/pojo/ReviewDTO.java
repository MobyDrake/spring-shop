package ru.mobydrake.springshop.persistence.entities.pojo;

import lombok.Data;

import java.util.UUID;

@Data
public class ReviewDTO {
    private String captchaCode;
    private String commentary;
    private UUID productId;
}
