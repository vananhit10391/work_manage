package vananh.work.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import vananh.work.util.validator.*;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Work
 */
@Entity
@Data
@DateRange()
public class Work implements Serializable {

    @Id
    @WorkName()
    private String workName;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date startingDate;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date endingDate;

    @WorkStatus()
    private String status;
}
