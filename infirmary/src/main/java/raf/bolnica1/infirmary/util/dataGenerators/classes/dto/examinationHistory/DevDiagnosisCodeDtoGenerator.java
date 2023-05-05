package raf.bolnica1.infirmary.util.dataGenerators.classes.dto.examinationHistory;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import raf.bolnica1.infirmary.config.client.RestServiceClientConfig;
import raf.bolnica1.infirmary.util.dataGenerators.primitives.*;
import raf.bolnica1.infirmary.dto.externalPatientService.medicalRecord.DiagnosisCodeDto;
import raf.bolnica1.infirmary.util.dataGenerators.jwtToken.DevJwtTokenGetter;

import java.util.List;

@Component
@AllArgsConstructor
public class DevDiagnosisCodeDtoGenerator {

    private final DevRandomString devRandomString;
    private final DevRandomNames devRandomNames;
    private final DevRandomSurnames devRandomSurnames;
    private final DevRandomJMBG devRandomJMBG;
    private final DevRandomLong devRandomLong;
    private final DevRandomDate devRandomDate;
    private final DevRandomDouble devRandomDouble;
    private final DevRandomTime devRandomTime;
    private final DevJwtTokenGetter devJwtTokenGetter;
    @Qualifier("diagnosisCodeRestTemplate")
    private final RestTemplate diagnosisCodeRestTemplate;



    public static DevDiagnosisCodeDtoGenerator getInstance(){
        return new DevDiagnosisCodeDtoGenerator(DevRandomString.getInstance(), DevRandomNames.getInstance(), DevRandomSurnames.getInstance(),
                DevRandomJMBG.getInstance(), DevRandomLong.getInstance(), DevRandomDate.getInstance(), DevRandomDouble.getInstance(),
                DevRandomTime.getInstance(), DevJwtTokenGetter.getInstance(), RestServiceClientConfig.diagnosisCodeRestTemplate());
    }

    public DiagnosisCodeDto getDiagnosisCodeDto(){

        DiagnosisCodeDto ret=new DiagnosisCodeDto();

        String token= devJwtTokenGetter.getDrMedSpec();
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

        return diagnosisCodeDtos.getBody().get(devRandomLong.getLong( new Long(diagnosisCodeDtos.getBody().size()) ).intValue());

    }

}
