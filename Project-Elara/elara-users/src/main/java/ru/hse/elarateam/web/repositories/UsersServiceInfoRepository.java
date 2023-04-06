package ru.hse.elarateam.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.elarateam.model.UserServiceInfo;

import java.util.Optional;
import java.util.UUID;

public interface UsersServiceInfoRepository extends JpaRepository<UserServiceInfo, UUID> {
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
