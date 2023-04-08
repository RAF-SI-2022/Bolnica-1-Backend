package raf.bolnica1.infirmary.dto.hospitalRoom;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HospitalRoomCreateDto {

    private Long idDepartment;
    private int roomNumber;
    private String name;
    private int capacity;
    private String description;

}
