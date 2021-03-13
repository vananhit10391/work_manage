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
 * WorkName
 */
@Documented
@Constraint(validatedBy = WorkNameValidator.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface WorkName {

    String message() default "{work.name.requid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
