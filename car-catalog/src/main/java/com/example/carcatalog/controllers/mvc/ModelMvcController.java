package com.example.carcatalog.controllers.mvc;


import com.example.carcatalog.dto.ModelFilterDto;
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
import com.example.carcatalog.dto.ModelFilterOptions;
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
    public String filterAllCarModels(@ModelAttribute("carModelFilterOptions") ModelFilterDto modelFilterDto,
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
                modelFilterDto.getCarModelId(),
                modelFilterDto.getModelName(),
                modelFilterDto.getBrandName(),
                     modelFilterDto.getSortBy(),
                modelFilterDto.getSortOrder());

        model.addAttribute("models", modelService.getAllModelsFilter(modelFilterOptions));

        return "ModelsView";
    }

    @GetMapping("/{carModelId}")
    public String showSingleCarModel(@PathVariable Long carModelId, org.springframework.ui.Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        try {
            Model carModel = modelService.getModelById(carModelId);
            model.addAttribute("carModel", carModel);
            return "ModelView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        }
    }

    @GetMapping("/new")
    public String showCarModelCreationForm(@ModelAttribute("modelSaveRequest") ModelSaveRequest modelSaveRequest, org.springframework.ui.Model model, HttpSession session, @RequestParam(required = false) Long brandId) {
        try {
            User user = authenticationHelper.tryGetUserWithSession(session);
//            checkAccessPermissions(user);
            Model carModel = new Model();

            if (brandId != null) {
                Brand brand = brandService.getBrandById(brandId);
                carModel.setBrand(brand);
            }


            model.addAttribute("model", carModel);
            model.addAttribute("brands", brandService.getAllBrandOptions());
            model.addAttribute("modelSaveRequest", modelSaveRequest);
            return "CarModelCreateView";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";

        }
    }

    @PostMapping("/new")
    public String createCarModel(@ModelAttribute("modelSaveRequest") ModelSaveRequest modelSaveRequest, BindingResult bindingResult, HttpSession session, org.springframework.ui.Model model) {
        try {
            User user = authenticationHelper.tryGetUserWithSession(session);
//            checkAccessPermissions(user);

            if (bindingResult.hasErrors()) {
                return "ModelCreateView";
            }


            Model carModel = modelMapper.convertToCarModel(modelSaveRequest);
            Model savedModel = modelService.createModel(carModel);
            Long carModelId = savedModel.getModelId();
            return "redirect:/models/" + carModelId;

        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        } catch (DuplicateEntityException e) {
            model.addAttribute("error", e.getMessage());
            return "DuplicateEntityView";

        }
    }

    @GetMapping("/{modelId}/update")
    public String showUpdateCarModelForm(@PathVariable Long carModelId, org.springframework.ui.Model model, @RequestParam(required = false) Long brandId) {
        try {

            Model carModel = modelService.getModelById(carModelId);
            Brand brand = carModel.getBrand();

            ModelResponse modelResponse = modelMapper.convertToCarModelResponse(carModel);
            ModelSaveRequest modelSaveRequest = new ModelSaveRequest();

            modelSaveRequest.setModelName(modelResponse.getModelName());
            modelSaveRequest.setBrand(brand);
            modelSaveRequest.setBrandId(brandId);
            modelSaveRequest.setBrandName(modelResponse.getBrandName());

            model.addAttribute("modelId", carModelId);
            model.addAttribute("modelSaveRequest", modelSaveRequest);
            return "ModelUpdateView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";

        }
    }

    @PostMapping("/{modelId}/update")
    public String updateModel(@PathVariable Long carModelId,
                              @Valid @ModelAttribute("modelSaveRequest") ModelSaveRequest modelSaveRequest,
                              BindingResult bindingResult,
                              org.springframework.ui.Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
//            checkAccessPermissions(user);

        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
        if (bindingResult.hasErrors()) {
            return "ModelUpdateView";
        }
        try {
            Model modelToBeUpdated = modelService.getModelById(carModelId);
          modelSaveRequest.setBrand(modelToBeUpdated.getBrand());
            Model carModel = modelMapper.convertToCarModel(modelSaveRequest);

            modelToBeUpdated.setModelName(carModel.getModelName());
            modelToBeUpdated.setBrand(carModel.getBrand());
            modelToBeUpdated.setModelId(carModelId);
            Model savedModel = modelService.updateModel(modelToBeUpdated);

            return "redirect:/models/" + carModelId;

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
    public String delete(@PathVariable Long carModelId, org.springframework.ui.Model model, HttpSession session) {

        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
//            checkAccessPermissions(user);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
        try {
            modelService.block(user, carModelId);
            return "redirect:/users";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView2";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }

//    private static void checkAccessPermissions(User executingUser) {
//        if (!executingUser.getRole().getRoleName().equals("admin")) {
//            throw new UnauthorizedOperationException(ERROR_MESSAGE);
//        }
//    }

}
