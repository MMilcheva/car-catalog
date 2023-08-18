package com.example.carcatalog.repositories;

import com.example.carcatalog.dto.ModelFilterOptions;
import com.example.carcatalog.models.Model;
import com.example.carcatalog.repositories.contracts.ModelRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ModelRepositoryImpl extends AbstractCRUDRepository<Model> implements ModelRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public ModelRepositoryImpl(SessionFactory sessionFactory) {
        super(Model.class, sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Model> getAllModels(Optional<String> search) {
        if (search.isEmpty()) {
            return getAll();
        }
        try (Session session = sessionFactory.openSession()) {
            Query<Model> list = session.createQuery(" from Model where " +
                    "modelName like :modelName ");
            list.setParameter("modelName", "%" + search.get() + "%");
            return list.list();
        }
    }

    @Override
    public Model getModelByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<Model> model = session.createQuery(" from Model where " +
                    "modelName = :modelName ");
            model.setParameter("modelName", name);
            return model.list().get(0);
        }
    }

    @Override
    public List<Model> getAllModelsByBrandId(Long brandId) {

        try (Session session = sessionFactory.openSession()) {
            Query<Model> list = session.createQuery(" from Model where " +
                    "brand.brandId=:brandId ");
            list.setParameter("brandId", brandId);

            return list.list();
        }
    }

    public List<Model> getAllModelsFilter(ModelFilterOptions modelFilterOptions) {
        return filter(modelFilterOptions.getModelId(),
                modelFilterOptions.getModelName(),
                modelFilterOptions.getBrandName(),
                modelFilterOptions.getSortBy(),
                modelFilterOptions.getSortOrder());
    }

    @Override
    public List<Model> filter(Optional<Long> modelId,
                              Optional<String> modelName,
                              Optional<String> brandName,
                              Optional<String> sortBy,
                              Optional<String> sortOrder) {

        try (Session session = sessionFactory.openSession()) {
            StringBuilder queryString = new StringBuilder(" Select m from Model m ");
            ArrayList<String> filter = new ArrayList<>();
            Map<String, Object> queryParams = new HashMap<>();

            modelName.ifPresent(value -> {
                filter.add(" m.modelName like :modelName ");
                queryParams.put("modelName", "%" + value + "%");
            });

            brandName.ifPresent(value -> {
                filter.add(" m.brand.brandName like :brandName ");
                queryParams.put("brandName", "%" + value + "%");
            });

            if (!filter.isEmpty()) {
                queryString.append(" where ").append(String.join(" and ", filter));
            }

            if (sortBy.isPresent() && !sortBy.get().isEmpty()) {
                String sortOrderString = sortOrder.orElse("ASC");
                queryString.append(" ORDER BY m.").append(sortBy.get()).append(" ").append(sortOrderString);
            }
            Query<Model> queryList = session.createQuery(queryString.toString(), Model.class);
            queryList.setProperties(queryParams);

            return queryList.list();
        }
    }

    private String generateStringFromSort(String value) {
        StringBuilder queryString = new StringBuilder(" order by ");
        String[] params = value.split("_");

        if (value.isEmpty()) {
            return "";
        }

        switch (params[0]) {
            case "title":
                queryString.append(" p.title ");
                break;
            case "content":
                queryString.append(" p.content ");
                break;
            case "username":
                queryString.append(" u.userName ");
                break;
            case "startDate":
            case "endDate":
                queryString.append(" p.creationDate");
                break;

            default:
                throw new UnsupportedOperationException(
                        "Sort should have max two params divided by _ symbol!");
        }

        if (params.length > 2) {
            throw new UnsupportedOperationException(
                    "Sort should have max two params divided by _ symbol!");
        }

        if (params.length == 2 && params[1].equalsIgnoreCase("desc")) {
            queryString.append(" desc ");
        }
        return queryString.toString();
    }

    @Override
    public void block(Long carModelId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Model model = session.load(Model.class, carModelId);
            session.update(model);
            session.getTransaction().commit();
        }
    }


}
