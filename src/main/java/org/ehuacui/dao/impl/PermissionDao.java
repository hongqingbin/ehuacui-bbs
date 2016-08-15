package org.ehuacui.dao.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import org.ehuacui.common.Constants.CacheEnum;
import org.ehuacui.dao.IPermissionDao;
import org.ehuacui.module.Permission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class PermissionDao implements IPermissionDao {

    private Permission me = new Permission();

    /**
     * 根据父节点查询权限列表
     *
     * @param pid
     * @return
     */
    @Override
    public List<Permission> findByPid(Integer pid) {
        return me.find("select * from ehuacui_permission where pid = ?", pid);
    }

    /**
     * 查询所有权限（不包括父节点）
     *
     * @return
     */
    @Override
    public List<Permission> findAll() {
        return me.find("select * from ehuacui_permission where pid <> 0");
    }

    /**
     * 查询所有权限，结构是父节点下是子节点的权限列表
     *
     * @return
     */
    @Override
    public List<Permission> findWithChild() {
        List<Permission> permissions = this.findByPid(0);
        for (Permission p : permissions) {
            p.put("childPermissions", this.findByPid(p.getInt("id")));
        }
        return permissions;
    }

    /**
     * 删除父节点下所有的话题
     *
     * @param pid
     */
    @Override
    public void deleteByPid(Integer pid) {
        Db.update("delete from ehuacui_permission where pid = ?", pid);
    }

    /**
     * 根据用户id查询所拥有的权限
     *
     * @param userId
     * @return
     */
    @Override
    public Map<String, String> findPermissions(Integer userId) {
        Map<String, String> map = new HashMap<String, String>();
        if (userId == null) return map;
        Cache cache = Redis.use();
        List<Permission> permissions = cache.get(CacheEnum.userpermissions.name() + userId);
        if (permissions == null) {
            permissions = me.find(
                    "select p.* from ehuacui_user u, ehuacui_role r, ehuacui_permission p, " +
                            "ehuacui_user_role ur, ehuacui_role_permission rp where u.id = ur.uid " +
                            "and r.id = ur.rid and r.id = rp.rid and p.id = rp.pid " +
                            "and u.id = ?",
                    userId
            );
            cache.set(CacheEnum.userpermissions.name() + userId, permissions);
        }
        for (Permission p : permissions) {
            map.put(p.getStr("name"), p.getStr("url"));
        }
        return map;
    }

    @Override
    public Permission findById(Integer id) {
        return me.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        me.deleteById(id);
    }
}
