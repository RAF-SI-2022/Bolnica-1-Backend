package raf.bolnica1.employees.checking.jwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import raf.bolnica1.employees.domain.Employee;
import raf.bolnica1.employees.domain.EmployeesPrivilege;
import raf.bolnica1.employees.domain.Privilege;
import raf.bolnica1.employees.repository.EmployeesPrivilegeRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class TokenServiceImpl implements TokenService{
    @Value("${oauth.jwt.secret}")
    private String SECRET_KEY;

    private EmployeesPrivilegeRepository employeesPrivilegeRepository;

    @Autowired
    public TokenServiceImpl(EmployeesPrivilegeRepository employeesPrivilegeRepository) {
        this.employeesPrivilegeRepository = employeesPrivilegeRepository;
    }

    public boolean isTokenExpired(String token){
        return parseToken(token).getExpiration().before(new Date());
    }

    public String generateToken(Claims claims){

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }

    public Claims parseToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e) {
            return null;
        }
        return claims;
    }

    public boolean validateToken(String token, UserDetails user) {
        return (user.getUsername().equals(parseToken(token).get("username", String.class)) && !isTokenExpired(token));
    }

    @Override
    public String generateToken(Employee emp) {

        List<EmployeesPrivilege> privilegesList = employeesPrivilegeRepository.findByEmployee(emp);
        List<Privilege> privileges = new ArrayList<>();
        
        for (EmployeesPrivilege empPr: privilegesList) {
            privileges.add(empPr.getPrivilege());
        }
        
        return Jwts.builder()
                .setSubject(emp.getUsername())
                .claim("name", emp.getName())
                .claim("surname", emp.getSurname())
                .claim("title", emp.getTitle())
                .claim("profession", emp.getProfession())
                .claim("LBZ", emp.getLbz())
                .claim("PBO", emp.getDepartment().getPbo())
                .claim("department", emp.getDepartment().getName())
                .claim("PBB", emp.getDepartment().getHospital().getPbb())
                .claim("hospital", emp.getDepartment().getHospital().getFullName())
                .claim("roles", privileges)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 60 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
