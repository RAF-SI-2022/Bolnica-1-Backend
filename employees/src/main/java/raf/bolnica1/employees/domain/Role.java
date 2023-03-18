package raf.bolnica1.employees.domain;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.employees.domain.constants.RoleShort;

import javax.persistence.*;

@Entity
@Table(name = "role")
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private RoleShort roleShort;
}
