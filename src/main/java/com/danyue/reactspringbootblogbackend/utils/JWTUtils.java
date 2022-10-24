package com.danyue.reactspringbootblogbackend.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static com.danyue.reactspringbootblogbackend.constants.SystemConstants.JWT_EXPIRE_TIME;

public class JWTUtils {
    public static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String createJWT(String id) {
        Instant now = Instant.now();

        return Jwts.builder()
                .setSubject(id)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(JWT_EXPIRE_TIME, ChronoUnit.MINUTES)))
                .signWith(key)
                .compact();
    }

    public static String parseJWT(String jws) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws)
                .getBody()
                .getSubject();
    }
}
