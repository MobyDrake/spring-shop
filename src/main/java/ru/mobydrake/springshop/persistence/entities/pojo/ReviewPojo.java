package ru.mobydrake.springshop.persistence.entities.pojo;

import lombok.Data;

import java.util.UUID;

@Data
public class ReviewPojo {
    private String captchaCode;
    private String commentary;
    private UUID productId;
}
