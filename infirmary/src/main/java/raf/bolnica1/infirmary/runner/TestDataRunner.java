package raf.bolnica1.infirmary.runner;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.repository.HospitalRoomRepository;

@Profile({"default"})
@Component
@AllArgsConstructor
public class TestDataRunner implements CommandLineRunner {

    private HospitalRoomRepository hospitalRoomRepository;

    @Override
    public void run(String... args) throws Exception {

        createHospitalRooms();
    }

    private void createHospitalRooms() {

    }


}