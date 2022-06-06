package com.foxmo.crm.workbench.web.controller;

import com.foxmo.crm.commons.constant.Constant;
import com.foxmo.crm.commons.domain.ReturnObject;
import com.foxmo.crm.commons.utils.DateUtils;
import com.foxmo.crm.commons.utils.UUIDUtils;
import com.foxmo.crm.settings.domain.User;
import com.foxmo.crm.workbench.domain.ActivityRemark;
import com.foxmo.crm.workbench.service.ActivityRemarkService;
import org.apache.poi.hssf.record.DVALRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.UUID;

@Controller
public class ActivityRemarkController {
    @Autowired
    private ActivityRemarkService activityRemarkService;

    @ResponseBody
    @RequestMapping("/workbench/activity/saveCreateActivityRemark.do")
    public Object saveCreateActivityRemark(String noteContent, String activityId, HttpSession session){
        //获取当前登录用户
        User user = (User)session.getAttribute(Constant.SESSION_USER);
        //封装参数
        ActivityRemark activityRemark = new ActivityRemark();
        activityRemark.setId(UUIDUtils.getUUID());
        activityRemark.setNoteContent(noteContent);
        activityRemark.setCreateTime(DateUtils.formatDateTima(new Date()));
        activityRemark.setCreateBy(user.getId());
        activityRemark.setEditFlag(Constant.REMARK_EDIT_FLAG_NO_EDITED);
        activityRemark.setActivityId(activityId);
        //新建响应对象
        ReturnObject retObject = new ReturnObject();
        try{
            //调用service层的方法，新增市场活动评价
            int count = activityRemarkService.saveCreateActivityRemark(activityRemark);
            //封装响应对象
            if (count > 0){
                //市场活动评价成功
                retObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                retObject.setRetData(activityRemark);
            }else{
                //新增市场活动失败
                retObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                retObject.setMessage("系统繁忙，请稍后重试。。。。");
            }
        }catch(Exception e){
            //新增市场活动失败
            retObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            retObject.setMessage("系统繁忙，请稍后重试。。。。");
            e.printStackTrace();
        }
        return retObject;
    }

    @ResponseBody
    @RequestMapping("/workbench/activity/removeActivityRemarkById.do")
    public Object removeAvtivityRemarkById(String id){
        //创建响应封装对象
        ReturnObject returnObject = new ReturnObject();
        try{
            //调用service层的方法，删除指定的市场活动评价信息
            int count = activityRemarkService.removeActivityRemarkById(id);
            if (count > 0){
                //删除成功
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            }else{
                //删除失败
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统繁忙，请稍后重试。。。。");
            }
        }catch(Exception e){
            //删除失败
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙，请稍后重试。。。。");
            e.printStackTrace();
        }
        return returnObject;
    }

    @ResponseBody
    @RequestMapping("/workbench/activity/modifyActivityRemarkById.do")
    public Object modifyActivityRemarkById(String id,String noteContent,HttpSession session){
        //获取当前登录用户对象
        User user = (User)session.getAttribute(Constant.SESSION_USER);
        //封装请求参数
        ActivityRemark activityRemark = new ActivityRemark();
        activityRemark.setId(id);
        activityRemark.setNoteContent(noteContent);
        activityRemark.setEditTime(DateUtils.formatDateTima(new Date()));
        activityRemark.setEditBy(user.getId());
        activityRemark.setEditFlag(Constant.REMARK_EDIT_FLAG_YES_EDITED);

        //创建响应对象
        ReturnObject returnObject = new ReturnObject();
        try{
            //调用service层的方法，更新市场活动评价信息
            int count = activityRemarkService.modifyActivityRemarkById(activityRemark);
            if (count > 0 ){
                //更新成功
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setRetData(activityRemark);
            }else{
                //更新失败
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统繁忙，请稍后重试。。。。");
            }
        }catch (Exception e){
            //更新失败
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙，请稍后重试。。。。");
            e.printStackTrace();
        }

        return returnObject;
    }
}
