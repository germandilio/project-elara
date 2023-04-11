package ru.hse.elarateam.productsget.web.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import ru.hse.elarateam.productsget.model.Color;

import java.util.List;
import java.util.Optional;

@Repository
public interface ColorsRepository extends JpaRepository<Color, Long> {
    @NonNull
    @Override
    List<Color> findAll();

    @NonNull
    @Override
    Optional<Color> findById(@RequestParam("id") @NonNull Long id);
}
