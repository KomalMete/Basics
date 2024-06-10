package com.omsoft.demo_3.serviceImpl;

import com.omsoft.demo_3.model.Employee;
import com.omsoft.demo_3.model.request.EmployeeRequest;
import com.omsoft.demo_3.repository.EmployeeRepository;
import com.omsoft.demo_3.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public Object saveOrUpdate(EmployeeRequest employeeRequest) {

        if(employeeRepository.existsById(employeeRequest.getEmployeeId()))
        {
            Employee emp = new Employee();
            emp.setEmployeeId(employeeRequest.getEmployeeId());
            emp.setName(employeeRequest.getName());
            emp.setLastName(employeeRequest.getLastName());
            emp.setEmail(employeeRequest.getEmail());

            //saving bcryptpassword
            String newBcryptPassword = hashPassword(employeeRequest.getPassword());
            emp.setPassword(newBcryptPassword);
            employeeRepository.save(emp);
            return "Employee Details Updated Successfully..";
        }
        else
        {

            Employee emp = new Employee();
            emp.setEmployeeId(employeeRequest.getEmployeeId());
            emp.setName(employeeRequest.getName());
            emp.setLastName(employeeRequest.getLastName());
            emp.setEmail(employeeRequest.getEmail());

            //emp.setPassword(employeeRequest.getPassword());
            //saving bcryptpassword
            String newBcryptPassword = hashPassword(employeeRequest.getPassword());
            emp.setPassword(newBcryptPassword);

            //email with html-content
            try{
                Context context = new Context();
                String otpGenerated = generateOTP();
                context.setVariable("name", emp.getName());
                context.setVariable("otp",otpGenerated);
                emp.setOtpGenerated(otpGenerated);
                this.sendEmailWithTemplate("komalmete8@gmail.com", "Using Template", context);
                employeeRepository.save(emp);
                return "Employee Details Saved Successfully...";
            }
            catch (MessagingException e)
            {
                e.printStackTrace();
               return "Employee Details not saved..";
            }
        }

    }

    @Override
    public Object verifyOTP(String email, String otp) {

        if(employeeRepository.existsByEmail(email))
        {
            Employee emp = employeeRepository.findByEmail(email);
            String employeeOTP = emp.getOtpGenerated();
            if(employeeOTP.equals(otp))
            {
                return "OTP MATCH..";
            }
            else
            {
                return "Employee OTP didnt match..";
            }
        }
        else
        {
            return "Employee email didnt match";
        }

    }

    public String hashPassword(String employeePassword)
    {

        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        String bcryptPassword = bcrypt.encode(employeePassword);
        return bcryptPassword;

    }

    public void sendEmailWithTemplate(String to, String subject, Context context) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");

        mimeMessageHelper.setFrom(fromEmail);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);

        String htmlContent = templateEngine.process("emailTemplate", context);
        mimeMessageHelper.setText(htmlContent,true);

        javaMailSender.send(mimeMessage);

    }

    public String generateOTP()
    {
        int length = 6;
        Random random = new Random();
        StringBuilder generatedOTP = new StringBuilder();
        for(int i=0; i<length; i++)
        {
            int otp = random.nextInt(10);
            generatedOTP.append(otp);
        }
        return generatedOTP.toString();
    }
}
