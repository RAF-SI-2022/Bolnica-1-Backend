package raf.bolnica1.employees.dataGenerators.dto;

import org.springframework.stereotype.Component;
import raf.bolnica1.employees.dataGenerators.primitives.RandomDate;
import raf.bolnica1.employees.dataGenerators.primitives.RandomLong;
import raf.bolnica1.employees.dataGenerators.primitives.RandomNames;
import raf.bolnica1.employees.dataGenerators.primitives.RandomString;
import raf.bolnica1.employees.domain.constants.Profession;
import raf.bolnica1.employees.domain.constants.RoleShort;
import raf.bolnica1.employees.domain.constants.Title;
import raf.bolnica1.employees.dto.employee.EmployeeUpdateAdminDto;
import raf.bolnica1.employees.dto.employee.EmployeeUpdateDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeUpdateAdminDtoGenerator {

    private List<EmployeeUpdateAdminDto> employees;
    private static long count;

    private RandomString randomString;
    private RandomNames randomNames;
    private RandomDate randomDate;
    private RandomLong randomLong;


    public EmployeeUpdateAdminDtoGenerator(RandomString randomString, RandomNames randomNames, RandomDate randomDate, RandomLong randomLong){
        this.randomString = randomString;
        this.randomNames = randomNames;
        this.randomDate = randomDate;
        this.randomLong = randomLong;
    }

    public void fill(String departmentPbo){
        count = 0;
        employees = new ArrayList<>();

        for(int i = 0; i<5; i++){
            employees.add(generateEmployee(departmentPbo));
        }
    }

    private EmployeeUpdateAdminDto generateEmployee(String departmentPbo) {
        EmployeeUpdateAdminDto employee = new EmployeeUpdateAdminDto();
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
        List<String>permission=new ArrayList<>();
        permission.add(RoleShort.values()[randomLong.getLong((long)RoleShort.values().length).intValue()].name());
        employee.setPermissions(permission);

        return employee;
    }
    public static EmployeeUpdateAdminDtoGenerator getInstance(){ return new EmployeeUpdateAdminDtoGenerator(RandomString.getInstance(), RandomNames.getInstance(), RandomDate.getInstance(), RandomLong.getInstance());}

    public EmployeeUpdateAdminDto getRandomEmployee(){ return employees.get(randomLong.getLong((long) employees.size()).intValue());}

    public List<EmployeeUpdateAdminDto> getEmployees() {
        return employees;
    }
}
