package ar.edu.itba.paw.webapp.auth;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TokenHandler {

    @Autowired
    private String authTokenKey;

    @Autowired
    private UserDetailsService userDetailsService;


    public UserDetails parseUserFromToken(final String token) {
        try {
            final String username = Jwts.parser()
                    .setSigningKey(authTokenKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return userDetailsService.loadUserByUsername(username);
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
            return null;
        } catch (MalformedJwtException e) {
            e.printStackTrace();
            return null;
        } catch (SignatureException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String createTokenForUser(final String email) {
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(email)
                .signWith(SignatureAlgorithm.HS512, authTokenKey)
                .compact();
    }


}
