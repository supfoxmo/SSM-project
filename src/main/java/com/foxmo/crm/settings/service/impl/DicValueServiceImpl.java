package com.foxmo.crm.settings.service.impl;

import com.foxmo.crm.settings.domain.DicValue;
import com.foxmo.crm.settings.mapper.DicValueMapper;
import com.foxmo.crm.settings.service.DicValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DicValueServiceImpl implements DicValueService {
    @Autowired
    public DicValueMapper dicValueMapper;
    @Override
    public List<DicValue> queryDicValueByTypeCode(String typeCode) {
        return dicValueMapper.selectDicValueByTypeCode(typeCode);
    }
}
