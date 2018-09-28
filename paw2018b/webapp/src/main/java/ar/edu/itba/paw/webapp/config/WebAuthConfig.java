package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@ComponentScan("ar.edu.itba.paw.webapp.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    /*TODO: security so as to not put info on the url*/
    /*TODO: CHECK from the tables, have to decide between user, doctor, regular user*/

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    };

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

//      auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());

        //add our users for in memory authentication
        auth.inMemoryAuthentication().withUser("martina").password("martina").roles("DOCTOR");
        auth.inMemoryAuthentication().withUser("esteban").password("esteban").roles("DOCTOR");
        auth.inMemoryAuthentication().withUser("oliver").password("oliver").roles("PACIENTE");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/javascript/**", "/images/**", "/favicon.ico", "/403");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http./*userDetailsService(userDetailsService).*/authorizeRequests()
                .antMatchers("/doctorPanel/**").hasRole("DOCTOR")
                .antMatchers("/patientPanel/**").hasRole("PACIENTE").and().formLogin()
                .loginPage("/showLogIn").successHandler(successHandler())
                .permitAll().and().logout().permitAll().and().exceptionHandling()
                .accessDeniedPage("/403");
    }
    /*TODO: aca con los .accesDenied se puede usar para todo? Con eso manejamos los stack traces?*/
    /*TODO AUTOLOGIN:
    * https://www.baeldung.com/spring-security-auto-login-user-after-registration
    * https://stackoverflow.com/questions/3813028/auto-login-after-successful-registration
    * https://stackoverflow.com/questions/46221515/spring-security-auto-login-not-working-after-registering-user
    * https://coderanch.com/t/627731/frameworks/Autologin-site-registering-spring-security
    * http://forum.spring.io/forum/spring-projects/security/19216-automatic-login-after-user-registration*/

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new CustomLogInSuccessHandler("/");
    }

}
