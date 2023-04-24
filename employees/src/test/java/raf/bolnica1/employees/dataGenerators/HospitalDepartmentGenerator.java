package raf.bolnica1.employees.dataGenerators;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raf.bolnica1.employees.dataGenerators.primitives.RandomDate;
import raf.bolnica1.employees.dataGenerators.primitives.RandomLong;
import raf.bolnica1.employees.dataGenerators.primitives.RandomNames;
import raf.bolnica1.employees.dataGenerators.primitives.RandomString;
import raf.bolnica1.employees.domain.Department;
import raf.bolnica1.employees.domain.Hospital;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class HospitalDepartmentGenerator {

    private RandomNames randomNames;
    private RandomString randomString;
    private RandomDate randomDate;
    private RandomLong randomLong;

    private List<Department> departments;
    private List<Hospital> hospitals;

    private static long countDep = 1;
    private static long countHospital = 1;

    @Autowired
    public HospitalDepartmentGenerator(RandomNames randomNames, RandomString randomString, RandomDate randomDate, RandomLong randomLong){

        this.randomNames = randomNames;
        this.randomString = randomString;
        this.randomDate = randomDate;
        this.randomLong = randomLong;

        departments = new ArrayList<>();
        hospitals = new ArrayList<>();

        fill();
    }

    private void fill(){
        for(int i = 0; i<5; i++){
            hospitals.add(generateHospital());
        }

        for(int i = 0; i<20; i++){
            departments.add(generateDepartments());
        }
    }

    public Hospital generateHospital(){
        Hospital h = new Hospital();
        h.setFullName(randomString.getString(10));
        h.setShortName(randomNames.randomNameHospital());
        h.setActivity(randomString.getString(10));
        h.setAddress(randomString.getString(20));
        h.setPbb(randomString.getString(5));
        h.setDateOfEstablishment(randomDate.getFromRandom());
        h.setPlace(randomString.getString(10));
        h.setDeleted(false);
        h.setId(countHospital);
        countHospital++;

        return h;
    }

    public Department generateDepartments(){
        Department d = new Department();
        d.setHospital(hospitals.get(randomLong.getLong((long) hospitals.size()).intValue()));
        d.setName(randomNames.randomNameDep());
        d.setDeleted(false);
        d.setPbo(randomString.getString(5));
        d.setId(countDep);
        countDep++;

        return d;
    }

    public static HospitalDepartmentGenerator getInstance(){ return new HospitalDepartmentGenerator(RandomNames.getInstance(), RandomString.getInstance(), RandomDate.getInstance(), RandomLong.getInstance()); }

    public Hospital getRandomHospital(){return hospitals.get(randomLong.getLong((long) hospitals.size()).intValue());}

    public Department getRandomDepartment(){return departments.get(randomLong.getLong((long) departments.size()).intValue());}

    public List<Hospital> getHospitals() {
        return hospitals;
    }

    public List<Department> getDepartments() {
        return departments;
    }
}
