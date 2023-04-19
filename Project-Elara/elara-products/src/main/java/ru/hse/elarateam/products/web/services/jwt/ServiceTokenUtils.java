package ru.hse.elarateam.products.web.services.jwt;

public interface ServiceTokenUtils {

    String generateToken();

    boolean validateToken(String token);
}
