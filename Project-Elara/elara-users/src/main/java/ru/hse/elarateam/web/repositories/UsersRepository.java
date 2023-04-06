package ru.hse.elarateam.web.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.hse.elarateam.model.UserServiceInfo;

import java.util.UUID;

public interface UsersRepository extends JpaRepository<UserServiceInfo, UUID> {

    @Override
    @Query("UPDATE UserServiceInfo user SET user.deleted = true WHERE user.id = ?1")
    @Modifying
    @Transactional
    void deleteById(UUID uuid);

    @Override
    @Query("UPDATE UserServiceInfo user SET user.deleted = true WHERE user = ?1")
    @Modifying
    @Transactional
    void delete(UserServiceInfo entity);
}
