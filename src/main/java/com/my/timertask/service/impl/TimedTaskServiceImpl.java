package com.my.timertask.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.timertask.dao.TimedTaskDaoI;
import com.my.timertask.entity.po.TimedTaskPO;
import com.my.timertask.service.inter.TimedTaskServiceI;

@Service
public class TimedTaskServiceImpl implements TimedTaskServiceI {
    private static Logger logger = LoggerFactory.getLogger(TimedTaskServiceImpl.class);
    @Autowired
    private TimedTaskDaoI timedTaskDao;
    @Override
    public int addTimedTask(TimedTaskPO po) {
        int key = -1;
        key = timedTaskDao.addOneTimedTask(po);
        return key;
    }
    @Override
    public List<TimedTaskPO> getTimedTaskList(TimedTaskPO po) {
        List<TimedTaskPO> daTaskPos = timedTaskDao.listTimedTask(po);
        return daTaskPos;
    }

}
