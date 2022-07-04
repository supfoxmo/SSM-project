package com.foxmo.crm.workbench.service;

import com.foxmo.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationService {
    //批量新建线索市场活动关系
    int saveCreateClueActivityRelationByList(List<ClueActivityRelation> clueActivityRelationList);
}
