package com.foxmo.crm.settings.web.controller;

import com.foxmo.crm.commons.constant.Constant;
import com.foxmo.crm.commons.domain.ReturnObject;
import com.foxmo.crm.commons.utils.DateUtils;
import com.foxmo.crm.settings.domain.User;
import com.foxmo.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * url要和controller方法处理完请求之后，响应信息返回的页面的资源目录保持一致
     */
    @RequestMapping("/settings/qx/user/toLogin.do")
    public String toLogin(){
        //请求转发到登录页面
        return "settings/qx/user/login";
    }

    /**
     * 用户登录
     * @param loginAct
     * @param loginPwd
     * @param isRemPwd
     * @param request
     * @param response
     * @param session
     * @return
     */
    @RequestMapping("/settings/qx/user/login.do")
    public @ResponseBody Object login(String loginAct, String loginPwd, String isRemPwd, HttpServletRequest request, HttpServletResponse response, HttpSession session){
        //封装参数
        Map<String,Object> map=new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        //调用service层方法，查询用户
        User user=userService.queryUserByLoginActAndPwd(map);
        //创建返回值封装对象
        ReturnObject retObject = new ReturnObject();
        //判断用户是否合法登录
        if (user == null){
            //登录失败，用户名或密码错误
            retObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            retObject.setMessage("用户名或者密码错误");
        }else { //进一步判断用户是否合法登录
            //获取当前时间并转化为指定是字符串格式
            String nowTime = DateUtils.formatDateTima(new Date());
            if (nowTime.compareTo(user.getExpireTime()) > 0){
                //登录失败，账号已过期
                retObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                retObject.setMessage("账号已过期");
            }else if ("0".equals(user.getLockState())){
                //登录失败，账号状态已锁定
                retObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                retObject.setMessage("账号状态已锁定");
            }else if (!(user.getAllowIps().contains(request.getRemoteAddr()) || user.getAllowIps().contains("0.0.0.0"))){
                //若后台数据 allow_ips 中包含0.0.0.0,则表示可以在任意IP下登录
                //登录失败，用户没有在指定IP下登录，IP受限
                retObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                retObject.setMessage("IP受限");
            }else{
                //登录成功
                retObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                retObject.setMessage("登录成功");
                //将user对象信息保存到session作用域中
                session.setAttribute(Constant.SESSION_USER,user);
                if ("true".equals(isRemPwd)){ //十天免登录
                    //将user对象信息保存到cookie作用域中
                    Cookie cookie1 = new Cookie("loginAct",user.getLoginAct());
                    //设置cookie最大生命周期
                    cookie1.setMaxAge(10*24*60*60);
                    //将cookie返回前端页面
                    response.addCookie(cookie1);
                    //将user对象信息保存到cookie作用域中
                    Cookie cookie2 = new Cookie("loginPwd",user.getLoginPwd());
                    //设置cookie最大生命周期
                    cookie2.setMaxAge(10*24*60*60);
                    //将cookie返回前端页面
                    response.addCookie(cookie2);
                }else{ //未选中十天免登录
                    // 删除cookie中的用户名和密码
                    Cookie cookie1 = new Cookie("loginAct", "1");
                    cookie1.setMaxAge(0);
                    //将cookie返回前端页面
                    response.addCookie(cookie1);
                    //将user对象信息保存到cookie作用域中
                    Cookie cookie2 = new Cookie("loginPwd","1");
                    //设置cookie最大生命周期
                    cookie2.setMaxAge(0);
                    //将cookie返回前端页面
                    response.addCookie(cookie2);
                }
            }
        }
        return retObject;
    }

    /**
     * 安全退出功能
     * @param response
     * @param session
     * @return
     */
    @RequestMapping("/settings/qx/user/logout.do")
    public String logout(HttpServletResponse response,HttpSession session){
        //删除Cookie中的数据
        Cookie cookie1 = new Cookie("loginAct", "0");
        cookie1.setMaxAge(0);
        response.addCookie(cookie1);
        Cookie cookie2 = new Cookie("loginPwd", "0");
        cookie2.setMaxAge(0);
        response.addCookie(cookie2);
        //销毁Session中数据
        session.invalidate();
        //重定向到登录页面
        return "redirect:/settings/qx/user/toLogin.do";
    }


}
