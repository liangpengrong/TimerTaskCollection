package com.my.timertask.util.sendwechat;

public enum WeChatLoginTypeEnum {
    打开登陆二维码登陆("1")
    , 将二维码发送到邮箱登陆("2")
    ;
    
    private String key;
    private WeChatLoginTypeEnum(String key) {
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
    public static WeChatLoginTypeEnum getEnumByKey(String key) {
        WeChatLoginTypeEnum enum1 = null;
        WeChatLoginTypeEnum[] all = WeChatLoginTypeEnum.class.getEnumConstants();
        for(WeChatLoginTypeEnum ee : all) {
            if(ee.getKey().equals(key)) {
                enum1 = ee;
                break;
            }
        }
        return enum1;
    }
}
