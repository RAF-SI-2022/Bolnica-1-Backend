package raf.bolnica1.employees.dataGenerators;

import org.springframework.stereotype.Component;
import raf.bolnica1.employees.dataGenerators.primitives.RandomDate;
import raf.bolnica1.employees.dataGenerators.primitives.RandomLong;
import raf.bolnica1.employees.dataGenerators.primitives.RandomNames;
import raf.bolnica1.employees.dataGenerators.primitives.RandomString;
import raf.bolnica1.employees.domain.Department;
import raf.bolnica1.employees.domain.Employee;
import raf.bolnica1.employees.domain.constants.Profession;
import raf.bolnica1.employees.domain.constants.Title;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeGenerator {

    private List<Employee> employees;
    private static long count;

    private RandomString randomString;
    private RandomNames randomNames;
    private RandomDate randomDate;
    private RandomLong randomLong;

    public EmployeeGenerator(RandomString randomString, RandomNames randomNames, RandomDate randomDate, RandomLong randomLong){
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

    private Employee generateEmployee(Department department) {
        Employee employee = new Employee();
        employee.setId(count);
        count++;
        employee.setPassword(randomString.getString(10));
        employee.setDeleted(false);
        employee.setEmail(randomString.getString(6) + "@ibis.rs");
        employee.setDateOfBirth(randomDate.getFromRandom());
        employee.setPhone(randomString.getString(10));
        employee.setGender("Musko");
        employee.setAddress(randomString.getString(20));
        employee.setName(randomNames.randomNameEmployee());
        employee.setSurname(randomNames.randomNameEmployee());
        employee.setDepartment(department);
        employee.setPlaceOfLiving(randomString.getString(10));
        employee.setProfession(Profession.SPEC_KARDIOLOG);
        employee.setTitle(Title.DR_MED_SPEC);
        employee.setLbz(randomString.getString(5));

        return employee;
    }

    public static EmployeeGenerator getInstance(){ return new EmployeeGenerator(RandomString.getInstance(), RandomNames.getInstance(), RandomDate.getInstance(), RandomLong.getInstance());}

    public Employee getRandomEmployee(){ return employees.get(randomLong.getLong((long) employees.size()).intValue());}

    public List<Employee> getEmployees() {
        return employees;
    }
}
