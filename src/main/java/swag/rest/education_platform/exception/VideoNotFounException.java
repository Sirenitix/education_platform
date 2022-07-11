package swag.rest.education_platform.exception;

public class VideoNotFounException extends RuntimeException{
    public VideoNotFounException() {
        super("Video not found");
    }
}
