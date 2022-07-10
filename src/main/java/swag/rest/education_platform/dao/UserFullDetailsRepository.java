package swag.rest.education_platform.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import swag.rest.education_platform.entity.UserFullDetails;

public interface UserFullDetailsRepository extends JpaRepository<UserFullDetails, Long> {
}
