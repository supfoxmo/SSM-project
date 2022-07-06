package com.foxmo.crm.workbench.service;

import com.foxmo.crm.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkService {
    /**
     * 根据clueId查询线索备注的所有信息
     * @param clueId
     * @return
     */
    List<ClueRemark> queryClueRemarkForDetailByClueId(String clueId);

    /**
     * 保存新建线索备注信息
     * @param clueRemark
     * @return
     */
    int saveCreateClueRemark(ClueRemark clueRemark);
}
