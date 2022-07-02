package com.foxmo.crm.workbench.service.impl;

import com.foxmo.crm.workbench.domain.Activity;
import com.foxmo.crm.workbench.domain.Clue;
import com.foxmo.crm.workbench.mapper.ClueMapper;
import com.foxmo.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ClueServiceImpl implements ClueService {
    @Autowired
    public ClueMapper clueMapper;

    @Override
    public int saveCreateClue(Clue clue) {
        return clueMapper.insertCreateClue(clue);
    }

    @Override
    public Clue queryClueForDetailById(String id) {
        return clueMapper.selectClueForDetailById(id);
    }

    @Override
    public List<Clue> queryClueByConditionForPage(Map<String, Object> map) {
        return clueMapper.selectClueByConditionForPage(map);
    }

    @Override
    public int queryConutOfClueByCondition(Map<String, Object> map) {
        return clueMapper.selectConutOfClueByCondition(map);
    }

    @Override
    public int removeClueByIds(String[] ids) {
        return clueMapper.deleteClueByIds(ids);
    }

    @Override
    public Clue queryClueById(String id) {
        return clueMapper.selectClueById(id);
    }

    @Override
    public int editClueForDetailById(Clue clue) {
        return clueMapper.updateClueForDetailById(clue);
    }
}
