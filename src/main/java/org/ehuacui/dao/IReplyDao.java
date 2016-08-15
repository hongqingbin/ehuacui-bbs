package org.ehuacui.dao;

import com.jfinal.plugin.activerecord.Page;
import org.ehuacui.module.Reply;

import java.util.List;

/**
 * Created by jianwei.zhou on 2016/8/15.
 */
public interface IReplyDao {

    /**
     * 根据话题id查询回复数量
     * @param tid
     * @return
     */
    int findCountByTid(Integer tid);

    /**
     * 分页查询全部话题
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Page<Reply> findAll(Integer pageNumber, Integer pageSize);

    /**
     * 分页查询话题的回复列表
     * @param pageNumber
     * @param pageSize
     * @param tid
     * @return
     */
    Page<Reply> page(Integer pageNumber, Integer pageSize, Integer tid);

    /**
     * 根据话题id查询回复列表
     * @param topicId
     * @return
     */
    List<Reply> findByTopicId(Integer topicId);

    /**
     * 分页查询回复列表
     * @param pageNumber
     * @param pageSize
     * @param author
     * @return
     */
    Page<Reply> pageByAuthor(Integer pageNumber, Integer pageSize, String author);

    /**
     * 删除话题回复内容
     * @param tid
     */
    void deleteByTid(Integer tid);

    void deleteById(Integer id);

    Reply findById(Integer id);
}
