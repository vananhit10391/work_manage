package vananh.work.util.validator;

import vananh.work.util.provider.StatusProvider;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * WorkStatusValidator
 */
public class WorkStatusValidator implements ConstraintValidator<WorkStatus, String> {

    /**
     * Override isValid() method
     *
     * @param value
     * @param context
     * @return
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty() || value.isBlank()) return false;

        if (!value.equalsIgnoreCase(StatusProvider.Planning.toString())
            && !value.equalsIgnoreCase(StatusProvider.Doing.toString())
            && !value.equalsIgnoreCase(StatusProvider.Complete.toString())) {
            return false;
        }

        return true;
    }
}
