package swag.rest.education_platform.service;

public interface AdminService {

    void deleteUser(Long id);
    void deleteUserByUsername(String username);
    void deleteSciencePostById(Long postId);
    void deleteReflectionPostById(Long refPosrId);
    void deleteScienceCommentById(Long scienceCommentId);
    void deleteReflectionCommentById(Long reflectionCommentId);

}
