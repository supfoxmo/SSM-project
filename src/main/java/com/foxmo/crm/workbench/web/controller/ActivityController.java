package com.foxmo.crm.workbench.web.controller;

import com.foxmo.crm.settings.domain.User;
import com.foxmo.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ActivityController {
    @Autowired
    private UserService userService;
    @RequestMapping("/workbench/activity/index.do")
    public String index(HttpServletRequest request){
        //调用service层方法，查询所有用户信息
        List<User> userList = userService.queryAllUser();
        //将查询到的信息保存到request域中
        request.setAttribute("userList",userList);
        //请求转发
        return "workbench/activity/index";
    }
}
