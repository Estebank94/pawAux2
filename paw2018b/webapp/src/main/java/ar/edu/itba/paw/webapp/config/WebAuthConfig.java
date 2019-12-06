package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.CorsFilter;
import ar.edu.itba.paw.webapp.auth.StatelessAuthenticationFilter;
import ar.edu.itba.paw.webapp.auth.StatelessAuthenticationSuccessHandler;
import ar.edu.itba.paw.webapp.auth.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Base64;
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

    @Autowired
    private StatelessAuthenticationSuccessHandler statelessAuthenticationSuccessHandler;

    @Autowired
    private StatelessAuthenticationFilter statelessAuthenticationFilter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CorsFilter corsFilter;

    @Bean
    public DaoAuthenticationProvider authProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//        auth.authenticationProvider(authProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**",
                "/javascript/**",
                "/images/**",
                "/favicon.ico",
                "/403");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.userDetailsService(userDetailsService)
                .authorizeRequests()
                    .antMatchers("/api/**/patient/me").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/**/patient/login").anonymous()
                    .antMatchers(HttpMethod.POST, "/api/**/patient/register").anonymous()
                    .antMatchers(HttpMethod.POST, "/api/**/doctor/register").anonymous()
                     //para ver si estoy loggeado
//                    .antMatchers("/api/users/me/**").authenticated()
                    .antMatchers(HttpMethod.POST).authenticated()
                    .antMatchers(HttpMethod.DELETE).authenticated()
                    .antMatchers(HttpMethod.PUT).authenticated()
                    .antMatchers("/**").permitAll()
                .and().sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().formLogin()
                    .usernameParameter("Jusername")
                    .passwordParameter("Jpassword")
                    .loginProcessingUrl("/api/**/patient/login")
                    .successHandler(statelessAuthenticationSuccessHandler)
                    .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                .and().exceptionHandling()
                    .authenticationEntryPoint(new Http403ForbiddenEntryPoint())
                .and()
                .addFilterBefore(corsFilter, ChannelProcessingFilter.class)
                .csrf().disable()
                    .addFilterBefore(statelessAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
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


    @Bean
    public String authTokenKey() {
        return Base64.getEncoder().encodeToString("576e5a7134743777217a25432a462d4a".getBytes());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
