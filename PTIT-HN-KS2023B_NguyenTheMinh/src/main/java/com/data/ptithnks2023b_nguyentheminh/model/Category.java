package com.data.ptithnks2023b_nguyentheminh.model;

import lombok.Data;

@Data
public class Category {
    private int id;
    private String name;
    private String description;
    private Status status;
}
