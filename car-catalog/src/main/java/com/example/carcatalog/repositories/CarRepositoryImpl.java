package com.example.carcatalog.repositories;

import com.example.carcatalog.models.Car;
import com.example.carcatalog.dto.CarFilterOptions;
import com.example.carcatalog.repositories.contracts.CarRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public class CarRepositoryImpl extends AbstractCRUDRepository<Car> implements CarRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public CarRepositoryImpl(SessionFactory sessionFactory) {
        super(Car.class, sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Car> getAllCarsByUserId(Long userId) {

        try (Session session = sessionFactory.openSession()) {
            Query<Car> query = session.createQuery(" select car from Car car, User u where  car.user.userId=:userId", Car.class);
            query.setParameter("userId", userId);

            return query.list();
        }
    }

    @Override
    public List<Car> getAllCars(Optional<String> search) {
        if (search.isEmpty()) {
            return getAll();
        }
        try (Session session = sessionFactory.openSession()) {
            Query<Car> query = session.createQuery(" from Car where " +
                    "vin like :vin or user.username like :username ");
            query.setParameter("vin", "%" + search.get() + "%");
            query.setParameter("username", "%" + search.get() + "%");
            return query.list();
        }
    }

    private Double getNumberIfPresent(Optional<String> search) {
        try {
            return Double.parseDouble(search.get());
        } catch (NumberFormatException e) {
            return -1.0;
        }

    }

    @Override
    public List<Car> getAllCarsFilter(CarFilterOptions carFilterOptions) {
        return filter(carFilterOptions.getCarId(),
                carFilterOptions.getUserId(),
                carFilterOptions.getModelName(),
                carFilterOptions.getBrandName(),
                carFilterOptions.getFuelType(),
                carFilterOptions.getTransmissionType(),
                carFilterOptions.getPrice(),
                carFilterOptions.getRegistrationDate(),
                carFilterOptions.getSortBy(),
                carFilterOptions.getSortOrder());
    }

    @Override
    public List<Car> filter(Optional<Long> carId,
                            Optional<Long> userId,
                            Optional<String> modelName,
                            Optional<String> brandName,
                            Optional<String> fuelTypeName,
                            Optional<String> transmissionTypeName,
                            Optional<Double> price,
                            Optional<LocalDate> registrationDate,
                            Optional<String> sortBy,
                            Optional<String> sortOrder) {

        try (Session session = sessionFactory.openSession()) {
            StringBuilder queryString = new StringBuilder(" Select c from Car c ");
            ArrayList<String> filter = new ArrayList<>();
            Map<String, Object> queryParams = new HashMap<>();

            carId.ifPresent(value -> {
                filter.add(" c.carId = :carId ");
                queryParams.put("carId", value);
            });

            userId.ifPresent(value -> {
                filter.add(" c.user.userId = :userId ");
                queryParams.put("userId", value);
            });


            modelName.ifPresent(value -> {
                filter.add(" c.model.modelName like :modelName ");
                queryParams.put("modelName", "%" + value + "%");
            });

            brandName.ifPresent(value -> {
                filter.add(" c.model.brand.brandName like :brandName ");
                queryParams.put("brandName", "%" + value + "%");
            });

            fuelTypeName.ifPresent(value -> {
                filter.add(" c.fuelType.fuelTypeName like :fuelTypeName ");
                queryParams.put("fuelTypeName", "%" + value + "%");
            });


            transmissionTypeName.ifPresent(value -> {
                filter.add(" c.transmissionType.transmissionTypeName like :transmissionTypeName ");
                queryParams.put("transmissionTypeName", "%" + value + "%");
            });
            price.ifPresent(value -> {
                filter.add(" c.price = :price ");
//                queryParams.put("price", "%" + value + "%");
                queryParams.put("price", value);
            });
            price.ifPresent(value -> {
                filter.add(" c.price = :price ");
                queryParams.put("price", value);
            });
            registrationDate.ifPresent(value -> {
                filter.add(" c.registrationDate = :registrationDate ");
                queryParams.put("registrationDate", value);
            });


            if (!filter.isEmpty()) {
                queryString.append(" where ").append(String.join(" and ", filter));
            }

            if (sortBy.isPresent() && !sortBy.get().isEmpty()) {
                String sortOrderString = sortOrder.orElse("ASC");
                queryString.append(" ORDER BY c.").append(sortBy.get()).append(" ").append(sortOrderString);
            }
            Query<Car> queryList = session.createQuery(queryString.toString(), Car.class);
            queryList.setProperties(queryParams);

            return queryList.list();
        }
    }
}
