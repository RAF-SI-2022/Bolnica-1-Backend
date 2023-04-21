package raf.bolnica1.patient.cucumber.dataGenerators.classes.domain;

import org.springframework.stereotype.Component;
import raf.bolnica1.patient.domain.Vaccination;
import raf.bolnica1.patient.domain.constants.VaccinationType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class VaccinationGenerator {

    //private final VaccinationRepository vaccinationRepository;

    private List<Vaccination> vaccinationList = new ArrayList<>();
    private Random random = new Random();

    public void generate() {

        Vaccination v1 = new Vaccination();
        v1.setName("PRIORIX");
        v1.setType(VaccinationType.VIRUS);
        v1.setDescription("Vakcina protiv morbila (malih boginja)");
        v1.setManufacturer("GlaxoSmithKline Biologicals S.A.,Belgija");
      //  vaccinationRepository.save(v1);

        Vaccination v2 = new Vaccination();
        v2.setName("HIBERIX");
        v2.setType(VaccinationType.BACTERIA);
        v2.setDescription("Kapsulirani antigen hemofilus influence tip B");
        v2.setManufacturer("GlaxoSmithKline Biologicals S.A.,Belgija");
     //   vaccinationRepository.save(v2);

        Vaccination v3 = new Vaccination();
        v3.setName("INFLUVAC");
        v3.setType(VaccinationType.VIRUS);
        v3.setDescription("Virusne vakcine protiv influence (grip)");
        v3.setManufacturer("Abbott Biologicals B.V., Holandija");
     //   vaccinationRepository.save(v3);

        vaccinationList.add(v1);
        vaccinationList.add(v2);
        vaccinationList.add(v3);
    }

    public List<Vaccination> getVaccinationList() {
        return vaccinationList;
    }

    public Vaccination getRandomVacc(){
        return vaccinationList.get(Math.abs(random.nextInt())%vaccinationList.size());
    }
}
