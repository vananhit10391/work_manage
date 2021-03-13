package vananh.work.util.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * WorkStatus
 */
@Documented
@Constraint(validatedBy = WorkStatusValidator.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface WorkStatus {

    String message() default "{work.status.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
