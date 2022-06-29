package com.foxmo.crm.workbench.web.controller;

import com.foxmo.crm.commons.constant.Constant;
import com.foxmo.crm.commons.domain.ReturnObject;
import com.foxmo.crm.commons.utils.DateUtils;
import com.foxmo.crm.commons.utils.UUIDUtils;
import com.foxmo.crm.settings.domain.DicValue;
import com.foxmo.crm.settings.domain.User;
import com.foxmo.crm.settings.service.DicValueService;
import com.foxmo.crm.settings.service.UserService;
import com.foxmo.crm.workbench.domain.Activity;
import com.foxmo.crm.workbench.domain.Clue;
import com.foxmo.crm.workbench.domain.ClueRemark;
import com.foxmo.crm.workbench.service.ActivityService;
import com.foxmo.crm.workbench.service.ClueRemarkService;
import com.foxmo.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class ClueController {
    @Autowired
    public UserService userService;
    @Autowired
    public DicValueService dicValueService;
    @Autowired
    public ClueService clueService;
    @Autowired
    public ClueRemarkService clueRemarkService;
    @Autowired
    public ActivityService activityService;

    @RequestMapping("/workbench/clue/index.do")
    public String clueIndex(HttpServletRequest request){
        //调用service层方法，查询所有用户信息
        List<User> userList = userService.queryAllUser();
        //调用service层方法，查询指定是下拉列表数据集合
        List<DicValue> appellationList = dicValueService.queryDicValueByTypeCode("appellation");
        List<DicValue> clueStateList = dicValueService.queryDicValueByTypeCode("clueState");
        List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode("source");
        //将查询到的信息保存到request域中
        request.setAttribute("userList",userList);
        request.setAttribute("appellationList",appellationList);
        request.setAttribute("clueStateList",clueStateList);
        request.setAttribute("sourceList",sourceList);
        //跳转到线索页面
        return "workbench/clue/index";
    }

    @ResponseBody
    @RequestMapping("/workbench/clue/saveCreateClue.do")
    public Object saveCreateClue(Clue clue, HttpSession session){
        //获取当前登录用户
        User user = (User)session.getAttribute(Constant.SESSION_USER);
        //封装数据
        clue.setId(UUIDUtils.getUUID());
        clue.setCreateBy(user.getId());
        clue.setCreateTime(DateUtils.formatDateTima(new Date()));
        //新建响应封装类
        ReturnObject returnObject = new ReturnObject();
        try{
            //调用service层的方法，保存新增线索信息
            int count = clueService.saveCreateClue(clue);
            if (count > 0){
                //新增成功
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            }else{
                //新增失败
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统繁忙，请稍后重试。。。。");
            }
        }catch(Exception e){
            //新增失败
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙，请稍后重试。。。。");
            e.printStackTrace();
        }
        return returnObject;
    }

    @RequestMapping("/workbench/clue/clueDetail.do")
    public String clueDetail(String id,HttpServletRequest request){
        //调用service层的方法，查询数据
        Clue clue = clueService.queryClueForDetailById(id);
        List<ClueRemark> remarkList = clueRemarkService.queryClueRemarkForDetailByClueId(id);
        List<Activity> activityList = activityService.queryActivityForDetailByClueId(id);
        //将查询到的数据存放到request域中
        request.setAttribute("clue",clue);
        request.setAttribute("remarkList",remarkList);
        request.setAttribute("activityList",activityList);
        //跳转到线索详情页面
        return "workbench/clue/detail";
    }
}
