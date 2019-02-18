package com.my.timertask.service.inter;

import java.util.List;

import com.my.timertask.entity.po.TimedTaskPO;

public interface TimedTaskServiceI {
    int addTimedTask(TimedTaskPO po);
    List<TimedTaskPO> getTimedTaskList(TimedTaskPO po);
}
