package swag.rest.education_platform.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import swag.rest.education_platform.exception.PostException;
import swag.rest.education_platform.exception.PostNotFoundException;
import swag.rest.education_platform.exception.UserExistException;

@ControllerAdvice
public class GlobalAdvice extends ResponseEntityExceptionHandler {
    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    StackTraceElement[] studentNotFound(Exception ex) {
        return ex.getStackTrace();
    }
    @ResponseBody
    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<String> userExistException(UserExistException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(PostException.class)
    public ResponseEntity<String> postException(PostException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(PostException.class)
    public ResponseEntity<String> postNotFoundException(PostNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> usernameNotFoundException(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}