package ru.hse.elarateam.login.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.elarateam.login.model.UserServiceInfo;

import java.util.Optional;
import java.util.UUID;

public interface UsersRepository extends JpaRepository<UserServiceInfo, UUID> {
    Optional<UserServiceInfo> findByLogin(@NonNull String login);

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
