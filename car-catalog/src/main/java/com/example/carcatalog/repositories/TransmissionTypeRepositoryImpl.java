package com.example.carcatalog.repositories;

import com.example.carcatalog.models.TransmissionType;
import com.example.carcatalog.dto.TransmissionTypeFilterOptions;
import com.example.carcatalog.repositories.contracts.TransmissionTypeRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TransmissionTypeRepositoryImpl extends AbstractCRUDRepository<TransmissionType> implements TransmissionTypeRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public TransmissionTypeRepositoryImpl(SessionFactory sessionFactory) {
        super(TransmissionType.class, sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<TransmissionType> getAllTransmissionTypesByUserId(Long userId) {

        try (Session session = sessionFactory.openSession()) {
            Query<TransmissionType> query = session.createQuery(" select ft from FuelType ft, User u where  ft.user.userId=:userId", TransmissionType.class);
            query.setParameter("userId", userId);

            return query.list();
        }
    }

    @Override
    public List<TransmissionType> getAllTransmissionTypes(Optional<String> search) {
        if (search.isEmpty()) {
            return getAll();
        }
        try (Session session = sessionFactory.openSession()) {
            Query<TransmissionType> query = session.createQuery(" from FuelType where " +
                    "fuelTypeName like :fuelTypeName ");
            query.setParameter("fuelTypeName", "%" + search.get() + "%");
            return query.list();
        }
    }
    @Override
    public TransmissionType getTransmissionTypeByName(String transmissionTypeName) {
        try (Session session = sessionFactory.openSession()) {
            Query<TransmissionType> query = session.createQuery(" from TransmissionType where " +
                    "transmissionTypeName = :transmissionTypeName ");
            query.setParameter("transmissionTypeName", transmissionTypeName);
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
    public List<TransmissionType> getAllTransmissionTypesFilter(TransmissionTypeFilterOptions transmissionTypeFilterOptions) {
        return filter(transmissionTypeFilterOptions.getTransmissionTypeName(),
                transmissionTypeFilterOptions.getSortBy(),
                transmissionTypeFilterOptions.getSortOrder());
    }

    @Override
    public List<TransmissionType> filter(Optional<String> transmissionTypeName,
                                 Optional<String> sortBy,
                                 Optional<String> sortOrder) {

        try (Session session = sessionFactory.openSession()) {
            StringBuilder queryString = new StringBuilder(" Select tt from TransmissionType tt ");
            ArrayList<String> filter = new ArrayList<>();
            Map<String, Object> queryParams = new HashMap<>();

            transmissionTypeName.ifPresent(value -> {
                filter.add(" tt.transmissionTypeName like :transmissionTypeName ");
                queryParams.put("transmissionTypeName", "%" + value + "%");
            });

            if (!filter.isEmpty()) {
                queryString.append(" where ").append(String.join(" and ", filter));
            }

            if (sortBy.isPresent() && !sortBy.get().isEmpty()) {
                String sortOrderString = sortOrder.orElse("ASC");
                queryString.append(" ORDER BY tt.").append(sortBy.get()).append(" ").append(sortOrderString);
            }
            Query<TransmissionType> queryList = session.createQuery(queryString.toString(), TransmissionType.class);
            queryList.setProperties(queryParams);

            return queryList.list();
        }
    }


}
