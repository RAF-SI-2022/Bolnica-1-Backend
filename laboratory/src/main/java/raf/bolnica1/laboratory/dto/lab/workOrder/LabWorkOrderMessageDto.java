package raf.bolnica1.laboratory.dto.lab.workOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LabWorkOrderMessageDto implements Serializable {
    private String message;
    private LabWorkOrderDto labWorkOrderDto;
}
