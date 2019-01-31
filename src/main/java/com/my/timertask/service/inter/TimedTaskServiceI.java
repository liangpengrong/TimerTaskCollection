package com.my.timertask.service.inter;

import java.util.List;

import com.my.timertask.po.TimedTaskPo;

public interface TimedTaskServiceI {
    int addTimedTask(TimedTaskPo po);
    List<TimedTaskPo> getTimedTaskList(TimedTaskPo po);
}
