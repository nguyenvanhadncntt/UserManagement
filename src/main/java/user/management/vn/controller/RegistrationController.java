package user.management.vn.controller;

import java.util.Date;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import user.management.vn.entity.Role;
import user.management.vn.entity.TokenVerifition;
import user.management.vn.entity.User;
import user.management.vn.entity.UserRole;
import user.management.vn.entity.dto.UserDTO;
import user.management.vn.service.MailService;
import user.management.vn.service.RoleService;
import user.management.vn.service.TokenVerificationService;
import user.management.vn.service.UserRoleService;
import user.management.vn.service.UserService;
import user.management.vn.util.RoleSystem;
import user.management.vn.util.VerificationUtil;

@Controller
public class RegistrationController {
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private VerificationUtil veritificationUtil;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenVerificationService tokenVerificationService;
	
	/**
	 * @summary show regist page
	 * @author ThaiLe
	 * @param model
	 * @return String
	 */
	@RequestMapping(path="showRegistPage",method = RequestMethod.GET)
	public String showRegisterPage(Model model) {
		model.addAttribute("userDTO", new UserDTO());
		return "regist-page";
	}
	
	/**
	 * @summary register account
	 * @author ThaiLe
	 * @param userModel
	 * @param rs
	 * @param model
	 * @return ResponseEntity<String>
	 */
	@PostMapping(path="registerAccount")	
	public  ResponseEntity<String> registNewAccount(@Valid @RequestBody UserDTO userModel,BindingResult rs,Model model) {
		if(rs.hasErrors()) {
			System.out.println(rs.getAllErrors().toString());
			return new ResponseEntity<String>("You must complete all infor", HttpStatus.BAD_REQUEST);
		}
		if(userService.checkDuplicateEmail(userModel.getEmail())) {
			return new ResponseEntity<String> ("Email is existed", HttpStatus.CONFLICT);
		}else {
			String registCode = veritificationUtil.generateVerificationCode(userModel.getEmail()+userModel.getPassword());
			Date expireDate = veritificationUtil.calculatorExpireTime();
			String passwordEncode = passwordEncoder.encode(userModel.getPassword());
			userModel.setPassword(passwordEncode);
			try {

				mailService.sendMail("active account","/activeAccount",userModel.getEmail(),registCode,expireDate);
				User user = userService.addUser(userModel);
				TokenVerifition tokenVerifition = new TokenVerifition(user, registCode, expireDate, 0);
				tokenVerificationService.addToken(tokenVerifition);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//model.addAttribute("msg", "register successful. Check mail to active account!!!, expire: "+expireDate.toString());
		}
		return new ResponseEntity<>("Created user successfully", HttpStatus.OK);
	}
	
	/**
	 * @summary active account
	 * @author ThaiLe
	 * @param request
	 * @param registCode
	 * @param model
	 * @throws MessagingException
	 * @return ResponseEntity<String>
	 */
	@GetMapping(path="activeAccount")
	public  ResponseEntity<String> activeAccount(HttpServletRequest request, @RequestParam("token")String registCode,Model model) throws MessagingException {
		TokenVerifition tokenVerification = tokenVerificationService.findTokenByTokenCode(registCode);
		if(tokenVerification == null) {
			return new ResponseEntity<String>("Token is not true", HttpStatus.NOT_FOUND);
		}
		User objUsers= tokenVerification.getUser();		
		
		if(objUsers != null) {
			
			Date nowDay = new Date();
			if(tokenVerification.getExpireTime().getTime() < nowDay.getTime()) {
				tokenVerification.setExpireTime(veritificationUtil.calculatorExpireTime());
				tokenVerification.setTokenCode(veritificationUtil.generateVerificationCode(objUsers.getEmail() + objUsers.getPassword()));
				tokenVerificationService.editToken(tokenVerification);
				mailService.sendMail("Active Account","/activeAccount",objUsers.getEmail(),tokenVerification.getTokenCode(),tokenVerification.getExpireTime());
				return new ResponseEntity<>("We sent you a new token", HttpStatus.OK);
			}else {
				boolean check = userService.activeUser(objUsers.getId());
				if(check == true) {
					tokenVerificationService.deleteTokenById(tokenVerification.getId());
					Role role = roleService.findByRoleName(RoleSystem.USER);
					User user = userService.findUserByUserId(objUsers.getId());
					UserRole userRole = new UserRole(user, role);
					userRoleService.addUserWithRole(userRole);
					return new ResponseEntity<>("Active user successfully", HttpStatus.OK);
				}else {
					return new ResponseEntity<>("Active user fail", HttpStatus.BAD_REQUEST);
				}
			}
			//userService.autoLogin(request, user.getEmail(), user.getPassword());
			//model.addAttribute("msg","Your account active successful !!!");
			//return "activeAccount";
			
		}		
		//model.addAttribute("msg","Regist code not exist check your mail agian !!!");
		//return "activeAccount";
	    return new ResponseEntity<>("Active user fail", HttpStatus.BAD_REQUEST);
	}
	
}
