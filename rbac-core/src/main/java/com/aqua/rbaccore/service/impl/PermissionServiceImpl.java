package com.aqua.rbaccore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aqua.rbaccore.model.entity.Permission;
import com.aqua.rbaccore.service.PermissionService;
import com.aqua.rbaccore.mapper.PermissionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
* @author 70742
* @description 针对表【permission(权限信息表)】的数据库操作Service实现
* @createDate 2024-02-05 23:26:00
*/
@Service
@Slf4j
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission>
    implements PermissionService{

}




