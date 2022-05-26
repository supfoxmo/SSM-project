package com.foxmo.crm.workbench.web.controller;

import com.foxmo.crm.commons.constant.Constant;
import com.foxmo.crm.commons.domain.ReturnObject;
import com.foxmo.crm.commons.utils.DateUtils;
import com.foxmo.crm.commons.utils.UUIDUtils;
import com.foxmo.crm.settings.domain.User;
import com.foxmo.crm.settings.service.UserService;
import com.foxmo.crm.workbench.domain.Activity;
import com.foxmo.crm.workbench.service.ActivityService;
import jdk.nashorn.internal.runtime.ECMAException;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

@Controller
public class ActivityController {
    @Autowired
    private UserService userService;
    @Autowired
    private ActivityService activityService;

    @RequestMapping("/workbench/activity/index.do")
    public String index(HttpServletRequest request){
        //调用service层方法，查询所有用户信息
        List<User> userList = userService.queryAllUser();
        //将查询到的信息保存到request域中
        request.setAttribute("userList",userList);
        //请求转发
        return "workbench/activity/index";
    }

    @ResponseBody
    @RequestMapping("/workbench/activity/saveCreateActivity.do")
    public Object saveCreateActivity(Activity activity, HttpSession session){
        //进一步封装包装类
        activity.setId(UUIDUtils.getUUID());
        activity.setCreateTime(DateUtils.formatDateTima(new Date()));
        //获取当前登录的用户信息
        User user = (User)session.getAttribute(Constant.SESSION_USER);
        activity.setCreateBy(user.getId());
        //创建响应包装类
        ReturnObject returnObject = new ReturnObject();
        try{
            //调用service层方法，新增市场活动
            int count = activityService.saveActivity(activity);
            if (count > 0){ //新增成功
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            }else{ //新增失败
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统繁忙，请稍后重试......");
            }
        }catch(Exception e){
            System.out.println("+++++++++++++++++++++++++++++++");
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙，请稍后重试......");
        }
        return returnObject;
    }

    @ResponseBody
    @RequestMapping("/workbench/activity/queryActivityByConditionForPage.do")
    public Object queryActivityByConditionForPage(String name,String owner,String startDate,String endDate,int pageNo,int pageSize) {
        //封装参数
        Map<String, Object> map = new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("beginNo",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        //调用service层的方法查询数据
        List<Activity> activityList = activityService.queryActivityByConditionForPage(map);
        int totalRows = activityService.queryConutOfActivityByCondition(map);
        //封装查询结果
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("activityList",activityList);
        retMap.put("totalRows",totalRows);

        return retMap;
    }

    @ResponseBody
    @RequestMapping("/workbench/activity/removeActivityByIds.do")
    public Object removeActivityByIds(String[] id){
        ReturnObject retObj = new ReturnObject();
        try{
            int i = activityService.removeActivityByIds(id);
            if (i > 0){
                retObj.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                retObj.setMessage("删除成功");
            }else{
                retObj.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                retObj.setMessage("删除失败，请稍后再试");
            }
        }catch(Exception e){
            retObj.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            retObj.setMessage("删除失败，请稍后再试");
            e.printStackTrace();
        }
        return retObj;
    }

    @ResponseBody
    @RequestMapping("/workbench/activity/queryActivityById.do")
    public Object queryActivityById(String id){
        return activityService.queryAvtivityById(id);
    }

    @ResponseBody
    @RequestMapping("/workbench/activity/saveEditActivity.do")
    public Object saveEditActivity(Activity activity,HttpSession session){
        //进一步封装数据
        activity.setEditTime(DateUtils.formatDateTima(new Date()));
        //获取当前登录用户
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        activity.setEditBy(user.getId());

        //创建响应信息封装对象
        ReturnObject returnObject = new ReturnObject();
        try{
            int i = activityService.saveEditActivityById(activity);
            if (i > 0){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setMessage("修改成功");
            }else{
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("修改失败");
            }
        }catch(Exception e){
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("修改失败");
            e.printStackTrace();
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/queryAllActivitys.do")
    public void queryAllActivitys(HttpServletResponse response) throws Exception{
        //调用service层的方法，查询所有市场活动的信息
        List<Activity> activityList = activityService.queryAllActivity();
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
        cell.setCellValue("ID");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("所有者");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("活动名称");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("开始时间");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("结束时间");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue("活动成本");
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue("活动描述");
        cell.setCellStyle(style);
        cell = row.createCell(7);
        cell.setCellValue("创建时间");
        cell.setCellStyle(style);
        cell = row.createCell(8);
        cell.setCellValue("创建者");
        cell.setCellStyle(style);
        cell = row.createCell(9);
        cell.setCellValue("修改时间");
        cell.setCellStyle(style);
        cell = row.createCell(10);
        cell.setCellValue("修改者");
        cell.setCellStyle(style);

        if (activityList != null && activityList.size() >0){
            Activity activity = null;
            for (int i = 1; i <= activityList.size(); i++) {
                row = sheet.createRow(i);
                activity = activityList.get(i - 1);

                cell = row.createCell(0);//列号：从0开始，依次递增
                cell.setCellValue(activity.getId());
                cell.setCellStyle(style);
                cell = row.createCell(1);
                cell.setCellValue(activity.getOwner());
                cell.setCellStyle(style);
                cell = row.createCell(2);
                cell.setCellValue(activity.getName());
                cell.setCellStyle(style);
                cell = row.createCell(3);
                cell.setCellValue(activity.getStartDate());
                cell.setCellStyle(style);
                cell = row.createCell(4);
                cell.setCellValue(activity.getEndDate());
                cell.setCellStyle(style);
                cell = row.createCell(5);
                cell.setCellValue(activity.getCost());
                cell.setCellStyle(style);
                cell = row.createCell(6);
                cell.setCellValue(activity.getDescription());
                cell.setCellStyle(style);
                cell = row.createCell(7);
                cell.setCellValue(activity.getCreateTime());
                cell.setCellStyle(style);
                cell = row.createCell(8);
                cell.setCellValue(activity.getCreateBy());
                cell.setCellStyle(style);
                cell = row.createCell(9);
                cell.setCellValue(activity.getEditTime());
                cell.setCellStyle(style);
                cell = row.createCell(10);
                cell.setCellValue(activity.getEditBy());
                cell.setCellStyle(style);
            }
        }
        //生成excel文件
        FileOutputStream fos = new FileOutputStream("D:\\文档文件\\Excel\\studentList.xls");
        workbook.write(fos);

        //关闭资源
        fos.close();
        workbook.close();

        //将生成的excel文件下载到客户端
        //设置响应类型
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.addHeader("Content-Disposition","attachment;filename=activityList.xls");
        //获取输出流
        ServletOutputStream outputStream = response.getOutputStream();
        //创建文件输入流，读取excel文件
        FileInputStream fis = new FileInputStream("D:\\文档文件\\Excel\\studentList.xls");

        byte[] buff = new byte[256];
        int len = 0;

        while((len = fis.read(buff)) != -1){
            //输出数据到客户端
            outputStream.write(buff,0,len);
        }

        //关闭资源
        fis.close();
        outputStream.flush();
    }
}
