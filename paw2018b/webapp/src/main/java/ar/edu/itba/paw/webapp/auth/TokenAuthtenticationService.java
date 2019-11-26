package ar.edu.itba.paw.webapp.auth;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenAuthtenticationService {
    private static final String AUTH_HEADER = "X-AUTH-TOKEN";

    @Autowired
    private TokenHandler tokenHandler;

    public void addAuthentication(final HttpServletResponse response, final Authentication authentication) {
        final String username = authentication.getName();
        final String token = tokenHandler.createTokenForUser(username);
        response.addHeader(AUTH_HEADER, token);
    }

    public Authentication getAuthentication(final HttpServletRequest request) {
        final String token = request.getHeader(AUTH_HEADER);
        if (token != null && Jwts.parser().isSigned(token)) {
            final UserDetails user = tokenHandler.parseUserFromToken(token);
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            }
        }
        return null;
    }
}
