package com.foxmo.crm.workbench.service;

import com.foxmo.crm.workbench.domain.Clue;

public interface ClueService {
    //保存新建线索信息
    int saveCreateClue(Clue clue);
    //根据id查询线索的详细信息
    Clue queryClueForDetailById(String id);
}
