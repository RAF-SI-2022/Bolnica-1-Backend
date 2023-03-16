<<<<<<<< HEAD:employees/src/main/java/raf/bolnica1/employees/dto/role/RolesCheckDto.java
package raf.bolnica1.employees.dto.role;
========
package raf.bolnica1.laboratory.dto.permission;
>>>>>>>> 7593a5535fe180ca83dbf38227674c72443c08e9:laboratory/src/main/java/raf/bolnica1/laboratory/dto/permission/PermissionsCheckDto.java

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RolesCheckDto {
  private List<String> rolesCheckDtoList;
}
