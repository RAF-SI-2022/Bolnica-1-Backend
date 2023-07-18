package raf.bolnica1.infirmary.runner;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.domain.HospitalRoom;
import raf.bolnica1.infirmary.mapper.DischargeListMapper;
import raf.bolnica1.infirmary.repository.*;
import raf.bolnica1.infirmary.services.*;
import raf.bolnica1.infirmary.util.dataGenerators.classes.dto.dischargeList.DevDischargeListDtoGenerator;
import raf.bolnica1.infirmary.util.dataGenerators.classes.dto.hospitalRoom.DevHospitalRoomCreateDtoGenerator;
import raf.bolnica1.infirmary.util.dataGenerators.classes.dto.hospitalization.DevHospitalizationCreateDtoGenerator;
import raf.bolnica1.infirmary.util.dataGenerators.classes.dto.patientState.DevPatientStateDtoGenerator;
import raf.bolnica1.infirmary.util.dataGenerators.classes.dto.prescription.DevPrescriptionReceiveDtoGenerator;
import raf.bolnica1.infirmary.util.dataGenerators.classes.dto.scheduledAppointment.DevScheduledAppointmentCreateDtoGenerator;
import raf.bolnica1.infirmary.util.dataGenerators.classes.dto.visit.DevVisitCreateDtoGenerator;
import raf.bolnica1.infirmary.util.dataGenerators.jwtToken.DevJwtTokenGetter;
import raf.bolnica1.infirmary.util.dataGenerators.jwtToken.DevTokenSetter;
import raf.bolnica1.infirmary.util.dataGenerators.primitives.DevRandomLong;

//@Profile({"default"})
@Component
@AllArgsConstructor
public class TestDataRunner implements CommandLineRunner {

    private AdmissionService admissionService;
    private DischargeListService dischargeListService;
    private HospitalRoomService hospitalRoomService;
    private PatientStateService patientStateService;
    private PrescriptionSendService prescriptionSendService;
    private VisitService visitService;


    private DevPrescriptionReceiveDtoGenerator devPrescriptionReceiveDtoGenerator;
    private DevVisitCreateDtoGenerator devVisitCreateDtoGenerator;
    private DevScheduledAppointmentCreateDtoGenerator devScheduledAppointmentCreateDtoGenerator;
    private DevPatientStateDtoGenerator devPatientStateDtoGenerator;
    private DevHospitalRoomCreateDtoGenerator devHospitalRoomCreateDtoGenerator;
    private DevHospitalizationCreateDtoGenerator devHospitalizationCreateDtoGenerator;
    private DevDischargeListDtoGenerator devDischargeListDtoGenerator;
    private DevRandomLong devRandomLong;
    private DevJwtTokenGetter devJwtTokenGetter;
    private DischargeListMapper dischargeListMapper;
    private DevTokenSetter devTokenSetter;


    private HospitalRoomRepository hospitalRoomRepository;
    private DischargeListRepository dischargeListRepository;
    private HospitalizationRepository hospitalizationRepository;
    private PatientStateRepository patientStateRepository;
    private PrescriptionRepository prescriptionRepository;
    private ScheduledAppointmentRepository scheduledAppointmentRepository;
    private VisitRepository visitRepository;

