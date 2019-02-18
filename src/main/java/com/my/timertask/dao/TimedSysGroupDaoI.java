package com.my.timertask.dao;

import java.util.List;

import com.my.timertask.entity.po.TimedSysGroupPO;


public interface TimedSysGroupDaoI {
     /** <blockquote>
     * 查询数据并返回list集合 
     * @param po
     * @return
     */  
     List<TimedSysGroupPO> listTimedSysGroup(TimedSysGroupPO po);


}
