package raf.bolnica1.laboratory.domain.constants.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumNotBlankValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumNotBlank {
    String message() default "Enum value must not be null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
