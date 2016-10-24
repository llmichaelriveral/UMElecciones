package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendMail {
	
	private JavaMailSender javaMailSender;
	
	@Autowired
	public SendMail(JavaMailSender javaMailSender){
		this.javaMailSender = javaMailSender;
	}
	
	public void sendNotification(String email, String eleccion) throws MailException{
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setFrom("lmichael.r.c@gmail.com");
		mail.setSubject("Voto registrado.");
		mail.setText("Su voto en la elección "+eleccion+" ha sido registrado exitosamente.");
		
		javaMailSender.send(mail);
	}
}
