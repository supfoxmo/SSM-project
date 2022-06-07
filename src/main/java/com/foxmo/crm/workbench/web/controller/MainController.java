package com.foxmo.crm.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @RequestMapping("/workbench/main/index.do")
    public String index(){
        //跳转到工作台页面
        return "workbench/main/index";
    }

}
