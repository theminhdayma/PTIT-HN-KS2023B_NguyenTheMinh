package com.data.ptithnks2023b_nguyentheminh.controller;

import com.data.ptithnks2023b_nguyentheminh.dto.ProductDto;
import com.data.ptithnks2023b_nguyentheminh.model.Category;
import com.data.ptithnks2023b_nguyentheminh.model.Product;
import com.data.ptithnks2023b_nguyentheminh.model.Status;
import com.data.ptithnks2023b_nguyentheminh.service.CategoryService;
import com.data.ptithnks2023b_nguyentheminh.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/product")
public class ProductController {

    private static final String UPLOAD_DIR = "C:/Users/USER-PC/OneDrive/Desktop/PTIT-HN-KS2023B_NguyenTheMinh/PTIT-HN-KS2023B_NguyenTheMinh/src/main/webapp/uploads";

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listProduct")
    public String showList(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "listProduct";
    }

    @GetMapping("/searchProduct")
    public String searchProducts(@RequestParam("keyword") String keyword, Model model) {
        List<Product> products = productService.searchProducts(keyword);
        model.addAttribute("products", products);
        return "listProduct";
    }

    @GetMapping("/addProduct")
    public String showAddForm(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("statusList", Status.values());
        model.addAttribute("productDto", new ProductDto());
        return "addProduct";
    }

    @PostMapping("/addProduct")
    public String addProduct(@Valid @ModelAttribute("productDto") ProductDto productDto,
                             BindingResult result,
                             Model model) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("statusList", Status.values());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "addProduct";
        }

        boolean isCheckNameExist = productService.checkNameExists(productDto.getName());
        if (isCheckNameExist) {
            model.addAttribute("error", "Tên sản phẩm đã tồn tại");
            model.addAttribute("statusList", Status.values());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "addProduct";
        }

        String fileName = saveFile(productDto.getImageUrl());
        Product product = convertProductDtoToProduct(productDto, fileName);
        productService.addProduct(product);
        return "redirect:/product/listProduct";
    }

    @GetMapping("/editProduct/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return "redirect:/product/listProduct";
        }

        ProductDto dto = new ProductDto();
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStatus(product.getStatus());
        dto.setCategoryId(product.getCategoryId());
        model.addAttribute("product", product);

        model.addAttribute("productDto", dto);
        model.addAttribute("statusList", Status.values());
        model.addAttribute("productId", id);
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "editProduct";
    }

    @PostMapping("/editProduct/{id}")
    public String editProduct(@PathVariable int id,
                              @Valid @ModelAttribute("productDto") ProductDto dto,
                              BindingResult result,
                              Model model) throws IOException {
        Product product = productService.getProductById(id);  // <-- Lấy product trước

        if (result.hasErrors()) {
            model.addAttribute("statusList", Status.values());
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("productId", id);
            model.addAttribute("product", product); // <-- Truyền vào model
            return "editProduct";
        }

        if (product == null) {
            return "redirect:/product/listProduct";
        }

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStatus(dto.getStatus());
        product.setCategoryId(dto.getCategoryId());

        if (dto.getImageUrl() != null && !dto.getImageUrl().isEmpty()) {
            String fileName = saveFile(dto.getImageUrl());
            product.setImageUrl(fileName);
        }

        productService.updateProduct(product);
        return "redirect:/product/listProduct";
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return "redirect:/product/listProduct";
    }

    private Product convertProductDtoToProduct(ProductDto dto, String fileName) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setStatus(dto.getStatus());
        product.setImageUrl(fileName);
        product.setCreatedAt(LocalDate.now());
        product.setCategoryId(dto.getCategoryId());
        return product;
    }

    // Lưu ảnh
    private String saveFile(MultipartFile file) throws IOException {
        String originalName = file.getOriginalFilename();

        if (originalName == null || !originalName.contains(".")) {
            throw new IOException("Tên file không hợp lệ: thiếu phần mở rộng");
        }

        String ext = originalName.substring(originalName.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString() + ext;

        File destination = new File(UPLOAD_DIR, newFileName);
        file.transferTo(destination);

        return "uploads/" + newFileName;
    }

}

