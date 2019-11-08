package ar.edu.itba.paw.webapp.auth;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.UUID;

public class TokenHandler {

    @Autowired
    private String authTokenKey;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

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

    public String createTokenForUser(final String userEmail) {
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(userEmail)
                .signWith(SignatureAlgorithm.HS512, authTokenKey)
                .compact();
    }
}
