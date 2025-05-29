package vn.edu.hcmuaf.st.DACN_BookStore_2025.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.hcmuaf.st.DACN_BookStore_2025.dto.CategoryDTO;
import vn.edu.hcmuaf.st.DACN_BookStore_2025.service.ICategoryService;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    private ICategoryService catServiceImp;

    @GetMapping("/getAllCat")
    public List<CategoryDTO> findAll() {
        return catServiceImp.findAll();
    }

    @GetMapping("/getCategory")
    public List<CategoryDTO> getTenCat() {
        return catServiceImp.findTenCat();
    }
}
