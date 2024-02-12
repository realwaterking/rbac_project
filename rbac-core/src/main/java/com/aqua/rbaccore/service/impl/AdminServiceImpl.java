package com.aqua.rbaccore.service.impl;

import com.aqua.rbaccommon.common.ErrorCode;
import com.aqua.rbaccore.exception.BusinessException;
import com.aqua.rbaccore.mapper.*;
import com.aqua.rbaccore.model.dto.admin.*;
import com.aqua.rbaccore.model.entity.Permission;
import com.aqua.rbaccore.model.entity.Role;
import com.aqua.rbaccore.model.entity.RolePermission;
import com.aqua.rbaccore.model.entity.UserRole;
import com.aqua.rbaccore.service.AdminService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author water king
 * @time 2024/2/12
 */
@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public Long setUserRole(UserRoleAddRequest userRoleAddRequest) {
        Long result = null;
        try {
            Long userId = userRoleAddRequest.getUserId();
            Long roleId = userRoleAddRequest.getRoleId();
            if (userId <= 0 || roleId <= 0) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "用户id 或 角色id 有误");
            }
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            result = (long) userRoleMapper.insert(userRole);
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Boolean deleteUserRole(UserRoleDeleteRequest userRoleDeleteRequest) {
        try {
            Long userId = userRoleDeleteRequest.getUserId();
            if (userId <= 0) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "用户id有误");
            }
            QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userId", userId);
            userRoleMapper.delete(queryWrapper);
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public Role getUserRole(UserRoleGetRequest userRoleGetRequest) {
        Long roleId = null;
        Long userId = userRoleGetRequest.getUserId();
        if (userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId);
        UserRole userRole = userRoleMapper.selectOne(queryWrapper);
        QueryWrapper<Role> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", userRole.getRoleId());
        Role role = roleMapper.selectOne(queryWrapper1);
        return role;
    }

    @Override
    public List<Long> addRolePermission(RolePermissionAddRequest rolePermissionAddRequest) {
        List<Long> list = null;
        try {
            list = null;
            Long roleId = rolePermissionAddRequest.getRoleId();
            List<Long> permissionIds = rolePermissionAddRequest.getPermissionId();
            if (roleId <= 0 || permissionIds == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
            for (Long permissionId : permissionIds) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setPermissionId(permissionId);
                rolePermission.setRoleId(roleId);
                Long result = (long) rolePermissionMapper.insert(rolePermission);
                list.add(result);
            }
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Boolean deleteRolePermission(RolePermissionDeleteRequest rolePermissionDeleteRequest) {
        try {
            Long roleId = rolePermissionDeleteRequest.getRoleId();
            List<Long> permissionIds = rolePermissionDeleteRequest.getPermissionId();
            if (roleId <= 0 || permissionIds == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
            for (Long permissionId : permissionIds) {
                QueryWrapper<RolePermission> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("roleId",roleId);
                queryWrapper.eq("permissionId", permissionId);
                Long result = (long) rolePermissionMapper.delete(queryWrapper);
            }
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public List<Permission> getRolePermission(RolePermissionGetRequest rolePermissionGetRequest) {
        List<Permission> list = null;

        try {
            list = null;
            Long roleId = rolePermissionGetRequest.getRoleId();
            if (roleId <= 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
            QueryWrapper<RolePermission> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("roleId", roleId);
            List<RolePermission> rolePermissions = rolePermissionMapper.selectList(queryWrapper);
            QueryWrapper<Permission> wrapper = new QueryWrapper<>();
            for (RolePermission rolePermission : rolePermissions) {
                Long permissionId = rolePermission.getPermissionId();
                wrapper.eq("id", permissionId);
                Permission permission = permissionMapper.selectOne(wrapper);
                list.add(permission);
            }
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }

        return list;
    }
}
