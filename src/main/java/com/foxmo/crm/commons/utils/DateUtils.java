package com.foxmo.crm.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    /**
     * 对指定的日期格式化：yyyy-MM--dd HH:mm:ss
     * @param date
     * @return
     */
    public static String formatDateTima(Date date){
        //定义时间字符串格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }
}
