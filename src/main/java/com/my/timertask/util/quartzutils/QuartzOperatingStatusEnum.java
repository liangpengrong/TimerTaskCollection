package com.my.timertask.util.quartzutils;

/** <blockquote>
* 运行状态 
* @author [liangpr]
* @date [2019-01-21 16:20:47]
*/
public enum QuartzOperatingStatusEnum {
    正在运行(1)
    , 暂停运行(2)
    , 停止运行(0)
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
    /** <blockquote>
    * 通过key查找对应的枚举 
    * @return
    */  
    public static QuartzOperatingStatusEnum getEnumByKey(Integer key) {
        QuartzOperatingStatusEnum enum1 = null;
        QuartzOperatingStatusEnum[] all = QuartzOperatingStatusEnum.class.getEnumConstants();
        for(QuartzOperatingStatusEnum ee : all) {
            if(ee.getKey().equals(key)) {
                enum1 = ee;
                break;
            }
        }
        return enum1;
    }
}
