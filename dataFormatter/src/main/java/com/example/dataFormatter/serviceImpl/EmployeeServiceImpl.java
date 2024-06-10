package com.example.dataFormatter.serviceImpl;

import com.example.dataFormatter.models.Employee;
import com.example.dataFormatter.models.request.EmployeeRequest;
import com.example.dataFormatter.repository.EmployeeRepository;
import com.example.dataFormatter.service.EmployeeService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Object saveOrUpdate(EmployeeRequest employeeRequest)
    {

        Optional<Employee> existingEmployee = employeeRepository.findById(employeeRequest.getEmployeeId());

        Employee emp = existingEmployee.orElseGet(() -> new Employee());

        emp.setEmployeeId(employeeRequest.getEmployeeId());
        emp.setName(employeeRequest.getName());
        emp.setLastName(employeeRequest.getLastName());
        emp.setEmail(employeeRequest.getEmail());
        emp.setPassword(employeeRequest.getPassword());
        emp.setBirthDate(employeeRequest.getBirthdate());
        emp.setSalary(employeeRequest.getSalary());
        employeeRepository.save(emp);

        return "Employee details saved successfully..";
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

            Sheet sheet = workbook.getSheet("employee_list_7");

            DataFormatter dataFormatter = new DataFormatter();

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

                            //employee.setBirthDate((String)getCellValue(cell));
                            employee.setBirthDate(dataFormatter.formatCellValue(cell));
                            break;
                        case 1:
                            //employee.setEmail((String) getCellValue(cell));
                            employee.setEmail(dataFormatter.formatCellValue(cell));
                            break;

                        case 2:

                            //employee.setLastName((String)getCellValue(cell));
                            employee.setLastName(dataFormatter.formatCellValue(cell));
                            break;

                        case 3:
                            //employee.setName((String)getCellValue(cell));
                            employee.setName(dataFormatter.formatCellValue(cell));
                            break;

                        case 4:
                            //employee.setPassword((String)getCellValue(cell));
                            employee.setPassword(dataFormatter.formatCellValue(cell));
                            break;

                        case 5:
                            //employee.setSalary((String)getCellValue(cell));
                            employee.setSalary(dataFormatter.formatCellValue(cell));
                            break;

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
}
