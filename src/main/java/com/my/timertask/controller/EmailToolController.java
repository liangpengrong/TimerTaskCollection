package com.my.timertask.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.my.timertask.config.QuartzConfug;
import com.my.timertask.entity.po.TimedTaskPO;
import com.my.timertask.entity.vo.TimedSendEmailVo;
import com.my.timertask.service.inter.TimedSendEmailServiceI;
import com.my.timertask.util.enumdata.EmailTypeEnum;
import com.my.timertask.util.quartzutils.QuartzJobServiceUtils;

@Controller
@RequestMapping("emailToolController")
public class EmailToolController {
    private static Logger logger = LoggerFactory.getLogger(EmailToolController.class);
    @Resource(name="timedSendEmailServiceImpl")
    private TimedSendEmailServiceI emailServiceI; 
    @RequestMapping(value="sendSimpleMail",method= {RequestMethod.POST,RequestMethod.GET},produces="application/json;charset=utf-8")
    @ResponseBody
    public JSONObject sendSimpleMail(){
        JSONObject json = new JSONObject();
        TimedSendEmailVo vo = null;
        String code = "1";
        String mess = "成功";
        try {
            /*vo = new TimedSendEmailVo();
            vo.setTimdeName("啊啊啊啊");
            vo.setHost("smtp.qq.com");
            vo.setUsername("575989285@qq.com");
            vo.setWarrantCode("ttgsycqqbiisbdjf");
            vo.setPort("465");
            vo.setProtocol("smtp");
            vo.setToUser("1985928228@qq.com");
            vo.seteTitle("王攀你干嘛呢");
            vo.seteContent("呱呱呱呱呱呱呱呱呱呱呱呱呱呱呱古古怪怪");
            vo.setCron("0/15 * * * * ?");
            vo.setGroup("我的分组");
            vo.setRun(true);
            vo.setSelfRun(true);
            vo.setAnnex("C:\\Users\\PC\\桌面\\linxu命令.txt");
            vo.setStartDate("2019-02-01 20:00:00");
            vo.setStopDate("2019-02-01 20:10:00");
            boolean bool = emailServiceI.addTimedSendSimpleEmail(vo, EmailTypeEnum.带附件的邮件);
            if(!bool) {
                code = "0";
                mess = "失败";
            }*/
            List<TimedTaskPO> jobPersistences = QuartzJobServiceUtils.getJobPersistences();
            TimedTaskPO timedTaskPO = jobPersistences.get(0);
            timedTaskPO.setBeanPath(timedTaskPO.getBeanPath().replace("\r\n\r\n", "").replace("\n\n", "").replace("\r\r", ""));
            jobPersistences.set(0, timedTaskPO);
            System.out.println(timedTaskPO.getBeanPath());
            json.put("data", jobPersistences);
            json.put("换行符", System.lineSeparator());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            code = "0";
            mess = "失败";
        }
        
        json.put("code", code);
        json.put("mess", mess);
        return json;
    }
}