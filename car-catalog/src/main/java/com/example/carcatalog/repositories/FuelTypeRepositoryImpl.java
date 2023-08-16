package com.example.carcatalog.repositories;

import com.example.carcatalog.models.FuelType;
import com.example.carcatalog.dto.FuelTypeFilterOptions;
import com.example.carcatalog.repositories.contracts.FuelTypeRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class FuelTypeRepositoryImpl extends AbstractCRUDRepository<FuelType> implements FuelTypeRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public FuelTypeRepositoryImpl(SessionFactory sessionFactory) {
        super(FuelType.class, sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<FuelType> getAllFuelTypesByUserId(Long userId) {

        try (Session session = sessionFactory.openSession()) {
            Query<FuelType> query = session.createQuery(" select ft from FuelType ft, User u where  ft.user.userId=:userId", FuelType.class);
            query.setParameter("userId", userId);

            return query.list();
        }
    }



    @Override
    public List<FuelType> getAllFuelTypes(Optional<String> search) {
        if (search.isEmpty()) {
            return getAll();
        }
        try (Session session = sessionFactory.openSession()) {
            Query<FuelType> query = session.createQuery(" from FuelType where " +
                    "fuelTypeName like :fuelTypeName ");
            query.setParameter("fuelTypeName", "%" + search.get() + "%");
            return query.list();
        }
    }

    @Override
    public FuelType getFuelTypeByName(String fuelTypeName) {
        try (Session session = sessionFactory.openSession()) {
            Query<FuelType> query = session.createQuery(" from FuelType where " +
                    "fuelTypeName = :fuelTypeName ");
            query.setParameter("fuelTypeName", fuelTypeName);
            return query.list().get(0);
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
    public List<FuelType> getAllFuelTypesFilter(FuelTypeFilterOptions fuelTypeFilterOptions) {
        return filter(fuelTypeFilterOptions.getFuelTypeName(),
                fuelTypeFilterOptions.getSortBy(),
                fuelTypeFilterOptions.getSortOrder());
    }

    @Override
    public List<FuelType> filter(Optional<String> fuelTypeName,
                                 Optional<String> sortBy,
                                 Optional<String> sortOrder) {

        try (Session session = sessionFactory.openSession()) {
            StringBuilder queryString = new StringBuilder(" Select ft from FuelType ft ");
            ArrayList<String> filter = new ArrayList<>();
            Map<String, Object> queryParams = new HashMap<>();

            fuelTypeName.ifPresent(value -> {
                filter.add(" ft.fuelTypeName like :fuelTypeName ");
                queryParams.put("fuelTypeName", "%" + value + "%");
            });

            if (!filter.isEmpty()) {
                queryString.append(" where ").append(String.join(" and ", filter));
            }

            if (sortBy.isPresent() && !sortBy.get().isEmpty()) {
                String sortOrderString = sortOrder.orElse("ASC");
                queryString.append(" ORDER BY ft.").append(sortBy.get()).append(" ").append(sortOrderString);
            }
            Query<FuelType> queryList = session.createQuery(queryString.toString(), FuelType.class);
            queryList.setProperties(queryParams);

            return queryList.list();
        }
    }


}
