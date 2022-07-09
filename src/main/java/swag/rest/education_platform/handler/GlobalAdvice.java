//package swag.rest.education_platform.handler;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//@ControllerAdvice
//public class GlobalAdvice {
//    @ResponseBody
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    StackTraceElement[] studentNotFound(Exception ex) {
//        return ex.getStackTrace();
//    }
//
//}