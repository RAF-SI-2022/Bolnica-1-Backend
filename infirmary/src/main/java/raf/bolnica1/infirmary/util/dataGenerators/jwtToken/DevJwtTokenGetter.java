package raf.bolnica1.infirmary.util.dataGenerators.jwtToken;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import raf.bolnica1.infirmary.config.client.RestServiceClientConfig;

@Component
@AllArgsConstructor
public class DevJwtTokenGetter {

    @Qualifier("loginRestTemplate")
    private final RestTemplate loginRestTemplate;


    public static DevJwtTokenGetter getInstance(){
        return new DevJwtTokenGetter(RestServiceClientConfig.loginRestTemplate());
    }


    public String getToken(String username,String password){

        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<DevCredentials> httpEntity = new HttpEntity(new DevCredentials(username,password), httpHeaders);
        ResponseEntity<DevRequestMessage> message = loginRestTemplate.exchange("", HttpMethod.POST, httpEntity, DevRequestMessage.class);
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

}
