package com.example.carcatalog.controllers.mvc;

import com.example.carcatalog.dto.FuelTypeFilterDto;
import com.example.carcatalog.dto.FuelTypeFilterOptions;
import com.example.carcatalog.dto.FuelTypeResponse;
import com.example.carcatalog.dto.FuelTypeSaveRequest;
import com.example.carcatalog.exceptions.AuthenticationFailureException;
import com.example.carcatalog.exceptions.DuplicateEntityException;
import com.example.carcatalog.exceptions.EntityNotFoundException;
import com.example.carcatalog.exceptions.UnauthorizedOperationException;
import com.example.carcatalog.helpers.AuthenticationHelper;
import com.example.carcatalog.helpers.FuelTypeMapper;
import com.example.carcatalog.models.FuelType;
import com.example.carcatalog.models.User;
import com.example.carcatalog.services.contracts.FuelTypeService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/fuel-types")
public class FuelTypeMvcController {

    private final FuelTypeService fuelTypeService;
    private final AuthenticationHelper authenticationHelper;

    private final FuelTypeMapper fuelTypeMapper;

    public FuelTypeMvcController(FuelTypeService fuelTypeService, AuthenticationHelper authenticationHelper, FuelTypeMapper fuelTypeMapper) {
        this.fuelTypeService = fuelTypeService;
        this.authenticationHelper = authenticationHelper;
        this.fuelTypeMapper = fuelTypeMapper;
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @GetMapping("/{fuelTypeId}")
    public String showSingleFuelType(@PathVariable Long fuelTypeId, Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        try {
            FuelType fuelType = fuelTypeService.getFuelTypeById(fuelTypeId);
            model.addAttribute("fuelType", fuelType);
            model.addAttribute("fuelTypes", fuelTypeService.getFuelTypeById(fuelTypeId));
            return "FuelTypeView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        }
    }

    @GetMapping
    public String filterAllFuelTypes(@ModelAttribute("fuelTypeFilterOptions") FuelTypeFilterDto fuelTypeFilterDto,
                                     Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        String sortBy = fuelTypeFilterDto.getSortBy();
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "fuelTypeName";
        }

        FuelTypeFilterOptions fuelTypeFilterOptions = new FuelTypeFilterOptions(
                fuelTypeFilterDto.getFuelTypeName(),
                fuelTypeFilterDto.getSortBy(),
                fuelTypeFilterDto.getSortOrder());
        model.addAttribute("fuelTypes", fuelTypeService.getAllFuelTypeFilter(fuelTypeFilterOptions));
        return "FuelTypesView";
    }

    @GetMapping("/new")
    public String showNewFuelTypePage(Model model, HttpSession session) {

        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        model.addAttribute("fuelTypeSaveRequest", new FuelTypeSaveRequest());
        return "FuelTypeCreateView";
    }

    @PostMapping("/new")
    public String createFuelType(@Valid @ModelAttribute FuelTypeSaveRequest fuelTypeSaveRequest,
                                 BindingResult bindingResult,
                                 Model model, HttpSession session) {

        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        if (bindingResult.hasErrors()) {
            return "FuelTypeCreateView";
        }

        try {
            FuelType fuelType = fuelTypeMapper.convertToFuelType(fuelTypeSaveRequest);
            FuelType createdFuelType = fuelTypeService.createFuelType(fuelType);
            return "redirect:/fuel-types/" + createdFuelType.getFuelTypeId();
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        } catch (DuplicateEntityException e) {
            model.addAttribute("error", e.getMessage());
            return "DuplicateEntityView";

        }
    }

    @GetMapping("/{fuelTypeId}/update")
    public String showEditFuelTypePage(@PathVariable Long fuelTypeId, Model model, HttpSession session) {

        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
        try {

            FuelType fuelType = fuelTypeService.getFuelTypeById(fuelTypeId);
            FuelTypeResponse fuelTypeResponse = fuelTypeMapper.convertToFuelTypeResponse(fuelType);
            FuelTypeSaveRequest fuelTypeSaveRequest = new FuelTypeSaveRequest();
            fuelTypeSaveRequest.setFuelTypeName(fuelTypeResponse.getFuelTypeName());

            model.addAttribute("fuelTypeId", fuelTypeId);
            model.addAttribute("fuelTypeSaveRequest", fuelTypeSaveRequest);
            return "FuelTypeUpdateView";

        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        }
    }

    @PostMapping("/{fuelTypeId}/update")
    public String updateBrand(@PathVariable Long fuelTypeId,
                              @Valid @ModelAttribute("fuelTypeSaveRequest") FuelTypeSaveRequest fuelTypeSaveRequest,
                              BindingResult bindingResult,
                              Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
        if (bindingResult.hasErrors()) {
            return "FuelTypeUpdateView";
        }
        try {
            FuelType fuelType = fuelTypeMapper.convertToFuelType(fuelTypeSaveRequest);
            fuelType.setFuelTypeName(fuelTypeSaveRequest.getFuelTypeName());

            FuelType fuelTypeToBeUpdated = fuelTypeService.getFuelTypeById(fuelTypeId);
            fuelTypeToBeUpdated.setFuelTypeName(fuelType.getFuelTypeName());

            fuelTypeService.updateFuelType(fuelTypeToBeUpdated);
            return "redirect:/fuel-types/{fuelTypeId}";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        } catch (DuplicateEntityException e) {
            model.addAttribute("error", e.getMessage());
            return "DuplicateEntityView";
        }
    }

    @GetMapping("/{fuelTypeId}/delete")
    public String delete(@PathVariable Long fuelTypeId, Model model, HttpSession session) {

        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
        try {
            fuelTypeService.deleteFuelType(fuelTypeId, user);
            return "redirect:/fuel-types";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }
}
