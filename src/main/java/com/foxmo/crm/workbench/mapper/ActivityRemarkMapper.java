package com.foxmo.crm.workbench.mapper;

import com.foxmo.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Sat Jun 04 11:33:03 CST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Sat Jun 04 11:33:03 CST 2022
     */
    int insert(ActivityRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Sat Jun 04 11:33:03 CST 2022
     */
    int insertSelective(ActivityRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Sat Jun 04 11:33:03 CST 2022
     */
    ActivityRemark selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Sat Jun 04 11:33:03 CST 2022
     */
    int updateByPrimaryKeySelective(ActivityRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Sat Jun 04 11:33:03 CST 2022
     */
    int updateByPrimaryKey(ActivityRemark record);

    /**
     * 根据市场活动id查询指定的市场活动评价信息
     * @param activityId
     * @return
     */
    List<ActivityRemark> selectActivityRemarkByActivityId(String activityId);

    /**
     * 新增市场活动评价
     * @param activityRemark
     * @return
     */
    int insertActivityRemark(ActivityRemark activityRemark);

    /**
     * 根据id删除指定市场活动评价信息
     * @param id
     * @return
     */
    int deleteActivityRemarkById(String id);

    /**
     * 根据id修改市场活动评价信息
     * @param activityRemark
     * @return
     */
    int updateActivityRemarkById(ActivityRemark activityRemark);
}