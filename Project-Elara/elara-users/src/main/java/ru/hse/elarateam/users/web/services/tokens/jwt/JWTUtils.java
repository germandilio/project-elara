package ru.hse.elarateam.users.web.services.tokens.jwt;

public interface JWTUtils {
    /**
     * Generates token for user with given login
     *
     * @param login user login
     * @return generated token
     */
    String generateToken(String login);

    /**
     * Validates token and returns login from it
     *
     * @param token token to validate
     * @return login from token or null if token is invalid
     */
    String getUserLoginFromToken(String token);
}
