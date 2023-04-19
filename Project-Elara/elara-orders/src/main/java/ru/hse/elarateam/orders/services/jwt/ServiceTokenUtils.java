package ru.hse.elarateam.orders.services.jwt;

public interface ServiceTokenUtils {

    String generateToken();

    boolean validateToken(String token);
}
