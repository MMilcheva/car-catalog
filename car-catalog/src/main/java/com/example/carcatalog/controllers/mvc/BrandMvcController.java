package com.example.carcatalog.controllers.mvc;

import com.example.carcatalog.dto.BrandFilterDto;
import com.example.carcatalog.dto.BrandResponse;
import com.example.carcatalog.dto.BrandSaveRequest;
import com.example.carcatalog.exceptions.AuthenticationFailureException;
import com.example.carcatalog.exceptions.DuplicateEntityException;
import com.example.carcatalog.exceptions.EntityNotFoundException;
import com.example.carcatalog.helpers.AuthenticationHelper;
import com.example.carcatalog.helpers.BrandMapper;
import com.example.carcatalog.models.Brand;
import com.example.carcatalog.dto.BrandFilterOptions;
import com.example.carcatalog.models.User;
import com.example.carcatalog.services.contracts.BrandService;
import com.example.carcatalog.services.contracts.ModelService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/brands")
public class BrandMvcController {
    private final BrandService brandService;
    private final BrandMapper brandMapper;
    private final ModelService modelService;
    private final AuthenticationHelper authenticationHelper;

    public BrandMvcController(BrandService brandService,
                              BrandMapper brandMapper,
                              ModelService modelService, AuthenticationHelper authenticationHelper) {
        this.brandService = brandService;
        this.brandMapper = brandMapper;
        this.modelService = modelService;
        this.authenticationHelper = authenticationHelper;
    }


    @GetMapping("/{brandId}/models")
    public String getAllCarModelsByBrandId(@PathVariable Long brandId,  Model model, HttpSession session) {
//@ModelAttribute("carModelFilterOptions") ModelFilterDto carModelFilterDto,
//
        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
//        checkAccessPermissions(user);

        model.addAttribute("selectedModels", modelService.getAllModelsByBrandId(brandId));
        return "SelectedCarModelsView";
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @GetMapping("/{brandId}")
    public String showSingleBrand(@PathVariable Long brandId, Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        try {
            Brand brand = brandService.getBrandById(brandId);
            model.addAttribute("brand", brand);
            model.addAttribute("selectedCarModels", modelService.getAllModelsByBrandId(brandId));
            return "BrandView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        }
    }

    @GetMapping
    public String filterAllBrands(@ModelAttribute("brandFilterOptions") BrandFilterDto brandFilterDto,
                                  Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        String sortBy = brandFilterDto.getSortBy();
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "brandName";
        }

        BrandFilterOptions brandFilterOptions = new BrandFilterOptions(
                brandFilterDto.getBrandName(),
                   brandFilterDto.getSortBy(),
                brandFilterDto.getSortOrder());
        model.addAttribute("brands", brandService.getAllBrandsFilter(brandFilterOptions));
        return "BrandsView";
    }

    @GetMapping("/new")
    public String showNewBrandPage(Model model, HttpSession session) {

        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        model.addAttribute("brandSaveRequest", new BrandSaveRequest());
        return "BrandCreateView";
    }

    @PostMapping("/new")
    public String createBrand(@Validated @ModelAttribute BrandSaveRequest brandSaveRequest,
                              BindingResult bindingResult,
                              Model model, HttpSession session) {

        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        if (bindingResult.hasErrors()) {
            return "BrandCreateView";
        }

        try {
            Brand brand = brandMapper.convertToBrand(brandSaveRequest);
            Brand createdBrand = brandService.createBrand(brand);
            return "redirect:/brands/" + createdBrand.getBrandId();

        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        } catch (DuplicateEntityException e) {
            model.addAttribute("error", e.getMessage());
            return "DuplicateEntityView";

        }
    }

    @GetMapping("/{brandId}/update")
    public String showEditBrandPage(@PathVariable Long brandId, Model model, HttpSession session) {

        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
        try {

            Brand brand = brandService.getBrandById(brandId);
            BrandResponse brandResponse = brandMapper.convertToBrandResponse(brand);
            BrandSaveRequest brandSaveRequest = new BrandSaveRequest();
            brandSaveRequest.setBrandName(brandResponse.getBrandName());

            model.addAttribute("brandId", brandId);
            model.addAttribute("brandSaveRequest", brandSaveRequest);
            return "BrandUpdateView";

        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        }
    }

    @PostMapping("/{brandId}/update")
    public String updateBrand(@PathVariable Long brandId,
                              @Valid @ModelAttribute("brandSaveRequest") BrandSaveRequest brandSaveRequest,
                              BindingResult bindingResult,
                              Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
        if (bindingResult.hasErrors()) {
            return "BrandUpdateView";
        }
        try {
//            checkModifyPermissions(postId, user);
            Brand brand = brandMapper.convertToBrand(brandSaveRequest);
            brand.setBrandName(brandSaveRequest.getBrandName());

            Brand brandToBeUpdated = brandService.getBrandById(brandId);
            brandToBeUpdated.setBrandName(brand.getBrandName());


            brandService.updateBrand(brandToBeUpdated);
            return "redirect:/brands/{brandId}";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        } catch (DuplicateEntityException e) {
            model.addAttribute("error", e.getMessage());
            return "DuplicateEntityView";

        }

    }
//    private static void checkAccessPermissions(User executingUser) {
//        if (!executingUser.getRole().getRoleName().equals("admin")) {
//            throw new UnauthorizedOperationException(ERROR_MESSAGE);
//        }
//    }
}
