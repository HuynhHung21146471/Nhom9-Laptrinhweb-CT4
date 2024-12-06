package vn.iotstar.services;


import java.util.Optional;

import vn.iotstar.entity.User;

public interface IUserService {

	Optional<User> findByUsername(String username);

	User login(String username, String password);

	boolean register(String username, String password, String fullname, String email, String phone, String code);

	<S extends User> S save(S entity);

	boolean existsByPhone(String phone);

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

	User updateStatus(String username);




}
