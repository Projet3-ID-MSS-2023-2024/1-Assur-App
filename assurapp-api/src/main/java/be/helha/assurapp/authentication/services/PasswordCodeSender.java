package be.helha.assurapp.authentication.services;

import be.helha.assurapp.authentication.models.User;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PasswordCodeSender {
    JavaMailSender javaMailSender;
    public void sendCode(User user){


        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("no-reply@assurapp.be");
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Reset password for AssurApp");

        String content = String.format("""
                        Dear %s,
                                                
                        We hope this email finds you well. It seems like you've forgotten your password for your AssurApp account. No worries! We're here to help you regain access to your account.
                                                
                        To reset your password, please follow the steps below:
                                                
                        Follow this link: http://localhost:4200/resetpassword/%s
                        You will be redirected to a page where you can create a new password.
                        Enter your new password and confirm it.
                        Click on the "Reset Password" button.
                        If you did not request a password reset, please contact our security team by mail to support@assurapp.com. Your account security is important to us.
                                                
                        If you encounter any issues or need further assistance, please don't hesitate to contact our support team at support@assurapp.com.
                                                
                        Thank you for choosing AssurApp to ensure you.
                                                
                        Best regards,
                        Your AssurApp Team""",
                user.getLastname(),
                user.getPwdCode());

        mailMessage.setText(content);

        javaMailSender.send(mailMessage);
}
}
