package com.example.carcatalog.controllers.mvc;


import com.example.carcatalog.dto.TransmissionTypeFilterDto;
import com.example.carcatalog.dto.TransmissionTypeFilterOptions;
import com.example.carcatalog.dto.TransmissionTypeResponse;
import com.example.carcatalog.dto.TransmissionTypeSaveRequest;
import com.example.carcatalog.exceptions.*;
import com.example.carcatalog.helpers.AuthenticationHelper;
import com.example.carcatalog.helpers.TransmissionTypeMapper;
import com.example.carcatalog.models.TransmissionType;
import com.example.carcatalog.models.User;
import com.example.carcatalog.services.contracts.TransmissionTypeService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/transmission-types")
public class TransmissionTypeMvcController {

    private final TransmissionTypeService transmissionTypeService;
    private final AuthenticationHelper authenticationHelper;

    private final TransmissionTypeMapper transmissionTypeMapper;

    public TransmissionTypeMvcController(TransmissionTypeService transmissionTypeService, AuthenticationHelper authenticationHelper, TransmissionTypeMapper transmissionTypeMapper) {
        this.transmissionTypeService = transmissionTypeService;
        this.authenticationHelper = authenticationHelper;
        this.transmissionTypeMapper = transmissionTypeMapper;
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @GetMapping("/{transmissionTypeId}")
    public String showSingleTransmissionType(@PathVariable Long transmissionTypeId, Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        try {
            TransmissionType transmissionType = transmissionTypeService.getTransmissionTypeById(transmissionTypeId);
            model.addAttribute("transmissionType", transmissionType);
            model.addAttribute("transmissionTypes", transmissionTypeService.getTransmissionTypeById(transmissionTypeId));
            return "transmissionTypeView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        }
    }

    @GetMapping
    public String filterAllTransmissionTypes(@ModelAttribute("transmissionTypeFilterOptions") TransmissionTypeFilterDto transmissionTypeFilterDto,
                                             Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        String sortBy = transmissionTypeFilterDto.getSortBy();
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "transmissionTypeName";
        }

        TransmissionTypeFilterOptions transmissionTypeFilterOptions = new TransmissionTypeFilterOptions(
                transmissionTypeFilterDto.getTransmissionTypeName(),
                transmissionTypeFilterDto.getSortBy(),
                transmissionTypeFilterDto.getSortOrder());
        model.addAttribute("transmissionTypes", transmissionTypeService.getAllTransmissionTypeFilter(transmissionTypeFilterOptions));
        return "TransmissionTypesView";
    }

    @GetMapping("/new")
    public String showNewTransmissionTypePage(Model model, HttpSession session) {

        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        model.addAttribute("transmissionTypeSaveRequest", new TransmissionTypeSaveRequest());
        return "TransmissionTypeCreateView";
    }

    @PostMapping("/new")
    public String createTransmissionType(@Valid @ModelAttribute TransmissionTypeSaveRequest transmissionTypeSaveRequest,
                                         BindingResult bindingResult,
                                         Model model, HttpSession session) {

        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        if (bindingResult.hasErrors()) {
            return "TransmissionTypeCreateView";
        }

        try {
            TransmissionType transmissionType = transmissionTypeMapper.convertToTransmissionType(transmissionTypeSaveRequest);
            TransmissionType createdTransmissionType = transmissionTypeService.createTransmissionType(transmissionType);
            return "redirect:/transmission-types/" + createdTransmissionType.getTransmissionTypeId();
        } catch (DuplicateEntityException e) {
            model.addAttribute("error", e.getMessage());
            return "DuplicateEntityView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        }
    }

    @GetMapping("/{transmissionTypeId}/update")
    public String showEditTransmissionTypePage(@PathVariable Long transmissionTypeId, Model model, HttpSession session) {

        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
        try {

            TransmissionType transmissionType = transmissionTypeService.getTransmissionTypeById(transmissionTypeId);
            TransmissionTypeResponse transmissionTypeResponse = transmissionTypeMapper.convertToTransmissionTypeResponse(transmissionType);
            TransmissionTypeSaveRequest transmissionTypeSaveRequest = new TransmissionTypeSaveRequest();
            transmissionTypeSaveRequest.setTransmissionTypeName(transmissionTypeResponse.getTransmissionTypeName());

            model.addAttribute("transmissionTypeId", transmissionTypeId);
            model.addAttribute("transmissionTypeSaveRequest", transmissionTypeSaveRequest);
            return "TransmissionTypeUpdateView";

        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        }
    }

    @PostMapping("/{transmissionTypeId}/update")
    public String updateBrand(@PathVariable Long transmissionTypeId,
                              @Valid @ModelAttribute("transmissionTypeSaveRequest") TransmissionTypeSaveRequest transmissionTypeSaveRequest,
                              BindingResult bindingResult,
                              Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
        if (bindingResult.hasErrors()) {
            return "TransmissionTypeUpdateView";
        }
        try {
            TransmissionType transmissionType = transmissionTypeMapper.convertToTransmissionType(transmissionTypeSaveRequest);
            transmissionType.setTransmissionTypeName(transmissionTypeSaveRequest.getTransmissionTypeName());

            TransmissionType transmissionTypeToBeUpdated = transmissionTypeService.getTransmissionTypeById(transmissionTypeId);
            transmissionTypeToBeUpdated.setTransmissionTypeName(transmissionType.getTransmissionTypeName());

            transmissionTypeService.updateTransmissionType(transmissionTypeToBeUpdated);
            return "redirect:/transmission-types/{transmissionTypeId}";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        } catch (DuplicateEntityException e) {
            model.addAttribute("error", e.getMessage());
            return "DuplicateEntityView";
        }
    }

    @GetMapping("/{transmissionTypeId}/delete")
    public String delete(@PathVariable Long transmissionTypeId, Model model, HttpSession session) {

        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
        try {
            transmissionTypeService.deleteTransmissionType(transmissionTypeId, user);
            return "redirect:/transmission-types";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        } catch (TransmissionTypeCannotBeDeletedException e) {
            model.addAttribute("error", e.getMessage());
            return "TransmissionTypeCannotBeDeleted";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }
}
