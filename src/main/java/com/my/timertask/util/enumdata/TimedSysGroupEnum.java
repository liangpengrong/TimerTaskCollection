package com.my.timertask.util.enumdata;

public enum TimedSysGroupEnum {
    发送邮件("1000");
    
    private String key;
    private TimedSysGroupEnum(String key) {
        this.key = key;
    }
    /**  
    * 获取枚举对应的key
    */
    public String getKey() {
        return key;
    }
    /** <blockquote>
    * 通过key查找对应的枚举 
    * @return
    */  
    public static TimedSysGroupEnum getEnumByKey(String key) {
        TimedSysGroupEnum enum1 = null;
        TimedSysGroupEnum[] all = TimedSysGroupEnum.class.getEnumConstants();
        for(TimedSysGroupEnum ee : all) {
            if(ee.getKey().equals(key)) {
                enum1 = ee;
                break;
            }
        }
        return enum1;
    }
}
