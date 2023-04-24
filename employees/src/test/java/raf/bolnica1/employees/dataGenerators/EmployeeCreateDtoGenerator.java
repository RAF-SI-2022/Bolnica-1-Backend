package raf.bolnica1.employees.dataGenerators;

import org.springframework.stereotype.Component;
import raf.bolnica1.employees.dataGenerators.primitives.RandomDate;
import raf.bolnica1.employees.dataGenerators.primitives.RandomLong;
import raf.bolnica1.employees.dataGenerators.primitives.RandomNames;
import raf.bolnica1.employees.dataGenerators.primitives.RandomString;
import raf.bolnica1.employees.domain.Department;
import raf.bolnica1.employees.domain.constants.Profession;
import raf.bolnica1.employees.domain.constants.Title;
import raf.bolnica1.employees.dto.employee.EmployeeCreateDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeCreateDtoGenerator {

    private List<EmployeeCreateDto> employees;
    private static long count;

    private RandomString randomString;
    private RandomNames randomNames;
    private RandomDate randomDate;
    private RandomLong randomLong;

    public EmployeeCreateDtoGenerator(RandomString randomString, RandomNames randomNames, RandomDate randomDate, RandomLong randomLong){
        this.randomString = randomString;
        this.randomNames = randomNames;
        this.randomDate = randomDate;
        this.randomLong = randomLong;
    }

    public void fill(Department department){
        count = 0;
        employees = new ArrayList<>();

        for(int i = 0; i<5; i++){
            employees.add(generateEmployee(department));
        }
    }

    private EmployeeCreateDto generateEmployee(String departmentPbo) {
        EmployeeCreateDto employee = new EmployeeCreateDto();
        count++;
        employee.setEmail(randomString.getString(6) + "@ibis.rs");
        employee.setDateOfBirth(randomDate.getFromRandom());
        employee.setPhone(randomString.getString(10));
        employee.setGender("Musko");
        employee.setAddress(randomString.getString(20));
        employee.setName(randomNames.randomNameEmployee());
        employee.setSurname(randomNames.randomNameEmployee());
        employee.setDepartmentPbo(departmentPbo);
        employee.setPlaceOfLiving(randomString.getString(10));
        employee.setProfession(Profession.SPEC_KARDIOLOG);
        employee.setTitle(Title.DR_MED_SPEC);
        employee.setLbz(randomString.getString(5));

        return employee;
    }

    public static EmployeeCreateDtoGenerator getInstance(){ return new EmployeeCreateDtoGenerator(RandomString.getInstance(), RandomNames.getInstance(), RandomDate.getInstance(), RandomLong.getInstance());}

    public EmployeeCreateDto getRandomEmployee(){ return employees.get(randomLong.getLong((long) employees.size()).intValue());}

    public List<EmployeeCreateDto> getEmployees() {
        return employees;
    }

}
