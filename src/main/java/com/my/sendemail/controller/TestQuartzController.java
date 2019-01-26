package com.my.sendemail.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.quartz.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.my.sendemail.po.TimedTaskPo;
import com.my.sendemail.service.inter.ScheduleJobServiceI;
import com.my.sendemail.service.inter.TimedTaskServiceI;
import com.my.sendemail.util.quartzutils.QuartzJobServiceUtils;
import com.my.sendemail.util.quartzutils.QuartzJobStatusEnum;
import com.my.sendemail.util.quartzutils.QuartzOperatingStatusEnum;

@Controller
@RequestMapping("TestQuartzController")
public class TestQuartzController {
    private static Logger logger = LoggerFactory.getLogger(TestQuartzController.class);
    @Resource(name="scheduleJobServiceImpl")
    private ScheduleJobServiceI scheduleJobService;
    @Resource(name="timedTaskServiceImpl")
    private TimedTaskServiceI timedTaskService;
    /** <blockquote>
    * 添加发邮件的定时任务
    * @param method
    * @return
    */  
    @RequestMapping(value="quartzSendEmail",method= {RequestMethod.POST,RequestMethod.GET},produces="application/json;charset=utf-8")
    @ResponseBody
    public String quartzSendEmail(@RequestParam(value="method")String method) {
        JSONObject jsonObject = new JSONObject();
        try {
            TimedTaskPo scheduleJob = QuartzJobServiceUtils.getDefaultTimedTask(EmailToolController.class, method, "0/5 * * * * ?", "这是我自动创建的", null,null);
            // 启动该定时任务
            QuartzJobServiceUtils.addJobsToTable(new TimedTaskPo[] {scheduleJob}, new boolean[] {true}, true);
            jsonObject.put("code", "1");
            jsonObject.put("status", "succses");
        } catch (Exception e) {
            jsonObject.put("code", "0");
            jsonObject.put("status", "error");
            e.printStackTrace();
        }
        return jsonObject.toJSONString();
    }
    /** <blockquote>
    * 暂停发邮件
    * @param method
    * @return
    */  
    @RequestMapping(value="quartzPauseSendEmail",method= {RequestMethod.POST,RequestMethod.GET},produces="application/json;charset=utf-8")
    @ResponseBody
    public String quartzPauseSendEmail(@RequestParam(value="method")String method) {
        JSONObject jsonObject = new JSONObject();
        TimedTaskPo scheduleJob = null;
        try {
            scheduleJob = QuartzJobServiceUtils.getDefaultTimedTask(EmailToolController.class, method, "0/5 * * * * ?", "这是我自动创建的", null,null);
            QuartzJobServiceUtils.pauseJob(scheduleJob);
            jsonObject.put("code", "1");
            jsonObject.put("status", "succses");
        } catch (Exception e) {
            jsonObject.put("code", "0");
            jsonObject.put("status", "error");
            e.printStackTrace();
        }
        
        return jsonObject.toJSONString();
    }
    /** <blockquote>
     * 恢复发邮件
     * @param method
     * @return
     */  
     @RequestMapping(value="quartzResumeSendEmail",method= {RequestMethod.POST,RequestMethod.GET},produces="application/json;charset=utf-8")
     @ResponseBody
     public String quartzResumeSendEmail(@RequestParam(value="method")String method) {
         JSONObject jsonObject = new JSONObject();
         TimedTaskPo scheduleJob = null;
         try {
             scheduleJob = QuartzJobServiceUtils.getDefaultTimedTask(EmailToolController.class, method, "0/5 * * * * ?", "这是我自动创建的", null,null);
             QuartzJobServiceUtils.resumeJob(scheduleJob);
             jsonObject.put("code", "1");
             jsonObject.put("status", "succses");
         } catch (Exception e) {
             jsonObject.put("code", "0");
             jsonObject.put("status", "error");
             e.printStackTrace();
         }
         return jsonObject.toJSONString();
     }
     /** <blockquote>
      * 删除发邮件
      * @param method
      * @return
      */  
      @RequestMapping(value="quartzDeleteSendEmail",method= {RequestMethod.POST,RequestMethod.GET},produces="application/json;charset=utf-8")
      @ResponseBody
      public String quartzDeleteSendEmail(@RequestParam(value="method")String method) {
          JSONObject jsonObject = new JSONObject();
          TimedTaskPo scheduleJob = null;
          try {
              scheduleJob = QuartzJobServiceUtils.getDefaultTimedTask(EmailToolController.class, method, "0/5 * * * * ?", "这是我自动创建的", null,null);
              QuartzJobServiceUtils.deleteJob(scheduleJob);
              jsonObject.put("code", "1");
              jsonObject.put("status", "succses");
          } catch (Exception e) {
              jsonObject.put("code", "0");
              jsonObject.put("status", "error");
              e.printStackTrace();
          }
          return jsonObject.toJSONString();
      }
      /** <blockquote>
       * 运行一次发邮件
       * @param method
       * @return
       */  
       @RequestMapping(value="quartzRunOneSendEmail",method= {RequestMethod.POST,RequestMethod.GET},produces="application/json;charset=utf-8")
       @ResponseBody
       public String quartzRunOneSendEmail(@RequestParam(value="method")String method) {
           JSONObject jsonObject = new JSONObject();
           TimedTaskPo scheduleJob = null;
           try {
               scheduleJob = QuartzJobServiceUtils.getDefaultTimedTask(EmailToolController.class, method, "0/5 * * * * ?", "这是我自动创建的", null,null);
               QuartzJobServiceUtils.runOnce(scheduleJob);
               jsonObject.put("code", "1");
               jsonObject.put("status", "succses");
           } catch (Exception e) {
               jsonObject.put("code", "0");
               jsonObject.put("status", "error");
               e.printStackTrace();
           }
           return jsonObject.toJSONString();
       }
}
