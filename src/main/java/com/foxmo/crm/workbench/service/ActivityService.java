package com.foxmo.crm.workbench.service;

import com.foxmo.crm.workbench.domain.Activity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
    //查询所有市场活动信息
    List<Activity> queryAllActivity();
    //根据ids数组查询指定市场活动的信息
    List<Activity> queryActivityByIds(String[] ids);
    //根据activityList集合新增市场活动
    int saveActivityByList(List<Activity> activityList);
    //根据id查询指定市场详细信息
    Activity queryActivityForDetailById(String id);
    //根据clueId查询所有与线索相关联的市场活动信息
    List<Activity> queryActivityForDetailByClueId(String clueId);
    //根据activityName模糊查询市场活动信息，并排除已经关联clueId的市场活动
    List<Activity> queryActivityForDetailByNameClueId(HashMap<String,Object> map);
    //根据clueId查询与之关联的所有市场活动的详细信息
    List<Activity> queryActivityForDetailByRelationClueID(String[] activityId);
    //根据Name和ClueId模糊查询指定线索已关联的市场活动信息
    List<Activity> queryAvtivityForConvertByNameClueId(Map<String,Object> map);
}
