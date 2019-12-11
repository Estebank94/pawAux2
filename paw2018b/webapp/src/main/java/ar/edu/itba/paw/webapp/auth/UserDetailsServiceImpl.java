package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.services.PatientService;
import ar.edu.itba.paw.models.Patient;
import ar.edu.itba.paw.models.exceptions.NotCreatePatientException;
import ar.edu.itba.paw.models.exceptions.NotFoundPacientException;
import ar.edu.itba.paw.models.exceptions.NotValidEmailException;

import ar.edu.itba.paw.models.exceptions.NotValidPatientIdException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;


@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PatientService us;


    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {

        Patient user = null;
        try {
            user = us.findPatientByEmail(email);
        } catch (NotValidPatientIdException | NotCreatePatientException | NotFoundPacientException | NotValidEmailException e) {
            user = null;
        }

        if (user == null) {
            throw new UsernameNotFoundException("No user found with email " + email);
        }

        final Collection<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_PATIENT"));


        if (user.getDoctor() != null && user.getDoctor().getId() != 0) {
            authorities.add(new SimpleGrantedAuthority("ROLE_DOCTOR"));
        }

        return new org.springframework.security.core.userdetails.User(email, user.getPassword(), user.isEnabled(),
                true, true, true, authorities);
    }


    public Patient getLoggedUser() throws NotFoundPacientException, NotValidEmailException {

        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) { // TODO: Ver que onda con esto
            return null;
        }

        final Patient user;
        try {
            user = us.findPatientByEmail(auth.getName());
        } catch (NotValidPatientIdException e) {
            return null;
        } catch (NotCreatePatientException e) {
            return null;
        }

        if(user == null){
            LOGGER.debug("Auth name " + auth.getName());
        }
        LOGGER.debug("Currently logged user is {}", auth.getDetails());
        return user;
    }
}
