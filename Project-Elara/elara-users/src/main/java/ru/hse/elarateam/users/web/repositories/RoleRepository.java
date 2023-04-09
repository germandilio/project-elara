package ru.hse.elarateam.users.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.elarateam.users.model.Role;
import ru.hse.elarateam.users.model.RoleEnum;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findOneByRole(RoleEnum role);

    @Override
    @Query("UPDATE Role role SET role.deleted = true WHERE role.id = ?1")
    @Modifying
    @Transactional
    void deleteById(UUID uuid);

    @Override
    @Query("UPDATE Role role SET role.deleted = true WHERE role = ?1")
    @Modifying
    @Transactional
    void delete(Role entity);
}
