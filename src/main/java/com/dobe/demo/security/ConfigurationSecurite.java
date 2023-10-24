package com.dobe.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.cors.CorsConfiguration;

import javax.servlet.Filter;
import javax.sql.DataSource;
import java.net.PasswordAuthentication;
import java.util.Arrays;

@EnableWebSecurity
public class ConfigurationSecurite  extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MonUserDetailsService monUserDetailsService;

    @Autowired
    JwtFilter filter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {



        // Authentication
//        auth
//                .inMemoryAuthentication()
//                .withUser("franck")
//                .password("root")
//                .roles("UTILISATEUR")
//                .and()
//                .withUser("admin")
//                .password("azerty")
//                .roles("ADMINISTRATEUR");


        // connection jdbc

//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery("SELECT email, mot_depasse, 1 FROM utilisateur WHERE email = ?")
//                .authoritiesByUsernameQuery(
//                        " SELECT email, IF(admin,'ROLE_ADMINISTRATEUR','ROLE_UTILISATEUR') " +
//                                " FROM utilisateur" +
//                                " WHERE email = ?"
//                );




        auth.userDetailsService(monUserDetailsService);


    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http.authorizeRequests()
//                .antMatchers("/login").permitAll()
//                .antMatchers("/admin/**")
//                .hasRole("ADMINISTRATEUR")
//                .antMatchers("/**" ).hasAnyRole("ROLE_ADMINISTRATEUR", "ROLE_UTILISATEUR")
//                .and().formLogin();
//

        http.cors().configurationSource(httpServletRequest -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.applyPermitDefaultValues();
            corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
            corsConfiguration.setAllowedHeaders(
                    Arrays.asList("X-Requested-With", "Origin", "Content-Type",
                            "Accept", "Authorization","Access-Control-Allow-Origin"));
            return corsConfiguration;
        }).and()
                .csrf().disable()
                .authorizeRequests()
//               .antMatchers("/connexion", "/inscription", "/utilisateur-par-pays/{monpays}/**").permitAll()
                .antMatchers("/connexion", "/inscription", "/").permitAll()
                //.antMatchers("/login").permitAll()
                .antMatchers("/admin/**").hasAnyRole("ADMINISTRATEUR", "SUPER_ADMINISTRATEUR")
                .antMatchers("/**" ).hasAnyRole("ADMINISTRATEUR", "UTILISATEUR", "SUPER_ADMINISTRATEUR")

                .anyRequest().authenticated()
                .and().exceptionHandling()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


                http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);






}


    @Bean
    public PasswordEncoder createPasswordEncoder () {

        return new BCryptPasswordEncoder ();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean () throws Exception {
        return super.authenticationManagerBean();
    }
}
