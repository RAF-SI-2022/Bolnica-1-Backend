package raf.bolnica1.infirmary.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import raf.bolnica1.infirmary.dataGenerators.classes.domain.HospitalRoomGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.hospitalRoom.HospitalRoomCreateDtoGenerator;
import raf.bolnica1.infirmary.domain.HospitalRoom;
import raf.bolnica1.infirmary.dto.hospitalRoom.HospitalRoomCreateDto;
import raf.bolnica1.infirmary.dto.hospitalRoom.HospitalRoomDto;
import raf.bolnica1.infirmary.mapper.HospitalRoomMapper;
import raf.bolnica1.infirmary.repository.HospitalRoomRepository;
import raf.bolnica1.infirmary.services.HospitalRoomService;
import raf.bolnica1.infirmary.services.impl.HospitalRoomServiceImpl;
import raf.bolnica1.infirmary.validation.ClassJsonComparator;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class HospitalRoomUnitTest {

    private HospitalRoomCreateDtoGenerator hospitalRoomCreateDtoGenerator=HospitalRoomCreateDtoGenerator.getInstance();
    private ClassJsonComparator classJsonComparator=ClassJsonComparator.getInstance();
    private HospitalRoomGenerator hospitalRoomGenerator=HospitalRoomGenerator.getInstance();


    /// MOCK
    private HospitalRoomMapper hospitalRoomMapper;
    private HospitalRoomRepository hospitalRoomRepository;
    private HospitalRoomService hospitalRoomService;


    @BeforeEach
    public void prepare(){
        hospitalRoomMapper=new HospitalRoomMapper();
        hospitalRoomRepository=mock(HospitalRoomRepository.class);
        hospitalRoomService=new HospitalRoomServiceImpl(hospitalRoomMapper,hospitalRoomRepository);
    }


    @Test
    public void createHospitalRoomTest(){

        long hospitalRoomId=2;

        HospitalRoomCreateDto hospitalRoomCreateDto=hospitalRoomCreateDtoGenerator.getHospitalRoomCreateDto();

        HospitalRoom hospitalRoom=hospitalRoomMapper.toEntity(hospitalRoomCreateDto);
        hospitalRoom.setId(hospitalRoomId);
        given(hospitalRoomRepository.save(any())).willReturn(hospitalRoom);

        HospitalRoomDto result=hospitalRoomService.createHospitalRoom(hospitalRoomCreateDto);

        Assertions.assertTrue(classJsonComparator.compareCommonFields(result,hospitalRoomCreateDto));
        Assertions.assertTrue(result.getId().equals(hospitalRoomId));
        Assertions.assertTrue(result.getOccupancy()==0);
    }


    @Test
    public void deleteHospitalRoomTest(){

        long hospitalRoomId=2;

        hospitalRoomService.deleteHospitalRoom(hospitalRoomId);

        verify(hospitalRoomRepository).deleteById(hospitalRoomId);

    }


    @Test
    public void getHospitalRoomsByDepartmentIdTest(){

        long departmentId=3;
        int hospitalRoomCount=10;

        List<HospitalRoom> hospitalRooms=new ArrayList<>();
        for(int i=0;i<hospitalRoomCount;i++){
            hospitalRooms.add(hospitalRoomGenerator.generateHospitalRoom());
            hospitalRooms.get(i).setId((long)i);
        }
        Page<HospitalRoom> page=new PageImpl<>(hospitalRooms);

        given(hospitalRoomRepository.findHospitalRoomsByDepartmentId(any(),eq(departmentId) )).willReturn(page);

        Page<HospitalRoomDto> result=hospitalRoomService.getHospitalRoomsByDepartmentId(departmentId,0,hospitalRoomCount);

        Assertions.assertTrue(classJsonComparator.compareListCommonFields(result.getContent(),hospitalRooms));

    }


    @Test
    public void getHospitalRoomByIdTest(){

        long hospitalRoomId=3;
        int hospitalRoomCount=10;

        HospitalRoom hospitalRoom=hospitalRoomGenerator.generateHospitalRoom();
        hospitalRoom.setId(hospitalRoomId);

        given(hospitalRoomRepository.findHospitalRoomById(hospitalRoomId)).willReturn(hospitalRoom);

        HospitalRoomDto result=hospitalRoomService.getHospitalRoomById(hospitalRoomId);

        Assertions.assertTrue(classJsonComparator.compareCommonFields(result,hospitalRoom));
    }

}
