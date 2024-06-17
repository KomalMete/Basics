package com.omsoftware.demo_2.serviceImpl;

import com.omsoftware.demo_2.model.Employee;
import com.omsoftware.demo_2.model.request.EmployeeRequest;
import com.omsoftware.demo_2.projection.EmployeeProjection;
import com.omsoftware.demo_2.repository.EmployeeRepository;
import com.omsoftware.demo_2.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public Object saveOrUpdate(EmployeeRequest employeeRequest)
    {

        if (employeeRepository.existsById(employeeRequest.getEmployeeId()))
        {
            Employee emp = new Employee();
            emp.setEmployeeId(employeeRequest.getEmployeeId());
            emp.setName(employeeRequest.getName());
            emp.setLastName(employeeRequest.getLastName());
            emp.setLocation(employeeRequest.getLocation());
            emp.setContact(employeeRequest.getContact());
            emp.setEmail(employeeRequest.getEmail());
            emp.setPassword(employeeRequest.getPassword());
            //saving bcryptpassword
            //String newBcryptPassword = hashPassword(employeeRequest.getPassword());
            //emp.setPassword(newBcryptPassword);
            employeeRepository.save(emp);
            return "Employee Details Updated Successfully..";
        }
        else {

            Employee emp = new Employee();
            emp.setEmployeeId(employeeRequest.getEmployeeId());
            emp.setName(employeeRequest.getName());
            emp.setLastName(employeeRequest.getLastName());
            emp.setLocation(employeeRequest.getLocation());
            emp.setContact(employeeRequest.getContact());
            emp.setEmail(employeeRequest.getEmail());
            emp.setPassword(employeeRequest.getPassword());
            //saving bcryptpassword
            //String newBcryptPassword = hashPassword(employeeRequest.getPassword());
            //emp.setPassword(newBcryptPassword);


            //email without attachment
            //this.sendEmail("komalmete8@gmail.com", "komaldmete16@gmail.com", "Inside Subject", "Inside Body");
            //employeeRepository.save(emp);
            //return "Employee Saved..";


            //email with attachment
            //double \\ after programs is manually given
            //if you want to include an actual backslash character in a string, you need to escape it by using a double backslash (\\)
//            try{
//                String attachment = "D:\\OM_Software_programs\\welcome-sign-design.webp";
//                this.sendEmailWithAttachment("komalmete8@gmail.com", "komaldmete16@gmail.com", "Inside Subject", "Inside Body", attachment);
//                employeeRepository.save(emp);
//                return "Employee Details Saved Successfully...";
//            }
//           catch (MessagingException e)
//           {
//               e.printStackTrace();
//               return "Employee Details not saved..";
//           }

            //email with html-content
//            try{
//                Context context = new Context();
//                context.setVariable("name", emp.getName());
//                this.sendEmailWithTemplate("komalmete8@gmail.com", "Using Template", context);
//                employeeRepository.save(emp);
//                return "Employee Details Saved Successfully...";
//            }
//            catch (MessagingException e)
//            {
//                e.printStackTrace();
//               return "Employee Details not saved..";
//            }


            //send with pdf
//            try
//            {
//                Context context = new Context();
//                context.setVariable("name", emp.getName());
//                this.sendEmailWithPDF("komalmete8@gmail.com", "Using HTML to PDF", context);
//                employeeRepository.save(emp);
//               return "Employee Details Saved Successfully...";
//            }
//            catch (MessagingException e)
//            {
//                e.printStackTrace();
//                return "Employee Details not saved..";
//            }

            //send email with template + otp
            try{
                String otpGenerated = generateOTP();
                Context context = new Context();
                context.setVariable("name", emp.getName());
                context.setVariable("otp",otpGenerated);
                this.sendEmailWithTemplate("komalmete8@gmail.com", "OTP Generation", context);
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

    public String hashPassword(String employeePassword)
    {

        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        String bcryptPassword = bcrypt.encode(employeePassword);
        return bcryptPassword;

    }
    public void sendEmail(String to, String cc, String subject, String body)
    {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom(fromEmail);
        message.setCc(cc);
        javaMailSender.send(message);
    }

    public void sendEmailWithAttachment(String to, String cc, String subject, String body,String attachment) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(fromEmail);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setCc(cc);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(body);

        FileSystemResource fileSystemResource = new FileSystemResource(new File(attachment));
        mimeMessageHelper.addAttachment(fileSystemResource.getFilename(),fileSystemResource);
        javaMailSender.send(mimeMessage);
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

    public void sendEmailWithPDF(String to, String subject, Context context) throws MessagingException
    {
        try
        {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,"utf-8");

            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);

            String htmlContent = templateEngine.process("emailTemplate", context);
            mimeMessageHelper.setText(htmlContent, true);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ITextRenderer iTextRenderer = new ITextRenderer();
            iTextRenderer.setDocumentFromString(htmlContent);
            iTextRenderer.layout();
            iTextRenderer.createPDF(byteArrayOutputStream);
            mimeMessageHelper.addAttachment("admission.pdf", new ByteArrayResource(byteArrayOutputStream.toByteArray()));
            javaMailSender.send(mimeMessage);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
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

    @Override
    public Object getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Object deleteById(Long employeeId) {
        if(employeeRepository.existsById(employeeId))
        {
            employeeRepository.deleteById(employeeId);
            return "Employee Record Deleted Successfully...";
        }
        else
        {
            return "Employee Record Not Present..";
        }
    }

    @Override
    public Object findById(Long employeeId) {
        if(employeeRepository.existsById(employeeId))
        {
            Employee emp = employeeRepository.findById(employeeId).get();
            return emp;
        }
        else
        {
            return "Employee Not Present..";
        }
    }

    @Override
    public Object searchByName(String name, Pageable pageable) {

        if(name != null && !name.isEmpty())
        {
            return employeeRepository.findByName(name, pageable);
        }
        else
        {
            return  "No Employee Name Match..";
        }
    }

    @Override
    public Object searchByAddress(String address, Pageable pageable) {

        if(address != null && !address.isEmpty())
        {
            return employeeRepository.findByAddress(address, pageable);
        }
        else
        {
            return  "No Employee Address Match..";
        }

    }

    @Override
    public Object searchByNameAndLastName(String name, String lastName , Pageable pageable) {

        if((name != null && !name.isEmpty()) || (lastName != null && !lastName.isEmpty()))
        {
            return employeeRepository.findByNameAndLastName(name,lastName, pageable);
        }
        else
        {
            return  "No Employee name and lastname Match..";
        }
    }

    @Override
    public Object searchByName1AndLastName(String userName, Pageable pageable)
    {
        if(!userName.isEmpty() && userName != null)
        {
            return employeeRepository.searchByName1AndLastName(userName, pageable);
        }
        else
        {
            return  "No Employee username Match..";
        }
    }

    @Override
    public Object projection(Pageable pageable) {

        Page<EmployeeProjection> byProjection = employeeRepository.findByProjection(pageable);
        return byProjection;
    }

    @Override
    public Object changePassword(Long employeeId, String oldPassword, String newPassword) {

        if(employeeId != 0)
        {

            if(employeeRepository.existsById(employeeId))
            {
                Employee emp = employeeRepository.findById(employeeId).get();
                String oldStoredPassword = emp.getPassword();
                if(oldStoredPassword.equals(oldPassword))
                {
                    emp.setPassword(newPassword);
                    //System.out.println(emp.getPassword());
                    employeeRepository.save(emp);
                    return "Password changed successfully..";
                }
                else
                {
                    return "Provided oldPassword is wrong..";
                }
            }
            else
            {
                 return "Employee Record not present for this ID..";
            }
        }
        else
        {
            return "Employee ID Doesnt Exist..";
        }
    }

//    @Override
//    public Object changeStatus(Long employeeId) {
//
//        if(employeeRepository.existsById(employeeId))
//        {
//            Employee emp = employeeRepository.findById(employeeId).get();
//            if(emp.isActive())
//            {
//                emp.setActive(false);
//                employeeRepository.save(emp);
//               return "Employee status change to In-Active..";
//            }
//            else
//            {
//                emp.setActive(true);
//                employeeRepository.save(emp);
//                return "Employee status change to Active..";
//            }
//        }
//        else
//        {
//           return "Employee Doesnt Exists..";
//        }
//    }

    @Override
    public Object fileUpload(MultipartFile file) {
        if(file != null && !file.isEmpty())
        {
            try
            {
                String storePath = "D:\\OM_Software_programs\\demo_2\\src\\main\\resources\\static\\folders";
                Path targetPath = Paths.get(storePath, file.getOriginalFilename());

                if (!Files.exists(targetPath.getParent())) {
                    Files.createDirectories(targetPath.getParent());
                }

                Files.copy(file.getInputStream(),targetPath, StandardCopyOption.REPLACE_EXISTING);
                return "File uploaded successfully: " + file.getOriginalFilename();
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return "File upload failed: " + e.getMessage();
            }

        }
        else
        {
            return "File not uploaded: File is empty or null.";
        }
    }

    @Override
    public Object forgetPassword(String email, String password) {

        if(employeeRepository.existsByEmail(email))
        {
            Employee emp = employeeRepository.findByEmail(email);
            String bcryptPassword = hashPassword(password);
            emp.setPassword(bcryptPassword);
            employeeRepository.save(emp);
            return "Password changed ";
        }
        else
        {
            return "Email doesn't exists";
        }
    }


}
