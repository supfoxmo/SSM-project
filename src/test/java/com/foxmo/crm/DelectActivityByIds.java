package com.foxmo.crm;

import com.foxmo.crm.workbench.service.ActivityService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DelectActivityByIds {
    @Test
    public void test1(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        ActivityService activityService = applicationContext.getBean("activityService", ActivityService.class);
        String[] ids = {"08540b68740647a3964ff3e063cedeb9"};
        int i = activityService.removeActivityByIds(ids);
        System.out.println(i);

    }
}
