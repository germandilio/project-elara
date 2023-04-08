package ru.hse.elarateam.login.web.services.jwt.service;

public interface ServiceTokenUtils {

    String generateToken();

    boolean validateToken(String token);
}
