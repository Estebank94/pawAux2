package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@ComponentScan("ar.edu.itba.paw.webapp.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    /*TODO: security so as to not put info on the url*/
    /*TODO: CHECK from the tables, have to decide between user, doctor, regular user*/

    @Value("${prefix.remember_me}")
    private String rememberMeKey;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    };

    @Bean
    public DaoAuthenticationProvider authProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(authProvider());

        //auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());

        //add our users for in memory authentication
//        auth.inMemoryAuthentication().withUser("martina").password("martina").roles("DOCTOR");
//        auth.inMemoryAuthentication().withUser("esteban").password("esteban").roles("DOCTOR");
//        auth.inMemoryAuthentication().withUser("oliver").password("oliver").roles("PACIENTE");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/javascript/**", "/images/**", "/favicon.ico", "/403");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.userDetailsService(userDetailsService)
                .sessionManagement().and()
                .authorizeRequests()
                .antMatchers("/doctorPanel/**").hasRole("DOCTOR")
                .antMatchers("/doctorProfile/").hasRole("DOCTOR")
                .antMatchers("/patientPanel/**").hasRole("PATIENT")
                .and().formLogin()
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .loginPage("/showLogIn").successHandler(successHandler()).defaultSuccessUrl("/")
                .permitAll()
                .and().rememberMe().rememberMeParameter("j_rememberme").userDetailsService(userDetailsService)
                .key(rememberMeKey).tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(30))
                .and().logout()
                .logoutSuccessUrl("/")
                .permitAll().and().exceptionHandling()
                .accessDeniedPage("/403")
                .and().csrf().disable();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new CustomLogInSuccessHandler("/");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
