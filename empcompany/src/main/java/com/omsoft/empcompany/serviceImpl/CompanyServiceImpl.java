package com.omsoft.empcompany.serviceImpl;

import com.omsoft.empcompany.models.Company;
import com.omsoft.empcompany.models.request.CompanyRequest;
import com.omsoft.empcompany.repository.CompanyRepository;
import com.omsoft.empcompany.service.CompanyService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Object saveOrUpdate(CompanyRequest companyRequest) {

        Company company = new Company();
        company.setCompanyName(companyRequest.getCompanyName());
        company.setDescription(companyRequest.getDescription());
        company.setWebsite(companyRequest.getWebsite());
        company.setContact(companyRequest.getContact());
        companyRepository.save(company);
        return "Company details saved successfully..";
    }

    @Override
    public Object deleteById(Long companyId) {
        if(companyId == 0 || companyId == null)
        {
            return "Company ID cannot be null or zero";
        }

        return companyRepository.findById(companyId)
                .map(company ->
                {
                    companyRepository.deleteById(companyId);
                    return "Company record deleted successfully...";
                })
                .orElse("Company record doesnt exists..");
    }

    @Override
    public Object getById(Long companyId) {

        if(companyId == 0 || companyId == null)
        {
            return "Company ID cannot be null or zero";
        }
        return companyRepository.findById(companyId)
                .map(company -> (Object) company)
                .orElse("Company record doesnt exists..");

    }

    @Override
    public Object getAll() {
        return companyRepository.findAll();
    }

    @Override
    public ByteArrayInputStream downloadExcelForCompany() {

        List<Company> list = companyRepository.findAll();
         String[] HEADERS = {
                "Company ID" ,
                "Company Name" ,
                "Description" ,
                "website",
                "Contact"
        };

        String SHEET_NAME ="company_list";
        String filePath = "D:\\OM_Software_programs\\empcompany\\company_list.xlsx";

        try {
            dataToExcel(list, HEADERS, SHEET_NAME, filePath);
            return new ByteArrayInputStream(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }



    public static void dataToExcel(List<Company> list, String[] HEADERS, String SHEET_NAME, String filePath)
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
            for(Company c : list)
            {
                Row dataRow = sheet.createRow(rowIndex);
                rowIndex++;

                dataRow.createCell(0).setCellValue(c.getCompanyId());
                dataRow.createCell(1).setCellValue(c.getCompanyName());
                dataRow.createCell(2).setCellValue(c.getDescription());
                dataRow.createCell(3).setCellValue(c.getWebsite());
                dataRow.createCell(4).setCellValue(c.getContact());
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
    public Object uploadExcelForCompany(MultipartFile file) throws IOException {
        boolean excelType = checkExcelFormat(file);
        if(excelType == true)
        {
            //System.out.println("in the uploadExcelForCompany try block");
            List<Company> companies = convertExcelToListOfCompany(file);
            System.out.println(companies.size()+" size");
            List<Company> companies1 = companyRepository.saveAll(companies);
            System.out.println(companies1.size()+"size");
            System.out.println("in the uploadExcelForCompany try block");
            return "Excel to database upload successfull..";
        }
        else
        {
            System.out.println("in the uploadExcelForCompany else block");
            return "please upload correct excel file";
        }
    }

    //checking file is of excel type or not
    public boolean checkExcelFormat(MultipartFile file)
    {
        String contentType = file.getContentType();
        if(contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
        {
            System.out.println("file format is correct");
            return true;
        }
        else
        {
            System.out.println("file format is not correct");
            return false;
        }
    }


        public List<Company> convertExcelToListOfCompany(MultipartFile file) {
            List<Company> list = new ArrayList<>();

            //creating workbook
            try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is))
            {
                //creating sheet
                //Sheet sheet = workbook.getSheetAt(0);
                Sheet sheet = workbook.getSheet("data");

                //filling row with sheet data
                Iterator<Row> iterator = sheet.iterator();

                if (!iterator.hasNext()) {
                    return list; // empty or no rows
                }

                iterator.next(); // skip header row

                while (iterator.hasNext()) {
                    Row row = iterator.next();
                    Company company = new Company();

                    for (int cid = 0; cid < 5; cid++) {
                        Cell cell = row.getCell(cid, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        switch (cid) {
                            case 0:
                                company.setCompanyId((long) cell.getNumericCellValue());
                                break;
                            case 1:
                                company.setCompanyName(getCellValueAsString(cell));
                                break;
                            case 2:
                                company.setDescription(getCellValueAsString(cell));
                                break;
                            case 3:
                                company.setWebsite(getCellValueAsString(cell));
                                break;
                            case 4:
                                company.setContact(getCellValueAsString(cell));
                                break;
                        }
                    }
                    list.add(company);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return list;
        }


        private String getCellValueAsString(Cell cell)
        {
            switch (cell.getCellType())
            {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
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


