package raf.bolnica1.patient.cucumber.dataGenerators.primitives;

import org.springframework.stereotype.Component;
import raf.bolnica1.patient.domain.Patient;
import raf.bolnica1.patient.repository.PatientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class RandomPatientData {

    private PatientRepository patientRepository;

    private final List<String> names = new ArrayList<>();
    private final List<String> surnames = new ArrayList<>();
    private final List<String> jmbgs = new ArrayList<>();
    private final List<String> lbps = new ArrayList<>();
    private Random random=new Random();

    public RandomPatientData(PatientRepository patientRepository){

        this.patientRepository = patientRepository;

        List<Patient> patients =patientRepository.findAll();
        for(Patient patient:patients) {
            names.add(patient.getName());
            surnames.add(patient.getSurname());
            jmbgs.add(patient.getJmbg());
            lbps.add(patient.getLbp());
        }

        if(names.size() == 0){
            names.add("Petar");
            names.add("Milan");
            names.add("Petra");
            names.add("Milana");

            surnames.add("Nikolic");
            surnames.add("Peric");
            surnames.add("Milovanovic");
            surnames.add("Petrovic");

            jmbgs.add("12345678");
            jmbgs.add("23456789");
            jmbgs.add("34567891");
            jmbgs.add("45678912");

            lbps.add("PL0001");
            lbps.add("PL0002");
            lbps.add("PL0003");
            lbps.add("PL0004");
        }

        /*System.out.println("NAMES LISTA");
        System.out.println(list);*/
    }

    public int getSize(){
        return names.size();
    }

    public String getNameFromPos(int pos){
        return new String(names.get(pos));
    }

    public String getNameFromRandom(){
        return new String(names.get(Math.abs(random.nextInt())%getSize()));
    }

    public String getSurnameFromRandom(){
        return new String(surnames.get(Math.abs(random.nextInt())%getSize()));
    }

    public String getJmbgFromRandom(){
        return new String(jmbgs.get(Math.abs(random.nextInt())%getSize()));
    }

    public String getLbpFromRandom(){
        return new String(lbps.get(Math.abs(random.nextInt())%getSize()));
    }
}
