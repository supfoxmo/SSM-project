package com.foxmo.crm.workbench.web.controller;

import com.foxmo.crm.commons.constant.Constant;
import com.foxmo.crm.commons.domain.ReturnObject;
import com.foxmo.crm.commons.utils.DateUtils;
import com.foxmo.crm.commons.utils.UUIDUtils;
import com.foxmo.crm.settings.domain.DicValue;
import com.foxmo.crm.settings.domain.User;
import com.foxmo.crm.settings.service.DicValueService;
import com.foxmo.crm.settings.service.UserService;
import com.foxmo.crm.workbench.domain.Activity;
import com.foxmo.crm.workbench.domain.Clue;
import com.foxmo.crm.workbench.domain.ClueActivityRelation;
import com.foxmo.crm.workbench.domain.ClueRemark;
import com.foxmo.crm.workbench.service.ActivityService;
import com.foxmo.crm.workbench.service.ClueActivityRelationService;
import com.foxmo.crm.workbench.service.ClueRemarkService;
import com.foxmo.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class ClueController {
    @Autowired
    public UserService userService;
    @Autowired
    public DicValueService dicValueService;
    @Autowired
    public ClueService clueService;
    @Autowired
    public ClueRemarkService clueRemarkService;
    @Autowired
    public ActivityService activityService;
    @Autowired
    public ClueActivityRelationService clueActivityRelationService;

    @RequestMapping("/workbench/clue/index.do")
    public String clueIndex(HttpServletRequest request){
        //调用service层方法，查询所有用户信息
        List<User> userList = userService.queryAllUser();
        //调用service层方法，查询指定是下拉列表数据集合
        List<DicValue> appellationList = dicValueService.queryDicValueByTypeCode("appellation");
        List<DicValue> clueStateList = dicValueService.queryDicValueByTypeCode("clueState");
        List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode("source");
        //将查询到的信息保存到request域中
        request.setAttribute("userList",userList);
        request.setAttribute("appellationList",appellationList);
        request.setAttribute("clueStateList",clueStateList);
        request.setAttribute("sourceList",sourceList);
        //跳转到线索页面
        return "workbench/clue/index";
    }

    @ResponseBody
    @RequestMapping("/workbench/clue/saveCreateClue.do")
    public Object saveCreateClue(Clue clue, HttpSession session){
        //获取当前登录用户
        User user = (User)session.getAttribute(Constant.SESSION_USER);
        //封装数据
        clue.setId(UUIDUtils.getUUID());
        clue.setCreateBy(user.getId());
        clue.setCreateTime(DateUtils.formatDateTima(new Date()));
        //新建响应封装类
        ReturnObject returnObject = new ReturnObject();
        try{
            //调用service层的方法，保存新增线索信息
            int count = clueService.saveCreateClue(clue);
            if (count > 0){
                //新增成功
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setMessage("保存成功");
            }else{
                //新增失败
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统繁忙，请稍后重试。。。。");
            }
        }catch(Exception e){
            //新增失败
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙，请稍后重试。。。。");
            e.printStackTrace();
        }
        return returnObject;
    }


    @ResponseBody
    @RequestMapping("/workbench/clue/queryClueByConditionForPage.do")
    public Object queryClueByConditionForPage(String fullname,String company,String phone,String source,String owner,String mphone,String state,int pageNo,int pageSize){
        //封装请求参数
        HashMap<String, Object> map = new HashMap<>();
        map.put("fullname",fullname);
        map.put("company",company);
        map.put("phone",phone);
        map.put("source",source);
        map.put("owner",owner);
        map.put("mphone",mphone);
        map.put("state",state);
        map.put("beginNo",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);

        //调用service层的方法查询数据
        List<Clue> clueList = clueService.queryClueByConditionForPage(map);
        int totalRows = clueService.queryConutOfClueByCondition(map);

        //封装查询结果
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("clueList",clueList);
        retMap.put("totalRows",totalRows);

        return retMap;
    }

    @ResponseBody
    @RequestMapping("/workbench/clue/removeClueByIds.do")
    public Object removeClueByIds(String[] id){
        //创建响应封装对象
        ReturnObject returnObject = new ReturnObject();
        try{
            //调用service层的方法，删除指定线索信息
            int count = clueService.removeClueByIds(id);
            //判断运行结果
            if (count > 0 ){
                //删除成功
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setMessage("删除成功!!!");
            }else{
                //删除失败
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("当前系统繁忙，请稍后重试。。。。");
            }
        }catch (Exception e){
            //删除失败
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("当前系统繁忙，请稍后重试。。。。");
            e.printStackTrace();
        }
        //返回响应对象
        return returnObject;
    }

    @ResponseBody
    @RequestMapping("/workbench/clue/queryClueById.do")
    public Object queryClueById(String id){
        //调用service层的方法，查询指定线索信息,并返回线索对象
        return clueService.queryClueById(id);
    }

    @ResponseBody
    @RequestMapping("/workbench/clue/editClueForDetailById.do")
    public Object editClueForDetailById(Clue clue,HttpSession session){
        //获取当前登录用户
        User user = (User)session.getAttribute(Constant.SESSION_USER);
        //进一步封装数据
        clue.setEditBy(user.getId());
        clue.setEditTime(DateUtils.formatDateTima(new Date()));
        //创建响应封装对象
        ReturnObject returnObject = new ReturnObject();
        try{
            //调用service层的方法，修改指定线索信息
            int count = clueService.editClueForDetailById(clue);
            //判断线索信息是否修改成功
            if (count > 0){
                //修改成功
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setMessage("修改成功！！！");
            }else {
                //修改失败
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("当前系统繁忙，请稍后重试。。。");
            }
        }catch (Exception e){
            //修改失败
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("当前系统繁忙，请稍后重试。。。");
            e.printStackTrace();
        }
        return returnObject;
    }

    @RequestMapping("/workbench/clue/clueDetail.do")
    public String clueDetail(String id,HttpServletRequest request){
        //调用service层的方法，查询数据
        Clue clue = clueService.queryClueForDetailById(id);
        List<ClueRemark> remarkList = clueRemarkService.queryClueRemarkForDetailByClueId(id);
        List<Activity> activityList = activityService.queryActivityForDetailByClueId(id);
        //将查询到的数据存放到request域中
        request.setAttribute("clue",clue);
        request.setAttribute("remarkList",remarkList);
        request.setAttribute("activityList",activityList);
        //跳转到线索详情页面
        return "workbench/clue/detail";
    }

    @ResponseBody
    @RequestMapping("/workbench/clue/queryActivityForDetailByNameClueId.do")
    public Object queryActivityForDetailByNameClueId(String activityName,String clueId){
        //封装参数
        HashMap<String, Object> map = new HashMap<>();
        map.put("activityName",activityName);
        map.put("clueId",clueId);
        //调用service层的方法，查询市场活动信息
        List<Activity> activityList = activityService.queryActivityForDetailByNameClueId(map);
        //返回响应对象
        return activityList;
    }

    @ResponseBody
    @RequestMapping("/workbench/clue/saveBund.do")
    public Object saveBund(String[] activityId,String clueId){
        //创建容器
        List<ClueActivityRelation> clueActivityRelationList = new ArrayList<>();
        //封装数据
        ClueActivityRelation clueActivityRelation = null;
        for (int i = 0;i < activityId.length;i++){
            clueActivityRelation = new ClueActivityRelation();
            clueActivityRelation.setId(UUIDUtils.getUUID());
            clueActivityRelation.setClueId(clueId);
            clueActivityRelation.setActivityId(activityId[i]);
            clueActivityRelationList.add(i,clueActivityRelation);
        }
        //创建响应封装对象
        ReturnObject returnObject = new ReturnObject();
        List<Activity> activityList = null;
        try{
            //调用service层的方法，新建线索市场活动关系
            int sum = clueActivityRelationService.saveCreateClueActivityRelationByList(clueActivityRelationList);
            //判断批量新建线索市场活动关系是否成功
            if (sum > 0){
                //新建成功,调用service层的方法查询线索与市场活动新建的关联关系
                activityList = activityService.queryActivityForDetailByRelationClueID(activityId);
                //封装响应对象
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setMessage("线索市场活动关联成功！！！");
                returnObject.setRetData(activityList);
            }else{
                //新建失败
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("当前系统繁忙，请稍后重试。。。");
            }
        }catch (Exception e){
            //新建失败
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("当前系统繁忙，请稍后重试。。。");
            e.printStackTrace();
        }
        return returnObject;
    }

    @ResponseBody
    @RequestMapping("/workbench/clue/saveUnbund.do")
    public Object saveUnbund(String activityId,String clueId){
        //封装数据
        HashMap<String, Object> map = new HashMap<>();
        map.put("activityId",activityId);
        map.put("clueId",clueId);
        //创建响应封装对象
        ReturnObject returnObject = new ReturnObject();
        try{
            //调用service层的方法，删除指定的线索与市场活动关联关系
            int count = clueActivityRelationService.removeClueActivityRelationByClueAndActivityIds(map);
            //判断是否删除成功
            if (count > 0){
                //删除成功
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setMessage("关联关系删除成功！！！");
            }else{
                //删除失败
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("当前系统繁忙，请稍后重试。。。");
            }
        }catch (Exception e){
            //删除失败
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("当前系统繁忙，请稍后重试。。。");
            e.printStackTrace();
        }
        return returnObject;
    }

    @ResponseBody
    @RequestMapping("/workbench/clue/saveClueRemark.do")
    public Object saveClueRemark(String remark,String clueId,HttpSession session){
        //获取当前用户
        User user = (User)session.getAttribute(Constant.SESSION_USER);
        //进一步封装参数
        ClueRemark clueRemark = new ClueRemark();
        clueRemark.setId(UUIDUtils.getUUID());
        clueRemark.setNoteContent(remark);
        clueRemark.setCreateBy(user.getId());
        clueRemark.setCreateTime(DateUtils.formatDateTima(new Date()));
        clueRemark.setEditFlag(Constant.REMARK_EDIT_FLAG_NO_EDITED);
        clueRemark.setClueId(clueId);
        //创建响应封装对象
        ReturnObject returnObject = new ReturnObject();
        try{
            //调用service层的方法，保存线索备注信息
            int count = clueRemarkService.saveCreateClueRemark(clueRemark);
            //判断保存线索备注信息是否成功
            if(count > 0){
                //保存成功
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setMessage("保存成功！！！");
                //调用service层的方法，根据clueId查询指定的线索备注信息
                List<ClueRemark> clueRemarkList = clueRemarkService.queryClueRemarkForDetailByClueId(clueId);
                //将查询到的线索备注信息存储到响应封装对象中
                returnObject.setRetData(clueRemarkList);
            }else{
                //保存失败
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("当前系统繁忙，请稍后重试。。。");
            }
        }catch (Exception e){
            //保存失败
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("当前系统繁忙，请稍后重试。。。");
            e.printStackTrace();
        }
        return returnObject;
    }

    @RequestMapping("/workbench/clue/toConvert.do")
    public String toConvert(String clueId,HttpSession session){
        //调用service层的方法查询指定线索的详细信息
        Clue clue = clueService.queryClueForDetailById(clueId);
        //调用service层的方法查询状态信息
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");
        //将查询结果保存到session域中
        session.setAttribute("clue",clue);
        session.setAttribute("stageList",stageList);
        //跳转到转换页面
        return "workbench/clue/convert";
    }

    @ResponseBody
    @RequestMapping("/workbench/clue/queryActivityForConvertByNameClueId.do")
    public Object queryActivityForConvertByNameClueId(String activityName,String clueId){
        //封装参数
        HashMap<String, Object> map = new HashMap<>();
        map.put("activityName",activityName);
        map.put("clueId",clueId);
        //调用service层的方法查询
        List<Activity> activityList = activityService.queryAvtivityForConvertByNameClueId(map);

        //返回响应对象
        return activityList;
    }
}
