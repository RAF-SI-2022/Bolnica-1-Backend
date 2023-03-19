package raf.bolnica1.laboratory.dto.lab.workOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LabWorkOrderMessageDto {
    private String message;
    private LabWorkOrderDto labWorkOrderDto;
}
