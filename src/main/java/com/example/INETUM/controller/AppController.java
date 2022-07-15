package com.example.INETUM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.INETUM.model.User;
import com.example.INETUM.repository.UserRepository;

@Controller
public class AppController {

	@Autowired
	private UserRepository userRepo;

	@GetMapping("/")
	public String viewHomePage() {
		return "index";
	}

	@GetMapping("/registration")
	public String showSignUpForm(Model model) {
		model.addAttribute("user", new User());
		return "sign_up";
	}

	@PostMapping("/process_register")
	public String processRegistration(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);

		userRepo.save(user);
		return "register_success";
	}

	@GetMapping("/login")
	public String viewLoginForm() {
		return "login";
	}
}