    private void createData(){

        int prescriptionCount=100;
        int scheduledAppointmentCount=25;
        int hospitalizationCount=50;
        int dischargeListCount=30;
        int hospitalRoomCount=30;
        int patientStateCount=150;
        int visitCount=150;

        devTokenSetter.setToken(devJwtTokenGetter.getDrMedSpec());


        for(int i=0;i<prescriptionCount;i++){
            prescriptionSendService.receivePrescription(devPrescriptionReceiveDtoGenerator.getPrescriptionReceiveDto());
        }

        for(int i=0;i<Math.min(scheduledAppointmentCount,prescriptionCount);i++){
            admissionService.createScheduledAppointment(devScheduledAppointmentCreateDtoGenerator.getScheduledAppointmentCreateDto((long)(i+1) ));
        }

        for(int i=0;i<hospitalRoomCount;i++){
            hospitalRoomService.createHospitalRoom(devHospitalRoomCreateDtoGenerator.getHospitalRoomCreateDto());
        }

        for(int i=scheduledAppointmentCount;i<Math.min(scheduledAppointmentCount+hospitalizationCount,prescriptionCount);i++){
            admissionService.createHospitalization(devHospitalizationCreateDtoGenerator
                    .getHospitalizationCreateDto((long)(devRandomLong.getLong(hospitalRoomRepository.count())+1), (long)(i+1)),
                    "Bearer "+ devJwtTokenGetter.getDrMedSpec());
        }

        for(int i=0;i<patientStateCount;i++){
            patientStateService.createPatientState(devPatientStateDtoGenerator.getPatientStateCreateDto(
                    (long)(i%hospitalizationCount+1)
            ));
        }

        for(int i=0;i<visitCount;i++){
            visitService.createVisit(devVisitCreateDtoGenerator.getVisitCreateDto(
                    (long)(i%hospitalizationCount+1)
            ));
        }

        for(int i=0;i<dischargeListCount;i++){
            dischargeListService.createDischargeList(
                    dischargeListMapper.toDto(
                        devDischargeListDtoGenerator.getDischargeListDto((long)(i%hospitalizationCount+1))
                    )
            );
        }

    }


    private void clearAllRepositories(){
        visitRepository.deleteAll();
        scheduledAppointmentRepository.deleteAll();
        patientStateRepository.deleteAll();
        dischargeListRepository.deleteAll();
        hospitalizationRepository.deleteAll();
        prescriptionRepository.deleteAll();
        hospitalRoomRepository.deleteAll();
        /*dischargeListRepository.deleteAll();
        hospitalizationRepository.deleteAll();
        hospitalRoomRepository.deleteAll();
        patientStateRepository.deleteAll();
        prescriptionRepository.deleteAll();
        scheduledAppointmentRepository.deleteAll();
        visitRepository.deleteAll();*/
    }

    @Override
    public void run(String... args) throws Exception {
        clearAllRepositories();
        boolean addTestData = true;
        if(hospitalRoomRepository.findAll().size() > 0 ||
            dischargeListRepository.findAll().size() > 0 ||
            hospitalizationRepository.findAll().size() > 0 ||
            patientStateRepository.findAll().size() > 0 ||
            prescriptionRepository.findAll().size() > 0 ||
            scheduledAppointmentRepository.findAll().size() > 0 ||
            visitRepository.findAll().size() > 0)
            addTestData = false;

        if(addTestData) {
            createHospitalRooms();
            createData();
        }
    }

    private void createHospitalRooms() {
        HospitalRoom room1 = new HospitalRoom();
        room1.setIdDepartment(Long.valueOf(1));
        room1.setRoomNumber(123);
        room1.setName("Soba 1");
        room1.setCapacity(10);
        room1.setOccupancy(1);
        room1.setDescription("Jednokrevetna soba");
        hospitalRoomRepository.save(room1);

        HospitalRoom room2 = new HospitalRoom();
        room2.setIdDepartment(Long.valueOf(1));
        room2.setRoomNumber(124);
        room2.setName("Soba 2");
        room2.setCapacity(20);
        room2.setOccupancy(1);
        room2.setDescription("Dvokrevetna soba");
        hospitalRoomRepository.save(room2);

        HospitalRoom room3 = new HospitalRoom();
        room3.setIdDepartment(Long.valueOf(1));
        room3.setRoomNumber(125);
        room3.setName("Soba 3");
        room3.setCapacity(30);
        room3.setOccupancy(2);
        room3.setDescription("Trokrevetna soba");
        hospitalRoomRepository.save(room3);

        HospitalRoom room4 = new HospitalRoom();
        room4.setIdDepartment(Long.valueOf(2));
        room4.setRoomNumber(126);
        room4.setName("Soba 4");
        room4.setCapacity(40);
        room4.setOccupancy(2);
        room4.setDescription("Cetvorokrevetna soba");
        hospitalRoomRepository.save(room4);
    }


}