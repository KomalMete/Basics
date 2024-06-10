package com.omsoftware.demo_4.serviceImpl;

import com.omsoftware.demo_4.models.PasswordResetToken;
import com.omsoftware.demo_4.models.Users;
import com.omsoftware.demo_4.repository.PasswordRepository;
import com.omsoftware.demo_4.repository.UserRepository;
import com.omsoftware.demo_4.service.PasswordResetTokenService;
import com.omsoftware.demo_4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordRepository passwordRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public Object forgotPassword(String email) {

        //finding user from email
        Users user = userRepository.findByEmail(email);
        if(user == null)
        {
            return "User email is wrong";
        }

        //creating token
        String token = UUID.randomUUID().toString();

        //saving the entry in PasswordResetToken entity
        PasswordResetToken generatedToken = new PasswordResetToken(token,user);
        //generatedToken.setToken(token);
        //generatedToken.setUsers(user);
        passwordRepository.save(generatedToken);

        String resetUrl = "http://localhost:8080/api/reset-password?token=" + token;

        //sending resetUrl to user in email
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("komalmete.omsoftware@gmail.com");
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setSubject("Password reset link");
        simpleMailMessage.setText("To reset ur password please click on below link: \n"+resetUrl);

        mailSender.send(simpleMailMessage);

        return "password-reset link is send to ur email";
    }

    @Override
    public Object resetPassword(String token, String password) {

        PasswordResetToken resetToken = passwordRepository.findByToken(token);

        if(resetToken == null || resetToken.getExpiryDate().before(new Date()))
        {
            return "token expired..";
        }
        Users user = resetToken.getUsers();
        user.setPassword(password);
        userRepository.save(user);
        return "Password reset successfull..";
    }
}
