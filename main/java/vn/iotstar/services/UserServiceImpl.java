package vn.iotstar.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.Role;
import vn.iotstar.entity.User;
import vn.iotstar.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User login(String username, String password) {
		Optional<User> user = this.findByUsername(username);
		if (user.isPresent()) { // Kiểm tra nếu Optional chứa giá trị
			User user1 = user.get(); // Lấy người dùng từ Optional
			if (passwordEncoder.matches(password, user1.getPassword())) { // So sánh mật khẩu
				return user1; // Trả về người dùng nếu đúng mật khẩu
			}
		}
		return null;
	}

	@Override
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	@Override
	public boolean existsByPhone(String phone) {
		return userRepository.existsByPhone(phone);
	}

	@Override
	public <S extends User> S save(S entity) {
		return userRepository.save(entity);
	}

	@Override
	public boolean register(String username, String password, String fullname, String email, String phone, String code) {
		/*
		if (userRepository.existsByPhone(phone)) {
			return false;
		}
		if (userRepository.existsByEmail(email)) {
			return false;
		}
		if (userRepository.existsByUsername(username)) {
			return false;
		}	*/
		User user = new User();
		user.setUsername(username);
	    user.setPassword(passwordEncoder.encode(password));
		user.setFullname(fullname);
		user.setEmail(email);
		user.setPhone(phone);
		user.setCode(code);
		user.setStatus(0);
		Role role = new Role();
		role.setRoleid(2);
		user.setRole(role);
		userRepository.save(user);
		return true;
	}

	@Override
	public User updateStatus(String username) {
		Optional<User> user = this.findByUsername(username);
		if (user.isPresent()) { 
			User user1 = user.get();
			user1.setStatus(1);
			userRepository.save(user1);
			return user1;
		} else {
			return null;
		}
	}
}
