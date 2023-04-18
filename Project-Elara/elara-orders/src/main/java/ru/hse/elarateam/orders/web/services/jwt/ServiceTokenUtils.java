package ru.hse.elarateam.orders.web.services.jwt;

public interface ServiceTokenUtils {

    String generateToken();

    boolean validateToken(String token);
}
