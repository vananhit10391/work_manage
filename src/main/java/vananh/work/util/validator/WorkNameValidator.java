package vananh.work.util.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * WorkNameValidator
 */
public class WorkNameValidator implements ConstraintValidator<WorkName, String> {

    /**
     * Override isValid() method
     *
     * @param value
     * @param context
     * @return
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) return false;
        return true;
    }
}
