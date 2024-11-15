package vn.duantn.sominamshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return new CustomSuccessHandler();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .dispatcherTypeMatchers(DispatcherType.FORWARD,
                                DispatcherType.INCLUDE)
                        .permitAll()
                        .requestMatchers("/", "/login", "/register", "/product/**", "/client/**",
                                "/css/**",
                                "/js/**", "/images/**", "/vendors/**", "/fonts/**", "/img/**")
                        .permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                // .sessionManagement((sessionManagement) -> sessionManagement
                // .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                // .invalidSessionUrl("/logout?expired")
                // .maximumSessions(1)
                // .maxSessionsPreventsLogin(false))
                // .logout(logout ->
                // logout.deleteCookies("JSESSIONID").invalidateHttpSession(true))
                // .rememberMe(r -> r.rememberMeServices(rememberMeServices()))
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .failureUrl("/login?error")
                        .successHandler(customSuccessHandler())
                        .permitAll());
        // .exceptionHandling(ex -> ex.accessDeniedPage("/access-deny"));
        return http.build();
    }
}
