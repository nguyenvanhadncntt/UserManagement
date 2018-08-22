package user.management.vn.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import user.management.vn.entity.ChangePasswordDTO;
import user.management.vn.entity.EmailDTO;
import user.management.vn.entity.TokenVerifition;
import user.management.vn.entity.User;
import user.management.vn.service.MailService;
import user.management.vn.service.TokenVerificationService;
import user.management.vn.service.UserService;
import user.management.vn.util.VerificationUtil;

/**
 * 
 * @summary forget password 
 * @date Aug 22, 2018
 * @author Tai
 * @return
 */
@Controller
public class ForgetPasswordController {
	@Autowired
	private MailService mailService;

	@Autowired
	UserService userService;

	@Autowired
	private VerificationUtil veritificationUtil;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private TokenVerificationService tokenVerificationService;

	/**
	 * 
	* @summary return page view chek email
	* @date Aug 22, 2018
	* @author Tai
	* @param modelMap
	* @return
	* @return String
	 */
	@GetMapping("/forget-passowrd")
	public String creadEmailDto(ModelMap modelMap) {
		EmailDTO email = new EmailDTO();
		modelMap.addAttribute("emailDTO", email);
		return "check-email";
	}

	/**
	 * 
	* @summary Processing email confirmation password change
	* @date Aug 22, 2018
	* @author Tai
	* @param emailDTO
	* @param bindingResult
	* @param modelMap
	* @return
	* @return String
	 */
	@PostMapping("/forget-passowrd")
	public String checkOutEmailAndSendMail(@Valid @ModelAttribute("emailDTO") EmailDTO emailDTO,
			BindingResult bindingResult, ModelMap modelMap) {
		String email = emailDTO.getEmail();
		// tim kiem user co email la ?
		User existEmail = userService.getUserByEmail(email);
		// not bank, dung dinh dang email
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getAllErrors().toString());
			List<ObjectError> list = bindingResult.getAllErrors();
			for (ObjectError objectError : list) {
				System.out.println(objectError);
			}
			modelMap.addAttribute("emailDTO", emailDTO);
			return "check-email";
		}
		// kiem tra k  ton tai
		if (existEmail == null || !existEmail.getNonDel()) {
			modelMap.addAttribute("msg", "Your email does not exist");
		} else {
			// da ton tai
			String registCode = veritificationUtil.generateVerificationCode(email);
			Date expireDate = veritificationUtil.calculatorExpireTime();
			try {

				// tim kiem user co gmail la ?
				User user = userService.getUserByEmail(email);
				// neu da goi email roi thi ra ve thong bao
				if (user.getTokenVerifition() != null) {
					modelMap.addAttribute("msg", "Sent email please check email");
				} else {
					// goi email
					mailService.sendMailActive("FORGET PASSWORD", "/change-password", email,
							registCode, expireDate);
					TokenVerifition tokenVerifition = new TokenVerifition(user, registCode, expireDate, 1);
					tokenVerificationService.addToken(tokenVerifition);
					modelMap.addAttribute("msg", "please check email");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "check-email";

	}

	/**
	 * 
	* @summary create password change page
	* @date Aug 22, 2018
	* @author Tai
	* @param token
	* @param modelMap
	* @return
	* @return String
	 */
	@GetMapping("/change-password")
	public String viewChangePassword(@RequestParam("token") String token, ModelMap modelMap) {
		TokenVerifition checkToKenVerifition = tokenVerificationService.findTokenByTokenCode(token);
		// kiem tra token da duoc su dung hay chua
		if (checkToKenVerifition == null) {
			modelMap.addAttribute("msg", "ma code nay k con gia tri");
			return "errorMessege";
		} else {
			Date nowDate = new Date();
			// kiem tra het thoi gian qua han
			if (checkToKenVerifition.getExpireTime().getTime() < nowDate.getTime()) {
				modelMap.addAttribute("msg", "thoi gian xac nhan da qua han!!!");
				return "errorMessege";
			}
			modelMap.addAttribute("emailToken", checkToKenVerifition.getUser().getEmail());
			
			// set email cua nguoi kich hoat 
			ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
			changePasswordDTO.setEmail(checkToKenVerifition.getUser().getEmail());
			changePasswordDTO.setToken(token);
			modelMap.addAttribute("changePasswordDTO", changePasswordDTO);
			return "forget-password";
		}

	}

	/**
	 * 
	* @summary save new password
	* @date Aug 22, 2018
	* @author Tai
	* @param changePasswordDTO
	* @param bindingResult
	* @param modelMap
	* @return
	* @return String
	 */
	@PostMapping("/change-password")
	public String toDoChangePassword(@Valid @ModelAttribute("changePasswordDTO") ChangePasswordDTO changePasswordDTO,
			BindingResult bindingResult, ModelMap modelMap) {
		// kiem tra trung password
		Boolean checkPassword = changePasswordDTO.getPassword().equals(changePasswordDTO.getMatchingPassword());
		// lay lai email cua nguoi da kich hoat
		String email = changePasswordDTO.getEmail();
		String token = changePasswordDTO.getToken();

		// not bank
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getAllErrors().toString());
			List<ObjectError> list = bindingResult.getAllErrors();
			for (ObjectError objectError : list) {
				System.out.println(objectError);
			}
			modelMap.addAttribute("changePasswordDTO", changePasswordDTO);
			return "forget-password";
		}
		// check password trung
		if (!checkPassword) {
			modelMap.addAttribute("msg", "You enter a mismatched password");
			return "forget-password";
		}

		// save change
		User user = userService.getUserByEmail(email);
		String passwordEC = passwordEncoder.encode(changePasswordDTO.getPassword());
		user.setPassword(passwordEC);
		userService.saveUser(user);

		// delete token
		TokenVerifition tokenVerifition = tokenVerificationService.findTokenByTokenCode(token);
		tokenVerificationService.deleteTokenById(tokenVerifition.getId());
		modelMap.addAttribute("msg", "You have successfully changed your password");
		return "forget-password";
	}

}
