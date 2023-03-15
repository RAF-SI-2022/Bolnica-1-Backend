package raf.bolnica1.laboratory.dto.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PermissionsCheckDto {
  private List<String> permissionsCheckDtoList;
}
