package com.foxmo.crm.workbench.service;

import com.foxmo.crm.workbench.domain.ClueActivityRelation;

import java.util.List;
import java.util.Map;

public interface ClueActivityRelationService {
    //批量新建线索市场活动关系
    int saveCreateClueActivityRelationByList(List<ClueActivityRelation> clueActivityRelationList);
    //根据ClueId和ActivityId删除指定的线索与市场活动关联关系
    int removeClueActivityRelationByClueAndActivityIds(Map<String,Object> map);
}
