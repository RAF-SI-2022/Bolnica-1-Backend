package raf.bolnica1.infirmary.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="hospital_room")
public class HospitalRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idDepartment;
    private int roomNumber;
    private String name;
    private int capacity;
    private int occupancy;
    private String description;
}
