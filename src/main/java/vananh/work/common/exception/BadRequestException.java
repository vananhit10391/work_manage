package vananh.work.common.exception;

import lombok.Getter;

/**
 * BadRequestException
 */
@Getter
public class BadRequestException extends RuntimeException {

    private String message;

    /**
     * BadRequestException Constructor
     *
     * @param message
     */
    public BadRequestException(String message) {
        super(message);
        this.message = message;
    }

    /**
     * BadRequestException Constructor
     *
     * @param message
     * @param cause
     */
    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
