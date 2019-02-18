package com.my.timertask.entity.po;

public class TimedSysGroupPO {
    private Integer id;
    private String group_code;
    private String group_val;
    private String group_desc;
    private String create_date;
    /**  */
    public Integer getId() {
        return id;
    }
    /**  */
    public String getGroup_code() {
        return group_code;
    }
    /**  */
    public String getGroup_val() {
        return group_val;
    }
    /**  */
    public String getGroup_desc() {
        return group_desc;
    }
    /**  */
    public String getCreate_date() {
        return create_date;
    }
    /**  */
    public void setId(Integer id) {
        this.id = id;
    }
    /**  */
    public void setGroup_code(String group_code) {
        this.group_code = group_code;
    }
    /**  */
    public void setGroup_val(String group_val) {
        this.group_val = group_val;
    }
    /**  */
    public void setGroup_desc(String group_desc) {
        this.group_desc = group_desc;
    }
    /**  */
    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }
    /** <blockquote>
    *
    * @return 
    */
    @Override
    public String toString() {
        return "TimedSysGroup [id=" + id + ", group_code=" + group_code + ", group_val=" + group_val + ", group_desc="
                + group_desc + ", create_date=" + create_date + "]";
    }
    
}
