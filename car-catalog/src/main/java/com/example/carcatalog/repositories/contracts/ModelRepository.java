package com.example.carcatalog.repositories.contracts;

import com.example.carcatalog.dto.ModelFilterOptions;
import com.example.carcatalog.models.Model;

import java.util.List;
import java.util.Optional;

public interface ModelRepository extends BaseCRUDRepository<Model> {
    List<Model> getAllModels(Optional<String> search);

    List<Model> filter(Optional<Long> modelId, Optional<String> modelName,
                       Optional<String> brandName,
                       Optional<String> sortBy, Optional<String> sortOrder);


    List<Model> getAllModelsByBrandId(Long brandId);

    List<Model> getAllModelsFilter(ModelFilterOptions modelFilterOptions);

    void block(Long modelId);

    List<Model> findModelsByName(String modelName);
}
