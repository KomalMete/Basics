package com.omsoftware.demo_4.controller;

import com.omsoftware.demo_4.models.PasswordResetToken;
import com.omsoftware.demo_4.models.Users;
import com.omsoftware.demo_4.models.response.CustomEntityResponse;
import com.omsoftware.demo_4.models.response.EntityResponse;
import com.omsoftware.demo_4.repository.PasswordRepository;
import com.omsoftware.demo_4.service.PasswordResetTokenService;
import com.omsoftware.demo_4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api")
public class PasswordResetTokenController {

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordRepository passwordRepository;

    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestParam(name = "email")String email)
    {
        try
        {
            return new ResponseEntity(new EntityResponse(passwordResetTokenService.forgotPassword(email), 0), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return  new ResponseEntity( new CustomEntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam(name = "token")String token,
                                           @RequestParam(name = "password")String password)
    {
        try
        {
            return new ResponseEntity(new EntityResponse(passwordResetTokenService.resetPassword(token,password), 0), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return  new ResponseEntity( new CustomEntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }

    @GetMapping("/reset-password")
    public ResponseEntity<?> showResetPasswordPage(@RequestParam("token") String token) {
        PasswordResetToken resetToken = passwordRepository.findByToken(token);
        if (resetToken == null || resetToken.getExpiryDate().before(new Date())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token");
        }
        return ResponseEntity.ok("Valid token");
    }
}
