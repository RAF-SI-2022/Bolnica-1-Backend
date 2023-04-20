package raf.bolnica1.infirmary.dataGenerators.classes.dto.examinationHistory;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import raf.bolnica1.infirmary.config.client.RestServiceClientConfig;
import raf.bolnica1.infirmary.dataGenerators.jwtToken.JwtTokenGetter;
import raf.bolnica1.infirmary.dataGenerators.primitives.*;
import raf.bolnica1.infirmary.dto.externalPatientService.medicalRecord.DiagnosisCodeDto;
import raf.bolnica1.infirmary.dto.externalPatientService.medicalRecord.MedicalRecordDto;

import java.util.List;

@Component
@AllArgsConstructor
public class DiagnosisCodeDtoGenerator {

    private final RandomString randomString;
    private final RandomNames randomNames;
    private final RandomSurnames randomSurnames;
    private final RandomJMBG randomJMBG;
    private final RandomLong randomLong;
    private final RandomDate randomDate;
    private final RandomDouble randomDouble;
    private final RandomTime randomTime;
    private final JwtTokenGetter jwtTokenGetter;
    @Qualifier("diagnosisCodeRestTemplate")
    private final RestTemplate diagnosisCodeRestTemplate;



    public static DiagnosisCodeDtoGenerator getInstance(){
        return new DiagnosisCodeDtoGenerator(RandomString.getInstance(),RandomNames.getInstance(),RandomSurnames.getInstance(),
                RandomJMBG.getInstance(),RandomLong.getInstance(),RandomDate.getInstance(),RandomDouble.getInstance(),
                RandomTime.getInstance(),JwtTokenGetter.getInstance(), RestServiceClientConfig.diagnosisCodeRestTemplate());
    }

    public DiagnosisCodeDto getDiagnosisCodeDto(){

        DiagnosisCodeDto ret=new DiagnosisCodeDto();

        String token= jwtTokenGetter.getDrMedSpec();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token);
        HttpEntity httpEntity = new HttpEntity(null, httpHeaders);
        ResponseEntity<List<DiagnosisCodeDto>> diagnosisCodeDtos = diagnosisCodeRestTemplate.exchange("", HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<DiagnosisCodeDto>>(){});
        if(diagnosisCodeDtos.getBody()==null) {
            System.out.println("NISU NADJENI DIAGNOSIS CODES");
            return null;
        }

        /*for(DiagnosisCodeDto diagnosisCodeDto:diagnosisCodeDtos.getBody())
            System.out.println(diagnosisCodeDto.getCode()+"  CODE");*/

        return diagnosisCodeDtos.getBody().get(randomLong.getLong( new Long(diagnosisCodeDtos.getBody().size()) ).intValue());

    }

}
