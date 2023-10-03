package ru.pet.library.librarypet.library.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import ru.pet.library.librarypet.library.constants.ResourcesConstants;
import ru.pet.library.librarypet.library.constants.UserRolesConstants;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final BCryptPasswordConfig bCryptPasswordConfig;



    protected  WebSecurityConfig(BCryptPasswordConfig bCryptPasswordConfig) {
        this.bCryptPasswordConfig = bCryptPasswordConfig;
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
                            .requestMatchers(ResourcesConstants.RESOURCES_WHITE_LIST.toArray(String[]::new)).permitAll()
                            .requestMatchers(ResourcesConstants.USERS_WHITE_LIST.toArray(String[]::new)).permitAll()
                            .requestMatchers(ResourcesConstants.BOOKS_WHITE_LIST.toArray(String[]::new)).permitAll()
                            .requestMatchers(ResourcesConstants.BOOKS_PERMISSION_LIST.toArray(String[]::new)).hasAnyRole(UserRolesConstants.ADMIN, UserRolesConstants.LIBRARIAN)
                            .anyRequest().authenticated()
                    )
                    .formLogin((form) -> form.loginPage("/login")
                            .defaultSuccessUrl("/")
                            .permitAll()
                    )
                    .logout((logout) -> logout.logoutUrl("/logout")
                            .logoutSuccessUrl("/login")
                            .invalidateHttpSession(true)
                            .permitAll()
                    );
            return http.build();
    }

}