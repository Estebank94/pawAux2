package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.PatientService;
import ar.edu.itba.paw.models.Patient;
import ar.edu.itba.paw.models.exceptions.NotFoundPacientException;
import ar.edu.itba.paw.models.exceptions.NotValidEmailException;
import org.springframework.security.core.userdetails.User;
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

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {

        Patient user = null;
        try {
            user = us.findPatientByEmail(email);
        } catch (NotValidEmailException e) {
            e.printStackTrace();
        } catch (NotFoundPacientException e) {
            e.printStackTrace();
        }

        if (user == null) {
            throw new UsernameNotFoundException("No user found with email " + email);
        }

        final Collection<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_PATIENT"));


        if(user.getDoctor() != null && user.getDoctor().getId() != 0) {
            authorities.add(new SimpleGrantedAuthority("ROLE_DOCTOR"));
        }

        return new User(email, user.getPassword(), authorities);
    }

}
