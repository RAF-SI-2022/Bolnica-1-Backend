package raf.bolnica1.laboratory.domain.constants.validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumNotBlankValidator implements ConstraintValidator<EnumNotBlank, Enum<?>> {

    @Override
    public void initialize(EnumNotBlank constraintAnnotation) {
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        return value != null;
    }
}
