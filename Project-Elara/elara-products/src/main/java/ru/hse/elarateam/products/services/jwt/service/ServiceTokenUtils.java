package ru.hse.elarateam.products.services.jwt.service;

public interface ServiceTokenUtils {

    String generateToken();

    boolean validateToken(String token);
}
