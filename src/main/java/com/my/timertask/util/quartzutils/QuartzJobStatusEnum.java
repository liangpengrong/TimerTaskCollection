package com.my.timertask.util.quartzutils;

import com.my.timertask.util.enumdata.EmailTypeEnum;

/** <blockquote>
* 定时任务状态 
* @author [liangpr]
* @date [2019-01-21 16:20:58]
*/
public enum QuartzJobStatusEnum {
    可用(1) 
    ,不可用(0);
    private int key;
    private QuartzJobStatusEnum(Integer key) {
        this.key = key;
    }
    /**  
    * 获取枚举对应的key
    */
    public Integer getKey() {
        return key;
    }
    /** <blockquote>
    * 通过key查找对应的枚举 
    * @return
    */  
    public static QuartzJobStatusEnum getEnumByKey(Integer key) {
        QuartzJobStatusEnum enum1 = null;
        QuartzJobStatusEnum[] all = QuartzJobStatusEnum.class.getEnumConstants();
        for(QuartzJobStatusEnum ee : all) {
            if(ee.getKey().equals(key)) {
                enum1 = ee;
                break;
            }
        }
        return enum1;
    }
}
