package vananh.work.config;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import vananh.work.exception.ErrorArgumentsMessage;
import vananh.work.exception.ErrorMessage;
import vananh.work.exception.ResourceNotFoundException;
import vananh.work.util.constant.ErrorCode;

import java.util.HashMap;
import java.util.Map;

/**
 * APIExceptionHandler
 */
@RestControllerAdvice
public class APIExceptionHandler {

    /**
     * Handle all Exception
     *
     * @param ex
     * @param request
     * @return ErrorMessage
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleAllException(Exception ex, WebRequest request) {
        return new ErrorMessage(ErrorCode.EXCEPTION_CODE, ex.getLocalizedMessage());
    }

    /**
     * Handle ResourceNotFoundException
     *
     * @param ex
     * @param request
     * @return ErrorMessage
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage handleResourceNotFoundException(Exception ex, WebRequest request) {
        return new ErrorMessage(ErrorCode.FIND_NOT_FOUND_CODE, ex.getLocalizedMessage());
    }

    /**
     * Handle MethodArgumentNotValidException
     *
     * @param ex
     * @return ErrorArgumentsMessage
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorArgumentsMessage handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String name = "";
            if (error instanceof FieldError) {
                // name = ((FieldError) error).getObjectName() + "." + ((FieldError) error).getField();
                name = ((FieldError) error).getField();
            } else if (error instanceof ObjectError){
                name = ((ObjectError) error).getObjectName();
            }
            String errorMessage = error.getDefaultMessage();
            errors.put(name, errorMessage);
        });
        return new ErrorArgumentsMessage(ErrorCode.ARGUMENT_NOT_INVALID_CODE, errors);
    }
}
