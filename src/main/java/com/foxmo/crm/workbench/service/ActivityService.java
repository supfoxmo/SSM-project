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
}
