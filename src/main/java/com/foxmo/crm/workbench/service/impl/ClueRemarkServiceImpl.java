package com.foxmo.crm.workbench.service.impl;

import com.foxmo.crm.workbench.domain.ClueRemark;
import com.foxmo.crm.workbench.mapper.ClueRemarkMapper;
import com.foxmo.crm.workbench.service.ClueRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClueRemarkServiceImpl implements ClueRemarkService {
    //映入Mapper层对象
    @Autowired
    public ClueRemarkMapper clueRemarkMapper;

    @Override
    public List<ClueRemark> queryClueRemarkForDetailByClueId(String clueId) {
        return clueRemarkMapper.selectClueRemarkForDetailByClueId(clueId);
    }
}
