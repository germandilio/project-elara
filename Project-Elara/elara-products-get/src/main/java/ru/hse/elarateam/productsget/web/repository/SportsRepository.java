package ru.hse.elarateam.productsget.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import ru.hse.elarateam.productsget.model.Sport;

import java.util.List;
import java.util.Optional;

@Repository
public interface SportsRepository extends JpaRepository<Sport, Long> {
    @NonNull
    @Override
    List<Sport> findAll();

    @NonNull
    @Override
    Optional<Sport> findById(@RequestParam("id") @NonNull Long id);
}
