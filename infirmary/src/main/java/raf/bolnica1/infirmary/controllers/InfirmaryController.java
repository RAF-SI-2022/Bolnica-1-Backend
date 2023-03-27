package raf.bolnica1.infirmary.controllers;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import raf.bolnica1.infirmary.domain.DischargeList;
import raf.bolnica1.infirmary.domain.HospitalRoom;
import raf.bolnica1.infirmary.services.InfirmaryService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/infirmary")
public class InfirmaryController {

    private InfirmaryService infirmaryService;

    public InfirmaryController(InfirmaryService infirmaryService) {
        this.infirmaryService = infirmaryService;
    }

    //Pretraga svih bolnickih soba preko id-a odeljenja
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,value = "/findHospitalRooms")
    public ResponseEntity<Optional<List<HospitalRoom>>> findHospitalRooms(@Param("idDepartment") Long idDepartment){
        //Dohvatanje svih bolnickih soba na osnovu id-a odeljenja
        Optional<List<HospitalRoom>> rooms = infirmaryService.findHospitalRooms(idDepartment);
        //Provera da li postoje sobe
        if( rooms != null){
            return ResponseEntity.ok(rooms);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    //Kreiranje otpusne liste
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,value = "/createDischargeList")
    public ResponseEntity<?> createDischargeList(@Param("idDepartment") Long idDepartment,
                                                 @Param("lbp") String lbp,
                                                 @Param("followingDiagnosis") String followingDiagnosis,
                                                 @Param("anamnesis") String anamnesis,
                                                 @Param("analysis") String analysis,
                                                 @Param("courseOfDisease") String courseOfDisease,
                                                 @Param("summary") String summary,
                                                 @Param("therapy") String therapy){
        //Dohvatanje svih bolnickih soba na osnovu id-a odeljenja
        //DischargeList list =
                infirmaryService.createDischargeList(idDepartment,lbp,followingDiagnosis,anamnesis,analysis,courseOfDisease,summary,therapy);

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
