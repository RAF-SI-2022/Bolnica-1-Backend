package raf.bolnica1.employees.security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import raf.bolnica1.employees.checking.jwtService.TokenService;
import raf.bolnica1.employees.filters.JwtFilter;
import raf.bolnica1.employees.services.JwtUserDetailService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {


    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;
    @Autowired
    private JwtUserDetailService jwtUserDetailService;
    @Autowired
    private JwtFilter jwtFilter;
    @Value("${oauth.jwt.secret}")
    private String SECRET_KEY;
    @Autowired
    private TokenService jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/v3/api-docs", "/swagger-ui/**").permitAll()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/employee/admin/**").hasAuthority("ADMIN")
                .anyRequest().permitAll()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.headers().frameOptions().disable();

        http.addFilterBefore(this.jwtFilter, UsernamePasswordAuthenticationFilter.class);
        //  http.addFilter(new AutorizationFilter(authenticationManager(authenticationConfiguration), jwtUserDetailService, SECRET_KEY, jwtUtil));
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
