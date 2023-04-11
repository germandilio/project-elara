package ru.hse.elarateam.productsget.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import ru.hse.elarateam.productsget.model.Feature;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {
    @NonNull
    @Override
    List<Feature> findAll();

    @NonNull
    @Override
    Optional<Feature> findById(@RequestParam("id") @NonNull Long id);
}
