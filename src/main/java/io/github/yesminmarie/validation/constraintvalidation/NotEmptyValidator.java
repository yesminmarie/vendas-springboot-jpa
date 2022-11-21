package io.github.yesminmarie.validation.constraintvalidation;

import io.github.yesminmarie.validation.NotEmptyList;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class NotEmptyValidator implements ConstraintValidator<NotEmptyList, List> {
    @Override
    public boolean isValid(List list, ConstraintValidatorContext context) {
        return list != null && !list.isEmpty();
    }
    @Override
    public void initialize(NotEmptyList constraintAnnotation) {
    }
}
