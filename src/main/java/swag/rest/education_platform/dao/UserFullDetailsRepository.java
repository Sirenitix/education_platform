package swag.rest.education_platform.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import swag.rest.education_platform.entity.UserFullDetails;

import java.util.List;
import java.util.Set;

public interface UserFullDetailsRepository extends JpaRepository<UserFullDetails, Long> {
}
