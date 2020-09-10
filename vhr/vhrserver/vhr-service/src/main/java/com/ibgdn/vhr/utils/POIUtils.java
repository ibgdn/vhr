package com.ibgdn.vhr.utils;

import com.ibgdn.vhr.model.*;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel 文件导出、导入工具类
 */
public class POIUtils {

    public static ResponseEntity<byte[]> employee2ExcelFile(List<Employee> data) {
        // 1. 创建 Excel 文档
        HSSFWorkbook workbook = new HSSFWorkbook();

        // 2. 创建文件摘要
        workbook.createInformationProperties();

        // 3. 获取并配置文档摘要信息
        DocumentSummaryInformation information = workbook.getDocumentSummaryInformation();
        // 文档类别
        information.setCategory("员工信息");
        // 文档管理员
        information.setManager("ibgdn");
        // 设置公司
        information.setCompany("www.ibgdn.com");

        // 4. 获取文档摘要信息
        SummaryInformation summaryInformation = workbook.getSummaryInformation();
        // 文档标题
        summaryInformation.setTitle("员工信息表");
        // 文档作者
        summaryInformation.setAuthor("author");
        // 文档备注
        summaryInformation.setComments("本文档由 POI 生成。");

        // 5. 定义单元格样式
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 日期格式
        HSSFCellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));

        // 6. 创建标题
        HSSFSheet hssfSheet = workbook.createSheet("员工信息 Sheet 页");
        hssfSheet.setColumnWidth(0, 5 * 256);
        hssfSheet.setColumnWidth(1, 12 * 256);
        hssfSheet.setColumnWidth(2, 10 * 256);
        hssfSheet.setColumnWidth(3, 5 * 256);
        hssfSheet.setColumnWidth(4, 12 * 256);
        hssfSheet.setColumnWidth(5, 20 * 256);
        hssfSheet.setColumnWidth(6, 10 * 256);
        hssfSheet.setColumnWidth(7, 10 * 256);
        hssfSheet.setColumnWidth(8, 16 * 256);
        hssfSheet.setColumnWidth(9, 12 * 256);
        hssfSheet.setColumnWidth(10, 15 * 256);
        hssfSheet.setColumnWidth(11, 20 * 256);
        hssfSheet.setColumnWidth(12, 16 * 256);
        hssfSheet.setColumnWidth(13, 14 * 256);
        hssfSheet.setColumnWidth(14, 14 * 256);
        hssfSheet.setColumnWidth(15, 12 * 256);
        hssfSheet.setColumnWidth(16, 8 * 256);
        hssfSheet.setColumnWidth(17, 20 * 256);
        hssfSheet.setColumnWidth(18, 20 * 256);
        hssfSheet.setColumnWidth(19, 15 * 256);
        hssfSheet.setColumnWidth(20, 8 * 256);
        hssfSheet.setColumnWidth(21, 25 * 256);
        hssfSheet.setColumnWidth(22, 14 * 256);
        hssfSheet.setColumnWidth(23, 15 * 256);
        hssfSheet.setColumnWidth(24, 15 * 256);
        HSSFRow row0 = hssfSheet.createRow(0);
        HSSFCell cell0 = row0.createCell(0);
        cell0.setCellValue("编号");
        cell0.setCellStyle(cellStyle);
        HSSFCell cell1 = row0.createCell(1);
        cell1.setCellValue("姓名");
        cell1.setCellStyle(cellStyle);
        HSSFCell cell2 = row0.createCell(2);
        cell2.setCellValue("工号");
        cell2.setCellStyle(cellStyle);
        HSSFCell cell3 = row0.createCell(3);
        cell3.setCellValue("性别");
        cell3.setCellStyle(cellStyle);
        HSSFCell cell4 = row0.createCell(4);
        cell4.setCellValue("出生日期");
        cell4.setCellStyle(cellStyle);
        HSSFCell cell5 = row0.createCell(5);
        cell5.setCellValue("身份证号码");
        cell5.setCellStyle(cellStyle);
        HSSFCell cell6 = row0.createCell(6);
        cell6.setCellValue("婚姻状况");
        cell6.setCellStyle(cellStyle);
        HSSFCell cell7 = row0.createCell(7);
        cell7.setCellValue("民族");
        cell7.setCellStyle(cellStyle);
        HSSFCell cell8 = row0.createCell(8);
        cell8.setCellValue("籍贯");
        cell8.setCellStyle(cellStyle);
        HSSFCell cell9 = row0.createCell(9);
        cell9.setCellValue("政治面貌");
        cell9.setCellStyle(cellStyle);
        HSSFCell cell10 = row0.createCell(10);
        cell10.setCellValue("电子邮件");
        cell10.setCellStyle(cellStyle);
        HSSFCell cell11 = row0.createCell(11);
        cell11.setCellValue("电话号码");
        cell11.setCellStyle(cellStyle);
        HSSFCell cell12 = row0.createCell(12);
        cell12.setCellValue("联系地址");
        cell12.setCellStyle(cellStyle);
        HSSFCell cell13 = row0.createCell(13);
        cell13.setCellValue("所属部门");
        cell13.setCellStyle(cellStyle);
        HSSFCell cell14 = row0.createCell(14);
        cell14.setCellValue("职位");
        cell14.setCellStyle(cellStyle);
        HSSFCell cell15 = row0.createCell(15);
        cell15.setCellValue("职称");
        cell15.setCellStyle(cellStyle);
        HSSFCell cell16 = row0.createCell(16);
        cell16.setCellValue("聘用形式");
        cell16.setCellStyle(cellStyle);
        HSSFCell cell17 = row0.createCell(17);
        cell17.setCellValue("最高学历");
        cell17.setCellStyle(cellStyle);
        HSSFCell cell18 = row0.createCell(18);
        cell18.setCellValue("专业");
        cell18.setCellStyle(cellStyle);
        HSSFCell cell19 = row0.createCell(19);
        cell19.setCellValue("毕业院校");
        cell19.setCellStyle(cellStyle);
        HSSFCell cell20 = row0.createCell(20);
        cell20.setCellValue("入职日期");
        cell20.setCellStyle(cellStyle);
        HSSFCell cell21 = row0.createCell(21);
        cell21.setCellValue("转正日期");
        cell21.setCellStyle(cellStyle);
        HSSFCell cell22 = row0.createCell(22);
        cell22.setCellValue("合同起始日期");
        cell22.setCellStyle(cellStyle);
        HSSFCell cell23 = row0.createCell(23);
        cell23.setCellValue("合同截止日期");
        cell23.setCellStyle(cellStyle);
        HSSFCell cell24 = row0.createCell(24);
        cell24.setCellValue("合同期限");
        cell24.setCellStyle(cellStyle);

        Employee employee;
        for (int i = 1; i <= data.size(); i++) {
            employee = data.get(i - 1);
            HSSFRow sheetRow = hssfSheet.createRow(i);
            sheetRow.createCell(0).setCellValue(employee.getId());
            sheetRow.createCell(1).setCellValue(employee.getName());
            sheetRow.createCell(2).setCellValue(employee.getWorkId());
            sheetRow.createCell(3).setCellValue(employee.getGender());
            HSSFCell c4 = sheetRow.createCell(4);
            c4.setCellStyle(dateCellStyle);
            c4.setCellValue(employee.getBirthday());
            sheetRow.createCell(5).setCellValue(employee.getIdCard());
            sheetRow.createCell(6).setCellValue(employee.getWedlock());
            sheetRow.createCell(7).setCellValue(employee.getNation().getName());
            sheetRow.createCell(8).setCellValue(employee.getNativePlace());
            sheetRow.createCell(9).setCellValue(employee.getPoliticsStatus().getName());
            sheetRow.createCell(10).setCellValue(employee.getPhone());
            sheetRow.createCell(11).setCellValue(employee.getAddress());
            sheetRow.createCell(12).setCellValue(employee.getDepartment().getName());
            sheetRow.createCell(13).setCellValue(employee.getJobLevel().getName());
            sheetRow.createCell(14).setCellValue(employee.getPosition().getName());
            sheetRow.createCell(15).setCellValue(employee.getEngageForm());
            sheetRow.createCell(16).setCellValue(employee.getTipTopDegree());
            sheetRow.createCell(17).setCellValue(employee.getSpecialty());
            sheetRow.createCell(18).setCellValue(employee.getSchool());
            HSSFCell c19 = sheetRow.createCell(19);
            c19.setCellStyle(dateCellStyle);
            c19.setCellValue(employee.getBeginDate());
            sheetRow.createCell(20).setCellValue(employee.getWorkState());
            sheetRow.createCell(21).setCellValue(employee.getEmail());
            sheetRow.createCell(22).setCellValue(employee.getContractTerm());
            HSSFCell c23 = sheetRow.createCell(23);
            c23.setCellStyle(dateCellStyle);
            c23.setCellValue(employee.getBeginContract());
            HSSFCell c24 = sheetRow.createCell(24);
            c24.setCellStyle(dateCellStyle);
            c24.setCellValue(employee.getEndContract());
            sheetRow.createCell(25).setCellValue(employee.getConversionTime());
        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.setContentDispositionFormData("attachment", new String("员工表.xls".getBytes("UTF-8"), "ISO-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            workbook.write(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(stream.toByteArray(), headers, HttpStatus.CREATED);
    }

    /**
     * excel 文件内容转换成 employee 对象
     *
     * @param file              excel 文件
     * @param allNations        民族
     * @param allPoliticsStatus 政治面貌
     * @param allDepartments    部门
     * @param allPositions      职务
     * @param allJobLevels      职级
     * @return List employee 集合
     */
    public static List<Employee> excelFile2Employee(MultipartFile file, List<Nation> allNations,
                                                    List<PoliticsStatus> allPoliticsStatus, List<Department> allDepartments,
                                                    List<Position> allPositions, List<JobLevel> allJobLevels) {
        List<Employee> employeeList = new ArrayList<>();
        // 1. 创建一个 workbook 对象
        HSSFWorkbook workbook = null;
        try {
            workbook = new HSSFWorkbook(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 2. 获取 sheet 页数量
        int numberOfSheets = workbook.getNumberOfSheets();
        HSSFSheet sheet;
        HSSFRow row;
        HSSFCell cell;
        int physicalNumberOfCells;
        Employee employee;
        for (int i = 0; i < numberOfSheets; i++) {
            // 3. 获取 sheet 页
            sheet = workbook.getSheetAt(i);
            // 4. 获取 sheet 行数
            int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
            for (int j = 0; j < physicalNumberOfRows; j++) {
                // 5. 跳过标题
                if (j == 0) {
                    continue;
                }
                // 6. 获取行
                row = sheet.getRow(j);
                // 跳过空行
                if (row == null) {
                    continue;
                }
                // 7. 获取列数
                physicalNumberOfCells = row.getPhysicalNumberOfCells();
                employee = new Employee();
                String stringCellValue;
                for (int k = 0; k < physicalNumberOfCells; k++) {
                    cell = row.getCell(k);
                    switch (cell.getCellType()) {
                        case STRING:
                            stringCellValue = cell.getStringCellValue();
                            switch (k) {
                                case 1:
                                    employee.setName(stringCellValue);
                                    break;
                                case 2:
                                    employee.setWorkId(stringCellValue);
                                    break;
                                case 3:
                                    employee.setGender(stringCellValue);
                                    break;
                                case 5:
                                    employee.setIdCard(stringCellValue);
                                    break;
                                case 6:
                                    employee.setWedlock(stringCellValue);
                                    break;
                                case 7:
                                    int nationIndex = allNations.indexOf(new Nation(stringCellValue));
                                    employee.setNationId(allNations.get(nationIndex).getId());
                                    break;
                                case 8:
                                    employee.setNativePlace(stringCellValue);
                                    break;
                                case 9:
                                    int politicstatusIndex = allPoliticsStatus.indexOf(new PoliticsStatus(stringCellValue));
                                    employee.setPoliticId(allPoliticsStatus.get(politicstatusIndex).getId());
                                    break;
                                case 10:
                                    employee.setPhone(stringCellValue);
                                    break;
                                case 11:
                                    employee.setAddress(stringCellValue);
                                    break;
                                case 12:
                                    int departmentIndex = allDepartments.indexOf(new Department(stringCellValue));
                                    employee.setDepartmentId(allDepartments.get(departmentIndex).getId());
                                    break;
                                case 13:
                                    int jobLevelIndex = allJobLevels.indexOf(new JobLevel(stringCellValue));
                                    employee.setJobLevelId(allJobLevels.get(jobLevelIndex).getId());
                                    break;
                                case 14:
                                    int positionIndex = allPositions.indexOf(new Position(stringCellValue));
                                    employee.setPosId(allPositions.get(positionIndex).getId());
                                    break;
                                case 15:
                                    employee.setEngageForm(stringCellValue);
                                    break;
                                case 16:
                                    employee.setTipTopDegree(stringCellValue);
                                    break;
                                case 17:
                                    employee.setSpecialty(stringCellValue);
                                    break;
                                case 18:
                                    employee.setSchool(stringCellValue);
                                    break;
                                case 20:
                                    employee.setWorkState(stringCellValue);
                                    break;
                                case 21:
                                    employee.setEmail(stringCellValue);
                                    break;
                            }
                            break;
                        default: {
                            switch (k) {
                                case 4:
                                    employee.setBirthday(cell.getDateCellValue());
                                    break;
                                case 19:
                                    employee.setBeginDate(cell.getDateCellValue());
                                    break;
                                case 23:
                                    employee.setBeginContract(cell.getDateCellValue());
                                    break;
                                case 24:
                                    employee.setEndContract(cell.getDateCellValue());
                                    break;
                                case 22:
                                    employee.setContractTerm(cell.getNumericCellValue());
                                    break;
                                case 25:
                                    employee.setConversionTime(cell.getDateCellValue());
                                    break;
                            }
                        }
                        break;
                    }
                }
                employeeList.add(employee);
            }
        }
        return employeeList;
    }
}
