package com.my.timertask.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.timertask.dao.TimedSendEmailDaoI;
import com.my.timertask.dao.TimedSysGroupDaoI;
import com.my.timertask.dao.TimedTaskDaoI;
import com.my.timertask.entity.po.TimedTaskPO;
import com.my.timertask.service.inter.ScheduleJobServiceI;
import com.my.timertask.util.quartzutils.QuartzJobServiceUtils;

/** <blockquote>
* 定时任务调动service
* @author [liangpr]
* @date [2019-01-17 16:49:02]
*/
@Service
public class ScheduleJobServiceImpl implements ScheduleJobServiceI{
    
    @Autowired
    private TimedTaskDaoI timedTaskDao;
    @Autowired
    private TimedSysGroupDaoI timedSysGroupDaoI;
    @Autowired
    private TimedSendEmailDaoI timedSendEmainDaoI;
    @Override
    public boolean[] StartTimedSendEmail() {
        boolean[] retBools = new boolean[0];
        return retBools;
    }
    
}
