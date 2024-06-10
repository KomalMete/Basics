package com.omsoft.empcompany.serviceImpl;

import com.omsoft.empcompany.models.Company;
import com.omsoft.empcompany.models.Employee;
import com.omsoft.empcompany.models.request.EmployeeRequest;
import com.omsoft.empcompany.repository.CompanyRepository;
import com.omsoft.empcompany.repository.EmployeeRepository;
import com.omsoft.empcompany.service.CompanyService;
import com.omsoft.empcompany.service.EmployeeService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private CompanyService companyService;

    @Override
    public Object saveOrUpdate(EmployeeRequest employeeRequest) {

        Optional<Employee> existingEmployee = employeeRepository.findById(employeeRequest.getEmployeeId());

        Employee emp = existingEmployee.orElseGet(() -> new Employee());

        Optional<Company> fetchCompany = companyRepository.findById(employeeRequest.getCompany().getCompanyId());
        if(!fetchCompany.isPresent())
        {
            System.out.println("company details are missing");
            return "company details are missing";
        }

        Company company = fetchCompany.get();

        emp.setEmployeeId(employeeRequest.getEmployeeId());
        emp.setName(employeeRequest.getName());
        emp.setUserName(employeeRequest.getUserName());
        emp.setLastName(employeeRequest.getLastName());
        emp.setLocation(employeeRequest.getLocation());
        emp.setContact(employeeRequest.getContact());
        emp.setEmail(employeeRequest.getEmail());
        emp.setPassword(employeeRequest.getPassword());
        emp.setActive(true);
        emp.setDeleted(false);
        emp.setCompany(company);

        Employee savedEmployee = employeeRepository.save(emp);


        String stringContact = String.valueOf(savedEmployee.getCompany().getContact());

        //String stringContact = company.getContact() + " ";
        //System.out.println(stringContact+"contact no  in string form");

        String companyCode = company.getCompanyName().substring(0,3).toUpperCase() + "-" + savedEmployee.getEmployeeId();
        //for template
        try
        {
            Context context = new Context();
            context.setVariable("name", savedEmployee.getName());
            context.setVariable("lastname", savedEmployee.getLastName());
            context.setVariable("companyName", savedEmployee.getCompany().getCompanyName());
            context.setVariable("code", companyCode);
            context.setVariable("website", savedEmployee.getCompany().getWebsite());
            context.setVariable("contact", company.getContact());


            this.sendEmailWithTemplate("komalmete8@gmail.com", "Registration Information", context);


            return existingEmployee.isPresent() ? "Employee details updated successfully.." : "Employee record created successfully..";
        }
        catch (MessagingException e)
        {
            e.printStackTrace();
            return "Employee record didnt save..";
        }





    }

    public void sendEmailWithTemplate(String to, String subject, Context context) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");

        mimeMessageHelper.setFrom(fromEmail);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);

        String htmlContent = templateEngine.process("emailTemplate", context);
        mimeMessageHelper.setText(htmlContent, true);

        javaMailSender.send(mimeMessage);
    }

    @Override
    public ByteArrayInputStream downloadExcelForEmployee() {

        List<Employee> list = employeeRepository.findAll();
        String[] HEADERS = {
                "Employee ID" ,
                "Contact",
                "Email",
                "LastName",
                "Location",
                "FirstName" ,
                "Password" ,
                "Username",
                "Company ID"
        };

        String SHEET_NAME ="employee_list";
        String filePath = "D:\\OM_Software_programs\\empcompany\\employee_list.xlsx";

        try {
            dataToExcelForEmployee(list, HEADERS, SHEET_NAME, filePath);
            return new ByteArrayInputStream(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void dataToExcelForEmployee(List<Employee> list, String[] HEADERS, String SHEET_NAME, String filePath)
    {

        try
        {
            //create workbook
            Workbook workbook = new XSSFWorkbook();
            //ByteArrayOutputStream out = new ByteArrayOutputStream();

            //create sheet
            Sheet sheet = workbook.createSheet(SHEET_NAME);

            //create row for header
            Row row = sheet.createRow(0);

            //create cell in that header row
            for(int i=0; i<HEADERS.length; i++)
            {
                Cell cell = row.createCell(i);
                cell.setCellValue(HEADERS[i]);
            }

            //create rows and cells for values

            int rowIndex = 1;
            for(Employee e : list)
            {
                Row dataRow = sheet.createRow(rowIndex);
                rowIndex++;

                dataRow.createCell(0).setCellValue(e.getEmployeeId());
                dataRow.createCell(1).setCellValue(e.getContact());
                dataRow.createCell(2).setCellValue(e.getEmail());
               // dataRow.createCell(3).setCellValue(e.getEmployeeCreatedAt());
                dataRow.createCell(3).setCellValue(e.getLastName());
                dataRow.createCell(4).setCellValue(e.getLocation());
                dataRow.createCell(5).setCellValue(e.getName());
                dataRow.createCell(6).setCellValue(e.getPassword());
                dataRow.createCell(7).setCellValue(e.getUserName());
                dataRow.createCell(8).setCellValue(e.getCompany().getCompanyName());
            }

            //workbook.write(out);
            //return new ByteArrayInputStream(out.toByteArray());
            FileOutputStream fos = new FileOutputStream(filePath);
            workbook.write(fos);

        }
        catch (IOException e)
        {
            e.printStackTrace();
            //return "cannot generate excel sheet";
            System.out.println("cannot generate excel sheet");
            //return null;

        }
    }

    @Override
    public Object uploadExcelForEmployee(MultipartFile file) throws IOException {
        if(checkForExcelFormat(file))
        {
            List<Employee> employees = uploadExcelToDatabaseForEmployee(file);
            System.out.println(employees.size()+" size employee");
            employeeRepository.saveAll(employees);

            //we r creating list here just for checking purpose
            //List<Employee> employees1 = employeeRepository.saveAll(employees);
            //System.out.println(employees1.size()+"size");
            System.out.println("in the uploadExcelForCompany try block");
            return "Excel to database upload successfull..";
        }
        else
        {
            return "Please enter correct Excel-format file";
        }
    }

    public boolean checkForExcelFormat(MultipartFile file)
    {
        String contentType = file.getContentType();
        return contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

    }

    public List<Employee> uploadExcelToDatabaseForEmployee(MultipartFile file) throws IOException
    {
        List<Employee> list = new ArrayList<>();

        try {
            //create workbook
            Workbook workbook = new XSSFWorkbook(file.getInputStream());

            Sheet sheet = workbook.getSheet("employee_sheet");

            //iterator for row
            Iterator<Row> iterator = sheet.iterator();

            if (!iterator.hasNext()) {
                return list; // empty or no rows
            }

            iterator.next(); // skip header row

            while (iterator.hasNext()) {
                Row row = iterator.next();
                Employee employee = new Employee();

                for (int cid = 0; cid <= 10; cid++) {
                    Cell cell = row.getCell(cid, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                    switch (cid) {
                        //if im not passing employee id in excel and want database to generate it
//                        case 0:
//                            employee.setEmployeeId((long) cell.getNumericCellValue());
//                            break;

                        case 0:
                            employee.setContact((String) getCellValue(cell));
                            break;

                        case 1:
                            employee.setEmail((String) getCellValue(cell));
                            break;

                        case 2:

                            employee.setEmployeeCreatedAt( (LocalDateTime) getCellValue(cell));
                            break;
                        case 3:
                            employee.setActive(true);
                            break;

                        case 4:
                            employee.setDeleted(false);
                            break;

                        case 5:
                            employee.setLastName((String)getCellValue(cell));
                            break;

                        case 6:
                            employee.setLocation((String)getCellValue(cell));
                            break;

                        case 7:
                            employee.setName((String)getCellValue(cell));
                            break;

                        case 8:
                            employee.setPassword((String)getCellValue(cell));
                            break;

                        case 9:
                            employee.setUserName((String)getCellValue(cell));
                            break;

                        case 10:
                            //when in input i have given companyId for company
//                            Long companyId = (long) cell.getNumericCellValue();
//                            Company company = companyRepository.findById(companyId).get(); // Adjust method call as necessary
//                            employee.setCompany(company);

                            //when in input i have given company name for company
                            String companyName = cell.getStringCellValue();
                            Company company = companyRepository.findByCompanyName(companyName);
                            employee.setCompany(company);
                            break;

//////////////////////////////////////////////////////////////////////////////////////////////////////
                            //normal when im passing employee id just do cid value from cid 0 to <=11
//                        switch (cid) {
//                            case 0:
//                                employee.setEmployeeId((long) cell.getNumericCellValue());
//                                break;
//
//                            case 1:
//                                employee.setContact((String) getCellValue(cell));
//                                break;
//
//                            case 2:
//                                employee.setEmail((String) getCellValue(cell));
//                                break;
//
//                            case 3:
//
//                                employee.setEmployeeCreatedAt( (LocalDateTime) getCellValue(cell));
//                                break;
//                            case 4:
//                                employee.setActive(true);
//                                break;
//
//                            case 5:
//                                employee.setDeleted(false);
//                                break;
//
//                            case 6:
//                                employee.setLastName((String)getCellValue(cell));
//                                break;
//
//                            case 7:
//                                employee.setLocation((String)getCellValue(cell));
//                                break;
//
//                            case 8:
//                                employee.setName((String)getCellValue(cell));
//                                break;
//
//                            case 9:
//                                employee.setPassword((String)getCellValue(cell));
//                                break;
//
//                            case 10:
//                                employee.setUserName((String)getCellValue(cell));
//                                break;
//
//                            case 11:
//                                //when in input i have given companyId for company
////                            Long companyId = (long) cell.getNumericCellValue();
////                            Company company = companyRepository.findById(companyId).get(); // Adjust method call as necessary
////                            employee.setCompany(company);
//
//                                //when in input i have given company name for company
//                                String companyName = cell.getStringCellValue();
//                                Company company = companyRepository.findByCompanyName(companyName);
//                                employee.setCompany(company);
//                                break;
//
                    }
                }
                list.add(employee);
            }
        }
     catch (Exception e) {
        e.printStackTrace();
    }

            return list;
    }


    private Object getCellValue(Cell cell)
    {
        switch (cell.getCellType())
        {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if(DateUtil.isCellDateFormatted(cell))
                {
                    return cell.getLocalDateTimeCellValue();
                }
                else {
                    return BigDecimal.valueOf(cell.getNumericCellValue()).toPlainString();
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
    }


//downloadListOfEmployeesFromSameCompany
    @Override
    public ByteArrayInputStream downloadListOfEmployeesFromSameCompany(Long companyId) {

        List<Employee> list = employeeRepository.findAll();

        List<Employee> fetchList = list
                                    .stream()
                                    .filter(employee -> employee.getCompany().getCompanyId().equals(companyId))
                                    .collect(Collectors.toList());

        String[] HEADERS = {
                "Employee ID" ,
                "Contact",
                "Email",
                "LastName",
                "Location",
                "FirstName" ,
                "Password" ,
                "Username",
                //"Company ID"
        };

        String SHEET_NAME ="employee_list_7";
        String filePath = "D:\\OM_Software_programs\\employee.xlsx";

        try {
            //creating file with given filename
            File file = new File(filePath);

            //creating workbook
            Workbook workbook;

            if(file.exists())
            {
                FileInputStream fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
            }
            else
            {
                workbook = new XSSFWorkbook();
            }

            dataToExcelForEmployeeWithSameCompany(workbook, fetchList, HEADERS, SHEET_NAME);

            FileOutputStream fos = new FileOutputStream(filePath);
            workbook.write(fos);
            workbook.close();

            return new ByteArrayInputStream(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void dataToExcelForEmployeeWithSameCompany(Workbook workbook, List<Employee> fetchList, String[] HEADERS, String SHEET_NAME)
    {

        try {


            //create workbook
            //Workbook workbook = new XSSFWorkbook();
            //ByteArrayOutputStream out = new ByteArrayOutputStream();

            //create sheet
            Sheet sheet = workbook.createSheet(SHEET_NAME);

            //create row for header
            Row row = sheet.createRow(0);//NORMAL

            //Row row = sheet.createRow(6);//WHAT IF THERE IS ALREADY DATA PRESENT AND I WANT TO START FROM 6 TH ROW

            //create cell in that header row
            for(int i=0; i<HEADERS.length; i++)
            {
                Cell cell = row.createCell(i);
                cell.setCellValue(HEADERS[i]);
            }

            //create rows and cells for values

            int rowIndex = 1;//NORMAL

            //int rowIndex = 7;//want to start filling data from
            for(Employee e : fetchList)
            {
                Row dataRow = sheet.createRow(rowIndex);
                rowIndex++;

                dataRow.createCell(0).setCellValue(e.getEmployeeId());
                dataRow.createCell(1).setCellValue(e.getContact());
                dataRow.createCell(2).setCellValue(e.getEmail());
                // dataRow.createCell(3).setCellValue(e.getEmployeeCreatedAt());
                dataRow.createCell(3).setCellValue(e.getLastName());
                dataRow.createCell(4).setCellValue(e.getLocation());
                dataRow.createCell(5).setCellValue(e.getName());
                dataRow.createCell(6).setCellValue(e.getPassword());
                dataRow.createCell(7).setCellValue(e.getUserName());
                //dataRow.createCell(8).setCellValue(e.getCompany().getCompanyName());
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            //return "cannot generate excel sheet";
            System.out.println("cannot generate excel sheet");
            //return null;

        }
    }

    @Override
    public ByteArrayInputStream downloadListForEmployeeAvoidOverwriteData(Long companyId)
    {
        List<Employee> list = employeeRepository.findAll();

        List<Employee> fetchList = list
                .stream()
                .filter(employee -> employee.getCompany().getCompanyId().equals(companyId))
                .collect(Collectors.toList());

        String[] HEADERS = {
                "Employee ID" ,
                "Contact",
                "Email",
                "LastName",
                "Location",
                "FirstName" ,
                "Password" ,
                "Username",
                //"Company ID"
        };

        String SHEET_NAME ="employee_list_7";
        String filePath = "D:\\OM_Software_programs\\employee.xlsx";

        try {
            //creating file with given filename
            File file = new File(filePath);

            //creating workbook
            Workbook workbook;

            if(file.exists())
            {
                FileInputStream fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
            }
            else
            {
                workbook = new XSSFWorkbook();
            }

            dataToExcelForEmployeeAvoidOverwriteData(workbook, fetchList, HEADERS, SHEET_NAME);

            FileOutputStream fos = new FileOutputStream(filePath);
            workbook.write(fos);
            workbook.close();

            return new ByteArrayInputStream(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void dataToExcelForEmployeeAvoidOverwriteData(Workbook workbook, List<Employee> fetchList, String[] HEADERS, String SHEET_NAME)
    {
        try {

            Sheet sheet = workbook.getSheet(SHEET_NAME);
            int rowIndex;

            if(sheet == null)
            {
                //create sheet
                sheet = workbook.createSheet(SHEET_NAME);

                //create row for header
                Row row = sheet.createRow(0);//NORMAL

                //Row row = sheet.createRow(6);//WHAT IF THERE IS ALREADY DATA PRESENT AND I WANT TO START FROM 6 TH ROW

                //create cell in that header row
                for(int i=0; i<HEADERS.length; i++)
                {
                    Cell cell = row.createCell(i);
                    cell.setCellValue(HEADERS[i]);
                }
                rowIndex = 1;//NORMAL
            }
            else
            {
                rowIndex = sheet.getLastRowNum() + 1;
            }
            //int rowIndex = 7;//want to start filling data from

            //start filling rows and cells
            for(Employee e : fetchList)
            {
                Row dataRow = sheet.createRow(rowIndex);
                rowIndex++;

                dataRow.createCell(0).setCellValue(e.getEmployeeId());
                dataRow.createCell(1).setCellValue(e.getContact());
                dataRow.createCell(2).setCellValue(e.getEmail());
                // dataRow.createCell(3).setCellValue(e.getEmployeeCreatedAt());
                dataRow.createCell(3).setCellValue(e.getLastName());
                dataRow.createCell(4).setCellValue(e.getLocation());
                dataRow.createCell(5).setCellValue(e.getName());
                dataRow.createCell(6).setCellValue(e.getPassword());
                dataRow.createCell(7).setCellValue(e.getUserName());
                //dataRow.createCell(8).setCellValue(e.getCompany().getCompanyName());
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            //return "cannot generate excel sheet";
            System.out.println("cannot generate excel sheet");
            //return null;

        }
    }
}