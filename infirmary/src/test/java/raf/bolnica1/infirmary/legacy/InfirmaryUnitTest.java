package raf.bolnica1.infirmary.legacy;

import io.cucumber.java.en.Given;
import org.apache.commons.collections4.CollectionUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raf.bolnica1.infirmary.domain.DischargeList;
import raf.bolnica1.infirmary.domain.HospitalRoom;
import raf.bolnica1.infirmary.domain.Hospitalization;
import raf.bolnica1.infirmary.domain.Prescription;
import raf.bolnica1.infirmary.domain.constants.PrescriptionStatus;
import raf.bolnica1.infirmary.domain.constants.PrescriptionType;
import raf.bolnica1.infirmary.dto.DtoDischargeList;
import raf.bolnica1.infirmary.dto.DtoHospitalization;
import raf.bolnica1.infirmary.mapper.DischargeListMapper;
import raf.bolnica1.infirmary.repository.DischargeListRepository;
import raf.bolnica1.infirmary.repository.HospitalRoomRepository;
import raf.bolnica1.infirmary.repository.HospitalizationRepository;
import raf.bolnica1.infirmary.repository.PrescriptionRepository;
import raf.bolnica1.infirmary.services.InfirmaryService;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.BDDMockito.given;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class InfirmaryUnitTest {
    @Mock
    private HospitalRoomRepository hospitalRoomRepository;
    @Mock
    private PrescriptionRepository prescriptionRepository;
    @Mock
    private HospitalizationRepository hospitalizationRepository;
    @Mock
    private DischargeListRepository dischargeListRepository;
    @Mock
    private DischargeListMapper dischargeListMapper;

    @InjectMocks
    private InfirmaryService infirmaryService;

    @Test
    void allRooms(){
        List<HospitalRoom> rooms = new ArrayList<>();
        HospitalRoom room1 = new HospitalRoom();
        room1.setIdDepartment(Long.valueOf(1));
        room1.setRoomNumber(123);
        room1.setName("Soba 1");
        room1.setCapacity(1);
        room1.setOccupancy(1);
        room1.setDescription("Jednokrevetna soba");


        HospitalRoom room2 = new HospitalRoom();
        room2.setIdDepartment(Long.valueOf(1));
        room2.setRoomNumber(124);
        room2.setName("Soba 2");
        room2.setCapacity(2);
        room2.setOccupancy(1);
        room2.setDescription("Dvokrevetna soba");

        rooms.add(room1);
        rooms.add(room2);

        given(hospitalRoomRepository.findAllByIdDepartment(Long.valueOf(1))).willReturn(Optional.of(rooms));


        try {
            Optional<List<HospitalRoom>> rooms2 = infirmaryService.findHospitalRooms(Long.valueOf(1));

            assertEquals(1,rooms2.get().get(1).getIdDepartment() );

            //Provera da li su ova dva niza ista ali zbog CollectionUtils.isEqualCollection sam dodao novi dependecy
            assertTrue(CollectionUtils.isEqualCollection(rooms2.get(), rooms));
        }catch (Exception e){
            fail(e.getMessage());
        }

    }

    @Test
    void createDischargeList(){

        Long  idDepartment = Long.valueOf(1);
        String lbp = "1";
        String followingDiagnosis = "Neka prateca dijagnoza" ;
        String anamnesis = "Anamneza";
        String analysis = "Analiza";
        String courseOfDisease = "Curse of desease";
        String summary = "zakljucak";
        String therapy = "Terapija";
        String lbzDepartment = "1";

        HospitalRoom room1 = new HospitalRoom();
        room1.setId(Long.valueOf(1));
        room1.setIdDepartment(Long.valueOf(1));
        room1.setRoomNumber(123);
        room1.setName("Soba 1");
        room1.setCapacity(0);
        room1.setOccupancy(1);
        room1.setDescription("Jednokrevetna soba");


        Prescription prescription = new Prescription();
        prescription.setId(Long.valueOf(1));
        prescription.setIdDoctor(Long.valueOf(1));
        prescription.setIdDepartmentFrom(idDepartment);
        prescription.setGetIdDepartmentTo(idDepartment);
        prescription.setLbp(lbp);
        Date date = new Date();
        Timestamp ts=new Timestamp(date.getTime());
        prescription.setCreation(ts);
        prescription.setPrescriptionType(PrescriptionType.STACIONAR);
        prescription.setPrescriptionStatus(PrescriptionStatus.NEREALIZOVAN);
        prescription.setRequestedAnalysis("Neki request analisis");
        prescription.setComment("Neki komentar");
        prescription.setReferralDiagnosis("neki referal diagnostic");
        prescription.setReferralReason("Neki referal reason");


        Hospitalization hospitalization = new Hospitalization();
        hospitalization.setId(Long.valueOf(1));
        hospitalization.setLbzDoctor("1");
        hospitalization.setPatientAdmission(ts);
        hospitalization.setHospitalRoom(room1);
        hospitalization.setLbzRegister("1");
        hospitalization.setDischargeDateAndTime(null);
        hospitalization.setPrescription(prescription);
        hospitalization.setNote("Neki note");


        DischargeList dischargeList = new DischargeList();
        dischargeList.setId(Long.valueOf(1));
        dischargeList.setAnamnesis(anamnesis);
        dischargeList.setAnalysis(analysis);
        dischargeList.setFollowingDiagnosis(followingDiagnosis);
        dischargeList.setCourseOfDisease(courseOfDisease);
        dischargeList.setSummary(summary);
        dischargeList.setTherapy(therapy);
        dischargeList.setHospitalization(hospitalization);
        dischargeList.setLbzDepartment(lbzDepartment);
        dischargeList.setLbzPrescribing("1");
        Date date1 = new Date();
        Timestamp tsTimestamp=new Timestamp(date1.getTime());
        dischargeList.setCreation(tsTimestamp);


        DtoDischargeList dtoDischargeList1 = new DtoDischargeList();
        dtoDischargeList1.setId(Long.valueOf(1));
        dtoDischargeList1.setAnamnesis(anamnesis);
        dtoDischargeList1.setAnalysis(analysis);
        dtoDischargeList1.setFollowingDiagnosis(followingDiagnosis);
        dtoDischargeList1.setCourseOfDisease(courseOfDisease);
        dtoDischargeList1.setSummary(summary);
        dtoDischargeList1.setTherapy(therapy);
        dtoDischargeList1.setHospitalizationId(hospitalization.getId());
        dtoDischargeList1.setLbzDepartment(lbzDepartment);
        dtoDischargeList1.setLbzPrescribing("1");
        Date date2 = new Date();
        Timestamp tsTimestamp2=new Timestamp(date2.getTime());
        dtoDischargeList1.setCreation(tsTimestamp2);



//        given(hospitalRoomRepository.decrementCapasity(Long.valueOf(1))).willReturn(room1);
        given(prescriptionRepository.findByLbp(lbp)).willReturn(Optional.of(prescription));
        given(hospitalizationRepository.findByPrescription(prescription)).willReturn(hospitalization);
        given(dischargeListRepository.save(dischargeList)).willReturn(dischargeList);
        given(dischargeListMapper.toDto(dischargeList)).willReturn(dtoDischargeList1);


        try {
            DtoDischargeList dtoDischargeList = infirmaryService.createDischargeList(idDepartment,lbp,followingDiagnosis,anamnesis,analysis,courseOfDisease,summary,therapy,lbzDepartment);


            assertNotNull(dtoDischargeList);
            assertEquals(dtoDischargeList.getHospitalizationId(),hospitalization.getId());

        }catch (Exception e){
            fail(e.getMessage());
        }


    }


    @Test
    void patientAdmission(){

        Long  idDepartment = Long.valueOf(1);
        String lbp = "1";
        String note =  "neki note";
        String lbzDoctor = "1";
        String refferalDiagnosis = "Neki refferal";
        Long idPrescription = Long.valueOf(1);

        HospitalRoom room1 = new HospitalRoom();
        room1.setId(Long.valueOf(1));
        room1.setIdDepartment(idDepartment);
        room1.setRoomNumber(123);
        room1.setName("Soba 1");
        room1.setCapacity(0);
        room1.setOccupancy(1);
        room1.setDescription("Jednokrevetna soba");

        Prescription prescription = new Prescription();
        prescription.setId(Long.valueOf(1));
        prescription.setIdDoctor(Long.valueOf(1));
        prescription.setIdDepartmentFrom(idDepartment);
        prescription.setGetIdDepartmentTo(idDepartment);
        prescription.setLbp(lbp);
        Date date = new Date();
        Timestamp ts=new Timestamp(date.getTime());
        prescription.setCreation(ts);
        prescription.setPrescriptionType(PrescriptionType.STACIONAR);
        prescription.setPrescriptionStatus(PrescriptionStatus.NEREALIZOVAN);
        prescription.setRequestedAnalysis("Neki request analisis");
        prescription.setComment("Neki komentar");
        prescription.setReferralDiagnosis(refferalDiagnosis);
        prescription.setReferralReason("Neki referal reason");


        Hospitalization hospitalization = new Hospitalization();
        hospitalization.setId(Long.valueOf(1));
        hospitalization.setLbzDoctor(lbzDoctor);
        hospitalization.setPatientAdmission(ts);
        hospitalization.setHospitalRoom(room1);
        hospitalization.setLbzRegister("1");
        hospitalization.setDischargeDateAndTime(null);
        hospitalization.setPrescription(prescription);
        hospitalization.setNote(note);

//         given(hospitalRoomRepository.incrementCapasity(idDepartment)).willReturn(room1);
        given(hospitalRoomRepository.findByIdDepartment(idDepartment)).willReturn(Optional.of(room1));
        given(prescriptionRepository.findById(idPrescription)).willReturn(Optional.of(prescription));
//        given(prescriptionRepository.setPrescriptionReferralDiagnosis(refferalDiagnosis,idPrescription)).willReturn();
//        given(prescriptionRepository.updatePrescriptionStatus(PrescriptionStatus.REALIZOVAN,Long.valueOf(1))).willReturn();
        given(hospitalizationRepository.save(hospitalization)).willReturn(hospitalization);


        try {
            DtoHospitalization dto = infirmaryService.pacientAdmission(idDepartment,note,lbzDoctor,refferalDiagnosis,idPrescription);

            //assertEquals(,prescription.getId());
            assertNotNull(dto);
            assertEquals(dto.getPrescriptionId(),prescription.getId());

        }catch (Exception e){
            fail(e.getMessage());
        }


    }




}
