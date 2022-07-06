package com.foxmo.crm.workbench.service.impl;

import com.foxmo.crm.workbench.domain.ClueActivityRelation;
import com.foxmo.crm.workbench.mapper.ClueActivityRelationMapper;
import com.foxmo.crm.workbench.service.ClueActivityRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ClueActivityRelationServiceImpl implements ClueActivityRelationService {
    @Autowired
    public ClueActivityRelationMapper clueActivityRelationMapper;

    @Override
    public int saveCreateClueActivityRelationByList(List<ClueActivityRelation> clueActivityRelationList) {
        return clueActivityRelationMapper.insertCreateClueActivityRelationByList(clueActivityRelationList);
    }

    @Override
    public int removeClueActivityRelationByClueAndActivityIds(Map<String, Object> map) {
        return clueActivityRelationMapper.deleteClueActivityRelationByClueAndActivityIds(map);
    }
}
