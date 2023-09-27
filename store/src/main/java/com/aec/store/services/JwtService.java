package com.aec.store.services;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Map;
import java.util.function.Function;

/**
 * The JwtService interface provides methods for working with JSON Web Tokens (JWTs) for user authentication.
 */
public interface JwtService {

    /**
     * Extracts the username from a JWT token.
     *
     * @param token The JWT token from which to extract the username.
     * @return The username extracted from the token.
     */
    String extractUsername(String token);

    /**
     * Extracts a specific claim from a JWT token using a custom resolver.
     *
     * @param <T>            The type of the claim value.
     * @param token          The JWT token from which to extract the claim.
     * @param claimResolver  A custom resolver function to extract the claim value.
     * @return The extracted claim value.
     */
    <T> T extractClaim(String token, Function<Claims, T> claimResolver);

    /**
     * Generates a JWT token for a user based on their UserDetails.
     *
     * @param userDetails The UserDetails of the user for whom the token is generated.
     * @return The generated JWT token.
     */
    String generateToken(UserDetails userDetails);

    /**
     * Generates a JWT token with extra claims for a user based on their UserDetails.
     *
     * @param extraClaims   Additional claims to include in the token.
     * @param userDetails   The UserDetails of the user for whom the token is generated.
     * @return The generated JWT token with extra claims.
     */
    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

    /**
     * Checks if a JWT token is valid for a given user.
     *
     * @param token         The JWT token to validate.
     * @param userDetails   The UserDetails of the user to whom the token belongs.
     * @return true if the token is valid for the user, false otherwise.
     */
    boolean isTokenValid(String token, UserDetails userDetails);
}
