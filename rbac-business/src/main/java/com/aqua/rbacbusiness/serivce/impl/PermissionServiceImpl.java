package com.aqua.rbacbusiness.serivce.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aqua.rbacbusiness.model.entity.Permission;
import com.aqua.rbacbusiness.serivce.PermissionService;
import com.aqua.rbacbusiness.mapper.PermissionMapper;
import org.springframework.stereotype.Service;

/**
* @author 70742
* @description 针对表【permission(权限信息表)】的数据库操作Service实现
* @createDate 2024-02-12 15:57:04
*/
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission>
    implements PermissionService{

}




