package com.my.sendemail.util.quartzutils;

/** <blockquote>
* 运行状态 
* @author [liangpr]
* @date [2019-01-21 16:20:47]
*/
public enum QuartzOperatingStatusEnum {
    立即运行(0)
    , 正在运行(1)
    , 暂停运行(2)
    , 停止运行(3)
    ;
    private int key;
    private QuartzOperatingStatusEnum(Integer key) {
        this.key = key;
    }
    /**  
    * 获取枚举对应的key
    */
    public Integer getKey() {
        return key;
    }
}
