package vananh.work.util.validator;

import vananh.work.entity.Work;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.sql.Date;

/**
 * DateRangeValidator
 */
public class DateRangeValidator implements ConstraintValidator<DateRange, Work> {

  /**
   * Override isValid() method
   *
   * @param work
   * @param context
   * @return
   */
  @Override
  public boolean isValid(Work work, ConstraintValidatorContext context) {
    Date statingDate = work.getStartingDate();
    Date endingDate = work.getEndingDate();
    return statingDate.before(endingDate);
  }
}