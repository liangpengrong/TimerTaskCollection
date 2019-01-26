package com.my.sendemail.service.inter;

import java.util.List;

import com.my.sendemail.po.TimedTaskPo;

public interface TimedTaskServiceI {
    int addTimedTask(TimedTaskPo po);
    List<TimedTaskPo> getTimedTaskList(TimedTaskPo po);
}
