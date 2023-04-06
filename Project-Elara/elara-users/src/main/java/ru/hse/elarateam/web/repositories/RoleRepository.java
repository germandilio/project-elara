package ru.hse.elarateam.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hse.elarateam.model.Role;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
}
