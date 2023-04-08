package raf.bolnica1.infirmary.controllers;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import raf.bolnica1.infirmary.domain.HospitalRoom;
import raf.bolnica1.infirmary.dto.DtoDischargeList;
import raf.bolnica1.infirmary.dto.PatientDto;
import raf.bolnica1.infirmary.dto.PatientInformationDto;
import raf.bolnica1.infirmary.dto.dischargeListDto.DischargeListDto;
import raf.bolnica1.infirmary.services.InfirmaryService;

import java.sql.Date;
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
    @GetMapping("/findHospitalRooms")
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
    @PostMapping("/createDischargeList")
    public ResponseEntity<DtoDischargeList> createDischargeList(@Param("idDepartment") Long idDepartment,
                                                                @Param("lbp") String lbp,
                                                                @Param("followingDiagnosis") String followingDiagnosis,
                                                                @Param("anamnesis") String anamnesis,
                                                                @Param("analysis") String analysis,
                                                                @Param("courseOfDisease") String courseOfDisease,
                                                                @Param("summary") String summary,
                                                                @Param("therapy") String therapy,
                                                                @Param("lbzDepartment") String lbzDepartment){



        return new ResponseEntity<>(infirmaryService.createDischargeList(idDepartment,lbp,followingDiagnosis,anamnesis,analysis,courseOfDisease,summary,therapy,lbzDepartment), HttpStatus.OK);
    }

    //Prijem pacijenta na stacionarno lecenje (Pravljenje hospitalizacije)
    @PostMapping("/pacientAdmission")
    public ResponseEntity<?> pacientAdmission(@Param("idDepartment") Long idDepartment,
                                                 @Param("lbzDoctor") String lbzDoctor,
                                                 @Param("referralDiagnosis") String referralDiagnosis,
                                                 @Param("note") String note,
                                                 @Param("idPrescription") Long idPrescription){



        return new ResponseEntity<>(infirmaryService.pacientAdmission(idDepartment,note,lbzDoctor,referralDiagnosis,idPrescription), HttpStatus.OK);
    }

    @GetMapping("/findDischargeListHistory/{lbp}")
    public ResponseEntity<DischargeListDto> findDischargeListHistory(@PathVariable String lbp,
                                                                     @RequestParam(required = false) Date startDate,
                                                                     @RequestParam(required = false) Date endDate)
    {
        return ResponseEntity.ok(infirmaryService.findDischargeListHistory(lbp, startDate, endDate));
    }

    @GetMapping("/patients/{pbo}")
    public ResponseEntity<List<PatientInformationDto>> findHospitalizedPatients(@RequestHeader("Authorization") String authorization,
                                                                                @PathVariable String pbo,
                                                                                @RequestParam(value = "lbp") String lbp,
                                                                                @RequestParam(value = "name") String name,
                                                                                @RequestParam(value = "surname") String surname,
                                                                                @RequestParam(value = "jmbg") String jmbg){
        return new ResponseEntity<>(infirmaryService.findHospitalizedPatients(authorization, pbo, lbp, name, surname, jmbg), HttpStatus.OK);
    }

}
