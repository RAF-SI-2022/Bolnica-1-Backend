package raf.bolnica1.infirmary.services;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import raf.bolnica1.infirmary.domain.DischargeList;
import raf.bolnica1.infirmary.domain.HospitalRoom;
import raf.bolnica1.infirmary.domain.Hospitalization;
import raf.bolnica1.infirmary.domain.Prescription;
import raf.bolnica1.infirmary.domain.constants.PrescriptionStatus;
import raf.bolnica1.infirmary.dto.PatientDto;
import raf.bolnica1.infirmary.dto.dischargeListDto.DischargeListDto;
import raf.bolnica1.infirmary.dto.dischargeListDto.HospitalizationDto;
import raf.bolnica1.infirmary.dto.dischargeListDto.PrescriptionDto;
import raf.bolnica1.infirmary.repository.DischargeListRepository;
import raf.bolnica1.infirmary.repository.HospitalRoomRepository;
import raf.bolnica1.infirmary.repository.HospitalizationRepository;
import raf.bolnica1.infirmary.repository.PrescriptionRepository;
import raf.bolnica1.infirmary.security.utils.JwtUtils;

import java.net.URI;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InfirmaryService {
    private HospitalRoomRepository hospitalRoomRepository;
    private DischargeListRepository dischargeListRepository;
    private PrescriptionRepository prescriptionRepository;
    private HospitalizationRepository hospitalizationRepository;
    private RestTemplate patientRestTemplate;
    private JwtUtils jwtUtils;

    public Optional<List<HospitalRoom>> findHospitalRooms(Long  idDepartment){
        //Dohvatanje svih bolnickih soba sa datim id-jem departmana
        Optional<List<HospitalRoom>> rooms;
        rooms = hospitalRoomRepository.findAllByIdDepartment(idDepartment);

        if(rooms.isPresent()){
            return rooms;
        }
        return null;
    }

    public void createDischargeList(Long  idDepartment,String lbp,String followingDiagnosis,String anamnesis,String analysis,String courseOfDisease,String summary,String therapy){
        //Dekrementiramo kapacitet sobe
        hospitalRoomRepository.decrementCapasity(idDepartment);
        //Pronalazimo uput preko lbp-a
        Optional<Prescription> prescription = prescriptionRepository.findByLbp(lbp);
        if(!prescription.isPresent())
            throw new RuntimeException("No prescription for patient with lbp: " + lbp);
        //Pronalazimo hospitalizaciju preko uputa
        Hospitalization hospitalization = hospitalizationRepository.findByPrescription(prescription.get());

        //Kreiramo objekat otpusne liste i setujemo podatke
        DischargeList dischargeList = new DischargeList();
        dischargeList.setAnamnesis(anamnesis);
        dischargeList.setAnalysis(analysis);
        dischargeList.setFollowingDiagnosis(followingDiagnosis);
        dischargeList.setCourseOfDisease(courseOfDisease);
        dischargeList.setSummary(summary);
        dischargeList.setTherapy(therapy);
        dischargeList.setHospitalization(hospitalization);

        //Ovo dobijas iz tokena
        //dischargeList.setLbzPrescribing();

        //Moras da posaljes upit na employees
        //dischargeList.setLbzDepartment();

        Date date = new Date();
        Timestamp ts=new Timestamp(date.getTime());
        dischargeList.setCreation(ts);

        //Sejvujemo otpusnu listu
        dischargeListRepository.save(dischargeList);

    }

    public void pacientAdmission(Long  idDepartment,String lbp,String lbzDoctor,String referralDiagnosis,String note,Long idPrescription){
        //Inkrementiramo kapacitet sobe
        hospitalRoomRepository.incrementCapasity(idDepartment);

        //Pronalazimo sobu sa id-jem odeljenja
        Optional<HospitalRoom> room = hospitalRoomRepository.findByIdDepartment(idDepartment);

        //Pronalazimo uput sa id-jem uputa
        Optional<Prescription> prescription = prescriptionRepository.findById(idPrescription);

        //Setujemo status uputa na realizovan
        prescriptionRepository.updatePrescriptionStatus(PrescriptionStatus.REALIZOVAN,idPrescription);

        Hospitalization hospitalization = new Hospitalization();
        hospitalization.setDischargeDateAndTime(null);
        hospitalization.setNote(note);
        hospitalization.setLbzDoctor(lbzDoctor);
        hospitalization.setPrescription(prescription.get());
        hospitalization.setHospitalRoom(room.get());

        //Ovo dobijas iz tokena
        //hospitalization.setLbzRegister();

        Date date = new Date();
        Timestamp ts=new Timestamp(date.getTime());
        hospitalization.setPatientAdmission(ts);

        hospitalizationRepository.save(hospitalization);

    }

    public DischargeListDto findDischargeListHistory(String lbp, Date startDate, Date endDate) {

        Optional<Prescription> prescription = prescriptionRepository.findByLbp(lbp);
        if(!prescription.isPresent())
            throw new RuntimeException("No prescription for patient with lbp: " + lbp);
        Hospitalization hospitalization = hospitalizationRepository.findByPrescription(prescription.get());
        DischargeList dischargeList = dischargeListRepository.findByHospitalization(hospitalization);

        if(dischargeList == null) {
            System.out.println("Discharge list not found");
            return null;
        }

        DischargeListDto dischargeListDto = new DischargeListDto();

        if(startDate == null && endDate == null){
            dischargeListDto.setDischargeList(dischargeList);
        }

        if(startDate != null && endDate != null){
            if(dischargeList.getCreation().after(startDate) && dischargeList.getCreation().before(endDate)) {
                dischargeListDto.setDischargeList(dischargeList);
            }

            Date date1 = new Date(dischargeList.getCreation().getTime());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = formatter.format(date1);
            Date dateFormat = null;
            try {
                dateFormat = formatter.parse(formattedDate);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            if(dateFormat.equals(endDate) || dateFormat.equals(startDate)){
                dischargeListDto.setDischargeList(dischargeList);
            }else{
                dischargeList = null;
            }
        }

        if(startDate == null && endDate != null){
            Date date1 = new Date(dischargeList.getCreation().getTime());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = formatter.format(date1);
            Date dateFormat = null;
            try {
                dateFormat = formatter.parse(formattedDate);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            if(dateFormat.equals(endDate)){
                dischargeListDto.setDischargeList(dischargeList);
            }else{
                dischargeList = null;
            }
        }

        if(startDate != null && endDate == null){
            Date date1 = new Date(dischargeList.getCreation().getTime());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = formatter.format(date1);
            Date dateFormat = null;
            try {
                 dateFormat = formatter.parse(formattedDate);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            if(dateFormat.equals(startDate)){
                dischargeListDto.setDischargeList(dischargeList);
            }else{
                dischargeList = null;
            }
        }

        if(dischargeList == null) {
            System.out.println("Discharge list does not exist for the selected date");
            return null;
        }
        dischargeListDto.setHospitalizationDto(new HospitalizationDto(hospitalization.getPatientAdmission(),
                hospitalization.getDischargeDateAndTime()));
        dischargeListDto.setPrescriptionDto(new PrescriptionDto(prescription.get().getReferralDiagnosis()));
        dischargeListDto.setDischargeList(dischargeList);

//        RestTemplate restTemplate = new RestTemplate();
//        String doctorLbp = prescription.getLbp();
//        String url = "https://localhost/api/employee/find/" + doctorlbp;
//        ResponseEntity<DoctorDto> response = restTemplate.getForEntity(url, DoctorDto.class);
//
//        dischargeListDto.setDoctorDto(response.getBody());

        return dischargeListDto;
    }

    public List<PatientDto> findHospitalizedPatients(String authorization, String pbo, String lbp, String name, String surname, String jmbg) {
        List<PatientDto> res = new ArrayList<>();

        String token = authorization.split(" ")[1];
        ParameterizedTypeReference<Page<PatientDto>> responseType = new ParameterizedTypeReference<Page<PatientDto>>() {};

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token);
        HttpEntity entity = new HttpEntity(null, httpHeaders);

        URI baseUri = URI.create("/filter_patients/");
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(baseUri)
                .queryParam("lbp", lbp)
                .queryParam("jmbg", jmbg)
                .queryParam("name", name)
                .queryParam("surname", surname)
                .queryParam("page", 0)
                .queryParam("size", 2000);

        ResponseEntity<Page<PatientDto>> patients = patientRestTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, responseType);
        if(patients.getBody().isEmpty())
            return res;

        List<String> lbps = new ArrayList<>();
        patients.getBody().forEach(p -> lbps.add(p.getLbp()));

        List<String> foundLbps = hospitalizationRepository.findHospitalizedPatients(pbo, lbps);

        patients.getBody().forEach(p -> {
            if(foundLbps.contains(p.getLbp()))
                res.add(p);
        });

        return res;
    }
}
