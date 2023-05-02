package raf.bolnica1.infirmary.runner;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.domain.HospitalRoom;
import raf.bolnica1.infirmary.domain.PatientState;
import raf.bolnica1.infirmary.repository.*;

@Profile({"default"})
@Component
@AllArgsConstructor
public class TestDataRunner implements CommandLineRunner {

    private HospitalRoomRepository hospitalRoomRepository;
    private DischargeListRepository dischargeListRepository;
    private HospitalizationRepository hospitalizationRepository;
    private PatientStateRepository patientStateRepository;
    private PrescriptionRepository prescriptionRepository;
    private ScheduledAppointmentRepository scheduledAppointmentRepository;
    private VisitRepository visitRepository;

    private void clearAllRepositories(){
        hospitalRoomRepository.deleteAll();
        dischargeListRepository.deleteAll();
        hospitalizationRepository.deleteAll();
        patientStateRepository.deleteAll();
        prescriptionRepository.deleteAll();
        scheduledAppointmentRepository.deleteAll();
        visitRepository.deleteAll();
    }

    @Override
    public void run(String... args) throws Exception {
        ///clearAllRepositories();
        createHospitalRooms();
    }

    private void createHospitalRooms() {
        HospitalRoom room1 = new HospitalRoom();
        room1.setIdDepartment(Long.valueOf(1));
        room1.setRoomNumber(123);
        room1.setName("Soba 1");
        room1.setCapacity(1);
        room1.setOccupancy(1);
        room1.setDescription("Jednokrevetna soba");
        hospitalRoomRepository.save(room1);

        HospitalRoom room2 = new HospitalRoom();
        room2.setIdDepartment(Long.valueOf(1));
        room2.setRoomNumber(124);
        room2.setName("Soba 2");
        room2.setCapacity(2);
        room2.setOccupancy(1);
        room2.setDescription("Dvokrevetna soba");
        hospitalRoomRepository.save(room2);

        HospitalRoom room3 = new HospitalRoom();
        room3.setIdDepartment(Long.valueOf(1));
        room3.setRoomNumber(125);
        room3.setName("Soba 3");
        room3.setCapacity(3);
        room3.setOccupancy(2);
        room3.setDescription("Trokrevetna soba");
        hospitalRoomRepository.save(room3);

        HospitalRoom room4 = new HospitalRoom();
        room4.setIdDepartment(Long.valueOf(2));
        room4.setRoomNumber(126);
        room4.setName("Soba 4");
        room4.setCapacity(4);
        room4.setOccupancy(2);
        room4.setDescription("Cetvorokrevetna soba");
        hospitalRoomRepository.save(room4);
    }


}