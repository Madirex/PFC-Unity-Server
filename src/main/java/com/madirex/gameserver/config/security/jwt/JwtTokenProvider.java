package com.madirex.gameserver.config.security.jwt;

import com.madirex.gameserver.model.User;
import com.madirex.gameserver.model.UserRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Log
@Component
public class JwtTokenProvider {
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String TOKEN_SECRET = "L2aUXz9EsdfCsdfUTPPtBlNda1wCaQlsdgsg7pC0CUMysdgFBPfhfgwm6ZpfDmdKJj509g4mlk00cd1Sq9otDfghxufghsvEn9PGCSg3YTy4Qfg6fgPfghkvmncjhg8jPdpJXmp38fghRghjgXtBfghztfghxdPfghfhtfghPhgGfghghvjrbm3kSHprtxjkRqMMr3bJqJ7bk23o0849hgeupi35hFSS59XzfghF9BxiphgjcrghjgQGL7X8SnEM8Xbr5o8gfhnEklj439g845nt345h46h";
    public static final String TOKEN_TYPE = "JWT";

    @Value("${jwt.secret:" + TOKEN_SECRET + "}")
    private String secretJwt;

    @Value("${jwt.token-expiration:86400}")
    private int jwtExpirationInSeconds;

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date tokenExpirationDate = new Date(System.currentTimeMillis() + (jwtExpirationInSeconds * 1000));

        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(secretJwt.getBytes()), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", TOKEN_TYPE)
                .setSubject(user.getId())
                .setIssuedAt(new Date())
                .setExpiration(tokenExpirationDate)
                .claim("name", user.getUsername())
                .claim("roles", user.getRoles().stream()
                        .map(UserRole::name)
                        .collect(Collectors.joining(", "))
                )
                .compact();
    }

    public String getUserIdFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretJwt.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretJwt.getBytes()))
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.info("Error en la firma del token JWT: " + ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.info("Token malformado: " + ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.info("El token ha expirado: " + ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.info("Token JWT no soportado: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.info("JWT claims vacío");
        }
        return false;
    }
}
