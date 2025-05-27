package com.data.ptithnks2023b_nguyentheminh.controller;

import com.data.ptithnks2023b_nguyentheminh.dto.CategoryDto;
import com.data.ptithnks2023b_nguyentheminh.model.Category;
import com.data.ptithnks2023b_nguyentheminh.model.Status;
import com.data.ptithnks2023b_nguyentheminh.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listCategory")
    public String showList(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "listCategory";
    }

    @GetMapping("/searchCategory")
    public String searchCategories(@RequestParam("keyword") String keyword, Model model) {
        List<Category> categories = categoryService.searchCategories(keyword);
        model.addAttribute("categories", categories);
        return "listCategory";
    }

    @GetMapping("/addCategory")
    public String showAddForm(Model model) {
        model.addAttribute("categoryDto", new CategoryDto());
        model.addAttribute("statusList", Status.values());
        return "addCategory";
    }

    @PostMapping("/addCategory")
    public String addCategory(@Valid @ModelAttribute("categoryDto") CategoryDto categoryDto,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("statusList", Status.values());
            return "addCategory";
        }

        boolean isCheckNameExist = categoryService.checkNameExists(categoryDto.getName());
        if (isCheckNameExist) {
            model.addAttribute("error", "Tên danh mục đã tồn tại");
            model.addAttribute("statusList", Status.values());
            return "addCategory";
        }

        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        if (categoryService.addCategory(category)) {
            return "redirect:/category/listCategory";
        } else {
            model.addAttribute("error", "Thêm thất bại");
            model.addAttribute("statusList", Status.values());
            return "addCategory";
        }
    }

    @GetMapping("/editCategory/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Category category = categoryService.getCategoryById(id);
        if (category == null) {
            return "redirect:/category/listCategory";
        }

        CategoryDto dto = new CategoryDto();
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());

        model.addAttribute("categoryDto", dto);
        model.addAttribute("categoryId", id);
        model.addAttribute("statusList", Status.values());

        return "editCategory";
    }

    @PostMapping("/editCategory/{id}")
    public String editCategory(@PathVariable int id,
                               @Valid @ModelAttribute("categoryDto") CategoryDto dto,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("statusList", Status.values());
            model.addAttribute("categoryId", id);
            return "editCategory";
        }

        Category category = categoryService.getCategoryById(id);
        if (category == null) {
            return "redirect:/category/listCategory";
        }

        if (!category.getName().equals(dto.getName())) {
            boolean isCheckNameExist = categoryService.checkNameExists(dto.getName());
            if (isCheckNameExist) {
                result.rejectValue("name", "categoryDto.name", "Tên danh mục đã tồn tại");
                model.addAttribute("statusList", Status.values());
                model.addAttribute("categoryId", id);
                return "editCategory";
            }

        }

        category.setName(dto.getName());
        category.setDescription(dto.getDescription());

        categoryService.updateCategory(category);
        return "redirect:/category/listCategory";
    }

    @GetMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable int id, Model model) {
        try {
            if (categoryService.deleteCategory(id)) {
                return "redirect:/category/listCategory";
            }
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            List<Category> categories = categoryService.getAllCategories();
            model.addAttribute("categories", categories);
            return "listCategory";
        }

        return "redirect:/category/listCategory";
    }
}
