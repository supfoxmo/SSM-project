package com.foxmo.crm;


import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreateExcelTest {
    public static void main(String[] args) {
        //创建HSSFWorkbook对象，对应一个excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        //使用workbook创建HSSFSheet对象，对应workbook文件中的一页
        HSSFSheet sheet = workbook.createSheet("学生列表");
        //使用sheet创建HSSFRow对象，对应sheet中的一行
        HSSFRow row = sheet.createRow(0);//行号：从0开始，依次递增
        //生成HSSFCellStyle对象（样式）
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);//居中设置
        //使用row创建HSSFCell对象，对应row中的列
        HSSFCell cell = row.createCell(0);//列号：从0开始，依次递增
        cell.setCellValue("学号");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("姓名");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("年龄");
        cell.setCellStyle(style);

        //使用sheet创建10个HSSFRow对象，对应sheet中的10行
        for (int i = 1; i <= 10; i++) {
            row = sheet.createRow(i);
            cell = row.createCell(0);
            cell.setCellValue(1000 + i);
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue("NAME" + i);
            cell.setCellStyle(style);
            cell = row.createCell(2);
            cell.setCellValue(20 + i);
            cell.setCellStyle(style);
        }
        FileOutputStream fos = null;
        try {
             fos = new FileOutputStream("D:\\文档文件\\Excel\\studentList.xls");
            //调用工具函数生成excel文件
            workbook.write(fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭资源
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("=====================create ok=====================");


    }
}
