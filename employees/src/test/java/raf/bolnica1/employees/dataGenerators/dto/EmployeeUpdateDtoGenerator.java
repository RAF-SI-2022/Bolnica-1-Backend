package raf.bolnica1.employees.dataGenerators.dto;

import org.springframework.stereotype.Component;
import raf.bolnica1.employees.dataGenerators.primitives.RandomDate;
import raf.bolnica1.employees.dataGenerators.primitives.RandomLong;
import raf.bolnica1.employees.dataGenerators.primitives.RandomNames;
import raf.bolnica1.employees.dataGenerators.primitives.RandomString;
import raf.bolnica1.employees.dto.employee.EmployeeUpdateDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeUpdateDtoGenerator {

    private List<EmployeeUpdateDto> employees;
    private static long count;

    private RandomString randomString;
    private RandomNames randomNames;
    private RandomDate randomDate;
    private RandomLong randomLong;

    public EmployeeUpdateDtoGenerator(RandomString randomString, RandomNames randomNames, RandomDate randomDate, RandomLong randomLong){
        this.randomString = randomString;
        this.randomNames = randomNames;
        this.randomDate = randomDate;
        this.randomLong = randomLong;
    }

    public void fill(){
        count = 0;
        employees = new ArrayList<>();

        for(int i = 0; i<5; i++){
            employees.add(generateEmployee());
        }
    }

    private EmployeeUpdateDto generateEmployee() {
        EmployeeUpdateDto employee = new EmployeeUpdateDto();
        count++;
        employee.setPhone(randomString.getString(10));
        employee.setNewPassword(randomString.getString(10));
        employee.setOldPassword(randomString.getString(10));

        return employee;
    }

    public static EmployeeUpdateDtoGenerator getInstance(){ return new EmployeeUpdateDtoGenerator(RandomString.getInstance(), RandomNames.getInstance(), RandomDate.getInstance(), RandomLong.getInstance());}

    public EmployeeUpdateDto getRandomEmployee(){ return employees.get(randomLong.getLong((long) employees.size()).intValue());}

    public List<EmployeeUpdateDto> getEmployees() {
        return employees;
    }

}
