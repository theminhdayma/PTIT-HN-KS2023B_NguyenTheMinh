package com.data.ptithnks2023b_nguyentheminh.dto;

import com.data.ptithnks2023b_nguyentheminh.model.Status;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryDto {

    @NotBlank(message = "Tên không được để trống")
    private String name;
    private String description;
    private Status status;
}
