package com.aqua.rbaccore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aqua.rbaccore.model.entity.Role;
import com.aqua.rbaccore.service.RoleService;
import com.aqua.rbaccore.mapper.RoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
* @author 70742
* @description 针对表【role(角色信息表)】的数据库操作Service实现
* @createDate 2024-02-05 23:26:04
*/
@Service
@Slf4j
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

}




