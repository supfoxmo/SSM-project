package com.foxmo.crm.workbench.service;

import com.foxmo.crm.workbench.domain.Activity;
import com.foxmo.crm.workbench.domain.Clue;

import java.util.List;
import java.util.Map;

public interface ClueService {
    //保存新建线索信息
    int saveCreateClue(Clue clue);
    //根据id查询线索的详细信息
    Clue queryClueForDetailById(String id);
    //根据条件分页查询线索信息
    List<Clue> queryClueByConditionForPage(Map<String,Object> map);
    //根据条件查询指定线索的总条数
    int queryConutOfClueByCondition(Map<String,Object> map);
    //根据ids数组删除指定的线索信息
    int removeClueByIds(String[] ids);
    //根据id查询指定线索信息
    Clue queryClueById(String id);
    //根据Id修改指定线索详细信息
    int editClueForDetailById(Clue clue);
}
