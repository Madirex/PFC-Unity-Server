package com.madirex.gameserver.config.security;

import com.madirex.gameserver.config.APIConfig;
import com.madirex.gameserver.config.security.jwt.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()

                //Users
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/users/").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/users/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, APIConfig.API_PATH + "/users/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/users/name/{username}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/users/email/{email}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/users/me").hasAnyRole("PLAYER", "ADMIN")
                .antMatchers(HttpMethod.PUT, APIConfig.API_PATH + "/users/me").hasAnyRole("PLAYER", "ADMIN")
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/users/login").permitAll()
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/users/").permitAll()

                //Login
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/logins/").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, APIConfig.API_PATH + "/logins/{id}").hasRole("ADMIN")

                //Item
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/item/").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/item/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/item/").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, APIConfig.API_PATH + "/item/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, APIConfig.API_PATH + "/item/buy/{id}").hasAnyRole("PLAYER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, APIConfig.API_PATH + "/item/{id}").hasRole("ADMIN")

                //Score
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/score/").hasAnyRole("PLAYER", "ADMIN")
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/score/").hasAnyRole("PLAYER", "ADMIN")
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/score/{id}").hasAnyRole("PLAYER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, APIConfig.API_PATH + "/score/{id}").hasAnyRole("PLAYER")

                //Shop
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/shop/").hasAnyRole("PLAYER", "ADMIN")
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/shop/").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/shop/{id}").hasAnyRole("PLAYER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, APIConfig.API_PATH + "/shop/{id}").hasRole("ADMIN")

                //Final
                .anyRequest().authenticated();

        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
