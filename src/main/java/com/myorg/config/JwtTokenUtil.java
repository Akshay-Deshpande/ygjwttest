package com.myorg.config;

import static com.myorg.model.Constants.ACCESS_TOKEN_VALIDITY_SECONDS;
import static com.myorg.model.Constants.SIGNING_KEY;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.myorg.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(User user) {
        return doGenerateToken(user.getUsername(), user.getUser_role());
    }

    private String doGenerateToken(String subject, String userRole) {
    	
    	List<SimpleGrantedAuthority> grantAuthority = new ArrayList<>();
    	grantAuthority.add(new SimpleGrantedAuthority(userRole));
    	/*grantAuthority.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
    	grantAuthority.add(new SimpleGrantedAuthority("ROLE_USER"));
    	grantAuthority.add(new SimpleGrantedAuthority("ROLE_DEVELOPER"));*/

        Claims claims = Jwts.claims().setSubject(subject);
        claims.put("scopes", grantAuthority);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("http://myorg.com")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS*1000))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (
              username.equals(userDetails.getUsername())
                    && !isTokenExpired(token));
    }

}
