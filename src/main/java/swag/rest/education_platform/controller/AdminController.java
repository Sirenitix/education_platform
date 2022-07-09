package swag.rest.education_platform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {


    public void deleteUserById(Long id) {

    }
    public void deleteUserByUsername(Long id) {

    }
    public void deleteSciencePostById(Long id) {

    }
    public void deleteReflectionPostById(Long id) {

    }
    public void deleteScienceCommentById(Long id) {

    }
    public void deleteReflectionCommentById(Long id){

    }
}
