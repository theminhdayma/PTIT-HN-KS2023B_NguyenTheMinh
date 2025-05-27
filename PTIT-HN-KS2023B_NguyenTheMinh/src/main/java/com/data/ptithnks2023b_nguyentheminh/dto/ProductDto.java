package com.data.ptithnks2023b_nguyentheminh.dto;

import com.data.ptithnks2023b_nguyentheminh.model.Status;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class ProductDto {

    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;
    private String description;
    @NotNull(message = "Giá sản phẩm không được để trống")
    private Double price;
    private MultipartFile imageUrl;
    private Status status;
    private LocalDate createdAt;
    @NotNull(message = "Danh mục không được để trống")
    private int categoryId;
}
