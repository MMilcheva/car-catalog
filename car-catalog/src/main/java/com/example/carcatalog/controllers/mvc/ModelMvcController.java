package com.example.carcatalog.controllers.mvc;


import com.example.carcatalog.dto.ModelFilterDto;
import com.example.carcatalog.dto.ModelFilterOptions;
import com.example.carcatalog.dto.ModelResponse;
import com.example.carcatalog.dto.ModelSaveRequest;
import com.example.carcatalog.exceptions.AuthenticationFailureException;
import com.example.carcatalog.exceptions.DuplicateEntityException;
import com.example.carcatalog.exceptions.EntityNotFoundException;
import com.example.carcatalog.exceptions.UnauthorizedOperationException;
import com.example.carcatalog.helpers.AuthenticationHelper;
import com.example.carcatalog.helpers.ModelMapper;
import com.example.carcatalog.models.Brand;
import com.example.carcatalog.models.Model;
import com.example.carcatalog.models.User;
import com.example.carcatalog.services.contracts.BrandService;
import com.example.carcatalog.services.contracts.ModelService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/models")
public class ModelMvcController {

    private final ModelService modelService;
    private final BrandService brandService;
    private final ModelMapper modelMapper;
    private final AuthenticationHelper authenticationHelper;

    public ModelMvcController(ModelService modelService, BrandService brandService, ModelMapper modelMapper,
                              AuthenticationHelper authenticationHelper) {
        this.modelService = modelService;
        this.brandService = brandService;
        this.modelMapper = modelMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @GetMapping
    public String filterAllModels(@ModelAttribute("modelFilterOptions") ModelFilterDto modelFilterDto,
                                  org.springframework.ui.Model model, HttpSession session) {

        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
//            checkAccessPermissions(user);

        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        String sortBy = modelFilterDto.getSortBy();
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "modelName";
        }
        ModelFilterOptions modelFilterOptions = new ModelFilterOptions(
                modelFilterDto.getModelId(),
                modelFilterDto.getModelName(),
                modelFilterDto.getBrandName(),
                modelFilterDto.getSortBy(),
                modelFilterDto.getSortOrder());

        model.addAttribute("models", modelService.getAllModelsFilter(modelFilterOptions));

        return "ModelsView";
    }

    @GetMapping("/{modelId}")
    public String showSingleModel(@PathVariable Long modelId, org.springframework.ui.Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        try {
            Model carModel = modelService.getModelById(modelId);
            model.addAttribute("model", carModel);
            return "ModelView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        }
    }


    @GetMapping("/new")
    public String showModelCreationForm(@ModelAttribute("modelSaveRequest") ModelSaveRequest modelSaveRequest, org.springframework.ui.Model model, HttpSession session, @RequestParam(required = false) Long brandId) {
        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
        Model carModel = new Model();

        if (brandId != null) {
            Brand brand = brandService.getBrandById(brandId);
            carModel.setBrand(brand);
        }

        model.addAttribute("model", carModel);
        model.addAttribute("brands", brandService.getAllBrandOptions());
        model.addAttribute("modelSaveRequest", modelSaveRequest);
        return "ModelCreateView";
    }

    @PostMapping("/new")
    public String createModel(@Valid @ModelAttribute("modelSaveRequest") ModelSaveRequest modelSaveRequest, BindingResult bindingResult, HttpSession session, org.springframework.ui.Model model) {
        try {
            User user = authenticationHelper.tryGetUserWithSession(session);

            if (bindingResult.hasErrors()) {
                return "ModelCreateView";
            }


            Model carModel = modelMapper.convertToModel(modelSaveRequest);
            Model savedModel = modelService.createModel(carModel);
            Long modelId = savedModel.getModelId();
            return "redirect:/models/" + modelId;

        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        } catch (DuplicateEntityException e) {
            model.addAttribute("error", e.getMessage());
            return "DuplicateEntityView";

        }
    }

    @GetMapping("/{modelId}/update")
    public String showUpdateModelForm(@PathVariable Long modelId, org.springframework.ui.Model model, @RequestParam(required = false) Long brandId) {
        try {

            Model carModel = modelService.getModelById(modelId);
            Brand brand = carModel.getBrand();

            ModelResponse modelResponse = modelMapper.convertToModelResponse(carModel);
            ModelSaveRequest modelSaveRequest = new ModelSaveRequest();

            modelSaveRequest.setModelName(modelResponse.getModelName());
            modelSaveRequest.setBrandName(modelResponse.getBrandName());

            model.addAttribute("modelId", modelId);
            model.addAttribute("brands", brandService.getAllBrandOptions());
            model.addAttribute("modelSaveRequest", modelSaveRequest);
            return "ModelUpdateView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";

        }
    }

    @PostMapping("/{modelId}/update")
    public String updateModel(@PathVariable Long modelId,
                              @Valid @ModelAttribute("modelSaveRequest") ModelSaveRequest modelSaveRequest,
                              BindingResult bindingResult,
                              org.springframework.ui.Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);

        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
        if (bindingResult.hasErrors()) {
            return "ModelUpdateView";
        }
        try {
            Model modelToBeUpdated = modelService.getModelById(modelId);

            ModelResponse modelResponse = modelMapper.convertToModelResponse(modelToBeUpdated);

            modelSaveRequest.setBrandName(modelToBeUpdated.getBrand().getBrandName());

            Model carModel = modelMapper.convertToModel(modelSaveRequest);

            modelToBeUpdated.setModelName(carModel.getModelName());
            modelToBeUpdated.setBrand(carModel.getBrand());
            modelToBeUpdated.setModelId(modelId);
            Model savedModel = modelService.updateModel(modelToBeUpdated);

            return "redirect:/models/"+modelId;

        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        } catch (DuplicateEntityException e) {
            model.addAttribute("error", e.getMessage());
            return "DuplicateEntityView";

        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }

    @GetMapping("/{modelId}/delete")
    public String delete(@PathVariable Long modelId, org.springframework.ui.Model model, HttpSession session) {

        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
        try {
            modelService.deleteModel(modelId);
            return "redirect:/models";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView2";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }

}
