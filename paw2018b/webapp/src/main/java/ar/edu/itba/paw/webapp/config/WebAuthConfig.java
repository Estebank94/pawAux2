package ar.edu.itba.paw.webapp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;




@Configuration
@EnableWebSecurity
@ComponentScan("ar.edu.itba.paw.webapp")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //add our users for in memory authentication
        auth.inMemoryAuthentication().withUser("martina").password("martina").roles("DOCTOR");
        auth.inMemoryAuthentication().withUser("esteban").password("esteban").roles("DOCTOR");
        auth.inMemoryAuthentication().withUser("oliver").password("oliver").roles("PACIENTE");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/css/**", "resources/javascript/**", "resources/images/**", "/favicon.ico", "/403");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").hasAnyRole("DOCTOR", "PACIENTE")
                .antMatchers("/doctorPanel/**").hasRole("DOCTOR")
                .antMatchers("/patientPanel/**").hasRole("PACIENTE").and().formLogin().loginPage("/showLogIn")
                .loginProcessingUrl("/authenticateUser").permitAll().and().logout().permitAll().and().exceptionHandling()
        .accessDeniedPage("/403");
    }
//        http.authorizeRequests()
//                .antMatchers("/doctorPanel/**").hasRole("DOCTOR")
//                .antMatchers("/patientPanel/**").hasRole("PACIENTE")
//                .and().formLogin().loginPage("/showLogIn").loginProcessingUrl("/authenticateUser").permitAll();
//    }

}