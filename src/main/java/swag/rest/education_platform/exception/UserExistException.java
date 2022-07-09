package swag.rest.education_platform.exception;

public class UserExistException extends RuntimeException{
    public UserExistException() {
        super("User already exist");
    }
}
