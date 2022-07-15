package com.example.INETUM.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.INETUM.config.UserNotFoundException;
import com.example.INETUM.model.User;
import com.example.INETUM.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepo;

	public User getUserByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
		User user = userRepo.findByEmail(email);

		if (user != null) {
			user.setResetPasswordToken(token);
			userRepo.save(user);
		} else {

			throw new UserNotFoundException("could not found an user with this email" + email);
		}
	}

	public User get(String resetPasswordToken) {
		return userRepo.findByResetPasswordToken(resetPasswordToken);
	}

	public void updatePassword(User user, String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(password);
		user.setPassword(encodedPassword);
		user.setResetPasswordToken(null);

	}
	
	
}
