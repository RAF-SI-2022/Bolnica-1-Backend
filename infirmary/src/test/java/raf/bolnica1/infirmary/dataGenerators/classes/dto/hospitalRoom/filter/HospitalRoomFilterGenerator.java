package raf.bolnica1.infirmary.dataGenerators.classes.dto.hospitalRoom.filter;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dataGenerators.primitives.RandomLong;
import raf.bolnica1.infirmary.domain.HospitalRoom;
import raf.bolnica1.infirmary.dto.hospitalRoom.HospitalRoomDto;

@AllArgsConstructor
@Component
public class HospitalRoomFilterGenerator {

    /// PRIMITIVE GENRATORS
    @Autowired
    private RandomLong randomLong;

    public HospitalRoomFilter getRandomFilter(){

        HospitalRoomFilter filter=new HospitalRoomFilter();

        filter.setDepartmentId(randomLong.getLong(20L));

        return filter;
    }

}
