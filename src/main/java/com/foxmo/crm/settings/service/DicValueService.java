package com.foxmo.crm.settings.service;

import com.foxmo.crm.settings.domain.DicValue;

import java.util.List;

public interface DicValueService {
    //根据TypeCodechaxun指定的DicValue
    List<DicValue> queryDicValueByTypeCode(String typeCode);
}
