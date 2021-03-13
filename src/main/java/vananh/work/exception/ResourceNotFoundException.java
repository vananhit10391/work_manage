package vananh.work.exception;

import lombok.Getter;

/**
 * ResourceNotFoundException
 */
@Getter
public class ResourceNotFoundException extends RuntimeException {

    private String resourceName;

    private String fieldName;

    private Object fieldValue;

    /**
     * ResourceNotFoundException constructor
     *
     * @param resourceName
     * @param fieldName
     * @param fieldValue
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s is not found with %s '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
