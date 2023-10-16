package com.example.awt.services.impl;

import com.example.awt.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    public String generateToken(UserDetails userDetails){
        return Jwts.builder().subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()*1000 * 24 *60) )
                .signWith(getSignaturekey())
                .compact();
    }
    private <T> T extractClaims(String token, Function<Claims,T> claimsResolver){
        final Claims claims =extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String ExtractUserName(String token){
        return extractClaims(token,Claims::getSubject);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSignaturekey()).build().parseSignedClaims(token).getPayload();
    }

    private SecretKey getSignaturekey() {
        byte[] keys = Decoders.BASE64.decode("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Imh1c3NlaW4gZm91YW5pIiwiaWF0IjoxNTE2MjM5MDIyfQ.0gnnphYcklZhipctqs7pChGz8Tl8_qldF9KqJaXUoFs");
        return Keys.hmacShaKeyFor(keys);
    }
    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username = ExtractUserName(token);
        return ((username).equals(userDetails.getUsername()) && isTokenExpired( token));
    }

    @Override
    public String generateRefreshToken(HashMap<String, Object> extractClaims, UserDetails userDetails) {
        return Jwts.builder().claims(extractClaims).subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()*60480000) )
                .signWith(getSignaturekey())
                .compact();
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token,Claims::getExpiration).before(new Date());

    }
}
