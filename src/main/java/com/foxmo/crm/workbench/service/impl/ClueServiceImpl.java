package com.foxmo.crm.workbench.service.impl;

import com.foxmo.crm.workbench.domain.Clue;
import com.foxmo.crm.workbench.mapper.ClueMapper;
import com.foxmo.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClueServiceImpl implements ClueService {
    @Autowired
    public ClueMapper clueMapper;

    @Override
    public int saveCreateClue(Clue clue) {
        return clueMapper.insertCreateClue(clue);
    }
}
