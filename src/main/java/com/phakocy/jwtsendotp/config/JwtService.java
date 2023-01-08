package com.phakocy.jwtsendotp.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    //Take this key to the application properties and use @Value
    private static final String SECRET_KEY = "50645367566B5970337336763979244226452948404D6351665468576D5A7134";
    public String getUsernameFromToken(String jwtToken) {
        return getClaimFromToken(jwtToken, Claims::getSubject); // Subject is the email of the user
    }


    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) { // Extract a Single claim
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) { //Extract all claims at Once
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigninKey()) //Generate Online from "https://www.allkeysgenerator.com/Random/Security-Encryption-Key-Generator.aspx" remember to click "yes" for Hex
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Key getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    //Generate Token for User
    public String generateToken(UserDetails userDetails) {
//        Map<String, Object> claims = new HashMap<>();
        return generateToken(new HashMap<>(), userDetails);
    }


    /* While Creating the Token
     * 1. Define claims of token, like Issuer, Expiration, Subject, and the ID
     * 2. Sign the JWT using HS512 algorithm and secret key
     */
    public String generateToken(Map<String, Object> extractClaims, UserDetails userDetails) {

        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 *24))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //Retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }


    //Check if Token has Expired
    private boolean isTokenExpired(String token) {
        final Date  expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //Validate Token
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token); // username is the email
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
