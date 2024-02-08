package com.aqua.rbaccore.service;

import com.aqua.rbaccore.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import javax.servlet.http.HttpServletRequest;

/**
* @author 70742
* @description 针对表【user(用户信息表)】的数据库操作Service
* @createDate 2024-02-05 23:26:10
*/
public interface UserService extends IService<User> {

    /**
     * 用户登录
     * @param userAccount
     * @param userPassword
     * @param request
     * @return
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户注销
     * @param request
     * @return
     */
    Boolean userLogout(HttpServletRequest request);

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 获取当前登录用户所拥有角色的权限
     * @param request
     * @return
     */
    String getLoginUserPermission(HttpServletRequest request);

    /**
     * 用户注册
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @return id
     */
    long userRegister(String username, String userAccount, String userPassword, String checkPassword
            , String phoneNumber);

}
