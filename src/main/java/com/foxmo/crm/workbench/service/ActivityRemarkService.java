package com.foxmo.crm.workbench.service;

import com.foxmo.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkService {
    //根据市场活动id查询指定的市场活动评价
    List<ActivityRemark> queryActivityRemarkByActivityId(String id);
    //新增时常常活动评价
    int saveCreateActivityRemark(ActivityRemark activityRemark);
    //根据id删除指定市场活动信息
    int removeActivityRemarkById(String id);
    //根据id修改市场活动评价信息
    int modifyActivityRemarkById(ActivityRemark activityRemark);
}
