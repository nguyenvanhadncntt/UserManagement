package user.management.vn.controller;

import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import user.management.vn.entity.TokenVerifition;
import user.management.vn.entity.User;
import user.management.vn.entity.dto.EmailDTO;
import user.management.vn.entity.dto.PasswordDTO;
import user.management.vn.service.MailService;
import user.management.vn.service.PasswordService;
import user.management.vn.service.TokenVerificationService;
import user.management.vn.service.UserService;
import user.management.vn.util.VerificationUtil;

/**
 * @summary forget password 
 * @date Aug 22, 2018
 * @author Tai
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
	private TokenVerificationService tokenVerificationService;
	
	@Autowired
	private PasswordService passwordService;

	/**
	* @summary return page view chek email
	* @date Aug 22, 2018
	* @author Tai
	* @param modelMap
	* @return String
	 */
	@GetMapping("/forget-password")
	public String creadEmailDto(ModelMap modelMap) {
		EmailDTO email = new EmailDTO();
		modelMap.addAttribute("emailDTO", email);
		return "check-email";
	}

	/**
	* @summary Processing email confirmation password change
	* @date Aug 22, 2018
	* @author Tai
	* @param emailDTO
	* @param bindingResult
	* @param modelMap
	* @return String
	 */
	@PostMapping("/forget-password")
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
					mailService.sendMail("FORGET PASSWORD", "/change-password", email,
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
	* @summary create password change page
	* @date Aug 22, 2018
	* @author Tai
	* @param token
	* @param modelMap
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
				checkToKenVerifition.setExpireTime(veritificationUtil.calculatorExpireTime());
				checkToKenVerifition.setTokenCode(veritificationUtil.generateVerificationCode(checkToKenVerifition.getUser().getEmail()));
	            tokenVerificationService.editToken(checkToKenVerifition);
	            try {
					mailService.sendMail("FORGET PASSWORD","/change-password",checkToKenVerifition.getUser().getEmail(),checkToKenVerifition.getTokenCode(),checkToKenVerifition.getExpireTime());
				} catch (MessagingException e) {
					e.printStackTrace();
				}
				modelMap.addAttribute("msg", "thoi gian xac nhan da qua han!!!");
				return "errorMessege";
			}
			modelMap.addAttribute("emailToken", checkToKenVerifition.getUser().getEmail());
			
			// set email cua nguoi kich hoat 
			PasswordDTO passwordDTO = new PasswordDTO();
			passwordDTO.setEmail(checkToKenVerifition.getUser().getEmail());
			passwordDTO.setToken(token);
			passwordDTO.setPasswordCurrent("a");
			modelMap.addAttribute("changePasswordDTO", passwordDTO);
			return "forget-password";
		}

	}

	/**
	* @summary save new password
	* @date Aug 22, 2018
	* @author Tai
	* @param passwordDTO
	* @param bindingResult
	* @param modelMap
	* @return String
	 */
	@PostMapping("/change-password")
	public String toDoChangePassword(@Valid @ModelAttribute("changePasswordDTO") PasswordDTO passwordDTO,
			BindingResult bindingResult, ModelMap modelMap) {
		String email = passwordDTO.getEmail();
		String token = passwordDTO.getToken();
		User user = userService.getUserByEmail(email);
		
		String dbPassword = user.getPassword(); 
		String newPassword = passwordDTO.getNewPassword();
		String newMatchingPassword= passwordDTO.getNewMatchingPassword();
		
		boolean checkDuplicatePasswordCurrent= passwordService.checkDuplicatePasswordCurrent(newPassword, dbPassword);
		boolean checkDuplicateMatchingPassword = passwordService.checkDuplicateMatchingPassword(newPassword, newMatchingPassword);
	
		// not bank
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getAllErrors().toString());
			List<ObjectError> list = bindingResult.getAllErrors();
			for (ObjectError objectError : list) {
				System.out.println(objectError);
			}
			modelMap.addAttribute("changePasswordDTO", passwordDTO);
			return "forget-password";
		}
		// check password trung
		if (checkDuplicatePasswordCurrent) {
			modelMap.addAttribute("msg", "The password you entered is the same as your current password");
			return "forget-password";
		}
		if (!checkDuplicateMatchingPassword) {
			modelMap.addAttribute("msg", "You enter a mismatched password");
			return "forget-password";
		}

		// save change
		passwordService.saveNewPasswords(user, newPassword);

		// delete token
		TokenVerifition tokenVerifition = tokenVerificationService.findTokenByTokenCode(token);
		tokenVerificationService.deleteTokenById(tokenVerifition.getId());
		
		modelMap.addAttribute("msg", "You have successfully changed your password");
		return "redirect:/login";
	}

}
