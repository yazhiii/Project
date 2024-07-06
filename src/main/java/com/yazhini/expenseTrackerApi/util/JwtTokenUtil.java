package com.yazhini.expenseTrackerApi.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {
    private static final long JWT_TOKEN_VALIDITY =5*60*100*60*100;

    @Value("${jwt.secret}")
    String secret;

    public String generateToken(UserDetails userDetails)
    {
        Map<String,Object> claims= new HashMap<>();

        return Jwts.builder().claims(claims).setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis()*JWT_TOKEN_VALIDITY*100000))
                .signWith(SignatureAlgorithm.HS512,secret).compact();

    }

//    private <T> T getCalimFromToken(String token, Function<Claims,T> claimsResolver)
//    {
//        Jwts.parser().setSigningKey(secret).parseClaimsJwts(token).getBody();
//    }

    public String getUserNameFromToken(String jwtToken)
    {
        return extractClaim(jwtToken,Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String jwtToken, UserDetails userDetails)
    {
        final String userName=getUserNameFromToken(jwtToken);
        return userName.equals(userDetails.getUsername())&& !isTokenExpired(jwtToken);

    }

    private boolean isTokenExpired(String jwtToken) {

        final Date expiration=getExpirationDateFromToken(jwtToken);
        return expiration.before(new Date());
    }

    private Date getExpirationDateFromToken(String jwtToken) {

        return extractClaim(jwtToken,Claims::getExpiration);
    }


}
