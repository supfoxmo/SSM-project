package com.foxmo.crm.commons.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;

public class HSSFUtils {
    public static String getCellValueForStr(HSSFCell cell){
        String ret = "";
        if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING){
            ret = cell.getStringCellValue();
        }else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
            ret = cell.getNumericCellValue() + "";
        }else if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN){
            ret = cell.getBooleanCellValue() + "";
        }else if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA){
            ret = cell.getCellFormula();
        }else{
            ret = "";
        }
        return ret;
    }
}
