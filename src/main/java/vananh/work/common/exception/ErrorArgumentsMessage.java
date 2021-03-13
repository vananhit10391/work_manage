package vananh.work.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Map;

/**
 * ErrorArgumentsMessage
 */
@Data
@AllArgsConstructor
public class ErrorArgumentsMessage {

    private int statusCode;

    private Map<String, String> errors;
}
