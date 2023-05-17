package raf.bolnica1.patient.dto.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PermissionsCheckDto implements Serializable {
  private List<String> permissionsCheckDtoList;
}
