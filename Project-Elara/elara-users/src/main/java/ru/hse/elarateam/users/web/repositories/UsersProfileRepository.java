package ru.hse.elarateam.users.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.elarateam.users.model.UserProfile;

import java.util.UUID;

public interface UsersProfileRepository extends JpaRepository<UserProfile, UUID> {
    @Override
    @Query("UPDATE UserProfile userProfile SET userProfile.deleted = true WHERE userProfile.id = ?1")
    @Modifying
    @Transactional
    void deleteById(UUID uuid);

    @Override
    @Query("UPDATE UserProfile userProfile SET userProfile.deleted = true WHERE userProfile = ?1")
    @Modifying
    @Transactional
    void delete(UserProfile entity);
}
