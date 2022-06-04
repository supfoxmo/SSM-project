package com.foxmo.crm.workbench.service.impl;

import com.foxmo.crm.workbench.domain.ActivityRemark;
import com.foxmo.crm.workbench.mapper.ActivityRemarkMapper;
import com.foxmo.crm.workbench.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ActivityRemarkServiceImpl implements ActivityRemarkService {
    @Autowired
    private ActivityRemarkMapper activityRemarkMapper;

    @Override
    public List<ActivityRemark> queryActivityRemarkByActivityId(String id) {
        return activityRemarkMapper.selectActivityRemarkByActivityId(id);
    }
}
