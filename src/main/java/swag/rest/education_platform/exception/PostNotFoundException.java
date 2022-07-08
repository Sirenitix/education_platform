package swag.rest.education_platform.exception;

public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException() {
        super("Post has not been found");
    }
}
