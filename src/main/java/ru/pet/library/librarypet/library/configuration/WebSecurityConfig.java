package ru.pet.library.librarypet.library.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.pet.library.librarypet.library.constants.ResourcesConstants;
import ru.pet.library.librarypet.library.constants.UserRolesConstants;
import ru.pet.library.librarypet.library.service.userdetails.CustomUserDetailsService;

import java.util.Arrays;

import static ru.pet.library.librarypet.library.constants.ResourcesConstants.*;
import static ru.pet.library.librarypet.library.constants.UserRolesConstants.ADMIN;
import static ru.pet.library.librarypet.library.constants.UserRolesConstants.LIBRARIAN;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

   private final BCryptPasswordEncoder bCryptPasswordEncoder;;

    private final CustomUserDetailsService customUserDetailsService;

    protected  WebSecurityConfig(BCryptPasswordEncoder bCryptPasswordEncoder,
                                 CustomUserDetailsService customUserDetailsService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public HttpFirewall httpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedPercent(true);
        firewall.setAllowUrlEncodedSlash(true);
        firewall.setAllowSemicolon(true);
        firewall.setAllowedHttpMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        return firewall;
    }

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    )
                    .authorizeHttpRequests((requests) -> requests
                            .requestMatchers(RESOURCES_WHITE_LIST.toArray(String[]::new)).permitAll()
                            .requestMatchers(BOOKS_WHITE_LIST.toArray(String[]::new)).permitAll()
                            .requestMatchers(USERS_WHITE_LIST.toArray(String[]::new)).permitAll()
                            .requestMatchers(BOOKS_PERMISSION_LIST.toArray(String[]::new)).hasAnyRole(ADMIN, LIBRARIAN)
                            .anyRequest().authenticated()
                    )
                    .formLogin((form) -> form.loginPage("/login")
                            .defaultSuccessUrl("/")
                            .permitAll()
                    )
                    .logout((logout) -> logout.logoutUrl("/logout")
                            .logoutSuccessUrl("/login")
                            .invalidateHttpSession(true)
                            .deleteCookies("JSESSIONID")
                            .permitAll()
                            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    );
            return http.build();
    }



    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
}