package swag.rest.education_platform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import swag.rest.education_platform.service.AdminService;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    private final AdminService adminService;

    @DeleteMapping("/deleteUserById")
    public void deleteUserById(Long id) {
        adminService.deleteUser(id);
    }

    @DeleteMapping("/deleteUserByUsername")
    public void deleteUserByUsername(String username) {
        adminService.deleteUserByUsername(username);
    }

    @DeleteMapping("/deleteSciencePostById")
    public void deleteSciencePostById(Long postId) {
        adminService.deleteSciencePostById(postId);
    }

    @DeleteMapping("/deleteReflectionPostById")
    public void deleteReflectionPostById(Long posrId) {
        adminService.deleteReflectionPostById(posrId);
    }

    @DeleteMapping("/deleteScienceCommentById")
    public void deleteScienceCommentById(Long commnentId, Long postId) {
        adminService.deleteScienceCommentById(commnentId, postId);
    }

    @DeleteMapping("/deleteReflectionCommentById")
    public void deleteReflectionCommentById(Long commentId, Long postId){
        adminService.deleteReflectionCommentById(commentId, postId);
    }
}
