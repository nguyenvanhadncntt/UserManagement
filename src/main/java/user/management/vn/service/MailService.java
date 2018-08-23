package user.management.vn.service;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	@Autowired
	JavaMailSender mailSender;
	
	String cssLinkActive="<style type='text/css'>" + 
			"    .custom-class { background-color: #2cd22c;text-decoration: none;display: inline-block;width: 224px;height: 35px;text-align: center;border-radius: 7px;font-size: 25px;}" + 
			"    .custom-class:hover { background-color:Red; }" + 
			"</style>";
	

	public void sendMail(String title, String href, String userMail, String registCode, Date expireDate) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper helper;
		helper = new MimeMessageHelper(message);

		helper.setSubject(title);
		helper.setText(mailContent(registCode, expireDate,href), true);

		helper.setTo(userMail);
		helper.setFrom("noreply@hanv.com");
		mailSender.send(message);
	}


	public String mailContent(String registCode, Date expireDate,String href) {
		StringBuilder mailContent = new StringBuilder("<h4>Date-Time Expire: "+expireDate+"</h4>");
		mailContent.append(cssLinkActive);
		mailContent.append("<a class='custom-class' href='http://localhost:8080");
		mailContent.append(href);
		mailContent.append("?token=");
		mailContent.append(registCode);
		mailContent.append("'>Checkout your account</a>");
		return mailContent.toString();
	}
}
