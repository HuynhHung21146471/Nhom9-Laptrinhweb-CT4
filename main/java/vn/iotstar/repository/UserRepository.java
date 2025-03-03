package vn.iotstar.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional <User> findByUsername(String username);
	
	boolean existsByEmail(String email);

	boolean existsByUsername(String username);

	boolean existsByPhone(String phone);
	
}
