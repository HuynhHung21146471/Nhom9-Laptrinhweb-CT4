package vn.iotstar.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.User;
import vn.iotstar.models.UserModel;
import vn.iotstar.services.IUserService;
import vn.iotstar.utils.Email;
@EnableMethodSecurity
@Controller
public class Menu {
	@Autowired
	IUserService service;

	@Autowired
	PasswordEncoder passwordEncoder;

	private UserModel userModel = new UserModel();
	
	@GetMapping("/dangnhap")
	public String Login() {
		return "dangnhap";
	}
	
	@GetMapping("/waiting")
	public String waiting(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		if (session != null && session.getAttribute("account") != null) {
			User u = (User) session.getAttribute("account");
			if (u.getRole().getRoleid() == 1) {
				return"redirect:/admin/home";
			} else if (u.getRole().getRoleid() == 2) {
				return "redirect:/admin/home";
			} else {
				return "redirect:/";
			}
		}
		return"redirect:/dangnhap";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.removeAttribute("account");
		return "redirect:/dangnhap";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@GetMapping("/verifycode")
	public String verifycode() {
		return "verifycode";
	}

	@GetMapping("/forgotpassword")
	public String forgotpassword() {
		return "forgotpassword";
	}

	@PostMapping("/dangnhap")
	public String postlogin(HttpServletRequest req, HttpServletResponse resp, Model model,
			@RequestParam("uname") String username, @RequestParam("psw") String password, String remember) {
		String alertMsg = "";
		boolean isRememberMe = false;
		if ("on".equals(remember)) {
			isRememberMe = true;
		}
		if (username.isEmpty() || password.isEmpty()) {
			alertMsg = "Tài khoản hoặc mật khẩu không được rỗng";
			model.addAttribute("alert", alertMsg);
			return "register";
		}
		User user = service.login(username, password);
		if (user != null) {
			if (user.getStatus() == 1) {
				HttpSession session = req.getSession(true);
				session.setAttribute("account", user);
				if (isRememberMe) {
					saveRemeberMe(resp, username);
				}
				return "redirect:/waiting";
			} else {
				alertMsg = "Tài khoản đang bị khóa";
				model.addAttribute("alert", alertMsg);
				return "dangnhap";
			}
		} else {
			alertMsg = "Tài khoản hoặc mật khẩu không đúng";
			model.addAttribute("alert", alertMsg);
			return "dangnhap";
		}
	}

	private void saveRemeberMe(HttpServletResponse response, String username) {
		Cookie cookie = new Cookie("username", username);
		cookie.setMaxAge(30 * 60);
		response.addCookie(cookie);
	}

	@PostMapping("/register")
	public String postregister(HttpServletRequest req, HttpServletResponse resp, Model model,
			@RequestParam("uname") String username, @RequestParam("psw") String password, String fullname, String email,
			String phone) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		String alertMsg = "";
		if (service.existsByEmail(email)) {
			alertMsg = "Email đã tồn tại!";
			model.addAttribute("error", alertMsg);
			return "register";
		} else if (service.existsByUsername(username)) {
			alertMsg = "Tài khoản đã tồn tại!";
			model.addAttribute("error", alertMsg);
			return "register";
		} else if (service.existsByPhone(phone)) {
			alertMsg = "Số điện thoại đã tồn tại!";
			model.addAttribute("error", alertMsg);
			return "register";
		} else {
			Email sm = new Email();
			// get the 6-digit code
			String code = sm.getRandom();
			// create new user using all information
			User user = new User(username, email, code);
			boolean test = sm.sendEmail(user);
			if (test) {
				HttpSession session = req.getSession(true);
				session.setAttribute("account", user);
				boolean isSuccess = service.register(username, password, fullname, email, phone, code);
				if (isSuccess) {
					userModel.setIsForgot(false);
					model.addAttribute("userModel", userModel);
					return "verifycode";
				} else {
					alertMsg = "Lỗi hệ thống!";
					model.addAttribute("error", alertMsg);
					return "register";
				}
			} else {
				alertMsg = "Lỗi khi gửi mail";
				model.addAttribute("error", alertMsg);
				return "register";
			}
		}
	}

	@PostMapping("/verifycode")
	protected void postVerifyCode(HttpServletRequest req, HttpServletResponse resp,
			@RequestParam("authcode") String code) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		try (PrintWriter out = resp.getWriter()) {
			HttpSession session = req.getSession();
			User user = (User) session.getAttribute("account");
			if (code.equals(user.getCode())) {			
				if (userModel.getIsForgot()) {
					user.setPassword(passwordEncoder.encode(userModel.getPassword()));
					User updatedPass = service.save(user);
					if (updatedPass != null) {
						session.setAttribute("account", updatedPass); // Cập nhật lại thông tin trong session
					}
					out.println("<div class=\"container\"><br/>\r\n" + "	<br/>\r\n"
							+ "		<br/>Đổi mật khẩu thành công!<br/>\r\n" + "		<br/>\r\n" + "		<br/></div>");
				} else {
					User updatedUser = service.updateStatus(user.getUsername());
					if (updatedUser != null) {
						session.setAttribute("account", updatedUser); // Cập nhật lại thông tin trong session
					}
					out.println("<div class=\"container\"><br/>\r\n" + "	<br/>\r\n"
							+ "		<br/>Kích hoạt tài khoản thành công!<br/>\r\n" + "		<br/>\r\n"
							+ "		<br/></div>");
				}
			} else {
				out.println("<div class=\"container\"><br/>\r\n" + "	<br/>\r\n"
						+ "		<br/>Sai mã kích hoạt, vui lòng kiểm tra lại<br/>\r\n" + "		<br/>\r\n"
						+ "		<br/></div>");
			}
		}
	}

	@PostMapping("/forgotpassword")
	protected String postForgotPassword(HttpServletRequest req,Model model, @RequestParam("uname") String username, String email,
			String psw) {
		Optional<User> user1 = service.findByUsername(username);
		User user = user1.get();
		if (user.getEmail().equals(email) && user.getUsername().equals(username)) {
			Email sm = new Email();
			String code = sm.getRandom();
			user.setCode(code);
			service.save(user);
			user.setPassword(psw);
			HttpSession session = req.getSession(true);
			session.setAttribute("account", user);
			boolean test = sm.sendEmail(user);
			if (test) {
				userModel.setPassword(psw);
				userModel.setIsForgot(true);
				model.addAttribute("userModel", userModel);
				return "verifycode";
			} else {
				model.addAttribute("error", "Lỗi gửi mail!");
			}
		} else {
			model.addAttribute("error", "Username hoặc Email không tồn tại trong hệ thống!");
		}
		return "forgotpassword";
	}
}
