package ru.hse.elarateam.login.web.services.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.elarateam.login.dto.UserServiceInfoDTO;
import ru.hse.elarateam.login.web.mappers.UsersMapper;
import ru.hse.elarateam.login.web.repositories.UsersRepository;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;

    private final UsersMapper usersMapper;

    @Transactional(readOnly = true)
    @Override
    public UserServiceInfoDTO getUserInfoById(UUID userId) {
        final var persistentUser = usersRepository.findById(userId);
        if (persistentUser.isEmpty()) {
            throw new IllegalArgumentException("User with id " + userId + " not found");
        }

        log.debug("User with id {} found", userId);
        log.trace("User: {}", persistentUser.get());
        return usersMapper.userServiceInfoToUserServiceInfoDTO(persistentUser.get());
    }

    @Transactional(readOnly = true)
    @Override
    public UserServiceInfoDTO getUserInfoByLogin(String login) {
        final var persistentUser = usersRepository.findByLogin(login);
        if (persistentUser.isEmpty()) {
            throw new IllegalArgumentException("User with login " + login + " not found");
        }

        log.debug("User with login {} found", login);
        log.trace("User: {}", persistentUser.get());
        return usersMapper.userServiceInfoToUserServiceInfoDTO(persistentUser.get());
    }
}
