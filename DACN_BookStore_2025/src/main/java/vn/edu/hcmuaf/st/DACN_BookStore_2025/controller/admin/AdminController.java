package vn.edu.hcmuaf.st.DACN_BookStore_2025.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.hcmuaf.st.DACN_BookStore_2025.api.input.BookInput;
import vn.edu.hcmuaf.st.DACN_BookStore_2025.api.input.UserInput;
import vn.edu.hcmuaf.st.DACN_BookStore_2025.dto.*;
import vn.edu.hcmuaf.st.DACN_BookStore_2025.oauth2.CustomOAuth2User;
import vn.edu.hcmuaf.st.DACN_BookStore_2025.service.*;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@RestController
@RequestMapping("/admin-page")
public class AdminController {
    private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));
    @Autowired
    private IBookService bookService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IAuthorService authorService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IRoleService roleService;


    @Autowired
    private ICategoryService categoryService;

    //amdin category
    @GetMapping("/category-management")
    public ModelAndView listCategory() {
        ModelAndView mav = new ModelAndView("admin/category-management/categories");
        mav.addObject("cats", categoryService.findAll());
        return mav;
    }

    @GetMapping("/add-category-page")
    public ModelAndView addCategoryPage() {
        ModelAndView mav = new ModelAndView("admin/category-management/addCategory");
        return mav;
    }

    @PostMapping("/add-category")
    public ModelAndView addCategory(@ModelAttribute("cat") CategoryDTO cat) {
        categoryService.save(cat);
        ModelAndView mav = new ModelAndView("redirect:/admin-page/category-management");
        return mav;
    }

    @GetMapping("/edit-category-page")
    public ModelAndView editCategoryPage(@RequestParam("id") int id) {
        ModelAndView mav = new ModelAndView("admin/category-management/editCategory");
        mav.addObject("category", categoryService.findById(id));
        return mav;
    }

    @PostMapping("/edit-category")
    public ModelAndView editCategory(@ModelAttribute("cat") CategoryDTO cat) {
        categoryService.updateCat(cat);
        ModelAndView mav = new ModelAndView("redirect:/admin-page/category-management");
        return mav;
    }

    @GetMapping("/delete-category")
    public ModelAndView deleteCat(@RequestParam("id") int id) {
        categoryService.deleteByCatId(id);
        ModelAndView mav = new ModelAndView("redirect:/admin-page/category-management");
        return mav;
    }

    @GetMapping("getAdmin")
    public UserDTO getAdmin(Authentication authentication) {
        if (authentication == null) return new UserDTO();
        else {
            String userEmail = "";
            if (authentication.getPrincipal() instanceof CustomOAuth2User) {
                CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
                userEmail = oAuth2User.getAttribute("email");
            } else userEmail = authentication.getName();
            return userService.findByEmailAndIsEnable(userEmail);
        }
    }


    //account
    @GetMapping("/account-management")
    public ModelAndView listAccount() {
        ModelAndView mav = new ModelAndView("admin/account-management/accounts");
        mav.addObject("accounts", userService.findAllUser());
        return mav;
    }

    @GetMapping("/detail-account")
    public ModelAndView detailAccount(@RequestParam("id") int id) {
        ModelAndView mav = new ModelAndView("admin/account-management/detail");
        mav.addObject("account", userService.findByUserId(id));
        return mav;
    }

    @GetMapping("/edit-account-page")
    public ModelAndView editAccountPage(@RequestParam("id") int id) {
        ModelAndView mav = new ModelAndView("admin/account-management/edit");
        mav.addObject("account", userService.findByUserId(id));
        return mav;
    }

    @PostMapping("/edit-account")
    public ModelAndView editAccount(@ModelAttribute("user") UserInput user) {
        ModelAndView mav = new ModelAndView("redirect:/admin-page/account-management");
        UserDTO userDb = userService.findByUserId(user.getUserId());
        //set lại tất cả thuộc tính của user để lưu lại trong db
        //user input chỉ thay đổi 1 số thuộc tính, các thuộc tính còn lại thì lấy từ trong db ra
        UserDTO newUser = new UserDTO();
        newUser.setUserID(user.getUserId());
        newUser.setUpdatedAt(user.getUpdatedAt());
        newUser.setStatus(user.isStatus());
        List<RoleDTO> roles = new ArrayList<>();
        //lay role cu cua user
        for (RoleDTO r : userDb.getRoles()) {
            roles.add(roleService.findRolebyName(r.getName()));
        }
        if (user.getRoleName() != null)
            roles.add(roleService.findRolebyName(user.getRoleName()));

        newUser.setRoles(roles);
        newUser.setUsername(userDb.getUsername());
        newUser.setEmail(userDb.getEmail());
        if (userDb.getFullname() != null) newUser.setFullname(userDb.getFullname());

        newUser.setPassword(userDb.getPassword());

        if (userDb.getBirthdate() != null)
            newUser.setBirthdate(userDb.getBirthdate());

        newUser.setConfirmToken(userDb.getConfirmToken());

        if (userDb.getCreatedAt() != null)
            newUser.setCreatedAt(userDb.getCreatedAt());

        newUser.setGender(userDb.isGender());

        newUser.setEnable(userDb.isEnable());

        if (userDb.getPhone() != null)
            newUser.setPhone(userDb.getPhone());

        userService.save(newUser);
        return mav;
    }

    @GetMapping("/delete-account")
    public ModelAndView deleteAccount(@RequestParam("id") int id) {
        ModelAndView mav = new ModelAndView("redirect:/admin-page/account-management");
        userService.deleteByUserId(id);
        return mav;
    }

    //order
    @GetMapping("/order-management")
    public ModelAndView listOrder() {
        ModelAndView mav = new ModelAndView("admin/order-management/orders");
        mav.addObject("orders", orderService.findAll());
        return mav;
    }

    @GetMapping("/detail-order")
    public ModelAndView detailOrder(@RequestParam("id") int id) {
        ModelAndView mav = new ModelAndView("admin/order-management/detail");
        mav.addObject("order", orderService.findById(id));
        return mav;
    }

    @GetMapping("/edit-order-page")
    public ModelAndView editOrderPage(@RequestParam("id") int id) {
        ModelAndView mav = new ModelAndView("admin/order-management/editOrder");
        mav.addObject("order", orderService.findById(id));
        return mav;
    }

    @PostMapping("/edit-order")
    public ModelAndView editOrder(@ModelAttribute("orderInput") OrderDTO order) {
        orderService.update(order, order.getOrderID());
        ModelAndView mav = new ModelAndView("redirect:/admin-page/order-management");
        return mav;
    }

    @GetMapping("/delete-order")
    public ModelAndView deleteOrder(@RequestParam("id") int id) {
        ModelAndView mav = new ModelAndView("redirect:/admin-page/order-management");
        orderService.deleteById(id);
        return mav;
    }
}

