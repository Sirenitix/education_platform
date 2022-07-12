package swag.rest.education_platform.exception;

public class UserNotInProjectException extends RuntimeException{
    public UserNotInProjectException(String message) {
        super(message);
    }
}
