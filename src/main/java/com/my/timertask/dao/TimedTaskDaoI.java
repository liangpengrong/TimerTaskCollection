package com.my.timertask.dao;


import java.util.List;

import com.my.timertask.po.TimedTaskPo;

/** <blockquote>
* 
* @author [liangpr]
* @date [2019-01-21 14:17:25]
*/
public interface TimedTaskDaoI {
    
    /** <blockquote>
    * 插入一条数据并返回主键 
    * @param po
    * @return
    */  
    int addOneTimedTask(TimedTaskPo po);
    
    /** <blockquote>
    * 查询数据并返回list集合 
    * @param po
    * @return
    */  
    List<TimedTaskPo> listTimedTask(TimedTaskPo po);

    /** <blockquote>
    * 更新一条数据 不更新null
    * @param po
    * @return
    */  
    int updateOneTimedTask(TimedTaskPo po);
    /** <blockquote>
     * 更新一条数据  更新null
     * @param po
     * @return
     */  
    int updateOneTimedTaskHaveNull(TimedTaskPo po);
}
