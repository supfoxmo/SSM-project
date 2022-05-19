package com.foxmo.crm;

import com.foxmo.crm.settings.domain.User;
import com.foxmo.crm.settings.service.UserService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class DatabaseTest {
    @Test
    public void test1(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = applicationContext.getBean("userService", UserService.class);
        Map<String, Object> map = new HashMap<>();
        map.put("loginAct","ls");
        map.put("loginPwd","yf123");
        User user = userService.queryUserByLoginActAndPwd(map);
        System.out.println(user);

    }
}
