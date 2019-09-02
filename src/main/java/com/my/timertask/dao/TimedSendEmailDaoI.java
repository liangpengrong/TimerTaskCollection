package com.my.timertask.dao;

import com.my.timertask.entity.po.TimedSendEmailPO;

import java.util.List;

/**
 * @author liangpr
 * @date 2019/09/02
 */
public interface TimedSendEmailDaoI {
    /** <blockquote>
     * 插入一条数据 可以获得插入条数 主键已经更新到传入的实体类中
     * @param po
     * @return
     */
    int addOneTimedSendEmail(TimedSendEmailPO po);

    /** <blockquote>
     * 查询数据并返回list集合 
     * @param po
     * @return
     */
    List<TimedSendEmailPO> listTimedSendEmail(TimedSendEmailPO po);

    /** <blockquote>
     * 更新一条数据 不更新null
     * @param po
     * @return
     */
    int updateOneTimedSendEmail(TimedSendEmailPO po);
    /** <blockquote>
     * 更新一条数据  更新null
     * @param po
     * @return
     */
    int updateOneTimedSendEmailHaveNull(TimedSendEmailPO po);
}
