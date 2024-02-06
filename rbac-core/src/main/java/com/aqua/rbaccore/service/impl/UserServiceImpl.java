package com.aqua.rbaccore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aqua.rbaccore.model.entity.User;
import com.aqua.rbaccore.service.UserService;
import com.aqua.rbaccore.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
* @author 70742
* @description 针对表【user(用户信息表)】的数据库操作Service实现
* @createDate 2024-02-05 23:26:10
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Override
    public User getLoginUser(HttpServletRequest request) {
        return null;
    }

    @Override
    public String getUserRolePermission(HttpServletRequest request) {
        return null;
    }
}




