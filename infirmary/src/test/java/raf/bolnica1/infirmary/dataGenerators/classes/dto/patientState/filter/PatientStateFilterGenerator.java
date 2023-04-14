package raf.bolnica1.infirmary.dataGenerators.classes.dto.patientState.filter;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.visit.filter.VisitFilter;
import raf.bolnica1.infirmary.dataGenerators.primitives.RandomDate;
import raf.bolnica1.infirmary.dataGenerators.primitives.RandomLong;

@AllArgsConstructor
@Component
public class PatientStateFilterGenerator {
    /// PRIMITIVE GENERATORS
    @Autowired
    private RandomLong randomLong;
    @Autowired
    private RandomDate randomDate;


    public PatientStateFilter getRandomFilter(){
        PatientStateFilter patientStateFilter=new PatientStateFilter();
        long maxIds=3;
        patientStateFilter.setHospitalizationId(randomLong.getLong(maxIds));
        patientStateFilter.setStartDate(randomDate.getFromRandom());
        patientStateFilter.setEndDate(randomDate.getFromRandom());

        ///if(randomLong.getLong(2L)==0L)patientStateFilter.setHospitalizationId(null);
        if(randomLong.getLong(2L)==0L)patientStateFilter.setStartDate(null);
        if(randomLong.getLong(2L)==0L)patientStateFilter.setEndDate(null);

        return patientStateFilter;
    }
}
