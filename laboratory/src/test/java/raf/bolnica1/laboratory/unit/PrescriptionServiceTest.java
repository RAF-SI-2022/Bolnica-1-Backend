package raf.bolnica1.laboratory.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import raf.bolnica1.laboratory.domain.constants.PrescriptionStatus;
import raf.bolnica1.laboratory.domain.lab.Prescription;
import raf.bolnica1.laboratory.repository.PrescriptionRepository;
import raf.bolnica1.laboratory.services.PrescriptionService;
import raf.bolnica1.laboratory.services.impl.PrescriptionServiceImpl;
import raf.bolnica1.laboratory.util.dataGenerators.classes.domain.prescription.PrescriptionGenerator;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PrescriptionServiceTest {

    private PrescriptionGenerator prescriptionGenerator=PrescriptionGenerator.getInstance();


    private PrescriptionRepository prescriptionRepository;

    private PrescriptionService prescriptionService;


    @BeforeEach
    public void prepare(){
        prescriptionRepository=mock(PrescriptionRepository.class);
        prescriptionService=new PrescriptionServiceImpl(prescriptionRepository);
    }


    @Test
    public void updatePrescriptionStatusTest(){

        Long id=(long) 4;
        PrescriptionStatus ps=PrescriptionStatus.REALIZOVAN;

        Prescription p=prescriptionGenerator.getPrescription();
        p.setId(id);

        given(prescriptionRepository.findById(id)).willReturn(Optional.of(p));

        prescriptionService.updatePrescriptionStatus(id,ps);

        Assertions.assertTrue(ps.equals(p.getStatus()));

        ArgumentCaptor<Prescription> args=ArgumentCaptor.forClass(Prescription.class);
        verify(prescriptionRepository).save(args.capture());
        Assertions.assertTrue(args.getValue().equals(p));

    }

}
