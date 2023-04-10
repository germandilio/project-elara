package ru.hse.elarateam.login.web.services.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.elarateam.login.dto.UserServiceInfoDTO;
import ru.hse.elarateam.login.web.controllers.advice.UnauthorizedException;
import ru.hse.elarateam.login.web.mappers.UsersMapper;
import ru.hse.elarateam.login.web.repositories.UsersRepository;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;

    private final UsersMapper usersMapper;

    private final PasswordEncoder passwordEncoder;

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
    public UserServiceInfoDTO login(String login, String password) {
        final var persistentUser = usersRepository.findByLogin(login);
        if (persistentUser.isEmpty()) {
            throw new UnauthorizedException("User with login " + login + " not found");
        }
        log.trace("User with login {} found: {}", login, persistentUser.get());

        if (passwordEncoder.matches(password, persistentUser.get().getPassword())) {
            log.debug("User with login {} logged in", login);
            return usersMapper.userServiceInfoToUserServiceInfoDTO(persistentUser.get());
        } else {
            log.debug("User with login {} failed to log in", login);
            throw new UnauthorizedException("Wrong password");
        }
    }

    @Override
    public UserServiceInfoDTO findUserByLogin(String login) {
        final var persistentUser = usersRepository.findByLogin(login);
        if (persistentUser.isEmpty()) {
            throw new IllegalArgumentException("User with login " + login + " not found");
        }

        log.trace("User with login {} found: {}", login, persistentUser.get());
        return usersMapper.userServiceInfoToUserServiceInfoDTO(persistentUser.get());
    }
}
