package ru.hse.elarateam.adminconsole.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hse.elarateam.adminconsole.model.Sport;

@Repository
public interface SportsRepository extends JpaRepository<Sport, Long> {
}
