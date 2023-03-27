package raf.bolnica1.infirmary.services;
import org.springframework.stereotype.Service;
import raf.bolnica1.infirmary.domain.DischargeList;
import raf.bolnica1.infirmary.domain.HospitalRoom;
import raf.bolnica1.infirmary.domain.Hospitalization;
import raf.bolnica1.infirmary.repository.DischargeListRepository;
import raf.bolnica1.infirmary.repository.HospitalRoomRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InfirmaryService {
    private HospitalRoomRepository hospitalRoomRepository;



    public InfirmaryService(HospitalRoomRepository hospitalRoomRepository) {
        this.hospitalRoomRepository = hospitalRoomRepository;

    }

    public Optional<List<HospitalRoom>> findHospitalRooms(Long  idDepartment){
        //Dohvatanje svih bolnickih soba sa datim id-jem departmana
        Optional<List<HospitalRoom>> rooms;
        rooms = hospitalRoomRepository.findAllByIdDepartment(idDepartment);

        if(!rooms.isPresent()){
            return rooms;
        }
        return null;
    }


}

