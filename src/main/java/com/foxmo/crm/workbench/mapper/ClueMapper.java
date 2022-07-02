package com.foxmo.crm.workbench.mapper;

import com.foxmo.crm.workbench.domain.Activity;
import com.foxmo.crm.workbench.domain.Clue;

import java.util.List;
import java.util.Map;

public interface ClueMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue
     *
     * @mbggenerated Tue Jun 07 15:11:28 CST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue
     *
     * @mbggenerated Tue Jun 07 15:11:28 CST 2022
     */
    int insert(Clue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue
     *
     * @mbggenerated Tue Jun 07 15:11:28 CST 2022
     */
    int insertSelective(Clue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue
     *
     * @mbggenerated Tue Jun 07 15:11:28 CST 2022
     */
    Clue selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue
     *
     * @mbggenerated Tue Jun 07 15:11:28 CST 2022
     */
    int updateByPrimaryKeySelective(Clue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue
     *
     * @mbggenerated Tue Jun 07 15:11:28 CST 2022
     */
    int updateByPrimaryKey(Clue record);

    /**
     * 查询所有的线索信息
     * @return
     */
    List<Clue> selectAllClue();

    /**
     * 保存新建线索信息
     * @param clue
     * @return
     */
    int insertCreateClue(Clue clue);

    /**
     * 查询线索的详细信息
     * @param id
     * @return
     */
    Clue selectClueForDetailById(String id);

    /**
     * 根据条件分页查询市场活动信息
     * @param map
     * @return
     */
    List<Clue> selectClueByConditionForPage(Map<String,Object> map);

    /**
     * 根据条件查询指定市场活动的总条数
     * @param map
     * @return
     */
    int selectConutOfClueByCondition(Map<String,Object> map);

    /**
     * 根据ID数组删除指定的线索信息
     * @param ids
     * @return
     */
    int deleteClueByIds(String[] ids);

    /**
     * 根据id查询指定线索信息
     * @param id
     * @return
     */
    Clue selectClueById(String id);

    /**
     * 根据Id修改指定线索信息
     * @param clue
     * @return
     */
    int updateClueForDetailById(Clue clue);
}