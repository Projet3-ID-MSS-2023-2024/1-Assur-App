package be.helha.assurapp.authentication.services;

import be.helha.assurapp.authentication.models.User;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@AllArgsConstructor
@Service
public class ActivationCodeService {
    JavaMailSender javaMailSender;
    public void sendCode(User user){

        int code = generateCode();
        user.setActivationCode(code);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("no-reply@assurapp.com");
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Activation code for AssurApp");

        String content = String.format("""
                        Dear %s,\s
                        
                         Thank you for choosing AssurApp to protect you.\s
                         To ensure the security of your account and verify your identity, we require you to enter the verification code provided below.
                         
                         Your activation code is %d.\s
                         
                         Best Regards,\s
                         
                        Your AssurApp team.""",
                user.getLastname(),
                user.getActivationCode());

        mailMessage.setText(content);

        javaMailSender.send(mailMessage);
    }

    private int generateCode(){
        Random random = new Random();
        return random.nextInt(999999);
    }

}
