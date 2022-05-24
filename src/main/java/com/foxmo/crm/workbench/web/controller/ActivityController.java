package com.foxmo.crm.workbench.web.controller;

import com.foxmo.crm.commons.constant.Constant;
import com.foxmo.crm.commons.domain.ReturnObject;
import com.foxmo.crm.commons.utils.DateUtils;
import com.foxmo.crm.commons.utils.UUIDUtils;
import com.foxmo.crm.settings.domain.User;
import com.foxmo.crm.settings.service.UserService;
import com.foxmo.crm.workbench.domain.Activity;
import com.foxmo.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public Object removeActivityByIds(String[] ids){
        ReturnObject retObj = new ReturnObject();
        try{
            int i = activityService.removeActivityByIds(ids);
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
}
