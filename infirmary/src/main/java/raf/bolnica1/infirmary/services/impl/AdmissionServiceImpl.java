package raf.bolnica1.infirmary.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import raf.bolnica1.infirmary.domain.HospitalRoom;
import raf.bolnica1.infirmary.domain.Hospitalization;
import raf.bolnica1.infirmary.domain.Prescription;
import raf.bolnica1.infirmary.domain.ScheduledAppointment;
import raf.bolnica1.infirmary.domain.constants.AdmissionStatus;
import raf.bolnica1.infirmary.domain.constants.PrescriptionStatus;
import raf.bolnica1.infirmary.dto.hospitalization.HospitalizationCreateDto;
import raf.bolnica1.infirmary.dto.hospitalization.HospitalizationDto;
import raf.bolnica1.infirmary.dto.prescription.PrescriptionDto;
import raf.bolnica1.infirmary.dto.response.MessageDto;
import raf.bolnica1.infirmary.dto.scheduledAppointment.ScheduledAppointmentCreateDto;
import raf.bolnica1.infirmary.dto.scheduledAppointment.ScheduledAppointmentDto;
import raf.bolnica1.infirmary.mapper.HospitalizationMapper;
import raf.bolnica1.infirmary.mapper.PrescriptionMapper;
import raf.bolnica1.infirmary.mapper.ScheduledAppointmentMapper;
import raf.bolnica1.infirmary.repository.HospitalRoomRepository;
import raf.bolnica1.infirmary.repository.HospitalizationRepository;
import raf.bolnica1.infirmary.repository.PrescriptionRepository;
import raf.bolnica1.infirmary.repository.ScheduledAppointmentRepository;
import raf.bolnica1.infirmary.services.AdmissionService;
import raf.bolnica1.infirmary.services.HospitalRoomService;

import java.sql.Date;

@Service
@AllArgsConstructor
public class AdmissionServiceImpl implements AdmissionService {

    /// MAPPERS
    private final PrescriptionMapper prescriptionMapper;
    private final ScheduledAppointmentMapper scheduledAppointmentMapper;
    private final HospitalizationMapper hospitalizationMapper;


    /// REPOSITORIES
    private final PrescriptionRepository prescriptionRepository;
    private final ScheduledAppointmentRepository scheduledAppointmentRepository;
    private final HospitalRoomRepository hospitalRoomRepository;
    private final HospitalizationRepository hospitalizationRepository;


    /// SERVICES
    private final HospitalRoomService hospitalRoomService;



    private void increaseHospitalRoomOccupancy(Long hospitalRoomId) {
        HospitalRoom hospitalRoom= hospitalRoomRepository.findHospitalRoomById(hospitalRoomId);
        hospitalRoom.setOccupancy(hospitalRoom.getOccupancy()+1);
        hospitalRoom=hospitalRoomRepository.save(hospitalRoom);
    }

    private void setPrescriptionStatusById(Long prescriptionId,PrescriptionStatus prescriptionStatus){
        Prescription prescription= prescriptionRepository.findPrescriptionById(prescriptionId);
        prescription.setStatus(prescriptionStatus);
        prescriptionRepository.save(prescription);
    }

    @Override
    public HospitalizationDto createHospitalization(HospitalizationCreateDto hospitalizationCreateDto,String authorization) {

        Hospitalization hospitalization= hospitalizationMapper.toEntity(hospitalizationCreateDto,hospitalRoomRepository,prescriptionRepository,authorization);
        hospitalization=hospitalizationRepository.save(hospitalization);

        ScheduledAppointmentDto scheduledAppointmentDto=getScheduledAppointmentByPrescriptionId(hospitalization.getPrescription().getId());
        if(scheduledAppointmentDto!=null)
            setScheduledAppointmentStatus(scheduledAppointmentDto.getId(),AdmissionStatus.REALIZOVAN);
        increaseHospitalRoomOccupancy(hospitalization.getHospitalRoom().getId());
        setPrescriptionStatusById(hospitalizationCreateDto.getPrescriptionId(), PrescriptionStatus.REALIZOVAN);

        return hospitalizationMapper.toDto(hospitalization);
    }

    @Override
    public ScheduledAppointmentDto createScheduledAppointment(ScheduledAppointmentCreateDto scheduledAppointmentCreateDto) {

        ScheduledAppointment scheduledAppointment= scheduledAppointmentMapper.toEntity(scheduledAppointmentCreateDto,prescriptionRepository);

        scheduledAppointment=scheduledAppointmentRepository.save(scheduledAppointment);

        return scheduledAppointmentMapper.toDto(scheduledAppointment);
    }

    @Override
    public Page<ScheduledAppointmentDto> getScheduledAppointmentsWithFilter(String lbp, Long departmentId, Date startDate, Date endDate, AdmissionStatus admissionStatus,Integer page,Integer size) {
        Pageable pageable= PageRequest.of(page,size);
        if(endDate!=null)endDate=new Date(endDate.getTime()+24*60*60*1000);
        Page<ScheduledAppointment> scheduledAppointments=scheduledAppointmentRepository.findScheduledAppointmentWithFilter(pageable,lbp,departmentId,startDate,endDate,admissionStatus);

        return scheduledAppointments.map(scheduledAppointmentMapper::toDto);
    }

    @Override
    public ScheduledAppointmentDto getScheduledAppointmentByPrescriptionId(Long prescriptionId) {

        ScheduledAppointment scheduledAppointment=scheduledAppointmentRepository.findScheduledAppointmentByPrescriptionId(prescriptionId);

        return scheduledAppointmentMapper.toDto(scheduledAppointment);
    }

    @Override
    public Page<PrescriptionDto> getPrescriptionsWithFilter(String lbp, Long departmentId, PrescriptionStatus prescriptionStatus,Integer page,Integer size) {
        Pageable pageable= PageRequest.of(page,size);

        Page<Prescription> prescriptions=prescriptionRepository.findPrescriptionWithFilter(pageable,lbp,departmentId,prescriptionStatus);

        return prescriptions.map(prescriptionMapper::toDto);
    }

    @Override
    public MessageDto setScheduledAppointmentStatus(Long scheduledAppointmentId, AdmissionStatus admissionStatus) {

        ScheduledAppointment scheduledAppointment=scheduledAppointmentRepository.findScheduledAppointmentById(scheduledAppointmentId);

        scheduledAppointment.setAdmissionStatus(admissionStatus);

        scheduledAppointment=scheduledAppointmentRepository.save(scheduledAppointment);

        return new MessageDto("ScheduledAppointment sa ID "+scheduledAppointment.getId()+" promenjen status na "+scheduledAppointment.getAdmissionStatus().name());

    }
}
