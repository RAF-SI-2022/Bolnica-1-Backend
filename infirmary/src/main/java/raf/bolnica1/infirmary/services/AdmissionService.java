package raf.bolnica1.infirmary.services;

import org.springframework.data.domain.Page;
import raf.bolnica1.infirmary.domain.constants.AdmissionStatus;
import raf.bolnica1.infirmary.domain.constants.PrescriptionStatus;
import raf.bolnica1.infirmary.dto.hospitalization.HospitalizationCreateDto;
import raf.bolnica1.infirmary.dto.hospitalization.HospitalizationDto;
import raf.bolnica1.infirmary.dto.prescription.PrescriptionDto;
import raf.bolnica1.infirmary.dto.response.MessageDto;
import raf.bolnica1.infirmary.dto.scheduledAppointment.ScheduledAppointmentCreateDto;
import raf.bolnica1.infirmary.dto.scheduledAppointment.ScheduledAppointmentDto;

import java.sql.Date;

public interface AdmissionService {


    HospitalizationDto createHospitalization(HospitalizationCreateDto hospitalizationCreateDto,String authorization);


    ScheduledAppointmentDto createScheduledAppointment(ScheduledAppointmentCreateDto scheduledAppointmentCreateDto);


    /// opcioni su svi argumenti
    Page<ScheduledAppointmentDto> getScheduledAppointmentsWithFilter(String lbp, Long departmentId, Date startDate, Date endDate, AdmissionStatus admissionStatus,Integer page,Integer size);


    ScheduledAppointmentDto getScheduledAppointmentByPrescriptionId(Long prescriptionId);


    /// opcioni su svi argumenti
    Page<PrescriptionDto> getPrescriptionsWithFilter(String lbp, Long departmentId, PrescriptionStatus prescriptionStatus,Integer page,Integer size);


    MessageDto setScheduledAppointmentStatus(Long scheduledAppointmentId,AdmissionStatus admissionStatus);

}
