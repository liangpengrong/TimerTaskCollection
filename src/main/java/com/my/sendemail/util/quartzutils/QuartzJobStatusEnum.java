package com.my.sendemail.util.quartzutils;

/** <blockquote>
* 定时任务状态 
* @author [liangpr]
* @date [2019-01-21 16:20:58]
*/
public enum QuartzJobStatusEnum {
    可用(0) 
    ,不可用(1);
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
}
