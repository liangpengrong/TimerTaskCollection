package com.my.timertask.util.email;

public enum EmailTypeEnum {
    普通类型邮件("1000")
    , 网页类型邮件("1001")
    , 带附件的邮件("1002");
    
    private String key;
    private EmailTypeEnum(String key) {
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
    public static EmailTypeEnum getEnumByKey(String key) {
        EmailTypeEnum enum1 = null;
        EmailTypeEnum[] all = EmailTypeEnum.class.getEnumConstants();
        for(EmailTypeEnum ee : all) {
            if(ee.getKey().equals(key)) {
                enum1 = ee;
                break;
            }
        }
        return enum1;
    }
}
