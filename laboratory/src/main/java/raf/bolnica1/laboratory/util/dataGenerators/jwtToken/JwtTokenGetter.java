package raf.bolnica1.laboratory.util.dataGenerators.jwtToken;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import raf.bolnica1.laboratory.appConfig.client.RestServiceClientConfig;

@Component
@AllArgsConstructor
public class JwtTokenGetter {

    @Qualifier("loginRestTemplate")
    private final RestTemplate loginRestTemplate;


    public static JwtTokenGetter getInstance(){
        return new JwtTokenGetter(RestServiceClientConfig.loginRestTemplate());
    }


    public String getToken(String username,String password){

        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<Credentials> httpEntity = new HttpEntity(new Credentials(username,password), httpHeaders);
        ResponseEntity<RequestMessage> message = loginRestTemplate.exchange("", HttpMethod.POST, httpEntity, RequestMessage.class);
        if(message.getBody()==null)
            return "";

        return message.getBody().getMessage();
    }

    public String getDefaultToken(){
        return getToken("john.doe","password");
    }

    public String getDrMedSpec(){
        return getToken("jane.smith","password");
    }

    public String getMedBiohem(){return getToken("steven.taylor","password");}

}
