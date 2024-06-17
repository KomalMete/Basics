package com.example.animatedpdf.serviceimpl;

import com.example.animatedpdf.models.Employee;
import com.example.animatedpdf.models.request.EmployeeRequest;
import com.example.animatedpdf.repository.EmployeeRepository;
import com.example.animatedpdf.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.server.DelegatingServerHttpResponse;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public Object saveOrUpdate(EmployeeRequest employeeRequest)
    {
        Employee employee;
        if(employeeRepository.existsById(employeeRequest.getId()))
        {
            employee = employeeRepository.findById(employeeRequest.getId()).get();
        }
        else
        {
            employee = new Employee();
        }

        employee.setId(employeeRequest.getId());
        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        employee.setEmail(employeeRequest.getEmail());
        employee.setPassword(employeeRequest.getPassword());

        try
        {
            String attachment = "D:\\OM_Software_programs\\animatedpdf\\welcome_gif.gif";
            this.sendEmailWithAnimatedGif("komalmete8@gmail.com", "Welcome",  "Please find the attached animated GIF.", attachment);
            employeeRepository.save(employee);
            return "Employee Details saved successfully..";
        }
        catch (MessagingException e)
           {
               e.printStackTrace();
               return "Employee Details not saved..";
           }


    }

    public void sendEmailWithAnimatedGif(String to, String subject, String body,  String attachment) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom(fromEmail);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(body);

        FileSystemResource fileSystemResource = new FileSystemResource(new File(attachment));

        if (!fileSystemResource.exists()) {
            throw new MessagingException("Attachment file not found at " + attachment);
        }
        mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);

        javaMailSender.send(mimeMessage);
    }
}
