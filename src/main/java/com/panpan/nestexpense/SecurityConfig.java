package com.panpan.nestexpense;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsService userDetailService;

    public SecurityConfig(UserDetailsService userDetailService) {
        this.userDetailService = userDetailService;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.
                authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        //Allow public request in login page
                        .requestMatchers("/login").permitAll()
                        //Allow public request in signup page
                        .requestMatchers("/signup").permitAll()
                        //All other request require authentication
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        //Specify the custom login page URL
                        .loginPage("/login")
                        //Tell the user is an email
                        .usernameParameter("email")
                        //Redirect to home once success
                        .defaultSuccessUrl("/", true)
                        //Allow access to login page for all
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}
