package com.foxmo.crm.workbench.service;

import com.foxmo.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkService {
    //根据市场活动id查询指定的市场活动评价
    List<ActivityRemark> queryActivityRemarkByActivityId(String id);
}
