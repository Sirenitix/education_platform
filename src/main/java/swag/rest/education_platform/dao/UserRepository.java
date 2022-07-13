package swag.rest.education_platform.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swag.rest.education_platform.entity.ProjectStudent;
import swag.rest.education_platform.entity.Users;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    @Query("select new Users(u.id, u.username, u.firstname, u.lastname) from Users u where u.projects = :project")
    List<Users> getsimpledetails(ProjectStudent project);
    Optional<Users> findByUsername(String username);
    Optional<Users> findById(Integer id);
    Boolean existsByUsername(String username);
    void deleteById(Long id);

//    @Query("Select u from Users u left join fetch u.fullDetails")
//    List<Users> findAllUsers();
}
