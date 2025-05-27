package com.data.ptithnks2023b_nguyentheminh.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Product {
    private int id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private Status status;
    private LocalDate createdAt;
    private int categoryId;
}
