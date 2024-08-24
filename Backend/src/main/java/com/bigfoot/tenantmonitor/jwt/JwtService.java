package com.bigfoot.tenantmonitor.jwt;

import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import java.security.SignatureException;
import java.util.Date;
import java.util.function.Function;


/*
 * Service to perform JWT related actions, like
 * Generating token, get claims, validate token
 * */
@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String SECRET_KEY;

    public String getUserName(String jwt){
        return extractClaims(jwt, Claims::getSubject);
    }

    public boolean isExpired(String jwt){
        return getExpiration(jwt).before(new Date());
    }

    public Date getExpiration(String jwt){
        return extractClaims(jwt, Claims::getExpiration);
    }

    private <T> T extractClaims(String jwt, Function<Claims, T> claimsResolver){
        Claims claims = extractAllClaims(jwt);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwt).getBody();
    }

    public String generateToken(UserDetails userDetails, Integer expiration){
        return Jwts
                .builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .setSubject(userDetails.getUsername())
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                .compact();
    }

    public boolean isValidToken(String jwt){
        try{
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwt).getBody();
            return !isExpired(jwt);
        }catch(Exception e){
            return false;
        }

    }
}