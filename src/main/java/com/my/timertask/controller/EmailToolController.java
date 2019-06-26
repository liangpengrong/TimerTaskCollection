package com.my.timertask.controller;

import com.alibaba.fastjson.JSONObject;
import com.my.timertask.annotation.QuartzMethod;
import org.quartz.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
@RequestMapping("emailToolController")
public class EmailToolController {
    private static Logger logger = LoggerFactory.getLogger(EmailToolController.class);
    @RequestMapping(value="sendSimpleMail",method= {RequestMethod.POST,RequestMethod.GET},produces="application/json;charset=utf-8")
    @ResponseBody
    public JSONObject sendSimpleMail() {
        JSONObject json = new JSONObject();
        return json;
    }
    @QuartzMethod(cron = "0/5 * * * * ?")
    public void aa(){
        System.out.println("====");
    }
}