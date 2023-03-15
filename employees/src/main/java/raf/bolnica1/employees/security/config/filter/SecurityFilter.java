package raf.bolnica1.employees.security.config.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import raf.bolnica1.employees.exceptionHandler.exceptions.jwt.CantParseJwtException;
import raf.bolnica1.employees.security.service.SecurityService;
import raf.bolnica1.employees.security.util.JwtUtils;
import raf.bolnica1.employees.util.JsonBuilder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.util.Map;

@Component
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final SecurityService securityService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            final String tokenHeader = request.getHeader("Authorization");

            String email = null;
            String jwtToken = null;

            if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
                jwtToken = tokenHeader.substring(7);
                email = jwtUtils.getUsernameFromToken(jwtToken);
            }

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = securityService.loadUserByUsername(email);

                if (jwtUtils.validateToken(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            }
            filterChain.doFilter(request, response);
        } catch (CantParseJwtException | ExpiredJwtException | MalformedJwtException e) {
            handleJwtException(response, e);
        }
    }

    private void handleJwtException(HttpServletResponse response, Exception ex) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");

        String error = "Unknown error.";

        if (ex instanceof CantParseJwtException || ex instanceof ExpiredJwtException || ex instanceof MalformedJwtException) {
            error = "Error parsing jwt. Double check the jwt you are sending.";
        }

        Map<String, Object> json = new JsonBuilder()
                .put("timestamp", Date.from(Instant.now()).toString())
                .put("error", error)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(json);
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }

}
