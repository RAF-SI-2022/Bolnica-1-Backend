package raf.bolnica1.employees.filters;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import raf.bolnica1.employees.domain.Privilege;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        boolean perm = false;

        if (!httpServletRequest.getRequestURI().equals("/auth")) {

            if (SecurityContextHolder.getContext().getAuthentication() == null || SecurityContextHolder.getContext().getAuthentication().getCredentials() == null) {
                httpServletResponse.setStatus(403);
            } else {

                @SuppressWarnings("unchecked")
                List<Privilege> list = (List<Privilege>) SecurityContextHolder.getContext().getAuthentication().getCredentials();

                if (httpServletRequest.getRequestURI().equals("/employee") && httpServletRequest.getMethod().equalsIgnoreCase("POST")) {
                    for (Privilege r : list) {
                        if (r.getShortName().equalsIgnoreCase("Admin")) {
                            perm = true;
                            break;
                        }
                    }
                }

                if (httpServletRequest.getRequestURI().equals("/employee") && httpServletRequest.getMethod().equalsIgnoreCase("DELETE")) {
                    for (Privilege r : list) {
                        if (r.getShortName().equalsIgnoreCase("Admin")) {
                            perm = true;
                            break;
                        }
                    }
                }

                if (httpServletRequest.getRequestURI().equals("/employee/list") && httpServletRequest.getMethod().equalsIgnoreCase("GET")) {
                    for (Privilege r : list) {
                        if (r.getShortName().equalsIgnoreCase("Admin")) {
                            perm = true;
                            break;
                        }
                    }
                }
            }

            if (perm) {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            } else {
                httpServletResponse.setStatus(403);
            }

        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
