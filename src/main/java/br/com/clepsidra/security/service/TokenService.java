package br.com.clepsidra.security.service;

import br.com.clepsidra.domain.entity.Usuario;
import br.com.clepsidra.failure.exception.TokenGenerationException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String subscriptionKey;

    public String generateToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(subscriptionKey);
            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(usuario.getNickname())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            log.error("[ERROR] Generation Token= {}", e.getMessage());
            throw new TokenGenerationException(e);
        }
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(subscriptionKey);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTCreationException e) {
            log.error("[ERROR] Validation Token= {}", e.getMessage());
            throw new TokenGenerationException(e);
        }
    }

    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }
}
