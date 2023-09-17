package com.dessertoasis.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtil {
	
	@Autowired
	  private JavaMailSender javaMailSender;
	
	 public void sendOtpEmail(String email, String otp) throws MessagingException {
		    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true, "UTF-8");
		    mimeMessageHelper.setTo(email);
		    mimeMessageHelper.setSubject("甜點綠洲驗證信");
		    mimeMessageHelper.setText("""
		    	
		        <div>
		        親愛的甜點綠洲會員，

						感謝您選擇加入我們的甜點綠洲社群！我們非常高興歡迎您成為我們大家庭的一員。<br/>
						
						為了完成您的註冊，請點擊以下連結進行驗證：
						<a href="http://localhost:8080/verify-account?email=%s&otp=%s" target="_blank">[註冊驗證連結]</a>
						
						一旦您點擊連結完成驗證，您就可以開始享受我們平台提供的所有優勢和服務。<br/>
						
						如果您有任何疑問或需要協助，請隨時聯繫我們的客戶支援團隊。再次感謝您的加入！<br/>
						
						提醒您，驗證需再十分鐘內完成。<br/>
						
						祝您度過美好的時光，
						甜點綠洲團隊
		        </div>
		        """.formatted(email, otp), true);

		    javaMailSender.send(mimeMessage);
		  }
	
	 public void sendNewPasswordEmail(String email, String newPassword) throws MessagingException {
		 MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true, "UTF-8");
		    mimeMessageHelper.setTo(email);
		    mimeMessageHelper.setSubject("甜點綠洲密碼重置通知");
		    String emailContent = "尊敬的用戶，<br>"
		    		+ "\r\n"
		    		+ "我們收到了您的密碼重置請求，並已為您生成了一個全新的密碼。請在收到此郵件後立即登錄並更改密碼，以確保您的賬戶安全。<br>"
		    		+ "\r\n"
		    		+ "新密碼："+newPassword+"<br>"
		    		+ "\r\n"
		    		+ "請注意，這是一個自動生成的密碼，為了您的安全，建議盡快登錄並設置一個您容易記住的新密碼。<br>"
		    		+ "\r\n"
		    		+ "如有任何疑問或需要幫助，請隨時聯系我們的客戶支持團隊。<br>"
		    		+ "\r\n"
		    		+ "感謝您使用甜點綠洲！<br>"
		    		+ "\r\n"
		    		+ "祝您一天愉快。<br>"
		    		+ "\r\n"
		    		+ "甜點綠洲團隊" ;
		    mimeMessageHelper.setText(
		        emailContent
		       .formatted(email), true);

		    javaMailSender.send(mimeMessage);
		  
}

	 
	 public void sendSetPasswordEmail(String email) throws MessagingException {
		    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
		    mimeMessageHelper.setTo(email);
		    mimeMessageHelper.setSubject("Set password.");
		    mimeMessageHelper.setText("""
		        <div>
		          <a href="http://localhost:8080/setpassword?email=%s" target="_blank">click link to set password</a>
		        </div>
		        """.formatted(email), true);

		    javaMailSender.send(mimeMessage);
		  }
}
