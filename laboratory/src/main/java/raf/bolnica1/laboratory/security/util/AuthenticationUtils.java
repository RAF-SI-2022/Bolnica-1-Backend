package raf.bolnica1.laboratory.security.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import raf.bolnica1.laboratory.exceptions.workOrder.NotAuthenticatedException;

@Component
public class AuthenticationUtils {

    public static AuthenticationUtils getInstance(){
        return new AuthenticationUtils();
    }

    public String getLbzFromAuthentication() {
        String lbz = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            lbz = (String) authentication.getPrincipal();
        }
        // temp linija, treba malo refaktorisati
        if(lbz == null) throw new NotAuthenticatedException("Something went wrong.");
        return lbz;
    }
}