package com.aqua.rbaccore.service;

import com.aqua.rbaccore.model.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 70742
* @description 针对表【permission(权限信息表)】的数据库操作Service
* @createDate 2024-02-05 23:26:00
*/
public interface PermissionService extends IService<Permission> {

    /**
     * 加载权限到权限表中
     * @return
     */
    String load();

    /**
     * 根据角色id获取该角色所拥有的权限
     * @param roleId
     * @return
     */
    List<String> getPermissionList(Long roleId);
}
