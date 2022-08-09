package swag.rest.education_platform.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import swag.rest.education_platform.entity.UserFullDetails;
import swag.rest.education_platform.entity.Users;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserFullDetailsRepository extends JpaRepository<UserFullDetails, Long> {

    Optional<UserFullDetails> findByEmail(String email);
    Optional<UserFullDetails> findById(Integer id);
}
