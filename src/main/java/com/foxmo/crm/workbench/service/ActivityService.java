package com.foxmo.crm.workbench.service;

import com.foxmo.crm.workbench.domain.Activity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    //新增市场活动
    int saveActivity(Activity activity);
    //根据条件分页查询市场活动信息
    List<Activity> queryActivityByConditionForPage(Map<String,Object> map);
    //根据条件查询指定市场活动的总条数
    int queryConutOfActivityByCondition(Map<String,Object> map);
    //根据ids数组删除所有符合条件的市场活动信息
    int removeActivityByIds(String[] ids);
    //根据id查询指定的市场活动信息
    Activity queryAvtivityById(String id);
    //根据id修改指定市场活动的信息
    int saveEditActivityById(Activity activity);
}
