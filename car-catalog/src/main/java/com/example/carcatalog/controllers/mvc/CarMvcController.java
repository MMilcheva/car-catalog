package com.example.carcatalog.controllers.mvc;

import com.example.carcatalog.dto.CarFilterDto;
import com.example.carcatalog.dto.CarFilterOptions;
import com.example.carcatalog.dto.CarResponse;
import com.example.carcatalog.dto.CarSaveRequest;
import com.example.carcatalog.exceptions.AuthenticationFailureException;
import com.example.carcatalog.exceptions.DuplicateEntityException;
import com.example.carcatalog.exceptions.EntityNotFoundException;
import com.example.carcatalog.exceptions.UnauthorizedOperationException;
import com.example.carcatalog.helpers.AuthenticationHelper;
import com.example.carcatalog.helpers.CarMapper;
import com.example.carcatalog.models.Car;
import com.example.carcatalog.models.User;
import com.example.carcatalog.services.contracts.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cars")
public class CarMvcController {
    private final CarService carService;
    private final CarMapper carMapper;
    private final UserService userService;
    private final ModelService modelService;
    private final FuelTypeService fuelTypeService;
    private final TransmissionTypeService transmissionTypeService;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public CarMvcController(CarService carService, CarMapper carMapper, UserService userService,
                            ModelService modelService, FuelTypeService fuelTypeService, TransmissionTypeService transmissionTypeService, AuthenticationHelper authenticationHelper) {
        this.carService = carService;
        this.carMapper = carMapper;
        this.userService = userService;
        this.modelService = modelService;
        this.fuelTypeService = fuelTypeService;
        this.transmissionTypeService = transmissionTypeService;
        this.authenticationHelper = authenticationHelper;
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @GetMapping("{carId}")
    public String showSingleCar(@PathVariable Long carId, Model model, HttpSession session) {

        Car car;
        try {
            car = carService.getCarById(carId);
            model.addAttribute("car", car);

            return "CarView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";

        } catch (UnauthorizedOperationException e) {
            return "NotFoundView";
        }
    }

    @GetMapping
    public String filterAllCars(@ModelAttribute("carFilterOptions") CarFilterDto carFilterDto,
                                Model model, HttpSession session) {

        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        CarFilterOptions carFilterOptions = new CarFilterOptions(
                carFilterDto.getCarId(),
                carFilterDto.getUserId(),
                carFilterDto.getModelName(),
                carFilterDto.getBrandName(),
                carFilterDto.getFuelType(),
                carFilterDto.getTransmissionType(),
                carFilterDto.getPrice(),
                carFilterDto.getRegistrationDate(),
                carFilterDto.getSortBy(),
                carFilterDto.getSortOrder());
        model.addAttribute("canCreateCar", true);
        model.addAttribute("cars", carService.getAllCarFilter(carFilterOptions));
        model.addAttribute("carFilterOptions", carFilterDto);

        return "CarsView";
    }

    @GetMapping("/my-cars")
    public String filterAllCarsByUser(@ModelAttribute("carFilterOptions") CarFilterDto carFilterDto,
                                      Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        carFilterDto.setUserId(user.getUserId());
        try {
            CarFilterOptions carFilterOptions = new CarFilterOptions(
                    carFilterDto.getCarId(),
                    carFilterDto.getUserId(),
                    carFilterDto.getModelName(),
                    carFilterDto.getBrandName(),
                    carFilterDto.getFuelType(),
                    carFilterDto.getTransmissionType(),
                    carFilterDto.getPrice(),
                    carFilterDto.getRegistrationDate(),
                    carFilterDto.getSortBy(),
                    carFilterDto.getSortOrder()
            );
            List<Car> cars = carService.getAllCarFilter(carFilterOptions);
            carMapper.convertToCarResponses(cars);
            model.addAttribute("canCreateCar", false);
            model.addAttribute("cars", cars);
            return "CarsView";
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";

        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }


    @GetMapping("/new")
    public String showCreateCarPage(@ModelAttribute("carSaveRequest") CarSaveRequest carSaveRequest,
                                    Model model,
                                    HttpSession session,
                                    @RequestParam(required = false) Optional<String> userSearch,
                                    @RequestParam(required = false) Optional<String> carSearch,
                                    @RequestParam(required = false) Long userIdForCarCreation) {
        try {
            User user = authenticationHelper.tryGetUserWithSession(session);
//            checkAccessPermissions(user);

            Car car = new Car();

            if (userIdForCarCreation != null) {
                User userForCarCreation = userService.getUserById(userIdForCarCreation);
                car.setUser(userForCarCreation);
            }

            model.addAttribute("car", car);
            model.addAttribute("users", userService.getAll(userSearch));
            model.addAttribute("models", modelService.getAllModels(carSearch));
            model.addAttribute("fuelTypes", fuelTypeService.getAllFuelTypes(carSearch));
            model.addAttribute("transmissionTypes", transmissionTypeService.getAllTransmissionTypes(carSearch));
            model.addAttribute("carSaveRequest", carSaveRequest);

            return "CarCreateView";
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PostMapping("/new")
    public String createCar(@Valid @ModelAttribute("carSaveRequest") CarSaveRequest carSaveRequest,
                            BindingResult bindingResult,
                            Model model, HttpSession session) {

        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
            carSaveRequest.setUserName(user.getUsername());
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }

        if (bindingResult.hasErrors()) {
            return "CarCreateView";
        }
        try {
            Car car = carMapper.convertToCar(carSaveRequest);
            Car createdCar = carService.createCar(car);
            return "redirect:/cars/" + createdCar.getCarId();
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView2";
        } catch (DuplicateEntityException e) {
            bindingResult.rejectValue("vin", "duplicate_VIN", e.getMessage());
            return "CarCreateView";
        }
    }


    @GetMapping("/{carId}/update")
    public String showUpdateCarPage(@PathVariable Long carId, Model model,
                                    @RequestParam(required = false) Optional<String> carSearch,
                                    HttpSession session) {

        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
//        if (bindingResult.hasErrors()) {
//            return "ModelUpdateView";
//        }
        try {
            Car car = carService.getCarById(carId);

            CarResponse carResponse = carMapper.convertToCarResponse(car);

            CarSaveRequest carSaveRequest = carMapper.convertCarResponseToCarSaveRequest(carResponse);

            carSaveRequest.setUserName(user.getUsername());

            model.addAttribute("carId", carId);
            model.addAttribute("models", modelService.getAllModels(carSearch));
            model.addAttribute("fuelTypes", fuelTypeService.getAllFuelTypes(carSearch));
            model.addAttribute("transmissionTypes", transmissionTypeService.getAllTransmissionTypes(carSearch));
            model.addAttribute("carSaveRequest", carSaveRequest);
            return "CarUpdateView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView2";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }

    @PostMapping("/{carId}/update")
    public String updateCar(@PathVariable Long carId,
                            @Valid @ModelAttribute("carSaveRequest") CarSaveRequest carSaveRequest, Model model, BindingResult bindingResult, HttpSession session) {
        User checkUser;
        try {
            checkUser = authenticationHelper.tryGetUserWithSession(session);
            carSaveRequest.setUserName(checkUser.getUsername());
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
        if (bindingResult.hasErrors()) {
            return "CarUpdateView";
        }
        try {
            Car car = carMapper.convertToCarToBeUpdated(carId, carSaveRequest);

            carService.updateCar(car);
            return "redirect:/cars/{carId}";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView2";
        } catch (DuplicateEntityException e) {
            bindingResult.rejectValue("VIN", "duplicate_VIN", e.getMessage());
            return "CarUpdateView";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }


    @GetMapping("/{carId}/delete")
    public String deleteDelete(@PathVariable int carId, Model model, HttpSession session) {

        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
        try {
            carService.deleteCar(carId, user);
            return "redirect:/cars";
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

