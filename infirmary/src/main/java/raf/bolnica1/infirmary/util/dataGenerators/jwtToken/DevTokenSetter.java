package raf.bolnica1.infirmary.util.dataGenerators.jwtToken;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.security.util.JwtUtils;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class DevTokenSetter {

    private final JwtUtils jwtUtils;


    public static DevTokenSetter getInstance(){
        return new DevTokenSetter(JwtUtils.getInstance());
    }

    /// setuje LBZ u security context i returnuje LBZ
    public String setToken(String customToken){

        List<GrantedAuthority> authorities = new ArrayList<>();
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(jwtUtils.getUsernameFromToken(customToken), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
        return jwtUtils.getUsernameFromToken(customToken);
    }

}
