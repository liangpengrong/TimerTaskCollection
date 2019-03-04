package com.my.timertask.dao;


import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.my.timertask.entity.po.TimedTaskPO;

/** <blockquote>
* 
* @author [liangpr]
* @date [2019-01-21 14:17:25]
*/
public interface TimedTaskDaoI {
    
    /** <blockquote>
    * 插入一条数据 可以获得插入条数 主键已经更新到传入的实体类中
    * @param po
    * @return
    */  
    int addOneTimedTask(TimedTaskPO po);
    
    /** <blockquote>
    * 查询数据并返回list集合 
    * @param po
    * @return
    */  
    // @Cacheable(cacheNames="listTimedTask", key="#root.caches[0].name" )
    List<TimedTaskPO> listTimedTask(TimedTaskPO po);

    /** <blockquote>
    * 更新一条数据 不更新null
    * @param po
    * @return
    */  
    int updateOneTimedTask(TimedTaskPO po);
    /** <blockquote>
     * 更新一条数据  更新null
     * @param po
     * @return
     */  
    int updateOneTimedTaskHaveNull(TimedTaskPO po);
}
