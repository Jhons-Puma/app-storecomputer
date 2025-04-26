package edu.cibertec.appstorecomputer.exception;

/**
 * Exception thrown when attempting to create a resource that already exists
 */
public class ResourceAlreadyExistsException extends BusinessException {

    public ResourceAlreadyExistsException(String message) {
        super("RESOURCE_ALREADY_EXISTS", message);
    }

    public ResourceAlreadyExistsException(String resourceName, String fieldName, Object fieldValue) {
        super("RESOURCE_ALREADY_EXISTS", 
              String.format("%s already exists with %s: '%s'", resourceName, fieldName, fieldValue));
    }
}